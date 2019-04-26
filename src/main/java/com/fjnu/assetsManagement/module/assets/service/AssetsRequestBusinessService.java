package com.fjnu.assetsManagement.module.assets.service;

import com.fjnu.assetsManagement.entity.*;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.Receive;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AssetsRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SessionFactory sessionFactory;

    //资产列表
    public void assetsListRequestProcess() throws SQLException, UnsupportedEncodingException {
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        PageUtil<Assets> assets = new PageUtil<>();
        String otherCondition = "where c.assetsState=0 and inState=1 or inState=2";
        this.getPageList(assets, pageNum, pageSize, Assets.class.getSimpleName(),otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assets);
    }


    //分页设置
    public void getPageList(PageUtil list, int pageNum, int pageSize, String className,String otherCondition) {
        Session session = sessionFactory.openSession();
        String getSizeHql = "select count(*) from " + className + " c " +otherCondition;
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();
        list.init(size, pageNum, pageSize);
        int curPageNum = list.getPageNum();
        int curPageSize = list.getPageSize();
        int firstResult = (curPageNum - 1) * curPageSize;
        String getListHql = "from " + className + " c  " +otherCondition ;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        list.setList(((org.hibernate.query.Query) getListQuery).list());
    }

    //显示可领用资产列表
    public void useListRequestProcess(){
        Integer kindId = dataCenterService.getData("kindId");
        String Hql;
        String hqlKind;
        Session session = sessionFactory.openSession();
        if (kindId == null) {
            Hql = "from Assets where assetsState=0 and inState=1 or inState=2";
        } else {
            hqlKind = "from Dictionary where id=" + kindId;
            Query getQuery = session.createQuery(hqlKind);
            List<Dictionary> dictionaryList = ((org.hibernate.query.Query) getQuery).list();
            dictionaryList.get(0).getKind();
            Hql = "from Assets a where a.assetsState=0 and (a.inState=1 or a.inState=2) and a.kind= '" + dictionaryList.get(0).getKind() + "'";

        }
        Query getListQuery =session.createQuery(Hql);
        List<Assets> assetsList = ((org.hibernate.query.Query) getListQuery).list();
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assetsList);
    }

    //更新资产表
    private void updateAssets(List<Long> cardId,String userName,String depository) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Long id : cardId) {
            String hql = "update Assets a set a.userName=:u, a.depository=:t, a.assetsState=1 where a.cardId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setString("u",userName);
            ((org.hibernate.query.Query) query).setString("t",depository);
            ((org.hibernate.query.Query) query).setLong("o", id);
            query.executeUpdate();
        }
        session.getTransaction().commit();
    }

    //领用
    public void useRequestProcess(){
        String Name = dataCenterService.getData("userName");
        String recorder = dataCenterService.getData("recorder");
        String depository = dataCenterService.getData("depository");
        String department = dataCenterService.getData("department");
        String purpose = dataCenterService.getData("purpose");
        String note = dataCenterService.getData("note");
        List<Long> cardIdList = dataCenterService.getData("cardIdList");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.clear();
        for (Long car:cardIdList) {
            RecordReceive recordReceive = new RecordReceive();
            String Hql = "from Assets a where cardId = "+ car ;
            Query getQuery =session.createQuery(Hql);
            List<Assets> assetsList = ((org.hibernate.query.Query) getQuery).list();
            for (Assets assets1:assetsList) {
                recordReceive.setAssetsId(assets1.getAssetsId());
            }
            recordReceive.setDepart(department);
            recordReceive.setNote(note);
            recordReceive.setPurpose(purpose);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            recordReceive.setReceiveTime(sdf.format(new Date()));
            recordReceive.setRecorder(recorder);
            recordReceive.setReceiveId(System.currentTimeMillis());
            recordReceive.setGetState(1);
            session.save(recordReceive);
        }
        updateAssets(cardIdList,Name,depository);
        session.getTransaction().commit();
        //关闭
        session.close();
    }

    //领用记录
    public void usedListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        PageUtil<RecordReceive> recordReceive = new PageUtil<>();
        String otherCondition = "  ";
        this.getPageList(recordReceive, pageNum, pageSize, RecordReceive.class.getSimpleName(),otherCondition);
        List<Receive> receives = new ArrayList<>();
        for (RecordReceive recordReceive1:recordReceive.getList()) {
            Receive receive = new Receive();
            receive.setAssetsId(recordReceive1.getAssetsId());
            receive.setDate(recordReceive1.getReceiveTime());
            receive.setDepartment(recordReceive1.getDepart());
            receive.setReceiveId(recordReceive1.getReceiveId());
            receive.setPurpose(recordReceive1.getPurpose());
            receive.setRecorder(recordReceive1.getRecorder());
            receive.setNote(recordReceive1.getNote());
            String Hql = "from Assets a where a.assetsId = "+ recordReceive1.getAssetsId();
            Query getQuery =session.createQuery(Hql);
            List<Assets> assetsList = ((org.hibernate.query.Query) getQuery).list();
            for (Assets assets1:assetsList) {
                receive.setAssetsName(assets1.getAssetsName());
                receive.setDepository(assets1.getDepository());
                receive.setUserName(assets1.getUserName());
            }
            receives.add(receive);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Receives", receives);
    }

    //归还
    public void returnRequestProcess(){
        List<Long> receiveIdList = dataCenterService.getData("receiveIdList");
        String returnName =dataCenterService.getData("returnName");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.clear();
        List<String> assets = new ArrayList<>();
        for (Long receiveId:receiveIdList) {
            RecordReturn recordReturn = new RecordReturn();
            String Hql = "from RecordReceive  where receiveId = "+ receiveId ;
            Query getQuery =session.createQuery(Hql);
            List<RecordReceive> recordReceives = ((org.hibernate.query.Query) getQuery).list();
            for (RecordReceive recordReceive1:recordReceives) {
                String assId = recordReceive1.getAssetsId();
                assets.add(assId);
                recordReturn.setAssetsId(assId);
            }
            recordReturn.setReturnName(returnName);
            recordReturn.setReturnId(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            recordReturn.setReturnTime(sdf.format(new Date()));
            session.save(recordReturn);
        }
        updateRecordReceive(receiveIdList);
        updateAssetsList(assets);
        session.getTransaction().commit();
        session.close();
    }

    public void updateRecordReceive(List<Long> receiveIdList){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Long receiveIdList1:receiveIdList) {
            String hql = "update RecordReceive a set a.getState=0 where a.receiveId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setLong("o", receiveIdList1);
            query.executeUpdate();
        }
        session.getTransaction().commit();
    }

    public void updateAssetsList(List<String>assetsId){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (String assets:assetsId) {
            String shql = "update Assets a set a.userName=null,a.assetsState=0, a.depository=:t where a.assetsId=:o";
            Query query1 = session.createQuery(shql);
            ((org.hibernate.query.Query) query1).setString("t", "刘在宁");
            ((org.hibernate.query.Query) query1).setString("o", assets);
            query1.executeUpdate();
        }
        session.getTransaction().commit();
    }

    //报废
    public void scrapRequestProcess(){
        String notifier = dataCenterService.getData("notifier");
        String recorder = dataCenterService.getData("recorder");
        String department = dataCenterService.getData("department");
        String assetsId = dataCenterService.getData("assetsId");
        String note = dataCenterService.getData("note");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        RecordScrap recordScrap = new RecordScrap();
        recordScrap.setAssetsId(assetsId);
        recordScrap.setNote(note);
        recordScrap.setDepartment(department);
        recordScrap.setNotifier(notifier);
        recordScrap.setRecorder(recorder);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        recordScrap.setScrapId(System.currentTimeMillis());
        recordScrap.setScrapTime(sdf.format(new Date()));
        String hql = "update Assets a set a.assetsState=2 where a.assetsId=:o";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setString("o", assetsId);
        query.executeUpdate();
        session.save(recordScrap);
        session.getTransaction().commit();
        session.close();
    }

    //报废列表
    public void scrapListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        PageUtil<RecordScrap> recordScrap = new PageUtil<>();
        String otherCondition = " ";
        this.getPageList(recordScrap, pageNum, pageSize, RecordScrap.class.getSimpleName(),otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "RecordScrap", recordScrap);

    }
}
