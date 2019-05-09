package com.fjnu.assetsManagement.module.personManagement.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.personManagement.constant.PersonManagementFunctionNoConstants;
import com.fjnu.assetsManagement.module.personManagement.service.PersonManagementRequestService;
import com.fjnu.assetsManagement.service.DataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@Namespace("/personManagement")
public class PersonManagementAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    PersonManagementRequestService personManagementRequestService;

    @Action(value = "/person")
    public String execute() {
        String functionNo = dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        try {
            switch (functionNo) {
                case PersonManagementFunctionNoConstants.GET_PERSON_LIST:
                    personManagementRequestService.getPersonListRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.ADD_PERSON:
                    personManagementRequestService.addPersonRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.UPDATE_PERSON:
                    personManagementRequestService.updatePersonRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.DELETE_PERSON:
                    personManagementRequestService.deletePersonRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.UPDATE_PASSWORD:
                    personManagementRequestService.updatePasswordRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.UPDATE_STATUS:
                    personManagementRequestService.updateStatusRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.GET_DEPARTMENT_LIST:
                    personManagementRequestService.getDepartmentListRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                case PersonManagementFunctionNoConstants.Get_CURRENT_PERSON:
                    personManagementRequestService.getCurrentPersonRequestProcess();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                default:
                    this.setResponseDataWithFailureInfo(dataCenterService.getResponseDataFromDataLocal(), ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                    break;
            }
        } catch (RequestFailureException e) {
            this.responseData = e.getResponseData();
            return ERROR;
        }
        return SUCCESS;
    }


}