package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.distribution.factory.DistributionType;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupUser {
    private Long id;
    @EqualsAndHashCode.Include private Long groupId;
    @EqualsAndHashCode.Include private Long userId;
    private UserGroupRole authority; // user or admin
    // admin wil have additional privilege
    private Date createAt;
    private Date updatedAt;

    private DistributionType distributionType;
    private Double distributionValue; // NA for equal distribution
}
