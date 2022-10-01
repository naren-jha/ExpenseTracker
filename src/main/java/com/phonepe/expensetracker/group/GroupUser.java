package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.distribution.factory.DistributionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupUser {
    private Long id;
    @EqualsAndHashCode.Include private Long groupId;
    @EqualsAndHashCode.Include private Long userId;
    private UserGroupRole authority; // user or admin. admin wil have additional privileges
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    private DistributionType distributionType;
    private Double distributionValue; // NA for equal distribution
}
