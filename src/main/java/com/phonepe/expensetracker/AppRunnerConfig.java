package com.phonepe.expensetracker;

import com.phonepe.expensetracker.activity.Activity;
import com.phonepe.expensetracker.activity.ActivityService;
import com.phonepe.expensetracker.distribution.DistributionService;
import com.phonepe.expensetracker.distribution.DistributionStrategy;
import com.phonepe.expensetracker.distribution.dto.DistributionResponse;
import com.phonepe.expensetracker.distribution.factory.DistributionType;
import com.phonepe.expensetracker.group.*;
import com.phonepe.expensetracker.user.User;
import com.phonepe.expensetracker.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class AppRunnerConfig {

    private Database db;
    private UserService userService;
    private GroupService groupService;
    private ActivityService activityService;
    private DistributionService distributionService;

    @Bean
    CommandLineRunner commandLineRunner() {
        DistributionStrategy equalDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.EQUAL)
                .executionOrder(0)
                .build();
        DistributionStrategy exactDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.EXACT)
                .executionOrder(0)
                .build();
        DistributionStrategy percentageDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.PERCENTAGE)
                .executionOrder(0)
                .build();

        User user1 = User
                .builder()
                .name("Joe Goldberg")
                .email("joeg1@gmail.com")
                .mobile("9999988881")
                .registered(true)
                .build();
        User user2 = User
                .builder()
                .name("Joe Goldberg 2")
                .email("joeg2@gmail.com")
                .mobile("9999988882")
                .registered(true)
                .build();
        User user3 = User
                .builder()
                .name("Joe Goldberg 3")
                .email("joeg3@gmail.com")
                .mobile("9999988883")
                .registered(true)
                .build();
        User user4 = User
                .builder()
                .name("Joe Goldberg 4")
                .email("joeg4@gmail.com")
                .mobile("9999988884")
                .registered(true)
                .build();
        User user5 = User
                .builder()
                .name("Joe Goldberg 5")
                .email("joeg5@gmail.com")
                .mobile("9999988885")
                .registered(true)
                .build();

        Group group1 = Group
                .builder()
                .name("Household shopping")
                .description("Household shopping expenses")
                .createdBy(0L)
                .build();

        GroupUserDTO groupUserDTO2 = GroupUserDTO
                .builder()
                .groupId(0L)
                .userMobile(user2.getMobile())
                .authority(UserGroupRole.USER)
                .build();
        GroupUserDTO groupUserDTO3 = GroupUserDTO
                .builder()
                .groupId(0L)
                .userMobile(user3.getMobile())
                .authority(UserGroupRole.USER)
                .build();
        GroupUserDTO groupUserDTO4 = GroupUserDTO
                .builder()
                .groupId(0L)
                .userMobile(user4.getMobile())
                .authority(UserGroupRole.USER)
                .build();
        GroupUserDTO groupUserDTO5 = GroupUserDTO
                .builder()
                .groupId(0L)
                .userMobile(user5.getMobile())
                .authority(UserGroupRole.USER)
                .build();

        Activity activity1 = Activity
                .builder()
                .name("Grocery shopping")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(0L)
                .amount(5.0)
                .build();
        Activity activity2 = Activity
                .builder()
                .name("Grocery shopping 2")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(1L)
                .amount(10.0)
                .build();
        Activity activity3 = Activity
                .builder()
                .name("Grocery shopping 3")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(2L)
                .amount(15.0)
                .build();
        Activity activity4 = Activity
                .builder()
                .name("Grocery shopping 4")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(2L)
                .amount(5.0)
                .build();
        Activity activity5 = Activity
                .builder()
                .name("Grocery shopping 5")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(3L)
                .amount(15.0)
                .build();
        Activity activity6 = Activity
                .builder()
                .name("Grocery shopping 6")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(3L)
                .amount(5.0)
                .build();
        Activity activity7 = Activity
                .builder()
                .name("Grocery shopping 7")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(3L)
                .amount(5.0)
                .build();
        Activity activity8 = Activity
                .builder()
                .name("Grocery shopping 8")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(4L)
                .amount(25.0)
                .build();
        Activity activity9 = Activity
                .builder()
                .name("Grocery shopping 9")
                .description("veggies, dairy and other kitchen items")
                .groupId(0L)
                .createdBy(0L)
                .paidBy(4L)
                .amount(15.0)
                .build();

        return args -> {
            // add available distribution methods
            db.addDistributionStrategies(List.of(equalDS, exactDS, percentageDS));

            // add 5 users
            userService.addUser(user1);
            userService.addUser(user2);
            userService.addUser(user3);
            userService.addUser(user4);
            userService.addUser(user5);

            // add a group
            groupService.addGroup(group1);

            // add remaining 4 users to group1
            groupService.addUserToGroup(group1.getId(), groupUserDTO2);
            groupService.addUserToGroup(group1.getId(), groupUserDTO3);
            groupService.addUserToGroup(group1.getId(), groupUserDTO4);
            groupService.addUserToGroup(group1.getId(), groupUserDTO5);

            /*
            Add following activities in the group
            user1 spends Rs. 5 in total
            user2 spends Rs. 10 in total
            user3 spends Rs. 20 in total
            user4 spends Rs. 25 in total
            user5 spends Rs. 40 in total
             */
            activityService.addActivity(group1.getId(), activity1);
            activityService.addActivity(group1.getId(), activity2);
            activityService.addActivity(group1.getId(), activity3);
            activityService.addActivity(group1.getId(), activity4);
            activityService.addActivity(group1.getId(), activity5);
            activityService.addActivity(group1.getId(), activity6);
            activityService.addActivity(group1.getId(), activity7);
            activityService.addActivity(group1.getId(), activity8);
            activityService.addActivity(group1.getId(), activity9);

            DistributionResponse response = distributionService.distributeExpenses(group1.getId());
            response.getDistributions().forEach(distributionTransaction -> {
                log.info("User {} should pay amount {} to user {}",
                        distributionTransaction.getFromUser(), distributionTransaction.getAmount(), distributionTransaction.getToUser());
            });
        };
    }
}
