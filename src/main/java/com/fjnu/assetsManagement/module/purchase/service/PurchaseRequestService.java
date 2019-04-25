package com.fjnu.assetsManagement.module.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseRequestService {

    @Autowired
    private PurchaseRequestCheckService purchaseRequestCheckService;
    @Autowired
    private PurchaseRequestBusinessService purchaseRequestBusinessService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //添加采购单
    public void addPurchaseItemServiceProcess() {
        purchaseRequestCheckService.addPurchaseItemServiceCheck();
        purchaseRequestBusinessService.addPurchaseItemServiceProcess();
    }

    //得到采购单列表
    public void getPurchaseMasterListProcess() {
        purchaseRequestCheckService.getPurchaseMasterListCheck();
        purchaseRequestBusinessService.getPurchaseMasterListProcess();
    }

    //根据采购单号得到采购单列表
    public void getPurchaseMasterListByOrderNoProcess() {
        purchaseRequestCheckService.getPurchaseMasterListByOrderNoCheck();
        purchaseRequestBusinessService.getPurchaseMasterListByOrderNoProcess();
    }

    //根据采购单号数组删除采购单
    public void deletePurchaseMasterListByOrderNoProcess() {
        purchaseRequestCheckService.deletePurchaseMasterListByOrderNoCheck();
        purchaseRequestBusinessService.deletePurchaseMasterListByOrderNoProcess();
    }


}
