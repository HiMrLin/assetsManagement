package com.fjnu.assetsManagement.module.loginManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManagementRequestService {
    @Autowired
    private LoginManagementRequestCheckService loginManagementRequestCheckService;
    @Autowired
    private LoginManagementRequestBusinessService loginManagementRequestBusinessService;

    public void loginRequestProcess() {

        loginManagementRequestCheckService.loginRequestCheck();
        loginManagementRequestBusinessService.loginRequestProcess();


    }
}
