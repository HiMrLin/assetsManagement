package com.fjnu.assetsManagement.module.assets.service;

import com.fjnu.assetsManagement.entity.*;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.AssetsItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.Query;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class AssetsRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;

    @Autowired
    HibernateTemplate hibernateTemplate;




    //资产列表
    public void assetsListRequestProcess() throws SQLException, UnsupportedEncodingException {
        Long userId = dataCenterService.getData("userId");
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        String departmentName = getDepartmentName(userId);
        PageUtil<Assets> assets = new PageUtil<>();
        String otherCondition = "where  (inState=1 or inState=2) and ascription = '"+departmentName+"'";
        this.getPageList(assets, pageNum, pageSize, Assets.class.getSimpleName(),otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assets);
    }


    //分页设置
    public void  getPageList(PageUtil list, int pageNum, int pageSize, String className,String otherCondition) {
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
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

    //根据ID获取部门
    public String getDepartmentName(Long id){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String Hql = "from SysUser where id=" +id;
        List<SysUser> sysUser = find(Hql);
        return sysUser.get(0).getSysDepartment().getName();
    }

    //按条件查询资产表
    public void useListRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Integer kindId = dataCenterService.getData("kindId");
        String department = dataCenterService.getData("department");
        String kind ;
        String Hql;
        String hqlKind;
        Session session = sessionFactory.getCurrentSession();
        hqlKind = "from Dictionary where id=" + kindId;
        List<Dictionary> dictionaryList = find(hqlKind);
        if(dictionaryList.size()==0){
            kind = "";
        }
        else {
            kind=dictionaryList.get(0).getKind();
        }
        Hql = "from Assets a where a.assetsState=0 and a.inState=2  and kind=IFNULL(NULLIF('"+kind+"',''),kind) AND ascription=IFNULL(NULLIF('"+department+"',''),ascription)";
        List<Assets> assetsList = find(Hql);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assetsList);
    }

    //获取仓管
    private String getKeeper(Long id){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String hql = "from SysUser where id="+id;
        List<SysUser> sysUsers = find(hql);
        if(sysUsers.get(0).getSysDepartment().getId()==1){
            return "张雷";
        }
        else if(sysUsers.get(0).getSysDepartment().getId()==2){
            return "陈琪";
        }
        else if(sysUsers.get(0).getSysDepartment().getId()==3){
            return "吴必辉";
        }
        else{
            return "0";
        }
    }

    //更新资产表
    private void updateAssets(List<Long> cardId,String userName,String depository) {
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        for (Long id : cardId) {
            String hql = "update Assets a set a.userName=:u, a.depository=:t, a.assetsState=1 where a.cardId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setString("u",userName);
            ((org.hibernate.query.Query) query).setString("t",depository);
            ((org.hibernate.query.Query) query).setLong("o", id);
            query.executeUpdate();
        }
    }

    //根据用户ID获取用户名
    private String getName(Long id){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String Hql = "from SysUser where id=" +id;
        List<SysUser> sysUser = find(Hql);
        return sysUser.get(0).getUserName();
    }

    private List find(String hql){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Query getQuery =session.createQuery(hql);
        return ((org.hibernate.query.Query) getQuery).list();
    }

    //资产领用
    public void useRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long userId = dataCenterService.getData("userId");
        String depository = getName(userId);
        String purpose = dataCenterService.getData("purpose");
        String note = dataCenterService.getData("note");
        List<Long> cardIdList = dataCenterService.getData("cardIdList");
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        Long Id = System.currentTimeMillis();
        ReceiveMaster receiveMaster = new ReceiveMaster();
        receiveMaster.setDepart(getDepartmentName(userId));
        receiveMaster.setGetState(1);
        receiveMaster.setNote(note);
        receiveMaster.setPurpose(purpose);
        receiveMaster.setReceiveId(Id);
        receiveMaster.setUserId(userId);
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
        updateAssets(cardIdList,getName(userId),depository);
    }

    //领用记录
    public void usedListRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        Long userId = dataCenterService.getData("userId");
        String otherCondition;
        Session session = sessionFactory.getCurrentSession();
        String Hql = "from SysUser where id=" +userId;
        List<SysUser> sysUser = find(Hql);
        if(sysUser.get(0).getSysRoleAcl().getId()==1){
            otherCondition = "where depart = '"+getDepartmentName(userId)+"'";
        }
        else {
            otherCondition = "where depart = '"+getDepartmentName(userId)+"' and userId="+userId;
        }
        PageUtil<ReceiveMaster> receiveMaster = new PageUtil<>();
        AssetsItem assetsItem = new AssetsItem();
        this.getPageList(receiveMaster, pageNum, pageSize, ReceiveMaster.class.getSimpleName(),otherCondition);
        for (ReceiveMaster receiveMaster1:receiveMaster.getList()) {
            receiveMaster1.setUserName(getName(receiveMaster1.getUserId()));
            for (RecordReceive recordReceive1:receiveMaster1.getRecordReceive()) {
                String hql = "from Assets where assetsId = "+recordReceive1.getAssetsId();
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
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        List<Long> receiveIdList = dataCenterService.getData("receiveIdList");
        Long nameId =dataCenterService.getData("nameId");
        String Hql;
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        Integer state;
        List<String> assets = new ArrayList<>();
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
            recordReturn.setReturnName(getName(nameId));
            recordReturn.setReturnId(System.currentTimeMillis());
            recordReturn.setReceiveId(receiveId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            recordReturn.setReturnTime(sdf.format(new Date()));
            session.save(recordReturn);
        }
        updateRecordReceive(receiveIdList);
        updateAssetsList(assets,getKeeper(nameId));
    }

    public void updateRecordReceive(List<Long> receiveIdList){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        for (Long receiveIdList1:receiveIdList) {
            String hql = "update  ReceiveMaster a set a.getState=0 where a.receiveId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setLong("o", receiveIdList1);
            query.executeUpdate();
        }
    }

    public void updateAssetsList(List<String>assetsId,String name){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        for (String assets:assetsId) {
            String shql = "update Assets a set a.userName=null,a.assetsState=0, a.depository=:t where a.assetsId=:o";
            Query query1 = session.createQuery(shql);
            ((org.hibernate.query.Query) query1).setString("t", name);
            ((org.hibernate.query.Query) query1).setString("o", assets);
            query1.executeUpdate();
        }
    }

    //报废
    public void scrapRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long notifier = dataCenterService.getData("userId");
        String assetsId = dataCenterService.getData("assetsId");
        String note = dataCenterService.getData("note");
        Session session = sessionFactory.getCurrentSession();
        RecordScrap recordScrap = new RecordScrap();
        recordScrap.setAssetsId(assetsId);
        recordScrap.setNote(note);
        recordScrap.setNotifier(getName(notifier));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        recordScrap.setScrapId(System.currentTimeMillis());
        recordScrap.setDepartment(getDepartmentName(notifier));
        recordScrap.setScrapTime(sdf.format(new Date()));
        String hql = "update Assets a set a.assetsState=2, userName=:n, depository=:n where a.assetsId=:o";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setString("o", assetsId);
        ((org.hibernate.query.Query) query).setString("n", null);
        query.executeUpdate();
        session.save(recordScrap);
    }

    //报废列表
    public void scrapListRequestProcess(){
        Long userId = dataCenterService.getData("userId");
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        PageUtil<RecordScrap> recordScrap = new PageUtil<>();
        String otherCondition = "where department = '"+getDepartmentName(userId)+"'";
        this.getPageList(recordScrap, pageNum, pageSize, RecordScrap.class.getSimpleName(),otherCondition);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "RecordScrap", recordScrap);
    }

    //根据id得到移交记录
    public void transferListRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        Long userId = dataCenterService.getData("userId");
        Session session = sessionFactory.getCurrentSession();
        String hql;
        PageUtil<TransferMaster> transferMaster = new PageUtil<>();
        String otherCondition = "where  ownerId = "+ userId+" or currentId = "+userId;
        AssetsItem assetsItem = new AssetsItem();
        this.getPageList(transferMaster, pageNum, pageSize, TransferMaster.class.getSimpleName(),otherCondition);
        for (TransferMaster transferMaster1:transferMaster.getList()) {
            if(transferMaster1.getOwnerId().equals(userId)||transferMaster1.getState()==1){
                transferMaster1.setAlc(false);
            }
            //设置发起者姓名和接受者姓名
            SysUser owner = session.get(SysUser.class, transferMaster1.getOwnerId());
            SysUser current = session.get(SysUser.class, transferMaster1.getCurrentId());
            transferMaster1.setOwnerName(owner.getUserName());
            transferMaster1.setCurrentName(current.getUserName());

            for (TransferDetail transferDetail:transferMaster1.getTransferDetailList()) {
                hql = "from Assets where assetsId = "+transferDetail.getAssetsId();
                List<Assets> assetsList  = find(hql);
                assetsItem.setAsssetsName(assetsList.get(0).getAssetsName());
                assetsItem.setPrice(assetsList.get(0).getUnitPrice());
                assetsItem.setKind(assetsList.get(0).getKind());
                assetsItem.setQuantity(assetsList.get(0).getQuantity());
                transferDetail.setAssetsItem(assetsItem);
            }
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "TransferMaster", transferMaster);
    }

    //个人资产表
    public void ownerAssetsListRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long userId = dataCenterService.getData("userId");
        Session session = sessionFactory.getCurrentSession();
        String userName = getName(userId);
        String hql = "from Assets where (assetsState=0 or assetsState=1) and (inState=1 or inState=2) and depository ='"+userName+"'";
        List<Assets> assetsList = find(hql);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Assets", assetsList);
    }

    //发起移交
    public void transferRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long userId = dataCenterService.getData("userId");
        Long currentId = dataCenterService.getData("currentId");

        //得到需要移交的资产id列表
        List<Long> cardIdList = dataCenterService.getData("cardIdList");

        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        Long Id = System.currentTimeMillis();
        TransferMaster transferMaster = new TransferMaster();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transferMaster.setTime(sdf.format(new Date()));
        transferMaster.setId(Id);
        transferMaster.setOwnerId(userId);
        transferMaster.setCurrentId(currentId);
        transferMaster.setState(0);
        session.save(transferMaster);
        for (Long car:cardIdList) {
            TransferDetail transferDetail = new TransferDetail();
            /*String Hql = "from Assets a where cardId = "+ car ;
            List<Assets> assetsList = find(Hql);
            transferDetail.setAssetsId(assetsList.get(0).getAssetsId());*/

            Assets curAssets = session.get(Assets.class, car);
            //设置资产状态为3 即移交中，不可用
            curAssets.setAssetsState(3);
            session.save(curAssets);

            transferDetail.setAssetsId(curAssets.getAssetsId());
            transferDetail.setTransferId(Id);
            session.save(transferDetail);
        }
    }

    //得到可移交用户列表
    /*
    * 目前思路：移交对象为同一分公司的其他员工
    * */
    public void getCouldTransferUserListProcess() {
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        //取出当前用户即移交发起人id
        Long id = dataCenterService.getData("id");
        //根据id得到该用户对象从而得到他所在的分公司
        Session session = sessionFactory.getCurrentSession();
        SysUser currentUser = session.get(SysUser.class, id);
        SysDepartment department = currentUser.getSysDepartment();
        Long departmentId = department.getId();
        //根据所得到的分公司id得到该分公司的其他所有用户
        String hql = "select new SysUser(id, account, userName) from SysUser s where s.id<>:i and s.sysDepartment.id=:d";
        Query query = session.createQuery(hql);
        ((org.hibernate.query.Query) query).setLong("i", id);
        ((org.hibernate.query.Query) query).setLong("d", departmentId);
        List<SysUser> couldTransferUsers = ((org.hibernate.query.Query) query).list();

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "couldTransferUsers", couldTransferUsers);

    }


    //移交核对
    public void transferCheckRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long transferId = dataCenterService.getData("transferId");
        Session session = sessionFactory.getCurrentSession();
        Long userId = dataCenterService.getData("userId");
        //得到transferMaster
        TransferMaster transferMaster = session.get(TransferMaster.class, transferId);
        //更新移交状态为1
        transferMaster.setState(1);
        //添加核对时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        transferMaster.setCheckTime(sdf.format(new Date()));
        //保存
        session.save(transferMaster);
        /*String hql = "update TransferMaster a set a.state=1 where a.id=:o";
        Query query1 = session.createQuery(hql);
        ((org.hibernate.query.Query) query1).setLong("o", transferId);
        query1.executeUpdate();*/
        String userName = getName(userId);
        String hql = "from TransferDetail where transferId="+transferId;
        List<TransferDetail> transferDetails = find(hql);
        List<String> assets = new ArrayList<>();
        for (TransferDetail transferDetail1:transferDetails) {
            assets.add(transferDetail1.getAssetsId());
        }
        for (String i:assets) {
            hql = "update Assets a set a.depository=:u, a.assetsState=0 where a.assetsId=:o";
            Query query = session.createQuery(hql);
            ((org.hibernate.query.Query) query).setString("o", i);
            ((org.hibernate.query.Query) query).setString("u", userName);
            query.executeUpdate();
        }
    }

    //发起调拨
    public void allotRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        String owner = dataCenterService.getData("owner");
        String current = dataCenterService.getData("current");
        List<Long> cardIdList = dataCenterService.getData("cardIdList");
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        Long Id = System.currentTimeMillis();
        AllotMaster allotMaster = new AllotMaster();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        allotMaster.setStartTime(sdf.format(new Date()));
        allotMaster.setAllotId(Id);
        allotMaster.setOwner(owner);
        allotMaster.setCurrent(current);
        allotMaster.setState(0);
        session.save(allotMaster);
        for (Long car1:cardIdList) {
            AllotDetail allotDetail = new AllotDetail();
            /*String Hql = "from Assets a where cardId = "+ car1 ;
            List<Assets> assetsList = find(Hql);
            allotDetail.setAssetsId(assetsList.get(0).getAssetsId());*/
            //设置资产状态为4 即调拨中，不可用
            Assets curAssets = session.get(Assets.class, car1);
            curAssets.setAssetsState(4);
            session.save(curAssets);

            allotDetail.setAssetsId(curAssets.getAssetsId());
            allotDetail.setAllotId(Id);
            session.save(allotDetail);
        }
    }

    //调拨列表
    public void allotListRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        //获取参数
        Long userId  = dataCenterService.getData("userId");
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");

        Session session = sessionFactory.getCurrentSession();
        String hql;
        PageUtil<AllotMaster> allotMaster = new PageUtil<>();

        //得到当前用户所在部门名称
        hql = "from SysUser where id=" +userId;
        List<SysUser> sysUser = find(hql);
        String departmentName = sysUser.get(0).getSysDepartment().getName();
        String otherCondition;
        //判断是不是总公司，是总公司的话可以返回全部所有的信息
        if(sysUser.get(0).getSysDepartment().getId() == 1)
            otherCondition = "where  1=1";
        else
            otherCondition = "where  owner = '"+ departmentName+"' or current = '"+departmentName+"'";

        AssetsItem assetsItem = new AssetsItem();
        this.getPageList(allotMaster, pageNum, pageSize, AllotMaster.class.getSimpleName(),otherCondition);

        for (AllotMaster allotMaster1:allotMaster.getList()) {
            //关于alc, ture-可核对 false-不可核对
            //如果是调出方并且该方已经核对，或者是完全完成的调拨记录
            if((allotMaster1.getOwner().equals(departmentName)&&allotMaster1.getState()==1)||allotMaster1.getState()==2){
                allotMaster1.setAlc(false);
            }
            //如果是调入方并且无人核对即待接收的调拨请求，或者是完全完成的订单
            else if ((allotMaster1.getCurrent().equals(departmentName)&&allotMaster1.getState()==0)||allotMaster1.getState()==2){
                allotMaster1.setAlc(false);
            }
            //填充进vo的assetItems对象
            for (AllotDetail allotDetail1:allotMaster1.getAllotDetailList()) {
                hql = "from Assets where assetsId = '"+allotDetail1.getAssetsId() + "'";
                List<Assets> assetsList  = find(hql);
                assetsItem.setAsssetsName(assetsList.get(0).getAssetsName());
                assetsItem.setPrice(assetsList.get(0).getUnitPrice());
                assetsItem.setKind(assetsList.get(0).getKind());
                assetsItem.setQuantity(assetsList.get(0).getQuantity());
                allotDetail1.setAssetsItem(assetsItem);
            }
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "AllotMaster", allotMaster);

    }

    //调拨核对
    public void allotCheckRequestProcess(){
        SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
        Long AllotId = dataCenterService.getData("AllotId");
        Long userId = dataCenterService.getData("userId");
        Session session = sessionFactory.getCurrentSession();
        String hql;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        hql = "from SysUser where id=" +userId;
        List<SysUser> sysUser = find(hql);
        String departmentName = sysUser.get(0).getSysDepartment().getName();
        hql = "from AllotMaster where allotId = " +AllotId;
        List<AllotMaster> allotMasters = find(hql);
        if (allotMasters.get(0).getOwner().equals(departmentName)){
            hql = "update AllotMaster a set a.state=1,midTime=:t where a.id=:o";
            Query query1 = session.createQuery(hql);
            ((org.hibernate.query.Query) query1).setLong("o", AllotId);
            ((org.hibernate.query.Query) query1).setString("t", sdf.format(new Date()));
            query1.executeUpdate();
        }
        else {
            hql = "update AllotMaster a set a.state=2, endTime=:t where a.id=:o";
            Query query1 = session.createQuery(hql);
            ((org.hibernate.query.Query) query1).setLong("o", AllotId);
            ((org.hibernate.query.Query) query1).setString("t", sdf.format(new Date()));
            query1.executeUpdate();
            hql = "from AllotDetail where allotId="+AllotId;
            List<AllotDetail> allotDetails = find(hql);
            List<String> assets = new ArrayList<>();
            for (AllotDetail allotDetail1:allotDetails) {
                assets.add(allotDetail1.getAssetsId());
            }
            for (String i:assets) {
                hql = "update Assets a set a.assetsState=0, a.depository=:u, a.ascription=:d where a.assetsId=:o";
                Query query = session.createQuery(hql);
                ((org.hibernate.query.Query) query).setString("d", departmentName);
                ((org.hibernate.query.Query) query).setString("o", i);
                ((org.hibernate.query.Query) query).setString("u", getKeeper(userId));
                query.executeUpdate();
            }
        }
    }


}
