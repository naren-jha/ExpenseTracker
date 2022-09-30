package com.phonepe.expensetracker.distribution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.phonepe.expensetracker.common.Constants.*;

@Slf4j
@RestController
@RequestMapping("api/v1/distribution")
public class DistributionController {

    @Autowired
    private DistributionService distributionService;

    @GetMapping()
    public ResponseEntity<DistributionResponse> distributeExpenses(@RequestParam Long groupId) {
        DistributionResponse distributionResponse = null;
        try {
            distributionResponse = distributionService.distributeExpenses(groupId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(distributionResponse);
        }
        catch (Exception e) {
            log.error("An exception occurred while distributing expenses for the group", e);

            distributionResponse.setMessage(COULD_NOT_DISTRIBUTE_EXPENSES_ERR_MSG);
            distributionResponse.setDistributeExpenseOutputs(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(distributionResponse);
        }
    }

}
