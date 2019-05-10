package com.fjnu.assetsManagement.module.assets.service;

import com.fjnu.assetsManagement.entity.*;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.AssetsItem;
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

    //按条件查询资产表
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

    //获取仓管
    private String getKeeper(Long id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from SysUser where id="+id;
        List<SysUser> sysUsers = find(hql);
        session.getTransaction().commit();
        if(sysUsers.get(0).getId()==1){
            return "张雷";
        }
        else if(sysUsers.get(0).getId()==2){
            return "陈琪";
        }
        else if(sysUsers.get(0).getId()==3){
            return "吴必辉";
        }
        else{
            return "0";
        }
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

    private List find(String hql){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query getQuery =session.createQuery(hql);
        session.getTransaction().commit();
        return ((org.hibernate.query.Query) getQuery).list();
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
        Long Id = System.currentTimeMillis();
        ReceiveMaster receiveMaster = new ReceiveMaster();
        receiveMaster.setDepart(department);
        receiveMaster.setGetState(1);
        receiveMaster.setNote(note);
        receiveMaster.setPurpose(purpose);
        receiveMaster.setReceiveId(Id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        receiveMaster.setReceiveTime(sdf.format(new Date()));
        session.save(receiveMaster);
        for (Long car:cardIdList) {
            RecordReceive recordReceive = new RecordReceive();
            String Hql = "from Assets a where cardId = "+ car ;
            List<Assets> assetsList = find(Hql);
            recordReceive.setAssetsId(assetsList.get(0).getAssetsId());
            recordReceive.setReceiveId(Id);
            session.save(recordReceive);
        }
        updateAssets(cardIdList,Name,depository);
        session.getTransaction().commit();
        session.close();
    }

    //领用记录
    public void usedListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql;
        PageUtil<ReceiveMaster> receiveMaster = new PageUtil<>();
        String otherCondition = "  ";
        AssetsItem assetsItem = new AssetsItem();
        this.getPageList(receiveMaster, pageNum, pageSize, ReceiveMaster.class.getSimpleName(),otherCondition);
        for (ReceiveMaster receiveMaster1:receiveMaster.getList()) {
            for (RecordReceive recordReceive1:receiveMaster1.getRecordReceive()) {
                hql = "from Assets where assetsId = "+recordReceive1.getAssetsId();
                List<Assets> assetsList  = find(hql);
                assetsItem.setAsssetsName(assetsList.get(0).getAssetsName());
                assetsItem.setPrice(assetsList.get(0).getUnitPrice());
                assetsItem.setKind(assetsList.get(0).getKind());
                assetsItem.setQuantity(assetsList.get(0).getQuantity());
                recordReceive1.setAssetsItem(assetsItem);
            }
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "ReceiveMasterList", receiveMaster);
    }
    //归还
    public void returnRequestProcess(){
        List<Long> receiveIdList = dataCenterService.getData("receiveIdList");
        Long nameId =dataCenterService.getData("nameId");
        String Hql;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();
        session.clear();
        Integer state;
        List<String> assets = new ArrayList<>();
        Hql = "from SysUser where id=" +nameId;
        List<SysUser> sysUser = find(Hql);
        for (Long receiveId:receiveIdList) {
            RecordReturn recordReturn = new RecordReturn();
            Hql = "from ReceiveMaster  where receiveId = "+ receiveId ;
            List<ReceiveMaster> receiveMasters = find(Hql);
            for (ReceiveMaster receiveMaster1:receiveMasters) {
                for (RecordReceive recordReceive1:receiveMaster1.getRecordReceive()) {
                    String assetsIdHql = "from Assets a where a.assetsId = "+ recordReceive1.getAssetsId();
                    List<Assets> assetsList =find(assetsIdHql);
                    state=assetsList.get(0).getAssetsState();
                    if(state==1) {
                        assets.add(assetsList.get(0).getAssetsId());
                    }
                }
            }
            recordReturn.setReturnName(sysUser.get(0).getUserName());
            recordReturn.setReturnId(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            recordReturn.setReturnTime(sdf.format(new Date()));
            session.save(recordReturn);
        }
        updateRecordReceive(receiveIdList);
        updateAssetsList(assets,getKeeper(nameId));
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

    public void updateAssetsList(List<String>assetsId,String name){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (String assets:assetsId) {
            String shql = "update Assets a set a.userName=null,a.assetsState=0, a.depository=:t where a.assetsId=:o";
            Query query1 = session.createQuery(shql);
            ((org.hibernate.query.Query) query1).setString("t", name);
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
        String hql = "update Assets a set a.assetsState=2 and userName=:n where a.assetsId=:o";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setString("o", assetsId);
        ((org.hibernate.query.Query) query).setString("o", null);

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
