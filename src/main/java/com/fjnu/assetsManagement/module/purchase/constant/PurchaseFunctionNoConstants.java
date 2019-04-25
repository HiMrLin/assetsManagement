package com.fjnu.assetsManagement.module.purchase.constant;

public interface PurchaseFunctionNoConstants {

    //增加采购主表记录
    String ADD_PURCHASE_ITEM = "add purchase item";

    //得到采购主表列表
    String GET_PURCHASE_LIST = "get purchaseMaster list";

    //得到已入库列表
    String GET_PURCHASE_IN_LIST = "get purchaseMaster in list";

    //得到未入库列表
    String GET_PURCHASE_OUT_LIST = "get purchaseMaster out list";

    //根据采购单号得到采购主表记录
    String GET_PURCHASE_BY_ORDERNO = "get purchaseMaster by orderNo";

    //修改采购主表记录
    String UPDATE_PURCHASE_ITEM = "update purchase item";

    //删除采购主表记录(多选)
    String DELETE_PURCHASE_ITEM = "delete purchase item";

}
