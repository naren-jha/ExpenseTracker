package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.distribution.strategy.DistributionType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DistributionStrategy {
    private Long id;
    private DistributionType distributionType;
    private Integer order;
    private Date createAt;
    private Date updatedAt;
}
