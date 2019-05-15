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

    //归还
    public void returnRequest(){
        assetsRequestCheckService.returnRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.returnRequestProcess();//获取数据后的具体操作
    }

    //报废
    public void scrapRequest(){
        assetsRequestCheckService.scrapRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.scrapRequestProcess();//获取数据后的具体操作
    }

    public void scrapListRequest(){
        assetsRequestCheckService.scrapListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.scrapListRequestProcess();//获取数据后的具体操作
    }

    public void transferListRequest(){
        assetsRequestCheckService.transferListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.transferListRequestProcess();//获取数据后的具体操作
    }

    public void ownerAssetsListRequest(){
        assetsRequestCheckService.ownerAssetsListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.ownerAssetsListRequestProcess();//获取数据后的具体操作
    }

    //移交
    public void transferRequest(){
        assetsRequestCheckService.transferRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.transferRequestProcess();//获取数据后的具体操作
    }

    //得到可移交用户列表
    public void getCouldTransferUserList() {
        assetsRequestCheckService.getCouldTransferUserListCheck(); //检查参数合法性
        assetsRequestBusinessService.getCouldTransferUserListProcess();//获取数据后的具体操作
    }

    public void transferCheckRequest(){
        assetsRequestCheckService.transferCheckRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.transferCheckRequestProcess();//获取数据后的具体操作
    }

    //调拨
    public void allotRequest(){
        assetsRequestCheckService.allotRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.allotRequestProcess();//获取数据后的具体操作
    }

    public void allotListRequest(){
        assetsRequestCheckService.allotListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.allotListRequestProcess();//获取数据后的具体操作
    }

    public void allotCheckRequest(){
        assetsRequestCheckService.allotCheckRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.allotCheckRequestProcess();//获取数据后的具体操作
    }


}
