package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.distribution.factory.DistributionType;
import com.phonepe.expensetracker.user.User;
import com.phonepe.expensetracker.user.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.phonepe.expensetracker.common.Constants.*;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    public void addGroup(Group group) {
        // group name should not be empty
        if (Strings.isBlank(group.getName())) {
            throw new IllegalArgumentException(GROUP_NAME_NULL_OR_EMPTY_ERR_MSG);
        }

        group.setMembers(new HashSet<>());
        group.setActivities(new ArrayList<>());
        groupRepository.addGroup(group);
        addGroupOwnerAsAdmin(group);
    }

    public List<Group> getGroups() {
        return groupRepository.getGroups();
    }

    public Group getGroup(Long groupId) {
        if (Objects.isNull(groupId)) {
            throw new IllegalArgumentException(GROUP_ID_NULL_ERR_MSG);
        }

        return groupRepository.getGroup(groupId)
                .orElseThrow(() -> new IllegalStateException(GROUP_DOES_NOT_EXIST_ERR_MSG + " group id = " + groupId));
    }

    private void addGroupOwnerAsAdmin(Group group) {
        User user = userService.getUser(group.getCreatedBy());
        GroupUserDTO groupUserDTO = GroupUserDTO
                .builder()
                .groupId(group.getId())
                .userEmail(user.getEmail())
                .userMobile(user.getMobile())
                .authority(UserGroupRole.ADMIN)
                .build();
        addUserToGroup(group.getId(), groupUserDTO);
    }

    public void addUserToGroup(Long groupId, GroupUserDTO groupUserDTO) {

        groupUserDTO.setGroupId(groupId);
        Group group = getGroup(groupId);
        if (Objects.isNull(group)) {
            throw new IllegalStateException(GROUP_DOES_NOT_EXIST_ERR_MSG);
        }

        User user = userService.getUserByMobile(groupUserDTO.getUserMobile());
        if (Objects.isNull(user)) {
            user = userService.getUserByEmail(groupUserDTO.getUserEmail());
        }
        if (Objects.isNull(user)) {
            // Unregistered user
            user = User
                    .builder()
                    .name(groupUserDTO.getUserName())
                    .email(groupUserDTO.getUserEmail())
                    .mobile(groupUserDTO.getUserMobile())
                    .build();
            userService.addUser(user);
        }

        if (Objects.isNull(user.getId())) {
            throw new IllegalStateException(COULD_NOT_ADD_USER_TO_GROUP_ERR_MSG);
        }

        GroupUser groupUser = GroupUser
                .builder()
                .groupId(group.getId())
                .userId(user.getId())
                .authority(groupUserDTO.getAuthority())
                .distributionType(DistributionType.EQUAL) // default distribution type
                .distributionValue(null) // no value needed for default distribution type (EQUAL)
                .build();
        addGroupUser(groupUser);
        group.getMembers().add(groupUser);
        user.getGroups().add(groupUser);

    }

    private void addGroupUser(GroupUser groupUser) {
        groupRepository.addGroupUser(groupUser);
    }

    public void removeUserFromGroup(Long groupId, Long userId) {
        Group group = getGroup(groupId);

        GroupUser targetGroupUser = null;
        int adminCount = 0;
        for (GroupUser groupUser : group.getMembers()) {
            if (groupUser.getAuthority().equals(UserGroupRole.ADMIN)) {
                ++adminCount;
            }
            if (groupUser.getUserId() == userId) {
                targetGroupUser = groupUser;
            }
        }

        if (targetGroupUser == null) {
            throw new IllegalStateException(MEMBER_NOT_PART_OF_GROUP_ERR_MSG);
        }
        if (group.getMembers().size() == 1) {
            throw new IllegalStateException(NOT_ENOUGH_MEMBER_IN_GROUP_ERR_MSG);
        }
        if (targetGroupUser.getAuthority().equals(UserGroupRole.ADMIN) && adminCount == 1) {
            throw new IllegalStateException(ONLY_ADMIN_IN_GROUP_ERR_MSG);
        }

        group.getMembers().remove(targetGroupUser);

        User user = userService.getUser(userId);
        user.getGroups().remove(targetGroupUser);

        removeGroupUser(targetGroupUser);
    }

    private void removeGroupUser(GroupUser groupUser) {
        groupRepository.removeGroupUser(groupUser);
    }
}
