package com.phonepe.expensetracker.distribution.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.COULD_NOT_DISTRIBUTE_EXPENSES_ERR_MSG;

@Data
@Builder
public class DistributionResponse {
    private String message;
    private List<DistributionTransaction> distributions;

    public static DistributionResponse getErrInstance() {
        return DistributionResponse
                .builder()
                .message(COULD_NOT_DISTRIBUTE_EXPENSES_ERR_MSG)
                .distributions(null)
                .build();
    }
}
