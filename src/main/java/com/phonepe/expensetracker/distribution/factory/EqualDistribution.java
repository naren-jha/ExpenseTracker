package com.phonepe.expensetracker.distribution.factory;

import com.phonepe.expensetracker.distribution.DistributeExpenseInput;
import com.phonepe.expensetracker.distribution.DistributeExpenseOutput;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class EqualDistribution implements Distribution {

    @Override
    public List<DistributeExpenseOutput> distributeExpenses(DistributeExpenseInput distributeExpenseInput) {
        // TODO
        return null;
    }

    @Override
    public DistributionType distributionType() {
        return DistributionType.EQUAL;
    }
}
