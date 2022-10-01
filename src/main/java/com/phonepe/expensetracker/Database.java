package com.phonepe.expensetracker;

import com.phonepe.expensetracker.activity.Activity;
import com.phonepe.expensetracker.distribution.DistributionStrategy;
import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupUser;
import com.phonepe.expensetracker.user.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Data
public class Database {
    Map<Long, User> users = new HashMap<>();
    Map<Long, Group> groups = new HashMap<>();
    Set<GroupUser> groupUsers = new HashSet<>();
    SortedMap<Integer, DistributionStrategy> distributionStrategies = new TreeMap<>();
    Map<Long, Activity> activities = new HashMap<>();

    public void addUser(User user) {
        user.setId(Long.valueOf(users.size()));
        user.setCreateAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        users.put(user.getId(), user);
    }

    public void addGroup(Group group) {
        group.setId(Long.valueOf(groups.size()));
        group.setCreateAt(LocalDateTime.now());
        group.setUpdatedAt(LocalDateTime.now());

        groups.put(group.getId(), group);
    }

    public void addGroupUser(GroupUser groupUser) {
        groupUser.setId(Long.valueOf(groupUsers.size()));
        groupUser.setCreateAt(LocalDateTime.now());
        groupUser.setUpdatedAt(LocalDateTime.now());

        groupUsers.add(groupUser);
    }

    public void removeGroupUser(GroupUser groupUser) {
        groupUsers.remove(groupUser);
    }

    public void addDistributionStrategies(List<DistributionStrategy> distributionStrategyList) {
        distributionStrategyList.forEach(distributionStrategy -> {
            distributionStrategy.setId(Long.valueOf(distributionStrategies.size()));
            distributionStrategy.setCreateAt(LocalDateTime.now());
            distributionStrategy.setUpdatedAt(LocalDateTime.now());

            distributionStrategies.put(distributionStrategy.getOrder(), distributionStrategy);
        });
    }

    public void addActivity(Activity activity) {
        activity.setId(Long.valueOf(activities.size()));
        activity.setCreateAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());

        activities.put(activity.getId(), activity);
    }

}
