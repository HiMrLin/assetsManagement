package com.fjnu.assetsManagement.module.assets.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum AssetsReasonOfFailure implements IReasonOfFailure {

    USERNAME_IS_NOT_BLANK("username is not blank", "使用人不可为空"),
    DEPARTMENT_IS_NOT_BLANK("department is not blank", "部门不可为空"),
    PURPOSE_IS_NOT_BLANK("purpose is not blank", "用途不可为空"),
    CARD_ID_LIST_IS_NOT_BLANK("cardid list is not blank","卡片编号列表不可为空"),
    USER_ID_LIST_IS_NOT_BLANK("use id is not blank","领用编号列表不可为空"),
    NOTIFITOR_IS_NOT_BLANK("notifier is not blank","报备者不可为空"),
    ASSETS_IS_NOT_BLANK("assets is not blank","资产编码不可为空"),
    KINDIN_IS_NOT_BLANK("kindid is not blank", "资产类别ID不可未负"),
    USERID_IS_NOT_BLANK("userid is not blank","使用者ID不可为空"),
    CURRENTER_IS_NOT_THE_SAME_DEPARTMENT("currenter is the same department","接受者与发起者须在同一家公司"),
    TRANSFERID_IS_NOT_BLANK("transferid is not blank","移交单号不可为空"),
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
