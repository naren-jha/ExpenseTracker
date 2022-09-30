package com.phonepe.expensetracker.group;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.*;

@Slf4j
@RestController
@RequestMapping("api/v1/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping()
    public ResponseEntity<String> createGroup(@RequestBody Group group) {
        try {
            groupService.addGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(GROUP_CREATED_SUCCESS_MSG);
        }
        catch (Exception e) {
            log.error("An exception occurred while creating group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(COULD_NOT_CREATE_GROUP_ERR_MSG + e.getMessage());
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable("groupId") Long groupId) {
        try {
            Group group = groupService.getGroup(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(group);
        }
        catch (IllegalArgumentException e) {
            log.error("Group not found for null id", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (IllegalStateException e) {
            log.error("Group not found for invalid id", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
            log.error("An exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Group>> getGroups() {
        try {
            List<Group> groups = groupService.getGroups();
            return ResponseEntity.status(HttpStatus.OK).body(groups);
        }
        catch (Exception e) {
            log.error("An exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUserToGroup(@RequestBody GroupUserDTO groupUserDTO) {
        try {
            groupService.addUserToGroup(groupUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(USER_ADDED_TO_GROUP_SUCCESS_MSG);
        }
        catch (Exception e) {
            log.error("An exception occurred while adding user to group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(COULD_NOT_ADD_USER_TO_GROUP_ERR_MSG + e.getMessage());
        }
    }

    @DeleteMapping("/{groupId}/user/{userId}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        try {
            groupService.removeUserFromGroup(groupId, userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(USER_REMOVED_FROM_GROUP_SUCCESS_MSG);
        }
        catch (IllegalStateException e) {
            log.error("User was not removed from group.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            log.error("User was not removed from group.", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            log.error("An exception occurred while removing user from group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(COULD_NOT_REMOVE_USER_TO_GROUP_ERR_MSG + e.getMessage());
        }
    }
}
