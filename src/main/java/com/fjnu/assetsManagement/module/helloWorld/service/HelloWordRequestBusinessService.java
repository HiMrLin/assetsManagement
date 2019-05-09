package com.fjnu.assetsManagement.module.helloWorld.service;

import com.fjnu.assetsManagement.entity.Assets;
import com.fjnu.assetsManagement.entity.Product;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.PageUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class HelloWordRequestBusinessService {
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    SessionFactory sessionFactory;

    public void getPageList(PageUtil purchaseMasterList, int pageNum, int pageSize, String className, String otherCondition) {
        Session session = sessionFactory.openSession();

        //得到记录总数
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

    //测试从数据库取出条形码转化成图片
    public void testTransferImagesProcess(Long id) {
        //Long cardId = dataCenterService.getData("cardId");

        Session session = sessionFactory.openSession();
        Assets assets = session.get(Assets.class, id);
        System.out.println(assets.getCode());

        //this.GenerateImage(assets.getCode());
        //转化为图片


    }

    public void helloWorldRequestProcess() {
        String userName = dataCenterService.getData("userName");//从容器中获取数据
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> list = Lists.newArrayList();
        product1.setId(1);
        product1.setName(userName+"11");
        product2.setId(2);
        product2.setName("测试2");
        list.add(product1);
        list.add(product2);

        this.testTransferImagesProcess(Longs.tryParse(userName));

        //操作完成后返回给前台数据
        ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "Product", product1);
        ResponseDataUtil.putValueToData(responseData, "list", list);
    }
}
