package com.phonepe.expensetracker.distribution.strategy;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DistributionFactory {

    @Autowired
    private Map<DistributionType, Distribution> distributionStrategyMap;

    public Distribution getDistribution(DistributionType type) {
        return distributionStrategyMap.get(type);
    }
}
