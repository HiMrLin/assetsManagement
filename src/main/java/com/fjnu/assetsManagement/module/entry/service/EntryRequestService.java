package com.fjnu.assetsManagement.module.entry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntryRequestService {

    @Autowired
    private EntryRequestCheckService entryRequestCheckService;
    @Autowired
    private EntryRequestBusinessService entryRequestBusinessService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //入库
    public void entryProcess() {
        entryRequestCheckService.entryCheck();
        entryRequestBusinessService.entryProcess();
    }

    //得到已入库采购单列表
    public void getInPurchaseMasterListProcess() {
        entryRequestCheckService.getInPurchaseMasterListCheck();
        entryRequestBusinessService.getInPurchaseMasterListProcess();
    }

    //得到未入库采购单列表
    public void getOutPurchaseMasterListProcess() {
        entryRequestCheckService.getOutPurchaseMasterListCheck();
        entryRequestBusinessService.getOutPurchaseMasterListProcess();
    }

    //根据采购单号得到采购单列表
    public void getPurchaseMasterListByOrderNoProcess() {
        entryRequestCheckService.getPurchaseMasterListByOrderNoCheck();
        entryRequestBusinessService.getPurchaseMasterListByOrderNoProcess();
    }
}
