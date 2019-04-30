package com.fjnu.assetsManagement.module.helloWorld.service;

import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.module.helloWorld.enums.HelloWorldReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWordRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;

    public void helloWorldRequestCheck() {

        ResponseData response = dataCenterService.getResponseDataFromDataLocal();
        ExceptionUtil.setFailureMsgAndThrow(response, HelloWorldReasonOfFailure.NAME_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息


        //dataCenterService.setData("userName", userName);//验证合法后插入容器
    }
}
