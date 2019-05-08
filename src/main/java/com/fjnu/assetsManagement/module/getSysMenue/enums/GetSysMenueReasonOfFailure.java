package com.fjnu.assetsManagement.module.getSysMenue.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum GetSysMenueReasonOfFailure implements IReasonOfFailure {

    ACCOUNT_IS_BLANK("Account is blank", "账号不为空"),
    PASSWORD_IS_BLANK("Password is blank", "密码不为空"),
    NO_ACL("No acl", "无权限"),
    NO_USER("No user", "无此用户"),
    USERNAME_OR_PASSWORD_IS_WRONG("User name or password is wrong", "用户名或密码错误");

    private String en_msg;
    private String zh_msg;

    GetSysMenueReasonOfFailure(String en_msg, String zh_msg) {
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
