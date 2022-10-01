package com.phonepe.expensetracker.group;

import com.phonepe.expensetracker.activity.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private Long id;
    private String name;
    private String description;
    private Long createdBy; // created by user id
    private Set<GroupUser> members; // admins and normal users
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Boolean deleted; // deleted groups are not visible to users anymore
    private Boolean closed; // closed groups are visible to users but no further transaction/activity can be added to them
    private List<Activity> activities;
}
