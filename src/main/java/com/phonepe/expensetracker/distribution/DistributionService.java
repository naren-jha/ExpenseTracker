package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.distribution.strategy.Distribution;
import com.phonepe.expensetracker.distribution.strategy.DistributionFactory;
import com.phonepe.expensetracker.distribution.strategy.DistributionType;
import com.phonepe.expensetracker.exception.NotFoundDistributionStrategy;
import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupService;
import com.phonepe.expensetracker.group.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DistributionService {

    @Autowired
    private DistributionRepository distributionRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private DistributionFactory distributionFactory;


    public DistributionResponse distributeExpenses(Long groupId) {
        Group group = groupService.getGroup(groupId);
        List<DistributeExpenseOutput> distributionResponse = new ArrayList<>();

        SortedMap<Integer, DistributionStrategy> distributionStrategies = distributionRepository.getDistributionStrategies();
        for (Map.Entry<Integer, DistributionStrategy> entry : distributionStrategies.entrySet()) {
            DistributionStrategy distributionStrategy = entry.getValue();
            DistributionType distributionType = distributionStrategy.getDistributionType();

            List<GroupUser> qualifiedMembers = new ArrayList<>();
            for (GroupUser groupUser : group.getMembers()) {
                if (groupUser.getDistributionType().equals(distributionType)) {
                    qualifiedMembers.add(groupUser);
                }
            }

            Distribution distribution = distributionFactory.getDistribution(distributionType);
            if (Objects.isNull(distribution)) {
                throw new NotFoundDistributionStrategy("This distribution strategy is not supported");
            }

            DistributeExpenseInput distributeExpenseInput = DistributeExpenseInput.builder().build();
            List<DistributeExpenseOutput> distributeExpenseOutputs = distribution.distributeExpenses(distributeExpenseInput);
            distributionResponse.addAll(distributeExpenseOutputs);
        }

        DistributionResponse response = DistributionResponse
                .builder()
                .message("success")
                .distributeExpenseOutputs(distributionResponse)
                .build();
        return response;
    }
}
