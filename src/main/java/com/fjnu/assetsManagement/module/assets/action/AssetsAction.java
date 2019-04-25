package com.fjnu.assetsManagement.module.assets.action;

import com.fjnu.assetsManagement.action.JsonAction;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import com.fjnu.assetsManagement.module.assets.constant.AssetsFunctionNoConstants;
import com.fjnu.assetsManagement.module.assets.service.AssetsRequestService;
import com.fjnu.assetsManagement.service.DataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@Namespace("/assetsAction")
public class AssetsAction extends JsonAction {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    AssetsRequestService assetsRequestService;

    @Action(value="/assets")
    public String execute() throws Exception {
        String functionNo=dataCenterService.getFunctionNo();
        if (functionNo == null) {
            return "none";
        }
        log.info("-----functionNo------" + functionNo);
        switch (functionNo) {
            case AssetsFunctionNoConstants.ASSETS_LIST:
                assetsRequestService.assetsListRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.USE_LIST:
                assetsRequestService.useListRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.USE:
                assetsRequestService.useRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.USED_LIST:
                assetsRequestService.usedListRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.RETURN:
                assetsRequestService.returnRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.SCRAP:
                assetsRequestService.scrapRequest();
                this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                this.responseData=dataCenterService.getResponseDataFromDataLocal();
                break;
            case AssetsFunctionNoConstants.SCRAP_LIST:
                assetsRequestService.scrapListRequest();
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