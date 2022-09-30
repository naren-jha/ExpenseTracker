package com.phonepe.expensetracker.user;

import com.phonepe.expensetracker.group.GroupUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
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
    private Date createAt;
    private Date updatedAt;
    private Set<GroupUser> groups; // groups that user is member of
}
