package com.fjnu.assetsManagement.module.entry.constant;

public interface EntryFunctionNoConstants {

    //入库
    String ENTRY = "entry";

    //根据采购单号得到采购主表记录
    String GET_PURCHASE_BY_ORDERNO = "get purchaseMaster by orderNo";

    //得到已入库列表
    String GET_PURCHASE_IN_LIST = "get purchaseMaster in list";

    //得到未入库列表
    String GET_PURCHASE_OUT_LIST = "get purchaseMaster out list";
}
