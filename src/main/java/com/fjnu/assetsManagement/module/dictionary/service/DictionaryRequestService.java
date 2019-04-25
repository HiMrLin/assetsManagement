package com.fjnu.assetsManagement.module.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryRequestService {

    @Autowired
    private DictionaryRequestCheckService dictionaryRequestCheckService;
    @Autowired
    private DictionaryRequestBusinessService dictionaryRequestBusinessService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //添加采购单
    public void addDictionaryItemServiceProcess() {
        dictionaryRequestCheckService.addDictionaryItemServiceCheck();
        dictionaryRequestBusinessService.addDictionaryItemServiceProcess();
    }

    //根据采购单号得到采购单列表
    public void getDictionaryByIdProcess() {
        dictionaryRequestCheckService.getDictionaryByIdCheck();
        dictionaryRequestBusinessService.getDictionaryByIdProcess();
    }

    //根据采购单号数组删除采购单
    public void deleteDictionaryListByIdProcess() {
        dictionaryRequestCheckService.deleteDictionaryListByIdCheck();
        dictionaryRequestBusinessService.deleteDictionaryListByIdProcess();
    }

    //得到数据字典列表
    public void getDictionaryList() {
        dictionaryRequestBusinessService.getDictionaryListProcess();
    }
}
