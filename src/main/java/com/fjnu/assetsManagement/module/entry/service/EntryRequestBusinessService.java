package com.fjnu.assetsManagement.module.entry.service;

import com.fjnu.assetsManagement.entity.PurchaseDetail;
import com.fjnu.assetsManagement.entity.PurchaseMaster;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class EntryRequestBusinessService {

    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    HibernateTemplate hibernateTemplate;

    SessionFactory sessionFactory;


    private void updateAssets(Date nowDate, List<Long> orderDetailId) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        for (Long id : orderDetailId) {
            String hql = "update Assets a set a.inState=1, a.inTime=:t where a.orderDetailId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setLong("o", id);
            ((org.hibernate.query.Query) query).setDate("t", nowDate);
            query.executeUpdate();
        }
    }

    //入库
    public void entryProcess() {
        sessionFactory = hibernateTemplate.getSessionFactory();
        //取得数据
        List<String> orderNoList = dataCenterService.getData("orderNoList");
        Long inOperatorId = dataCenterService.getData("inOperatorId");

        //进行入库——将state属性改为1
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        for (String ordeNo : orderNoList) {

            Date nowDate = new Date();
            String hql = "update PurchaseMaster p set p.state=1, p.inTime=:n, p.inOperatorId=:i where p.orderNo=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setString("o", ordeNo);
            ((org.hibernate.query.Query) query).setLong("i", inOperatorId);
            ((org.hibernate.query.Query) query).setDate("n", nowDate);
            query.executeUpdate();

            String getPurchaseDetailHql = "from PurchaseMaster pm where pm.orderNo=:o";
            Query query1 = session.createQuery(getPurchaseDetailHql);
            ((org.hibernate.query.Query) query1).setString("o", ordeNo);
            List<PurchaseMaster> purchaseMasterList = ((org.hibernate.query.Query) query1).list();
            List<Long> orderDetailId = new ArrayList<>();
            for (PurchaseMaster purchaseMaster : purchaseMasterList) {
                for (PurchaseDetail purchaseDetail : purchaseMaster.getPurchaseDetailSet()) {
                    orderDetailId.add(purchaseDetail.getId());
                }
            }
            this.updateAssets(nowDate, orderDetailId);

        }

    }

    //分页方法
    public void getPageList(PageUtil purchaseMasterList, int pageNum, int pageSize, String className, String otherCondition) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String getSizeHql = "select count(*) from " + className + " c " + otherCondition;
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();

        purchaseMasterList.init(size, pageNum, pageSize);
        //得到记录
        int curPageNum = purchaseMasterList.getPageNum();
        int curPageSize = purchaseMasterList.getPageSize();
        int firstResult = (curPageNum - 1) * curPageSize;
//        System.out.println("第一条：" + firstResult);
//        System.out.println("取几条：" + curPageSize);
        String getListHql = "from " + className + " c " + otherCondition;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        purchaseMasterList.setList(((org.hibernate.query.Query) getListQuery).list());

    }

    //得到已入库采购单列表
    public void getInPurchaseMasterListProcess() {
        //设置分页
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");

        //hql语句其他条件
        String otherCondition = "WHERE (c.existState<>0 or c.existState = null) and (c.state=1 or c.state=2) ";
        PageUtil<PurchaseMaster> purchaseMasterList = new PageUtil<>();
        this.getPageList(purchaseMasterList, pageNum, pageSize, PurchaseMaster.class.getSimpleName(), otherCondition);

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", purchaseMasterList);
    }

    //得到未入库采购单列表
    public void getOutPurchaseMasterListProcess() {
        //设置分页
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");

        //hql语句其他条件
        String otherCondition = "WHERE (c.existState<>0 or c.existState = null) and c.state=0";
        PageUtil<PurchaseMaster> purchaseMasterList = new PageUtil<>();
        this.getPageList(purchaseMasterList, pageNum, pageSize, PurchaseMaster.class.getSimpleName(), otherCondition);

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", purchaseMasterList);

    }

    //根据采购单号得到采购单记录
    public void getPurchaseMasterListByOrderNoProcess() {

        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String hql = "from PurchaseMaster p WHERE p.orderNo=:oo and (p.existState<>0 or p.existState = null)";
        Query query = session.createQuery(hql);
        String orderNo = dataCenterService.getData("orderNo");
        ((org.hibernate.query.Query) query).setString("oo", orderNo);
        PurchaseMaster purchaseMaster = (PurchaseMaster) ((org.hibernate.query.Query) query).uniqueResult();


        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMaster", purchaseMaster);
    }

}
