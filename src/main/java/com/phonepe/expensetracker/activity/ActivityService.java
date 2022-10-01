package com.phonepe.expensetracker.activity;

import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.phonepe.expensetracker.common.Constants.*;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private GroupService groupService;

    public void addActivity(Long groupId, Activity activity) {
        validateObject(activity);
        activity.setGroupId(groupId);
        activityRepository.addActivity(activity);

        Group group = groupService.getGroup(groupId);
        if (Objects.isNull(group)) {
            throw new IllegalStateException(GROUP_DOES_NOT_EXIST_ERR_MSG);
        }
        group.getActivities().add(activity);
    }

    public Activity getActivity(Long activityId) {
        if (Objects.isNull(activityId)) {
            throw new IllegalArgumentException(ACTIVITY_ID_NULL_ERR_MSG);
        }

        return activityRepository.getActivity(activityId)
                .orElseThrow(() -> new IllegalStateException(ACTIVITY_DOES_NOT_EXIST_ERR_MSG + " activity id = " + activityId));
    }

    public List<Activity> getActivities() {
        return activityRepository.getActivities();
    }

    public List<Activity> getActivitiesByGroup(Long groupId) {
        if (Objects.isNull(groupId)) {
            throw new IllegalArgumentException(GROUP_ID_NULL_ERR_MSG);
        }

        return activityRepository.getActivitiesByGroup(groupId);
    }

    private void validateObject(Activity activity) {
        if (Strings.isBlank(activity.getName())) {
            throw new IllegalArgumentException(ACTIVITY_NAME_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getGroupId())) {
            throw new IllegalArgumentException(ACTIVITY_GROUP_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getCreatedBy())) {
            throw new IllegalArgumentException(ACTIVITY_CREATED_BY_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getPaidBy())) {
            throw new IllegalArgumentException(ACTIVITY_PAID_BY_CANNOT_BE_NULL_ERR_MSG);
        }
        if (activity.getAmount() <= 0) {
            throw new IllegalArgumentException(ACTIVITY_AMOUNT_CANNOT_BE_ZERO_NEGATIVE_ERR_MSG);
        }
    }

}
