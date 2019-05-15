package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;


@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;
    @Getter(onMethod_ = {@Column(name = "account")})
    private String account;
    @Getter(onMethod_ = {@Column(name = "user_name")})
    private String userName;
    @Getter(onMethod_ = {@Column(name = "password")})
    private String password;
    @Getter(onMethod_ = {@Column(name = "sex")})
    private String sex;
    @Getter(onMethod_ = {@Column(name = "phone")})
    private String phone;
    @Getter(onMethod_ = {@Column(name = "status")})
    private Integer status;
    //    @Getter(onMethod_ = {@Column(name = "role")})
//    private Long role;
//    @Getter(onMethod_ = {@Column(name = "department")})
//    private Long department;
    @Getter(onMethod_ = {@ManyToOne, @JoinColumn(name = "department")})
    private SysDepartment sysDepartment;

    @Getter(onMethod_ = {@ManyToOne, @JoinColumn(name = "role")})
    private SysRoleAcl sysRoleAcl;

    public SysUser (){
    }
    //用于移交对象的构造函数
    public SysUser (Long id, String account, String userName){
        this.id = id;
        this.account = account;
        this.userName = userName;
    }
}
