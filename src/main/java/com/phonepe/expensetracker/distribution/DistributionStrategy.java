package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.distribution.factory.DistributionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DistributionStrategy {
    private Long id;
    private DistributionType distributionType;
    private Integer executionOrder;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
