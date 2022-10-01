package com.phonepe.expensetracker.user;

import com.phonepe.expensetracker.group.GroupUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
    private String pwd;
    private String mobile;
    private String gender;
    private Boolean registered;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Set<GroupUser> groups; // groups that user is member of
}
