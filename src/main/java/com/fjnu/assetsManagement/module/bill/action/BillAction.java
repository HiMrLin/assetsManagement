package com.fjnu.assetsManagement.module.bill.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.bill.constant.BillFunctionNoConstants;
import com.fjnu.assetsManagement.module.bill.service.BillRequestService;
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
@Namespace("/billAction")
public class BillAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    BillRequestService billRequestService;

    @Action(value="/bill")
    public String execute() throws Exception {
        String functionNo=dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            case BillFunctionNoConstants.BILL_OUT_LIST:
                billRequestService.billOutListRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case BillFunctionNoConstants.IN_BILL:
                billRequestService.inBillRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case BillFunctionNoConstants.BILL_LIST:
                billRequestService.billListRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            default:
                this.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
        return SUCCESS;
    }
    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException){
        ResponseData responseData=requestFailureException.getResponseData();
        this.responseData=responseData;
        return ERROR;

    }

}