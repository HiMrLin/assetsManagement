package com.fjnu.assetsManagement.module.dictionary.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.module.dictionary.enums.DictionaryReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    SessionFactory sessionFactory;

    //验证添加数据字典参数
    public void addDictionaryItemServiceCheck() {
        //得到要添加的类别名称
        String kind = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kind");
        Integer quantityState = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("quantityState");

        if (StringUtils.isBlank(kind)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), DictionaryReasonOfFailure.KIND_IS_NOT_BLANK);
        }
        if (quantityState == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), DictionaryReasonOfFailure.QUANTITY_STATE_IS_NOT_BLANK);
        }

        dataCenterService.setData("kind", kind);
        dataCenterService.setData("quantityState", quantityState);
    }

    //验证根据ID得到数据字典参数
    public void getDictionaryByIdCheck() {
        //得到要搜索的id
        Integer curId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        Long id = curId.longValue();

        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), DictionaryReasonOfFailure.ID_IS_NOT_BLANK);
        }

        dataCenterService.setData("id", id);
    }

    //验证删除数据字典参数
    public void deleteDictionaryListByIdCheck() {
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("idOfDeleteDictionaryItems");
        List<Long> idList = array.toJavaList(Long.class);

        if (idList.size() <= 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), DictionaryReasonOfFailure.ID_IS_NOT_BLANK);
        }

        dataCenterService.setData("idList", idList);
    }
}
