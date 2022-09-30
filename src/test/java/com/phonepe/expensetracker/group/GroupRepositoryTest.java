package com.phonepe.expensetracker.group;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void shouldTestThatNewGroupIsAddedSuccessfully() {
        // given
        Group actual = Group
                .builder()
                .name("Trip to Himalaya")
                .description("trip to Himalaya with colleagues")
                .createdBy(0L)
                .build();

        // when
        groupRepository.addGroup(actual);

        // then
        boolean found = groupRepository.getGroup(actual.getId()).isPresent();
        assertThat(found).isTrue();
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
        groupRepository.addGroup(actual);

        // then
        boolean found = groupRepository.getGroup(actual.getId()).isPresent();
        assertThat(found).isTrue();
    }

    @Test
    void shouldTestThatGroupIsNotFoundForInvalidId() {
        // given
        Long groupId = 1000L;

        // when
        boolean found = groupRepository.getGroup(groupId).isPresent();

        // then
        assertThat(found).isFalse();
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
        groupRepository.addGroup(group);

        // then
        List<Group> groups = groupRepository.getGroups();
        assertThat(groups.size()).isGreaterThanOrEqualTo(1);
    }
}