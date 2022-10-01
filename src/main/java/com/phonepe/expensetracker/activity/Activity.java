package com.phonepe.expensetracker.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private Long id;
    private String name;
    private String description;
    private Long groupId;
    private Long createdBy;
    private Long paidBy;
    private Double amount;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
