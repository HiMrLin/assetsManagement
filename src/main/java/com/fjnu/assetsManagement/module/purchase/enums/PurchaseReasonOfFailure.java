package com.fjnu.assetsManagement.module.purchase.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum PurchaseReasonOfFailure implements IReasonOfFailure {

    ORDERNO_IS_NOT_BLANK("orderNo is not blank", "采购单号不可为空"),
    OPERATOR_IS_NOT_BLANK("operator is not blank", "操作员不可为空"),
    PURCHASEDETAIL_IS_NOT_BLANK("purchaseDetail is not blank", "采购详细信息不可为空"),
    PURCHASEDETAIL_IS_NOT_ENOUGH("purchaseDetail is not enough", "采购详细信息不全"),
    PAGE_PARAMS_IS_NOT_CORRECT("page params is not correct", "分页参数错误"),
    KIND_IS_NOT_CORRECT("kind is not correct", "类别错误");


    private String en_msg;
    private String zh_msg;

    PurchaseReasonOfFailure(String en_msg, String zh_msg) {
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
