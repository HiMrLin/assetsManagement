package com.fjnu.assetsManagement.module.helloWorld.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.helloWorld.constant.HelloWorldFunctionNoConstants;
import com.fjnu.assetsManagement.module.helloWorld.service.HelloWordRequestService;
import com.fjnu.assetsManagement.service.DataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@Namespace("/helloWorld")
public class HelloWorldAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    HelloWordRequestService helloWordRequestService;

    @Action(value="/hello")
    public String execute() {
        String functionNo=dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        try {
            switch (functionNo) {
                case HelloWorldFunctionNoConstants.TEST:
                    helloWordRequestService.helloWorldRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData=dataCenterService.getResponseDataFromDataLocal();
                    break;
                default:
                    this.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(),ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                    break;
            }
        } catch (RequestFailureException e) {
            this.responseData = e.getResponseData();
            return ERROR;
        }
        return SUCCESS;
    }


}