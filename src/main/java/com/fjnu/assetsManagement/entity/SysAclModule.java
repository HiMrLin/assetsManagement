package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_acl_module")
public class SysAclModule {
    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;
    @Getter(onMethod_ = {@Column(name = "code")})
    private String code;
    @Getter(onMethod_ = {@Column(name = "name")})
    private String name;
    @Getter(onMethod_ = {@Column(name = "parent_id")})
    private Long parentId;
    @Getter(onMethod_ = {@Column(name = "level")})
    private String level;
    @Getter(onMethod_ = {@Column(name = "type")})
    private Integer type;
    @Getter(onMethod_ = {@Column(name = "seq")})
    private Integer seq;
    @Getter(onMethod_ = {@Column(name = "status")})
    private Integer status;
    @Getter(onMethod_ = {@Column(name = "remark")})
    private String remark;
    @Getter(onMethod_ = {@Column(name = "role_name")})
    private String routeName;
    @Getter(onMethod_ = {@Column(name = "icon")})
    private String icon;
    @Getter(onMethod_ = {@Column(name = "component_path")})
    private String componentPath;
    @Getter(onMethod_ = {@Column(name = "component")})
    private String component;
    @Getter(onMethod_ = {@Column(name = "account")})
    private boolean cache;
    @Getter(onMethod_ = {@Column(name = "path")})
    private String path;


}
