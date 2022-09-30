package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.user.User;
import com.phonepe.expensetracker.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.COULD_NOT_CREATE_GROUP_ERR_MSG;
import static com.phonepe.expensetracker.common.Constants.GROUP_CREATED_SUCCESS_MSG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GroupControllerTest {

    @Autowired
    private GroupController groupController;

    @Autowired
    private UserService userService;

    @Test
    void shouldTestThatGroupIsCreatedSuccessfully() {
        // given
        User user = User
                .builder()
                .name("rohan")
                .email("rohan15@gmail.com")
                .mobile("9999988815")
                .build();
        userService.addUser(user);

        Group group = Group
                .builder()
                .name("Trip to Himalaya")
                .description("trip to Himalaya with colleagues")
                .createdBy(user.getId())
                .build();

        // when
        ResponseEntity<String> response =  groupController.createGroup(group);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(GROUP_CREATED_SUCCESS_MSG);
    }

    @Test
    void shouldTestThatGroupCreationHasFailed() {
        // given
        Group group = Group
                .builder()
                .name("")
                .description("")
                .createdBy(0L)
                .build();

        // when
        ResponseEntity<String> response =  groupController.createGroup(group);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains(COULD_NOT_CREATE_GROUP_ERR_MSG);
    }

    @Test
    void shouldTestThatGroupByIdIsFetchedSuccessfully() {
        // given
        Group actual = Group
                .builder()
                .name("Household shopping")
                .description("Household shopping expenses")
                .createdBy(0L)
                .build();
        groupController.createGroup(actual);

        // when
        ResponseEntity<Group> response =  groupController.getGroup(actual.getId());
        Group expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatGroupByNullIdIsNotFound() {
        // given
        Long groupId = null;

        // when
        ResponseEntity<Group> response =  groupController.getGroup(groupId);
        Group expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void shouldTestThatGroupByInvalidIdIsNotFound() {
        // given
        Long groupId = 1000L;

        // when
        ResponseEntity<Group> response =  groupController.getGroup(groupId);
        Group expected = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void shouldTestThatListOfAllGroupsIsFetchedSuccessfully() {
        //given
        Group group = Group
                .builder()
                .name("Movie Night")
                .description("Saturday night movie with friends")
                .createdBy(0L)
                .build();

        // when
        groupController.createGroup(group);

        // then
        ResponseEntity<List<Group>> response = groupController.getGroups();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isGreaterThanOrEqualTo(1);
    }
}