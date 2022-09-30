package com.phonepe.expensetracker.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void shouldTestThatUserIsCreatedSuccessfully() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan9@gmail.com")
                .mobile("9999988889")
                .build();

        // when
        ResponseEntity<String> response =  userController.createUser(user);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(USER_CREATED_SUCCESS_MSG);
    }

    @Test
    void shouldTestThatUserCreationHasFailed() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan10@gmail.com")
                .mobile("9999988810")
                .build();

        // when
        ResponseEntity<String> response =  userController.createUser(user);
        response =  userController.createUser(user);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains(COULD_NOT_CREATE_USER_ERR_MSG);
    }

    @Test
    void shouldTestThatUserByIdIsFetchedSuccessfully() {
        // given
        User actual = User
                .builder()
                .name("rohan")
                .email("rohan11@gmail.com")
                .mobile("9999988811")
                .build();
        userController.createUser(actual);

        // when
        ResponseEntity<User> response =  userController.getUser(actual.getId());
        User expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatUserByNullIdIsNotFound() {
        // given
        Long userId = null;

        // when
        ResponseEntity<User> response =  userController.getUser(userId);
        User expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void shouldTestThatUserByInvalidIdIsNotFound() {
        // given
        Long userId = 1000L;

        // when
        ResponseEntity<User> response =  userController.getUser(userId);
        User expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void shouldTestThatListOfAllUsersIsFetchedSuccessfully() {
        //given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan12@gmail.com")
                .mobile("9999988812")
                .build();

        // when
        userController.createUser(user);

        // then
        ResponseEntity<List<User>> response = userController.getUsers();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(1);
    }
}