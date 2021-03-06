package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysUserDao;
import com.fjnu.assetsManagement.entity.SysUser;
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
public class SysUserDaoImpl implements SysUserDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
//    @Transactional(readOnly=true)
    public SysUser getByAccountAndPassword(String account, String password) {
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setPassword(password);
        List<SysUser> result = hibernateTemplate.findByExample(user);
        if (result == null || result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
//    @Transactional
    public void addUser(SysUser user) {
        hibernateTemplate.save(user);

    }

    @Override
//    @Transactional
    public void updateUser(SysUser user) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        session.merge(user);
//        hibernateTemplate.update(user);
    }

    @Override
//    @Transactional
    public void removeUser(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        hibernateTemplate.delete(user);
    }

    @Override
//    @Transactional(readOnly=true)
    public List<SysUser> getAllUserByDepartment(List<Long> departmentId) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysUser s where s.sysDepartment.id IN (:list)");
        q.setParameterList("list", departmentId);
        List<SysUser> userList = q.list();
//        session.close();
//        sf.close();
        return userList;
    }

    @Override
    public List<SysUser> getAllUserByDepartmentByPage(List<Long> departmentId, Integer pageNum, Integer pageSize) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysUser s where s.sysDepartment.id IN (:list)");
        q.setParameterList("list", departmentId);
        q.setFirstResult((pageNum - 1) * pageSize);
        q.setMaxResults(pageSize);
        List<SysUser> userList = q.list();
        return userList;
    }

    @Override
    public int totalCount(List<Long> departmentId) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysUser s where s.sysDepartment.id IN (:list)");
        q.setParameterList("list", departmentId);
        List<SysUser> userList = q.list();
        if (userList == null) {
            return 0;
        }
        return userList.size();
    }


    @Override
    public SysUser getCurrentUser(Long id) {
        return hibernateTemplate.get(SysUser.class, id);
    }


}
