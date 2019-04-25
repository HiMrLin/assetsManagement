package com.fjnu.assetsManagement.module.assets.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum AssetsReasonOfFailure implements IReasonOfFailure {

    USERNAME_IS_NOT_BLANK("username is not blank", "使用人不可为空"),
    DEPOSITORY_IS_NOT_BLANK("depository is not blank", "保管人不可为空"),
    DEPARTMENT_IS_NOT_BLANK("department is not blank", "部门不可为空"),
    PURPOSE_IS_NOT_BLANK("purpose is not blank", "用途不可为空"),
    CARD_ID_LIST_IS_NOT_BLANK("cardid list is not blank","卡片编号列表不可为空"),
    USER_ID_LIST_IS_NOT_BLANK("use id is not blank","领用编号列表不可为空"),
    PAGE_PARAMS_IS_NOT_CORRECT("page params is not correct", "分页参数错误");

    private String en_msg;
    private String zh_msg;

    AssetsReasonOfFailure(String en_msg, String zh_msg) {
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
