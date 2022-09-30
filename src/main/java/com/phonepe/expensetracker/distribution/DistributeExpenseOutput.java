package com.phonepe.expensetracker.distribution;

import lombok.Data;

@Data
public class DistributeExpenseOutput {
    private Long fromUser;
    private Long toUser;
    private Double amount;

    public String getMessage() {
        return String.format("%s should pay to %s %.2f amount", "", "", 0.0); // TODO
    }
}
