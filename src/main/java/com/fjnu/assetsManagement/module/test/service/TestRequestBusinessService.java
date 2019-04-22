package com.fjnu.assetsManagement.module.test.service;

import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.entity.PurchaseMaster;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.google.common.collect.Lists;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class TestRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    private SessionFactory sessionFactory;

    public void testRequestProcess() throws Exception {
        //从容器中获取数据
        String userName = dataCenterService.getData("userName");

        //测试插入一条数据
        PurchaseMaster purchaseMasterTest = new PurchaseMaster();
        List<PurchaseMaster> list = Lists.newArrayList();

        //设置Date格式
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        purchaseMasterTest.setId(12302L);
        purchaseMasterTest.setOrderNo("122222");
        purchaseMasterTest.setTotalPrice(1000d);
        purchaseMasterTest.setOrderTime(sft.parse("2019-04-19"));
        purchaseMasterTest.setOperator("yzq");
        //purchaseMasterTest.setRemark("测试插入");
        purchaseMasterTest.setState(0L);


        //测试一对多关系插入
        PurchaseDetail purchaseDetail1 = new PurchaseDetail();
        purchaseDetail1.setName("电脑");
        purchaseDetail1.setKind("电脑");
        purchaseDetail1.setQuantity(11L);
        purchaseDetail1.setUnitPrice(6000d);


        PurchaseDetail purchaseDetail2 = new PurchaseDetail();
        purchaseDetail2.setName("手机");
        purchaseDetail2.setKind("手机");
        purchaseDetail2.setQuantity(11L);
        purchaseDetail2.setUnitPrice(3000d);

//        Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();
//        purchaseDetailSet.add(purchaseDetail1);
//        purchaseDetailSet.add(purchaseDetail2);
//        purchaseMasterTest.setPurchaseDetailSet(purchaseDetailSet);

        purchaseMasterTest.getPurchaseDetailSet().add(purchaseDetail1);
        purchaseMasterTest.getPurchaseDetailSet().add(purchaseDetail2);
        System.out.println(purchaseDetail1);
        System.out.println(purchaseDetail2);
        System.out.println(purchaseDetail1.hashCode());
        System.out.println(purchaseDetail2.hashCode());
        System.out.println(purchaseDetail2.equals(purchaseDetail1));
        System.out.println(purchaseMasterTest);
        purchaseDetail1.setPurchaseMaster(purchaseMasterTest);
        purchaseDetail2.setPurchaseMaster(purchaseMasterTest);
        list.add(purchaseMasterTest);
        //尝试插入表中
        Session testSession = sessionFactory.openSession();
        testSession.beginTransaction();
//        testSession.save(purchaseDetail1);
//        testSession.save(purchaseDetail2);
        testSession.save(purchaseMasterTest);


        testSession.getTransaction().commit();
        testSession.close();

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "list", list);
    }
}
