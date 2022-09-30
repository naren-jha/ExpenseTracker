package com.phonepe.expensetracker.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void shouldTestThatUserIsAddedSuccessfully() {
        // given
        User actual = User
                .builder()
                .name("rohan")
                .email("rohan2@gmail.com")
                .mobile("9999988882")
                .build();

        // when
        userService.addUser(actual);

        // then
        User expected = userService.getUser(actual.getId());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToNullEmail() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email(null)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMAIL_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToEmptyEmail() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMAIL_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToInvalidEmail() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan@gmail")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_EMAIL_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToDuplicateEmail() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan3@gmail.com")
                .mobile("9999988883")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DUPLICATE_EMAIL_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToNullMobile() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan4@gmail.com")
                .mobile(null)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MOBILE_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToEmptyMobile() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan5@gmail.com")
                .mobile("")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MOBILE_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotAddedDueToDuplicateMobile() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan6@gmail.com")
                .mobile("9999988886")
                .build();
        User user2 = User
                .builder()
                .name("rohan")
                .email("rohan7@gmail.com")
                .mobile("9999988886")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.addUser(user);
            userService.addUser(user2);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DUPLICATE_MOBILE_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotFetchedForNullId() {
        // given
        Long userId = null;

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.getUser(userId);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(USER_ID_NULL_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsNotFetchedForInvalidUserId() {
        // given
        Long userId = 1000L;

        // when
        Throwable thrown = catchThrowable(() -> {
            userService.getUser(userId);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(USER_DOES_NOT_EXIST_ERR_MSG);
    }

    @Test
    void shouldTestThatListOfAllUsersIsFetchedSuccessfully() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan8@gmail.com")
                .mobile("9999988888")
                .build();

        // when
        userService.addUser(user);

        // then
        List<User> users = userService.getUsers();
        assertThat(users.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void shouldTestThatUserIsFetchedByEmailSuccessfully() {
        // given
        User actual = User
                .builder()
                .name("rohan")
                .email("rohan13@gmail.com")
                .mobile("9999988813")
                .build();

        // when
        userService.addUser(actual);

        // then
        User expected = userService.getUserByEmail("rohan13@gmail.com");
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatUserIsNotFetchedDueToInvalidEmail() {
        // given
        String email = "";

        Throwable thrown = catchThrowable(() -> {
            userService.getUserByEmail(email);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(EMAIL_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatUserIsFetchedByMobileSuccessfully() {
        // given
        User actual = User
                .builder()
                .name("rohan")
                .email("rohan14@gmail.com")
                .mobile("9999988814")
                .build();

        // when
        userService.addUser(actual);

        // then
        User expected = userService.getUserByMobile("9999988814");
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatUserIsNotFetchedDueToInvalidMobile() {
        // given
        String mobile = "";

        Throwable thrown = catchThrowable(() -> {
            userService.getUserByMobile(mobile);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(MOBILE_NULL_OR_EMPTY_ERR_MSG);
    }
}