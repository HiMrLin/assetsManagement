package com.fjnu.assetsManagement.module.getSysMenue.service;

import com.fjnu.assetsManagement.module.getSysMenue.enums.GetSysMenueReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetSysMenueRequestCheckService {
    @Autowired
    private DataCenterService dataCenterService;

    public void getSysMenueRequestCheck() {
        String idStr = dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id");
        Long id = Longs.tryParse(idStr);
        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), GetSysMenueReasonOfFailure.Id_IS_BLANK);
        }
        dataCenterService.setData("id", id);
    }
}
