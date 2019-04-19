package com.fjnu.assetsManagement.module.purchase.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.module.purchase.enums.PurchaseReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PurchaseRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;

    public void addPurchaseItemServiceCheck() {
        String operator = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("operator");
        Long orderNo = Long.valueOf(String.valueOf(dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNo")));

        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("purchaseDetailSet");
        List<PurchaseDetail> purchaseDetailList = array.toJavaList(PurchaseDetail.class);
        Set<PurchaseDetail> purchaseDetailSet = new HashSet<>(purchaseDetailList);

        //判空
        if (StringUtils.isBlank(operator)) {
            //验证数据不合法后返回前台提示信息
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.OPERATOR_IS_NOT_BLANK);
        } else if (orderNo == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.ORDERNO_IS_NOT_BLANK);
        } else if (purchaseDetailSet.isEmpty()) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_BLANK);
        }
        //对于每个purchaseDetail进行判空
        for (PurchaseDetail purchaseDetail : purchaseDetailSet) {
            if (StringUtils.isBlank(purchaseDetail.getKind())) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            } else if (StringUtils.isBlank(purchaseDetail.getName())) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            } else if (purchaseDetail.getQuantity() == null) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            } else if (purchaseDetail.getUnitPrice() == null) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            }

        }

        //验证合法后插入容器
        dataCenterService.setData("operator", operator);
        dataCenterService.setData("orderNo", orderNo);
        dataCenterService.setData("purchaseDetailSet", purchaseDetailSet);
    }
}
