package com.fjnu.assetsManagement.module.assets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetsRequestService {
    @Autowired
    private AssetsRequestCheckService assetsRequestCheckService;
    @Autowired
    private AssetsRequestBusinessService assetsRequestBusinessService;
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")

    //获取资产列表
    public void assetsListRequest() {
        assetsRequestCheckService.assetsListRequestCheck(); //检查参数合法性
        assetsRequestBusinessService.assetsListRequestProcess();//获取数据后的具体操作
    }

}
