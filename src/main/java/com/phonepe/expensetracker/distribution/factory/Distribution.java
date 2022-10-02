package com.phonepe.expensetracker.distribution.factory;

import com.phonepe.expensetracker.distribution.dto.DistributeExpenseInput;
import com.phonepe.expensetracker.distribution.dto.DistributionTypeResponse;

public interface Distribution {
    DistributionTypeResponse distributeExpenses(DistributeExpenseInput distributeExpenseInput);
    DistributionType distributionType();
}
