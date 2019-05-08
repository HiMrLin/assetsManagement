package com.fjnu.assetsManagement.module.getSysMenue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetSysMenueRequestService {
    @Autowired
    private GetSysMenueRequestCheckService getSysMenueRequestCheckService;
    @Autowired
    private GetSysMenueRequestBusinessService getSysMenueRequestBusinessService;

    public void getSysMenueRequestProcess() {

        getSysMenueRequestCheckService.getSysMenueRequestCheck();
        getSysMenueRequestBusinessService.getSysMenueRequestProcess();


    }
}
