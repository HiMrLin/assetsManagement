package com.fjnu.assetsManagement.vo;

import lombok.Data;

@Data
public class PersonInfoVo {
    private Long userId;
    private String userName;
    private String account;
    private Long userCompanyType;
    private Long userCompanyId;
    private String userRole;

}
