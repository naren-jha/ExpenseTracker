package com.phonepe.expensetracker;

import com.phonepe.expensetracker.distribution.DistributionStrategy;
import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupUser;
import com.phonepe.expensetracker.user.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class Database {
    Map<Long, User> users = new HashMap<>();
    Map<Long, Group> groups = new HashMap<>();
    Set<GroupUser> groupUsers = new HashSet<>();
    SortedMap<Integer, DistributionStrategy> distributionStrategies = new TreeMap<>();

    public void addUser(User user) {
        user.setId(Long.valueOf(users.size()));
        users.put(user.getId(), user);
    }

    public void addGroup(Group group) {
        group.setId(Long.valueOf(groups.size()));
        groups.put(group.getId(), group);
    }

    public void addGroupUser(GroupUser groupUser) {
        groupUser.setId(Long.valueOf(groupUsers.size()));
        groupUsers.add(groupUser);
    }

    public void removeGroupUser(GroupUser groupUser) {
        groupUsers.remove(groupUser);
    }

    public void addDistributionStrategies(List<DistributionStrategy> distributionStrategyList) {
        distributionStrategyList.forEach(distributionStrategy -> {
            distributionStrategy.setId(Long.valueOf(distributionStrategies.size()));
            distributionStrategies.put(distributionStrategy.getOrder(), distributionStrategy);
        });
    }

}
