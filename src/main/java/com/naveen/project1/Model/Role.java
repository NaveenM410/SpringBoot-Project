package com.naveen.project1.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "role_name")
    private AppRole roleName;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public AppRole getRoleName() {
        return roleName;
    }

    public void setRoleName(AppRole roleName) {
        this.roleName = roleName;
    }
}
