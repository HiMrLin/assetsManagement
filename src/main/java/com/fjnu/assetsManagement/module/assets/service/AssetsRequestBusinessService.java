package com.fjnu.assetsManagement.module.assets.service;

import com.fjnu.assetsManagement.entity.Assets;
import com.fjnu.assetsManagement.entity.RecordReceive;
import com.fjnu.assetsManagement.entity.ResponseData;
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
        String otherCondition = "c.scrapState=0";
        this.getPageList(assets, pageNum, pageSize, Assets.class.getSimpleName(),otherCondition);
        List<Assets> assetsList = assets.getList();
        for (Assets x:assets.getList()) {
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assets);
    }


    //分页设置
    public void getPageList(PageUtil list, int pageNum, int pageSize, String className,String otherCondition) {
        Session session = sessionFactory.openSession();
        String getSizeHql = "select count(*) from " + className + " c  where " +otherCondition;
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();
        list.init(size, pageNum, pageSize);
        int curPageNum = list.getPageNum();
        int curPageSize = list.getPageSize();
        int firstResult = (curPageNum - 1) * curPageSize;
        String getListHql = "from " + className + " c where " +otherCondition ;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        list.setList(((org.hibernate.query.Query) getListQuery).list());
    }

    //显示可领用资产列表
    public void useListRequestProcess(){
        Session session = sessionFactory.openSession();
        String Hql = "from Assets where scrapState=0 and getState=0" ;
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
            String hql = "update Assets a set a.userName=:u, a.depository=:t where a.cardId=:o";
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
            recordReceive.setRecorder("刘光文");
            long now = System.currentTimeMillis();
            recordReceive.setReceiveId(now);
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
        String otherCondition = "c.getState=1";
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
            String Hql = "from Assets a where a. = "+ recordReceive1.getAssetsId();
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
        for (Long long1:receiveIdList) {

        }
    }
}
