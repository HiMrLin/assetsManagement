package com.fjnu.assetsManagement.module.dictionary.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum DictionaryReasonOfFailure implements IReasonOfFailure {

    KIND_IS_NOT_BLANK("kind is not blank", "类别名称不可为空"),
    ID_IS_NOT_BLANK("id is not blank", "id不可为空"),
    KIND_IS_NOT_CORRECT("kind is not correct", "类别错误");


    private String en_msg;
    private String zh_msg;

    DictionaryReasonOfFailure(String en_msg, String zh_msg) {
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
