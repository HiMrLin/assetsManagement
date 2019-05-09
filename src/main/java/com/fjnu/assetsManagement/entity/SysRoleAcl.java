package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_role_acl")
public class SysRoleAcl {
    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;
    @Getter(onMethod_ = {@Column(name = "role_name")})
    private String roleName;
    @Getter(onMethod_ = {@Column(name = "acl_code")})
    private String aclCode;
    @Getter(onMethod_ = {@Column(name = "description")})
    private String description;
}
