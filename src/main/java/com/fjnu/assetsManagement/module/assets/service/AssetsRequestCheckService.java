package com.fjnu.assetsManagement.module.assets.service;

import com.alibaba.fastjson.JSONArray;
import com.fjnu.assetsManagement.entity.SysUser;
import com.fjnu.assetsManagement.module.assets.enums.AssetsReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.CheckVariableUtil;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class AssetsRequestCheckService {
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    DataCenterService dataCenterService;

    public void pageCheck(){
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        if (CheckVariableUtil.pageParamIsIllegal(pageNum,pageSize)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.PAGE_PARAMS_IS_NOT_CORRECT);
        }
        dataCenterService.setData("pageSize", pageSize);
        dataCenterService.setData("pageNum", pageNum);
    }

    public void assetsListRequestCheck() {
       pageCheck();
       Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        dataCenterService.setData("userId", id);
    }

    public void useRequestCheck(){
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        String purpose = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("purpose");
        String note = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("note");
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("cardIdItems");
        List<Long> cardIdList = array.toJavaList(Long.class);
        if (StringUtils.isBlank(purpose)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.PURPOSE_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (cardIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.CARD_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("cardIdList", cardIdList);
        dataCenterService.setData("userId", id);
        dataCenterService.setData("purpose", purpose);
        dataCenterService.setData("note", note);
    }

    public void usedListRequestCheck(){
        pageCheck();
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        dataCenterService.setData("userId", id);
    }

    public void returnRequestCheck(){
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("receiveIdList");
        List<Long> receiveIdList = array.toJavaList(Long.class);
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        if (receiveIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USER_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("receiveIdList", receiveIdList);
        dataCenterService.setData("nameId", id);
    }

    public void scrapRequestCheck(){
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        String note = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("note");
        String assetsId =dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("assetsId");
        if (id<0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.NOTIFITOR_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }

        if (StringUtils.isBlank(assetsId)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.ASSETS_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("userId", id);
        dataCenterService.setData("assetsId", assetsId);
        dataCenterService.setData("note", note);
    }

    public void scrapListRequestCheck(){
        pageCheck();
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        dataCenterService.setData("userId", id);
    }

    public void useListRequestCheck() {
        String kindId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("kindId");
        Integer kind;
        String department  = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
        if (StringUtils.isBlank(kindId)) {
            kind = null;
        }
        else {
            kind = Integer.valueOf(kindId).intValue();
            if (kind < 0) {
                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.KINDIN_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
            }
        }
        dataCenterService.setData("kindId", kind);
        dataCenterService.setData("department", department);
    }

    public void transferListRequestCheck(){
        pageCheck();
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        if (id<0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("userId", id);
    }

    public void ownerAssetsListRequestCheck(){
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        if (id<0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("userId", id);
    }

    //进行移交参数检查
    public void transferRequestCheck(){
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        Long currentId = Longs.tryParse(dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("currentId"));
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("cardIdItems");
        List<Long> cardIdList = array.toJavaList(Long.class);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String Hql = "from SysUser where id=" +id;
        Query getQuery =session.createQuery(Hql);
        List<SysUser> sysUser = ((org.hibernate.query.Query) getQuery).list();
        Hql = "from SysUser where id=" +currentId;
        Query Query =session.createQuery(Hql);
        List<SysUser> sysUser1 = ((org.hibernate.query.Query) Query).list();
        session.getTransaction().commit();
        if (currentId==null){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (sysUser.get(0).getSysDepartment()!=sysUser1.get(0).getSysDepartment()){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.CURRENTER_IS_NOT_THE_SAME_DEPARTMENT); //验证数据不合法后返回前台提示信息
        }
        if (cardIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.CARD_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("userId", id);
        dataCenterService.setData("currentId", currentId);
        dataCenterService.setData("cardIdList", cardIdList);
    }

    //得到可移交用户列表参数检查
    public void getCouldTransferUserListCheck() {

        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        if (id == null){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.USERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }

        dataCenterService.setData("id", id);
    }


    public void transferCheckRequestCheck(){
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        Long transferId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("transferId");
        if(transferId==null){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.TRANSFERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("transferId",transferId);
        dataCenterService.setData("userId",id);
    }

    public void allotRequestCheck(){
        String owner = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("owner");
        String current =dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("current");
        JSONArray array = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("cardIdItems");
        List<Long> cardIdList = array.toJavaList(Long.class);
        if(StringUtils.isBlank(owner)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.DEPARTMENT_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if(StringUtils.isBlank(current)){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.DEPARTMENT_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        if (cardIdList.size()<=0){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.CARD_ID_LIST_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("owner",owner);
        dataCenterService.setData("current",current);
        dataCenterService.setData("cardIdList",cardIdList);
    }

    public void allotListRequestCheck(){
        pageCheck();
        Long id = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        dataCenterService.setData("userId",id);
    }

    public void allotCheckRequestCheck(){
        Long userId = Longs.tryParse(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("id"));
        Long AllotId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("AllotId");
        if(AllotId==null){
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), AssetsReasonOfFailure.TRANSFERID_IS_NOT_BLANK); //验证数据不合法后返回前台提示信息
        }
        dataCenterService.setData("AllotId",AllotId);
        dataCenterService.setData("userId",userId);
    }


}
