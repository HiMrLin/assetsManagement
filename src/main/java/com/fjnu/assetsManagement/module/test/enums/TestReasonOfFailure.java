package com.fjnu.assetsManagement.module.test.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum TestReasonOfFailure implements IReasonOfFailure {

    NAME_IS_NOT_BLANK("name is not blank", "一级部门不可移动");

    private String en_msg;
    private String zh_msg;

    TestReasonOfFailure(String en_msg, String zh_msg) {
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
