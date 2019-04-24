package com.fjnu.assetsManagement.module.entry.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.entry.constant.EntryFunctionNoConstants;
import com.fjnu.assetsManagement.module.entry.service.EntryRequestService;
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
@Namespace("/entryAction")
public class EntryAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    EntryRequestService entryRequestService;

    @Action(value = "/entry")
    public String execute() throws Exception {
        String functionNo = dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            //入库
            case EntryFunctionNoConstants.ENTRY:
                entryRequestService.entryProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //得到已入库采购单列表
            case EntryFunctionNoConstants.GET_PURCHASE_IN_LIST:
                entryRequestService.getInPurchaseMasterListProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //得到未入库采购单列表
            case EntryFunctionNoConstants.GET_PURCHASE_OUT_LIST:
                entryRequestService.getOutPurchaseMasterListProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //根据采购单号得到采购单记录
            case EntryFunctionNoConstants.GET_PURCHASE_BY_ORDERNO:
                entryRequestService.getPurchaseMasterListByOrderNoProcess();
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
