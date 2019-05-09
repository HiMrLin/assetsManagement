package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_department")
public class SysDepartment {
    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;
    @Getter(onMethod_ = {@Column(name = "name")})
    private String name;
    @Getter(onMethod_ = {@Column(name = "parent_id")})
    private Long parentId;
    @Getter(onMethod_ = {@Column(name = "level")})
    private String level;
    @Getter(onMethod_ = {@Column(name = "seq")})
    private Integer seq;
    @Getter(onMethod_ = {@Column(name = "type")})
    private Integer type;
    @Getter(onMethod_ = {@Column(name = "remark")})
    private String remark;
    @Getter(onMethod_ = {@Column(name = "create_time")})
    private Date createTime;
    @Getter(onMethod_ = {@Column(name = "update_time")})
    private Date updateTime;

}
