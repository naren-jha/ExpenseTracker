package com.phonepe.expensetracker.activity;

import com.phonepe.expensetracker.group.Group;
import com.phonepe.expensetracker.group.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.phonepe.expensetracker.common.Constants.*;

@Slf4j
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
        log.info("User {} spent amount {} in group {}", activity.getPaidBy(), activity.getAmount(), group.getId());
    }

    public Activity getActivity(Long activityId) {
        if (Objects.isNull(activityId)) {
            log.error("Can not get activity. Activity id is null!");
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
            log.error("Can not get activity. Group id is null!");
            throw new IllegalArgumentException(GROUP_ID_NULL_ERR_MSG);
        }

        return activityRepository.getActivitiesByGroup(groupId);
    }

    private void validateObject(Activity activity) {
        if (Strings.isBlank(activity.getName())) {
            log.error("Activity name is null!");
            throw new IllegalArgumentException(ACTIVITY_NAME_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getGroupId())) {
            log.error("Activity group is null!");
            throw new IllegalArgumentException(ACTIVITY_GROUP_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getCreatedBy())) {
            log.error("Activity createdBy is null!");
            throw new IllegalArgumentException(ACTIVITY_CREATED_BY_CANNOT_BE_NULL_ERR_MSG);
        }
        if (Objects.isNull(activity.getPaidBy())) {
            log.error("Activity paidBy is null!");
            throw new IllegalArgumentException(ACTIVITY_PAID_BY_CANNOT_BE_NULL_ERR_MSG);
        }
        if (activity.getAmount() <= 0) {
            log.error("Activity amount is <= 0!");
            throw new IllegalArgumentException(ACTIVITY_AMOUNT_CANNOT_BE_ZERO_NEGATIVE_ERR_MSG);
        }
    }

}
