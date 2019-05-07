package com.fjnu.assetsManagement.dao;

import com.fjnu.assetsManagement.entity.SysDepartment;

import java.util.List;

public interface SysDepartmentDao {
    SysDepartment getDepartmentById(Long id);

    List<SysDepartment> getChildrenDepartment(String newLevel);
}
