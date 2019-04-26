package com.fjnu.assetsManagement.module.assets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

@Service
public class AssetsRequestService {
    @Autowired
    private AssetsRequestCheckService assetsRequestCheckService;
    @Autowired
    private AssetsRequestBusinessService assetsRequestBusinessService;
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //获取资产列表
    public void assetsListRequest() throws UnsupportedEncodingException, SQLException {
        assetsRequestCheckService.assetsListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.assetsListRequestProcess();//获取数据后的具体操作
    }

    public void useListRequest(){
        assetsRequestCheckService.useListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.useListRequestProcess();//获取数据后的具体操作
    }

    public void useRequest(){
        assetsRequestCheckService.useRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.useRequestProcess();//获取数据后的具体操作
    }

    public void usedListRequest(){
        assetsRequestCheckService.usedListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.usedListRequestProcess();//获取数据后的具体操作
    }

    public void returnRequest(){
        assetsRequestCheckService.returnRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.returnRequestProcess();//获取数据后的具体操作
    }

    public void scrapRequest(){
        assetsRequestCheckService.scrapRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.scrapRequestProcess();//获取数据后的具体操作
    }

    public void scrapListRequest(){
        assetsRequestCheckService.scrapListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.scrapListRequestProcess();//获取数据后的具体操作
    }
}
