package com.hfkj.bbt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色表
 */
@Entity
@Table(name = "tab_role")
public class Role implements Serializable{


    private static final long serialVersionUID = -5430932339187588203L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role_name",nullable = false)
    private String roleName;

    @Column(name = "role_level",nullable = false)
    private Integer roleLevel;

    /**
     * 角色描述
     */
    @Column(name = "description",nullable = false)
    private String description;

    public Role(){}

    public Role(String roleName, Integer roleLevel, String description) {
        this.roleName = roleName;
        this.roleLevel = roleLevel;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
