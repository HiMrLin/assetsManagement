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
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@Namespace("/purchaseAction")
public class PurchaseAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    PurchaseRequestService purchaseRequestService;

    @Action(value = "/purchase")
    public String execute() throws Exception {
        String functionNo = dataCenterService.getFunctionNo();
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            case PurchaseFunctionNoConstants.ADD_PURCHASE_ITEM:
                purchaseRequestService.addPurchaseItemServiceProcess();
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
