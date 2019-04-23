package com.fjnu.assetsManagement.module.bill.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.module.bill.enums.BillReasonOfFailure;
import com.fjnu.assetsManagement.module.entry.enums.EntryReasonOfFailure;
import com.fjnu.assetsManagement.module.helloWorld.enums.HelloWorldReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.CheckVariableUtil;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;
    public void pageCheck(){
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum,pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), BillReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);
    }

    public void billOutListRequestCheck(){
        pageCheck();
    }

    public void inBillRequestCheck(){
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNoItems");
        List<String> orderNoList = array.toJavaList(String.class);

        if (orderNoList.size() <= 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), BillReasonOfFailure.ORDERNO_IS_NOT_BLANK);
        }

        dataCenterService.setData("orderNoList", orderNoList);
    }

    public void billListRequestCheck(){
           pageCheck();
    }

}
