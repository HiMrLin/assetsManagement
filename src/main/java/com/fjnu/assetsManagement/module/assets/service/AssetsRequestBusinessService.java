package com.fjnu.assetsManagement.module.assets.service;

import com.fjnu.assetsManagement.entity.Assets;
import com.fjnu.assetsManagement.entity.PurchaseMaster;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

@Component
public class AssetsRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SessionFactory sessionFactory;

    //资产列表
    public void assetsListRequestProcess(){
        Integer pageSize = dataCenterService.getData("pageSize");
        Integer pageNum = dataCenterService.getData("pageNum");
        PageUtil<Assets> assets = new PageUtil<>();
        this.getPageList(assets, pageNum, pageSize, PurchaseMaster.class.getSimpleName());
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "purchaseMasterList", assets);

    }


    //分页设置
    public void getPageList(PageUtil purchaseMasterList, int pageNum, int pageSize, String className) {
        Session session = sessionFactory.openSession();
        String getSizeHql = "select count(*) from " + className + " c ";
        Query getSizeQuery = session.createQuery(getSizeHql);
        Long sizeLong = (Long) ((org.hibernate.query.Query) getSizeQuery).uniqueResult();
        int size = sizeLong.intValue();
        purchaseMasterList.init(size, pageNum, pageSize);
        int curPageNum = purchaseMasterList.getPageNum();
        int curPageSize = purchaseMasterList.getPageSize();
        int firstResult = (curPageNum - 1) * curPageSize;
        String getListHql = "from " + className + " c " ;
        Query getListQuery = session.createQuery(getListHql);
        getListQuery.setFirstResult(firstResult);
        getListQuery.setMaxResults(curPageSize);
        purchaseMasterList.setList(((org.hibernate.query.Query) getListQuery).list());
    }

}
