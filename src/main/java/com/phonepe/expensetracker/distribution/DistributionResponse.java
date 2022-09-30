package com.phonepe.expensetracker.distribution;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DistributionResponse {
    private String message;
    private List<DistributeExpenseOutput> distributeExpenseOutputs;
}
