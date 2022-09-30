package com.phonepe.expensetracker.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldTestThatUserIsAddedSuccessfully() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan@gmail.com")
                .mobile("9999988880")
                .build();

        // when
        userRepository.addUser(user);

        // then
        boolean found = userRepository.getUserByEmail("rohan@gmail.com").isPresent();
        assertThat(found).isTrue();
    }

    @Test
    void shouldTestThatUserIsNotFoundForInvalidId() {
        // given
        Long userId = 1000L;

        // when
        boolean found = userRepository.getUser(userId).isPresent();

        // then
        assertThat(found).isFalse();
    }

    @Test
    void shouldTestThatListOfAllUsersAreFetchedSuccessfully() {
        //given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan1@gmail.com")
                .mobile("9999988881")
                .build();

        // when
        userRepository.addUser(user);

        // then
        List<User> users = userRepository.getUsers();
        assertThat(users.size()).isGreaterThanOrEqualTo(1);
    }
}