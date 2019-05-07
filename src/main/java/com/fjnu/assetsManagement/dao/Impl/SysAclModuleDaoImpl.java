package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysAclModuleDao;
import com.fjnu.assetsManagement.entity.SysAclModule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SysAclModuleDaoImpl implements SysAclModuleDao {
    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public List<SysAclModule> getModuleByIds(List<Long> ids) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysAclModule s where s.id IN (:list)");
        q.setParameterList("list", ids);
        List<SysAclModule> sysAclModules = q.list();
        session.close();
        sf.close();
        return sysAclModules;
    }
}
