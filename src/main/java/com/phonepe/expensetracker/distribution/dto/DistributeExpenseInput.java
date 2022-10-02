package com.phonepe.expensetracker.distribution.dto;

import com.phonepe.expensetracker.group.GroupUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DistributeExpenseInput {
    private Double totalAmount; // amount to be divided
    private List<GroupUser> users; // users among whom distribution is needed
    private List<UserSpend> userSpends; // amount spend by users in the group
}
