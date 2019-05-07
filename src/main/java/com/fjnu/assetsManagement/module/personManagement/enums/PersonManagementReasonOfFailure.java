package com.fjnu.assetsManagement.module.personManagement.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum PersonManagementReasonOfFailure implements IReasonOfFailure {

    ACCOUNT_IS_BLANK("Account is blank", "账号不为空"),
    SEX_IS_BLANK("Sex is blank", "性别不为空"),
    USERNAME_IS_BLANK("User name is blank", "用户名不为空"),
    PASSWORD_IS_BLANK("Password is blank", "密码不为空"),
    PHONE_IS_ILLEGAL("Phone is illegal", "手机号非法"),
    STATUS_IS_ILLEGAL("Status is illegal", "状态码非法"),
    ROLE_IS_ILLEGAL("Role is illegal", "角色id错误"),
    DEPARTMENT_IS_ILLEGAL("Department is illegal", "部门错误"),
    ID_IS_ILLEGAL("ID is illegal", "id错误"),
    NO_ACL("No acl", "无权限"),
    NO_USER("No user", "无此用户"),
    ACCOUNT_EXTISTS("Account extists", "账号已存在"),;

    private String en_msg;
    private String zh_msg;

    PersonManagementReasonOfFailure(String en_msg, String zh_msg) {
        this.en_msg = en_msg;
        this.zh_msg = zh_msg;
    }

    @Override
    public String getZhMsgOfFailure() {
        return zh_msg;
    }

    @Override
    public String getEnMsgOfFailure() {
        return en_msg;
    }


}
