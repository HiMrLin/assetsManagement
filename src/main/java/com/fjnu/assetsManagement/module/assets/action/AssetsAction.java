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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

@Slf4j
@CrossOrigin
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
        try {
            switch (functionNo) {
                //得到所有资产列表
                case AssetsFunctionNoConstants.ASSETS_LIST:
                    assetsRequestService.assetsListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //得到当前用户名下的资产列表
                case AssetsFunctionNoConstants.GET_OWNER_ASSTETS_LIST:
                    assetsRequestService.ownerAssetsListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //发起移交
                case AssetsFunctionNoConstants.TRANSFER:
                    assetsRequestService.transferRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //得到可移交用户列表
                case AssetsFunctionNoConstants.GET_COULD_TRANSFER_USER_LIST:
                    assetsRequestService.getCouldTransferUserList();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //根据id查询资产列表
                case AssetsFunctionNoConstants.USE_LIST:
                    assetsRequestService.useListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //根据资产id得到移交记录
                case AssetsFunctionNoConstants.TRANSFER_LIST:
                    assetsRequestService.transferListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //资产领用
                case AssetsFunctionNoConstants.USE:
                    assetsRequestService.useRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //根据资产id得到领用记录
                case AssetsFunctionNoConstants.USED_LIST:
                    assetsRequestService.usedListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //归还
                case AssetsFunctionNoConstants.RETURN:
                    assetsRequestService.returnRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //报废
                case AssetsFunctionNoConstants.SCRAP:
                    assetsRequestService.scrapRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //发起调拨
                case AssetsFunctionNoConstants.ALLOT:
                    assetsRequestService.allotRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //调拨列表
                case AssetsFunctionNoConstants.ALLOT_LIST:
                    assetsRequestService.allotListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //根据调拨编号进行调拨核对
                case AssetsFunctionNoConstants.ALLOT_CHECK:
                    assetsRequestService.allotCheckRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //报废列表
                case AssetsFunctionNoConstants.SCRAP_LIST:
                    assetsRequestService.scrapListRequest();
                    this.setHeadOfResponseDataWithSuccessInfo(dataCenterService.getResponseDataFromDataLocal());
                    this.responseData = dataCenterService.getResponseDataFromDataLocal();
                    break;
                //根据移交单号进行移交核对
                case AssetsFunctionNoConstants.TRANSFERCHECK:
                    assetsRequestService.transferCheckRequest();
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
    @ExceptionHandler(RequestFailureException.class)
    public Object handleException(RequestFailureException requestFailureException){
        ResponseData responseData=requestFailureException.getResponseData();
        this.responseData=responseData;
        return ERROR;
    }

}