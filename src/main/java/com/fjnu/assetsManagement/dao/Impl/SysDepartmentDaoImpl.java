package com.fjnu.assetsManagement.dao.Impl;

import com.fjnu.assetsManagement.dao.SysDepartmentDao;
import com.fjnu.assetsManagement.entity.SysDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
        List<SysDepartment> departments = (List<SysDepartment>) hibernateTemplate.find("from SysDepartment sd where sd.level like");
        return departments;
    }
}
