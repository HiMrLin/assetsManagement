package com.fjnu.assetsManagement.module.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestRequestService {
    @Autowired
    private TestRequestCheckService testRequestCheckService;
    @Autowired
    private TestRequestBusinessService testRequestBusinessService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    public void testRequestProcess() throws Exception {
        testRequestCheckService.testRequestCheck(); //检查参数合法性
        testRequestBusinessService.testRequestProcess();//获取数据后的具体操作

    }

    public void testTransferImagesProcess() {

        testRequestCheckService.testTransferImagesCheck(); //检查参数合法性
        testRequestBusinessService.testTransferImagesProcess();//获取数据后的具体操作
    }
}
