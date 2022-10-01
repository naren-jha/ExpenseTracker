package com.phonepe.expensetracker.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.COULD_NOT_CREATE_USER_ERR_MSG;
import static com.phonepe.expensetracker.common.Constants.USER_CREATED_SUCCESS_MSG;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            user.setRegistered(true);
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(USER_CREATED_SUCCESS_MSG);
        }
        catch (Exception e) {
            log.error("An exception occurred while creating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(COULD_NOT_CREATE_USER_ERR_MSG + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        try {
            User user = userService.getUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (IllegalArgumentException e) {
            log.error("User not found", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (IllegalStateException e) {
            log.error("User not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception e) {
            log.error("An exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = userService.getUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
        catch (Exception e) {
            log.error("An exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
