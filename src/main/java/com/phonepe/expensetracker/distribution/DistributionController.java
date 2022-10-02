package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.distribution.dto.DistributionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/group/{groupId}/distribution")
public class DistributionController {

    @Autowired
    private DistributionService distributionService;

    @GetMapping()
    public ResponseEntity<DistributionResponse> getDistribution(@PathVariable("groupId") Long groupId) {
        try {
            log.debug("Distributing expenses for group {}", groupId);
            DistributionResponse distributionResponse = distributionService.distributeExpenses(groupId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(distributionResponse);
        }
        catch (IllegalArgumentException e) {
            log.error("Could not distribute expenses for the group", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(DistributionResponse.getErrInstance());
        }
        catch (Exception e) {
            log.error("An exception occurred while distributing expenses for the group", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(DistributionResponse.getErrInstance());
        }
    }

}
