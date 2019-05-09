package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysDepartmentDao;
import com.fjnu.assetsManagement.entity.SysDepartment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SysDepartmentDaoImpl implements SysDepartmentDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public SysDepartment getDepartmentById(Long id) {
        return hibernateTemplate.get(SysDepartment.class, id);
    }

    @Override
    public List<SysDepartment> getChildrenDepartment(String newLevel) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysDepartment sd where sd.level = :level or sd.level like :newlevel");
        q.setParameter("level", newLevel);
        q.setParameter("newlevel", newLevel + "." + "%");
        List<SysDepartment> departments = q.list();
        return departments;
    }
}
