package com.fjnu.assetsManagement.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserVo {
    private Long id;
    private String account;
    private String userName;
    private Long role;
    private Long department;
    private Integer status;
    private String sex;
    private String phone;
}
