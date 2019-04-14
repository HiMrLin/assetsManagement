package com.fjnu.assetsManagement.module.helloWorld.service;

import com.fjnu.assetsManagement.module.helloWorld.enums.HelloWorldReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelloWordRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;
    public void helloWorldRequestCheck() {
        String userName=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        if (StringUtils.isBlank(userName)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(),HelloWorldReasonOfFailure.NAME_IS_NOT_BLANK);
        }
        dataCenterService.setData("userName",userName);
    }
}
