package com.phonepe.expensetracker.group;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    void shouldTestThatGroupIsAddedSuccessfully() {
        // given
        Group actual = Group
                .builder()
                .name("Trip to Himalaya")
                .description("trip to Himalaya with colleagues")
                .createdBy(0L)
                .build();

        // when
        groupService.addGroup(actual);

        // then
        Group expected = groupService.getGroup(actual.getId());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatGroupIsNotAddedDueToNullGroupName() {
        // given
        Group group = Group
                .builder()
                .name(null)
                .description("")
                .createdBy(0L)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            groupService.addGroup(group);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(GROUP_NAME_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatGroupIsNotAddedDueToEmptyGroupName() {
        // given
        Group group = Group
                .builder()
                .name("")
                .description("")
                .createdBy(0L)
                .build();

        // when
        Throwable thrown = catchThrowable(() -> {
            groupService.addGroup(group);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(GROUP_NAME_NULL_OR_EMPTY_ERR_MSG);
    }

    @Test
    void shouldTestThatGroupIsFetchedSuccessfully() {
        // given
        Group actual = Group
                .builder()
                .name("Household shopping")
                .description("Household shopping expenses")
                .createdBy(0L)
                .build();

        // when
        groupService.addGroup(actual);

        // then
        Group expected = groupService.getGroup(actual.getId());
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldTestThatGroupIsNotFetchedForNullId() {
        // given
        Long groupId = null;

        // when
        Throwable thrown = catchThrowable(() -> {
            groupService.getGroup(groupId);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(GROUP_ID_NULL_ERR_MSG);
    }

    @Test
    void shouldTestThatGroupIsNotFetchedForInvalidGroupId() {
        // given
        Long groupId = 1000L;

        // when
        Throwable thrown = catchThrowable(() -> {
            groupService.getGroup(groupId);
        });

        // then
        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(GROUP_DOES_NOT_EXIST_ERR_MSG);
    }

    @Test
    void shouldTestThatListOfAllGroupsIsFetchedSuccessfully() {
        // given
        Group group = Group
                .builder()
                .name("Movie Night")
                .description("Saturday night movie with friends")
                .createdBy(0L)
                .build();

        // when
        groupService.addGroup(group);

        // then
        List<Group> groups = groupService.getGroups();
        assertThat(groups.size()).isGreaterThanOrEqualTo(1);
    }
}