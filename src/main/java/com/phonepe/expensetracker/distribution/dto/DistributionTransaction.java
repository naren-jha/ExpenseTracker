package com.phonepe.expensetracker.distribution.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistributionTransaction {
    private Long fromUser;
    private Long toUser;
    private Double amount;
}
