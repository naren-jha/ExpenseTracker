package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.distribution.factory.DistributionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupUserDTO {
    private Long groupId;
    private String userEmail;
    private String userMobile;
    private String userName;
    private UserGroupRole authority;
    private DistributionType distributionType;
    private Double distributionValue;
}
