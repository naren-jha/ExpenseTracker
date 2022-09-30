package com.phonepe.expensetracker.common;

public interface Constants {
    String EMAIL_NULL_OR_EMPTY_ERR_MSG = "Email is null or empty!";
    String INVALID_EMAIL_ERR_MSG = "Email is invalid. Please enter a correct email.";
    String DUPLICATE_EMAIL_ERR_MSG = "Email is taken!";
    String MOBILE_NULL_OR_EMPTY_ERR_MSG = "Mobile is null or empty!";
    String DUPLICATE_MOBILE_ERR_MSG = "Mobile is taken!";
    String USER_ID_NULL_ERR_MSG = "User id is null!";
    String USER_DOES_NOT_EXIST_ERR_MSG = "User does not exist!";
    String USER_CREATED_SUCCESS_MSG = "User created successfully!";
    String COULD_NOT_CREATE_USER_ERR_MSG = "Could not create user. ";
    String GROUP_CREATED_SUCCESS_MSG = "Group created successfully!";
    String COULD_NOT_CREATE_GROUP_ERR_MSG = "Could not create group. Please try again.";
    String GROUP_NAME_NULL_OR_EMPTY_ERR_MSG = "Group name is null or empty!";
    String GROUP_ID_NULL_ERR_MSG = "Group id is null!";
    String GROUP_DOES_NOT_EXIST_ERR_MSG = "Group does not exist!";
    String USER_ADDED_TO_GROUP_SUCCESS_MSG = "User successfully added to group!";
    String COULD_NOT_ADD_USER_TO_GROUP_ERR_MSG = "Could not add user to group. Please try again.";
    String USER_REMOVED_FROM_GROUP_SUCCESS_MSG = "User successfully removed from group!";
    String COULD_NOT_REMOVE_USER_TO_GROUP_ERR_MSG = "Could not remove user from group. Please try again.";
    String MEMBER_NOT_PART_OF_GROUUP_ERR_MSG = "Cannot remove member. Given member is not part of the given group.";
    String NOT_ENOUGH_MEMBER_IN_GROUP_ERR_MSG = "Cannot remove member. Group has only one member.";
    String ONLY_ADMIN_IN_GROUP_ERR_MSG = "Cannot remove member. Given member is the only ADMIN of this group.";
    String COULD_NOT_DISTRIBUTE_EXPENSES_ERR_MSG = "Could not distribute expenses for the group. Please try again.";
}
