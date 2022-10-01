package com.phonepe.expensetracker.activity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.ACTIVITY_CREATED_SUCCESS_MSG;
import static com.phonepe.expensetracker.common.Constants.COULD_NOT_CREATE_ACTIVITY_ERR_MSG;

@Slf4j
@RestController
@RequestMapping("api/v1/group/{groupId}/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<String> addActivityToGroup(@PathVariable("groupId") Long groupId, @RequestBody Activity activity) {
        try {
            activityService.addActivity(groupId, activity);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ACTIVITY_CREATED_SUCCESS_MSG);
        }
        catch (Exception e) {
            log.error("An exception occurred while adding activity", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(COULD_NOT_CREATE_ACTIVITY_ERR_MSG + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<Activity>> getActivitiesByGroup(@PathVariable("groupId") Long groupId) {
        try {
            List<Activity> groupActivities = activityService.getActivitiesByGroup(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(groupActivities);
        }
        catch (Exception e) {
            log.error("An exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
