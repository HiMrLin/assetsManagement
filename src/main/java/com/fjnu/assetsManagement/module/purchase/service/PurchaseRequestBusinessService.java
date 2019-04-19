package com.fjnu.assetsManagement.module.purchase.service;

import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.entity.PurchaseMaster;
import com.fjnu.assetsManagement.service.DataCenterService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class PurchaseRequestBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private SessionFactory sessionFactory;

    //添加采购主表记录以及采购详表记录
    public void addPurchaseItemServiceProcess() {

        //读取数据
        //PurchaseMaster purchaseMaster = dataCenterService.getData("purchaseMaster");
        //获取操作员以及采购单号
        String operator = dataCenterService.getData("operator");
        Long orderNo = dataCenterService.getData("orderNo");
        //获取采购的详细物品
        Set<PurchaseDetail> purchaseDetailSet = dataCenterService.getData("purchaseDetailSet");

        PurchaseMaster purchaseMaster = new PurchaseMaster();
        purchaseMaster.setOrderNo(orderNo);
        purchaseMaster.setOrderTime(new Date());
        purchaseMaster.setOperator(operator);
        purchaseMaster.setState(0L);
        //建立关联
        Double totalPrice = 0d;
        for (PurchaseDetail purchaseDetail : purchaseDetailSet) {
            purchaseDetail.setPurchaseMaster(purchaseMaster);
            purchaseMaster.getPurchaseDetailSet().add(purchaseDetail);
            //计算总价
            totalPrice += purchaseDetail.getUnitPrice() * purchaseDetail.getQuantity();
        }

        dataCenterService.setData("totalPrice", totalPrice);
        purchaseMaster.setTotalPrice(totalPrice);
        //存入数据库
        Session session = sessionFactory.openSession();
        session.save(purchaseMaster);

        //关闭
        session.close();
    }
}
