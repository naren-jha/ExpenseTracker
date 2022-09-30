package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepository {

    @Autowired
    private Database db;

    public void addGroup(Group group) {
        db.addGroup(group);
    }

    public Optional<Group> getGroup(Long id) {
        return Optional.ofNullable(db.getGroups().get(id));
    }

    public List<Group> getGroups() {
        return new ArrayList<>(db.getGroups().values());
    }

    public void addGroupUser(GroupUser groupUser) {
        db.addGroupUser(groupUser);
    }

    public void removeGroupUser(GroupUser groupUser) {
        db.removeGroupUser(groupUser);
    }
}
