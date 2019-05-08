package com.fjnu.assetsManagement.module.getSysMenue.service;

import com.fjnu.assetsManagement.module.getSysMenue.enums.GetSysMenueReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetSysMenueRequestCheckService {
    @Autowired
    private DataCenterService dataCenterService;

    public void getSysMenueRequestCheck() {
        String account = dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("account");
        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), GetSysMenueReasonOfFailure.ACCOUNT_IS_BLANK);
        }
        dataCenterService.setData("account", account);
    }
}
