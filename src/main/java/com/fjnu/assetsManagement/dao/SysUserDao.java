package com.fjnu.assetsManagement.dao;

import com.fjnu.assetsManagement.entity.SysUser;

import java.util.List;


public interface SysUserDao {
    SysUser getByAccountAndPassword(String account, String password);

    void addUser(SysUser user);

    void updateUser(SysUser user);

    void removeUser(Long id);

    List<SysUser> getAllUserByDepartment(List<Long> departmentId);

    List<SysUser> getAllUserByDepartmentByPage(List<Long> departmentId, Integer pageNum, Integer pageSize);

    int totalCount(List<Long> departmentId);
    SysUser getCurrentUser(Long id);//按照人id查询人员信息

}
