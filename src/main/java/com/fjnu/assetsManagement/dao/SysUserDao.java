package com.fjnu.assetsManagement.dao;

import com.fjnu.assetsManagement.entity.SysUser;

import java.util.List;


public interface SysUserDao {
    SysUser getByAccountAndPassword(String account, String password);

    void addUser(SysUser user);

    void updateUser(SysUser user);

    void removeUser(int id);

    List<SysUser> getAllUserByDepartment(List<Long> departmentId);
}
