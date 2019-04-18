package com.fjnu.assetsManagement.module.helloWorld.service;

import com.fjnu.assetsManagement.entity.Product;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelloWordRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    public void helloWorldRequestProcess() {
        String userName = dataCenterService.getData("userName");//从容器中获取数据
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> list = Lists.newArrayList();
        product1.setId(1);
        product1.setName("测试1");
        product2.setId(2);
        product2.setName("测试2");
        list.add(product1);
        list.add(product2);
        //操作完成后返回给前台数据
        ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.putValueToData(responseData, "list", list);
    }
}
