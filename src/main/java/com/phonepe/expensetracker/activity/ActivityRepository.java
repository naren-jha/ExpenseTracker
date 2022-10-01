package com.phonepe.expensetracker.activity;

import com.phonepe.expensetracker.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ActivityRepository {

    @Autowired
    private Database db;

    public void addActivity(Activity activity) {
        db.addActivity(activity);
    }

    public Optional<Activity> getActivity(Long activityId) {
        return Optional.ofNullable(db.getActivities().get(activityId));
    }

    public List<Activity> getActivities() {
        return new ArrayList<>(db.getActivities().values());
    }

    public List<Activity> getActivitiesByGroup(Long groupId) {
        return db.getActivities().values().stream()
                .filter(activity -> activity.getGroupId() == groupId)
                .collect(Collectors.toList());
    }
}
