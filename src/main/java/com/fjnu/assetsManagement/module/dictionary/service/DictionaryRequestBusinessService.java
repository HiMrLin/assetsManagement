package com.fjnu.assetsManagement.module.dictionary.service;

import com.fjnu.assetsManagement.entity.Dictionary;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class DictionaryRequestBusinessService {

    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private SessionFactory sessionFactory;


    //添加数据字典列表
    public void addDictionaryItemServiceProcess() {
        //得到数据
        String kind = dataCenterService.getData("kind");
        Integer quantityState = dataCenterService.getData("quantityState");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Dictionary dictionary = new Dictionary();
        dictionary.setKind(kind);
        dictionary.setQuantityState(quantityState);
        session.save(dictionary);
        session.getTransaction().commit();
        session.close();

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

    }

    //根据id得到数据字典
    public void getDictionaryByIdProcess() {
        //得到数据
        Long id = dataCenterService.getData("id");

        Session session = sessionFactory.openSession();
        Dictionary dictionary = (Dictionary) session.get(Dictionary.class, id);

        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "dictionary", dictionary);

    }

    //删除数据字典列表
    public void deleteDictionaryListByIdProcess() {
        //得到数据
        List<Long> idList = dataCenterService.getData("idList");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "delete Dictionary d where ";
        int temp = 0;
        for (Long id : idList) {
            if (temp == 0) {
                hql += " d.id=" + id;
            } else {
                hql += " or ";
                hql += " d.id=" + id;
            }
            temp++;
        }
        System.out.println(hql);

        Query query = session.createQuery(hql);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

    }

    //得到数据字典列表
    public void getDictionaryListProcess() {
        Session session = sessionFactory.openSession();
        String hql = "from Dictionary d ";
        Query query = session.createQuery(hql);
        List<Dictionary> dictionaryList = ((org.hibernate.query.Query) query).list();

        session.close();
        //操作完成后返回给前台数据
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "dictionaryList", dictionaryList);

    }
}
