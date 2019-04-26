package com.fjnu.assetsManagement.module.assets.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.module.assets.enums.AssetsReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.CheckVariableUtil;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetsRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;
    public void pageCheck(){
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum,pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);
    }

    public void assetsListRequestCheck() {
       pageCheck();
    }

    public void useRequestCheck(){
        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String recorder = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("recorder");
        String depository = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("depository");
        String department = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
        String purpose = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("purpose");
        String note = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("note");
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("cardIdItems");
        List<Long> cardIdList = array.toJavaList(Long.class);
        if (StringUtils.isBlank(userName)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USERNAME_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(depository)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.DEPOSITORY_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(department)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.DEPARTMENT_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(recorder)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.RECORDER_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(purpose)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.PURPOSE_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (cardIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.CARD_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("cardIdList", cardIdList);
        dataCenterService.setData("userName", userName);
        dataCenterService.setData("depository", depository);
        dataCenterService.setData("department", department);
        dataCenterService.setData("purpose", purpose);
        dataCenterService.setData("note", note);
        dataCenterService.setData("recorder", recorder);
    }

    public void usedListRequestCheck(){
        pageCheck();
    }

    public void returnRequestCheck(){
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("receiveIdList");
        List<Long> receiveIdList = array.toJavaList(Long.class);
        String returnName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("returnName");
        if (receiveIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USER_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if(StringUtils.isBlank(returnName)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.RETURN_NAME_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("receiveIdList", receiveIdList);
        dataCenterService.setData("returnName", returnName);
    }

    public void scrapRequestCheck(){
        String notifier = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("notifier");
        String recorder = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("recorder");
        String department = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
        String note = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("note");
        String assetsId =dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("assetsId");
        if (StringUtils.isBlank(notifier)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.NOTIFITOR_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(recorder)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.RECORDER_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(department)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.DEPARTMENT_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (StringUtils.isBlank(assetsId)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.ASSETS_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("notifier", notifier);
        dataCenterService.setData("department", department);
        dataCenterService.setData("assetsId", assetsId);
        dataCenterService.setData("note", note);
        dataCenterService.setData("recorder", recorder);
    }

    public void scrapListRequestCheck(){
        pageCheck();
    }

    public void useListRequestCheck() {
        String kindId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kindId");
        Integer kind;
        if (StringUtils.isBlank(kindId)) {
            kind = null;
        } else {
            kind = Integer.valueOf(kindId).intValue();
            if (kind < 0) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.KINDIN_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
            }
        }
        dataCenterService.setData("kindId", kind);
    }
}
