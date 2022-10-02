package com.phonepe.expensetracker.distribution.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSpend {
    private Long user;
    private Double spend;
}