package com.fjnu.assetsManagement.module.bill.service;

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
public class BillRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    HibernateTemplate hibernateTemplate;

    SessionFactory sessionFactory;

    //待入账记录
    public void billOutListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        String otherCondition = "WHERE c.existState<>0 or c.existState=null and c.state=1";
        PageUtil<PurchaseMaster> purchaseMasterList = new PageUtil<>();
        this.getPageList(purchaseMasterList, pageNum, pageSize, PurchaseMaster.class.getSimpleName(), otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", purchaseMasterList);
    }

    //更新资产列表
    private void updateAssets(Date nowDate, List<Long> orderDetailId) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        for (Long id : orderDetailId) {
            long now = System.currentTimeMillis();
            long nowadd = now + 12345;
            String hql = "update Assets a set a.inState=2, a.assetsState=0, a.inAccountTime=:t, a.financeId=:f, a.accountId=:q where a.orderDetailId=:o";
            System.out.println(hql);
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setLong("o", id);
            ((org.hibernate.query.Query) query).setLong("f", now);
            ((org.hibernate.query.Query) query).setLong("q", nowadd);
            ((org.hibernate.query.Query) query).setDate("t", nowDate);
            query.executeUpdate();
        }
    }

    //入账
    public void inBillRequestProcess(){
        sessionFactory = hibernateTemplate.getSessionFactory();
        List<String> orderNo = dataCenterService.getData("orderNo");
        Long entryOperatorId = dataCenterService.getData("entryOperatorId");
        Session session = sessionFactory.getCurrentSession();
        for (String entry : orderNo) {
            Date nowDate = new Date();
            String hql = "update PurchaseMaster p set p.state=2,p.entryTime=:t,p.entryOperatorId=:e where p.orderNo=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setDate("t", nowDate);
            ((org.hibernate.query.Query) query).setString("o", entry);
            ((org.hibernate.query.Query) query).setLong("e", entryOperatorId);
            query.executeUpdate();
            String getPurchaseDetailHql = "from PurchaseMaster pm where pm.orderNo=:o";
            Query query1 = session.createQuery(getPurchaseDetailHql);
            ((org.hibernate.query.Query) query1).setString("o", entry);
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

    //分页设置
    public void getPageList(PageUtil purchaseMasterList, int pageNum, int pageSize, String className, String otherCondition) {
        sessionFactory = hibernateTemplate.getSessionFactory();
        String  wu= null;
        Session session = sessionFactory.getCurrentSession();
        String getSizeHql = "select count(*) from " + className + " c " + otherCondition;
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();
        purchaseMasterList.init(size, pageNum, pageSize);
        int curPageNum = purchaseMasterList.getPageNum();
        int curPageSize = purchaseMasterList.getPageSize();
        int firstResult = (curPageNum - 1) * curPageSize;
        String getListHql = "from " + className + " c " + otherCondition;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        purchaseMasterList.setList(((org.hibernate.query.Query) getListQuery).list());
    }

    //入账记录
    public void billListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        String otherCondition = "WHERE c.state=2";
        PageUtil<PurchaseMaster> purchaseMasterList = new PageUtil<>();
        this.getPageList(purchaseMasterList, pageNum, pageSize, PurchaseMaster.class.getSimpleName(), otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", purchaseMasterList);

    }
}
