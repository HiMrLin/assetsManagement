package com.fjnu.assetsManagement.module.entry.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.module.entry.enums.EntryReasonOfFailure;
import com.fjnu.assetsManagement.module.purchase.enums.PurchaseReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    SessionFactory sessionFactory;

    //入库
    public void entryCheck() {
        String inOperator = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("inOperator");
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNoList");
        List<String> orderNoList = array.toJavaList(String.class);

        if (orderNoList.size() <= 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.ENTRY_IS_NOT_ENOUGH);
        }

        if (StringUtils.isBlank(inOperator)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.ENTRY_IS_NOT_ENOUGH);
        }

        dataCenterService.setData("orderNoList", orderNoList);
        dataCenterService.setData("inOperator", inOperator);
    }

    //得到已入库采购单列表
    public void getInPurchaseMasterListCheck() {

        //得到分页参数
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");

        if (pageSize == null || pageSize < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        if (pageNum == null || pageNum < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }

        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);

    }

    //得到未入库采购单列表
    public void getOutPurchaseMasterListCheck() {

        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        if (pageSize == null || pageSize < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        if (pageNum == null || pageNum < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), EntryReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }

        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);
    }

    //根据采购单号得到采购单记录
    public void getPurchaseMasterListByOrderNoCheck() {
        String orderNo = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNo");

        if (StringUtils.isBlank(orderNo)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.ORDERNO_IS_NOT_BLANK);
        }

        dataCenterService.setData("orderNo", orderNo);
    }

}
