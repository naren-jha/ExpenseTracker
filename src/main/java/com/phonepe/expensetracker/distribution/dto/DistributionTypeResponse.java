package com.phonepe.expensetracker.distribution.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DistributionTypeResponse {
    private Double amountDivided;
    private List<DistributionTransaction> distributionTransactions;
}
