package com.fjnu.assetsManagement.module.helloWorld.service;

import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWordRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    public void helloWorldRequestProcess() {
        String userName=dataCenterService.getData("userName");
        ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.putValueToData(responseData,"userName",userName);
    }
}
