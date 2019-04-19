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

    public void addPurchaseItemServiceProcess() {
        purchaseRequestCheckService.addPurchaseItemServiceCheck();
        purchaseRequestBusinessService.addPurchaseItemServiceProcess();
    }
}
