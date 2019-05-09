package com.fjnu.assetsManagement.module.loginManagement.service;

import com.fjnu.assetsManagement.module.loginManagement.enums.LoginManagementReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginManagementRequestCheckService {
    @Autowired
    private DataCenterService dataCenterService;

    public void loginRequestCheck() {
        String account = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("account");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");
        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), LoginManagementReasonOfFailure.ACCOUNT_IS_BLANK);
        }
        if (StringUtils.isBlank(password)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), LoginManagementReasonOfFailure.PASSWORD_IS_BLANK);
        }
        dataCenterService.setData("account", account);
        dataCenterService.setData("password", password);
    }
}
