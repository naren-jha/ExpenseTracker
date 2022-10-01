package com.phonepe.expensetracker.distribution.factory;

import com.phonepe.expensetracker.distribution.DistributeExpenseInput;
import com.phonepe.expensetracker.distribution.DistributeExpenseOutput;

import java.util.List;

public interface Distribution {
    List<DistributeExpenseOutput> distributeExpenses(DistributeExpenseInput distributeExpenseInput);
    DistributionType distributionType();
}
