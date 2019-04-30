package com.fjnu.assetsManagement.module.helloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWordRequestService {
    @Autowired
    private HelloWordRequestCheckService helloWordRequestCheckService;
    @Autowired
    private HelloWordRequestBusinessService helloWordRequestBusinessService;

    public void helloWorldRequestProcess() {

        helloWordRequestCheckService.helloWorldRequestCheck(); //检查参数合法性
            helloWordRequestBusinessService.helloWorldRequestProcess();//获取数据后的具体操作


    }
}
