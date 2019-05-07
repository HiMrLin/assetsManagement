package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysUserDao;
import com.fjnu.assetsManagement.entity.SysUser;
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
public class SysUserDaoImpl implements SysUserDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public SysUser getByAccountAndPassword(String account, String password) {
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setPassword(password);
//        String [] condition={account,password};
//        SessionFactory sf = hibernateTemplate.getSessionFactory();
//        Session session=sf.getCurrentSession();
//        Query q= session.createSQLQuery("select * from  sys_user where account="+account+" and password="+password);
//        List<SysUser>result=q.list();
        List<SysUser> result = hibernateTemplate.findByExample(user);
//        List<SysUser>result=hibernateTemplate.find("from SysUser s where s.account=? and s.password=?",condition);
//        session.close();
//        sf.close();
        if (result == null || result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public void addUser(SysUser user) {
        hibernateTemplate.save(user);
        ;
    }

    @Override
    public void updateUser(SysUser user) {
        hibernateTemplate.update(user);
    }

    @Override
    public void removeUser(int id) {
        SysUser user = new SysUser();
        user.setId(id);
        hibernateTemplate.delete(user);
    }

    @Override
    public List<SysUser> getAllUserByDepartment(List<Long> departmentId) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Query q = session.createQuery("from SysUser s where s.department IN (:list)");
        q.setParameterList("list", departmentId);
        List<SysUser> userList = q.list();
        session.close();
        sf.close();
        return userList;
    }


}
