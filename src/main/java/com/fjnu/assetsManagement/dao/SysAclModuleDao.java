package com.fjnu.assetsManagement.dao;

import com.fjnu.assetsManagement.entity.SysAclModule;

import java.util.List;

public interface SysAclModuleDao {
    List<SysAclModule> getModuleByIds(List<Long> ids);
}
