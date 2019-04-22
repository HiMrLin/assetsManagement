package com.fjnu.assetsManagement.module.purchase.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.entity.Dictionary;
import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.module.purchase.enums.PurchaseReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PurchaseRequestCheckService {
    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    SessionFactory sessionFactory;

    //生成采购单号
    private String genernateOrderNo() {
        int r1 = (int) (Math.random() * (10));//产生2个0-9的随机数
        int r2 = (int) (Math.random() * (10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        //生成采购单号
        String orderNo = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);
        return orderNo;
    }

    public Dictionary getDictionary(String kind) {
        Session session = sessionFactory.openSession();
        String hql = "from Dictionary d where d.kind=:k";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setString("k", kind);
        Dictionary dictionary = (Dictionary) ((org.hibernate.query.Query) query).uniqueResult();
        return dictionary;
    }

    //添加采购单参数校验
    public void addPurchaseItemServiceCheck() {
        String operator = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("operator");

        //有可能造成空指针异常，使用tryParse，如果转换失败会返回null，效果更好，不需要在之前判空
        //Long orderNo = Long.valueOf(String.valueOf(dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNo")));
        //Long state = Longs.tryParse((String)dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("state"));

        String orderNo = this.genernateOrderNo();

        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("purchaseDetailSet");
        List<PurchaseDetail> purchaseDetailList = array.toJavaList(PurchaseDetail.class);
        Set<PurchaseDetail> purchaseDetailSet = new HashSet<>(purchaseDetailList);

        //判空
        if (StringUtils.isBlank(operator)) {
            //验证数据不合法后返回前台提示信息
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.OPERATOR_IS_NOT_BLANK);
        }
        if (purchaseDetailSet.isEmpty()) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_BLANK);
        }
        //对于每个purchaseDetail进行判空
        for (PurchaseDetail purchaseDetail : purchaseDetailSet) {
            if (StringUtils.isBlank(purchaseDetail.getKind())) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            }
            if (StringUtils.isBlank(purchaseDetail.getName())) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            }
            if (purchaseDetail.getQuantity() == null) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            }
            if (purchaseDetail.getUnitPrice() == null) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PURCHASEDETAIL_IS_NOT_ENOUGH);
            }
            if (this.getDictionary(purchaseDetail.getKind()) == null) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.KIND_IS_NOT_CORRECT);
            }

        }

        //验证合法后插入容器
        dataCenterService.setData("operator", operator);
        dataCenterService.setData("orderNo", orderNo);
        dataCenterService.setData("purchaseDetailSet", purchaseDetailSet);
    }

    //得到采购列表参数校验
    public void getPurchaseMasterListCheck() {

        //得到分页参数
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");

        if (pageSize == null || pageSize < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        if (pageNum == null || pageNum < 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }

        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);
    }

    //根据采购单号得到采购单列表
    public void getPurchaseMasterListByOrderNoCheck() {
        String orderNo = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNo");

        if (StringUtils.isBlank(orderNo)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.ORDERNO_IS_NOT_BLANK);
        }

        dataCenterService.setData("orderNo", orderNo);
    }

    //根据采购单号数组删除采购单
    public void deletePurchaseMasterListByOrderNoCheck() {
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("orderNoOfDeletePurchaseItems");
        List<String> orderNoList = array.toJavaList(String.class);

        if (orderNoList.size() <= 0) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PurchaseReasonOfFailure.ORDERNO_IS_NOT_BLANK);
        }

        dataCenterService.setData("orderNoList", orderNoList);
    }
}
