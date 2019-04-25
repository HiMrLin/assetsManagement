package com.fjnu.assetsManagement.module.assets.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.module.assets.enums.AssetsReasonOfFailure;
import com.fjnu.assetsManagement.module.helloWorld.enums.HelloWorldReasonOfFailure;
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
    }

    public void usedListRequestCheck(){
        pageCheck();
    }

    public void returnRequestCheck(){
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("receiveIdList");
        List<Long> receiveIdList = array.toJavaList(Long.class);
        if (receiveIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USER_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("receiveIdList", receiveIdList);
    }
}
