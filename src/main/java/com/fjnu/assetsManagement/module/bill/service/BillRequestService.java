package com.fjnu.assetsManagement.module.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillRequestService {
    @Autowired
    private BillRequestCheckService billRequestCheckService;
    @Autowired
    private BillRequestBusinessService billRequestBusinessService;
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //获取待入库列表
    public void billOutListRequest() {
        billRequestCheckService.billOutListRequestCheck(); //检查参数合法性
        billRequestBusinessService.billOutListRequestProcess();//获取数据后的具体操作

    }

    public void inBillRequest() {
        billRequestCheckService.inBillRequestCheck(); //检查参数合法性
        billRequestBusinessService.inBillRequestProcess();//获取数据后的具体操作
    }

    public void billListRequest() {
        billRequestCheckService.billListRequestCheck(); //检查参数合法性
        billRequestBusinessService.billListRequestProcess();//获取数据后的具体操作
    }

}
