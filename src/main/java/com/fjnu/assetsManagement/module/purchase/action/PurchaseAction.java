package com.fjnu.assetsManagement.module.purchase.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.purchase.constant.PurchaseFunctionNoConstants;
import com.fjnu.assetsManagement.module.purchase.service.PurchaseRequestService;
import com.fjnu.assetsManagement.service.DataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@CrossOrigin
@Namespace("/purchaseAction")
public class PurchaseAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    PurchaseRequestService purchaseRequestService;

    @Action(value = "/purchase")
    public String execute() throws Exception {
        String functionNo = dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            //添加采购单
            case PurchaseFunctionNoConstants.ADD_PURCHASE_ITEM:
                purchaseRequestService.addPurchaseItemServiceProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //得到采购单列表
            case PurchaseFunctionNoConstants.GET_PURCHASE_LIST:
                purchaseRequestService.getPurchaseMasterListProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //根据采购单号得到采购单
            case PurchaseFunctionNoConstants.GET_PURCHASE_BY_ORDERNO:
                purchaseRequestService.getPurchaseMasterListByOrderNoProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //根据采购单号数组删除采购单
            case PurchaseFunctionNoConstants.DELETE_PURCHASE_ITEM:
                purchaseRequestService.deletePurchaseMasterListByOrderNoProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //得到数据字典列表
            case PurchaseFunctionNoConstants.GET_DICTIONARY_ITEM:
                purchaseRequestService.getDictionaryList();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            default:
                this.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
        return SUCCESS;
    }

    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException) {
        ResponseData responseData = requestFailureException.getResponseData();
        this.responseData = responseData;
        return ERROR;

    }
}
