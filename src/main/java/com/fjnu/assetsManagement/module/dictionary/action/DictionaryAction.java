package com.fjnu.assetsManagement.module.dictionary.action;


import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.dictionary.constant.DictionaryFunctionNoConstants;
import com.fjnu.assetsManagement.module.dictionary.service.DictionaryRequestService;
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
@Namespace("/dictionaryAction")
public class DictionaryAction extends JsonAction {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    DictionaryRequestService dictionaryRequestService;

    @Action(value = "/dictionary")
    public String execute() throws Exception {
        String functionNo = dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            //添加数据字典
            case DictionaryFunctionNoConstants.ADD_DICTIONARY_ITEM:
                dictionaryRequestService.addDictionaryItemServiceProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //根据id得到数据字典
            case DictionaryFunctionNoConstants.GET_DICTIONARY_ITEM_BY_ID:
                dictionaryRequestService.getDictionaryByIdProcess();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //得到数据字典列表
            case DictionaryFunctionNoConstants.GET_DICTIONARY_ITEM:
                dictionaryRequestService.getDictionaryList();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData = dataCenterService.getResponseDataFromDataLocal();
                break;
            //删除数据字典
            case DictionaryFunctionNoConstants.DELETE_DICTIONARY_ITEM:
                dictionaryRequestService.deleteDictionaryListByIdProcess();
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
