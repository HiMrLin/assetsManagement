package com.fjnu.assetsManagement.module.bill.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum BillReasonOfFailure implements IReasonOfFailure {

    ORDERNO_IS_NOT_BLANK("orderNo is not blank", "采购单号不可为空"),
    PAGE_PARAMS_IS_NOT_CORRECT("page params is not correct", "分页参数错误");

    private String en_msg;
    private String zh_msg;

    BillReasonOfFailure(String en_msg, String zh_msg) {
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
