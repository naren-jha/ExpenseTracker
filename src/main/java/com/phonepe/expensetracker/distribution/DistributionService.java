package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.activity.Activity;
import com.phonepe.expensetracker.activity.ActivityService;
import com.phonepe.expensetracker.distribution.dto.*;
import com.phonepe.expensetracker.distribution.factory.Distribution;
import com.phonepe.expensetracker.distribution.factory.DistributionFactory;
import com.phonepe.expensetracker.distribution.factory.DistributionType;
import com.phonepe.expensetracker.exception.NotFoundDistributionStrategy;
import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupService;
import com.phonepe.expensetracker.group.GroupUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.phonepe.expensetracker.common.Constants.GROUP_DOES_NOT_EXIST_ERR_MSG;

@Slf4j
@Service
public class DistributionService {

    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private DistributionFactory distributionFactory;

    @Autowired
    private ActivityService activityService;

    public List<DistributionStrategy> getDistributionStrategies() {
        return distributionRepository.getDistributionStrategies();
    }

    public DistributionResponse distributeExpenses(Long groupId) {
        Group group = groupService.getGroup(groupId);
        if (Objects.isNull(group)) {
            throw new IllegalArgumentException(GROUP_DOES_NOT_EXIST_ERR_MSG);
        }
        log.debug("{} members found in the group in total", group.getMembers().size());

        List<Activity> groupActivities = activityService.getActivitiesByGroup(groupId);
        Double amountToBeDivided = 0.0;
        Map<Long, UserSpend> userSpendMap = new HashMap<>();
        group.getMembers().forEach(groupUser -> {
            userSpendMap.put(groupUser.getUserId(), UserSpend.builder().user(groupUser.getUserId()).spend(0.0).build());
        });
        for (Activity activity : groupActivities) {
            amountToBeDivided += activity.getAmount();

            UserSpend userSpend = userSpendMap.get(activity.getPaidBy());
            userSpend.setSpend(userSpend.getSpend() + activity.getAmount());
        }
        log.debug("{} activities found in the group with an amount of sum {}", groupActivities.size(), amountToBeDivided);

        List<DistributionTransaction> distributionResponse = new ArrayList<>();

        log.debug("Fetching available distribution strategies and sorting them by execution order");
        List<DistributionStrategy> distributionStrategies = getDistributionStrategies();
        Collections.sort(distributionStrategies, (a, b) -> Integer.compare(a.getExecutionOrder(), b.getExecutionOrder()));

        log.debug("executing distribution strategies in order");
        for (DistributionStrategy distributionStrategy : distributionStrategies) {

            DistributionType distributionType = distributionStrategy.getDistributionType();
            log.debug("Executing for distribution type {}", distributionType);

            log.debug("Evaluating members qualified for distribution type {}", distributionType);
            List<GroupUser> qualifiedMembers = new ArrayList<>();
            List<UserSpend> userSpendsForDistribution = new ArrayList<>();
            for (GroupUser groupUser : group.getMembers()) {
                if (groupUser.getDistributionType().equals(distributionType)) {
                    qualifiedMembers.add(groupUser);
                    userSpendsForDistribution.add(userSpendMap.get(groupUser.getUserId()));
                }
            }
            log.debug("{} qualified members found for distribution type {}", qualifiedMembers.size(), distributionType);

            if (qualifiedMembers.size() == 0) {
                log.debug("Skipping execution for distribution type {} since no members found for it", distributionType);
                continue;
            }

            Distribution distribution = distributionFactory.getDistribution(distributionType);
            if (Objects.isNull(distribution)) {
                log.error("distribution object not found for distributionType {}", distributionType);
                throw new NotFoundDistributionStrategy("This distribution strategy is not supported");
            }

            log.debug("Executing distribution for distribution type {}", distributionType);
            DistributeExpenseInput distributeExpenseInput = DistributeExpenseInput
                    .builder()
                    .totalAmount(amountToBeDivided)
                    .users(qualifiedMembers)
                    .userSpends(userSpendsForDistribution)
                    .build();
            DistributionTypeResponse distributionTypeResponse = distribution.distributeExpenses(distributeExpenseInput);
            log.debug("Executed distribution for distribution type {}", distributionType);

            distributionResponse.addAll(distributionTypeResponse.getDistributionTransactions());

            amountToBeDivided -= distributionTypeResponse.getAmountDivided();
            log.debug("{} amount divided with {} distribution, leftover amount is {}",
                    distributionTypeResponse.getAmountDivided(), distributionType, amountToBeDivided);

            if (amountToBeDivided < 0) {
                log.debug("Invalid data for distribution. Total amount should not be less than distribution");
                throw new IllegalArgumentException("Invalid data for distribution. Total amount is less than distribution");
            }
        }

        DistributionResponse response = DistributionResponse
                .builder()
                .message("success")
                .distributions(distributionResponse)
                .build();
        log.info("distribution = {}", response);
        return response;
    }
}
