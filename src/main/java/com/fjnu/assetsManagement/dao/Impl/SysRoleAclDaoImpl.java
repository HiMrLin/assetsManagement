package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysRoleAclDao;
import com.fjnu.assetsManagement.entity.SysRoleAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class SysRoleAclDaoImpl implements SysRoleAclDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public SysRoleAcl getAcl(Long id) {
        return hibernateTemplate.get(SysRoleAcl.class, id);
    }
}
