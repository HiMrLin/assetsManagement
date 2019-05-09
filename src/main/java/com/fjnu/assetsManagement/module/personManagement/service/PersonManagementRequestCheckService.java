package com.fjnu.assetsManagement.module.personManagement.service;

import com.fjnu.assetsManagement.dao.SysUserDao;
import com.fjnu.assetsManagement.entity.SysDepartment;
import com.fjnu.assetsManagement.entity.SysRoleAcl;
import com.fjnu.assetsManagement.entity.SysUser;
import com.fjnu.assetsManagement.module.personManagement.constant.PersonManagementConstants;
import com.fjnu.assetsManagement.module.personManagement.enums.PersonManagementReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.CheckVariableUtil;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PersonManagementRequestCheckService {
    @Autowired
    private DataCenterService dataCenterService;
    @Autowired
    private SysUserDao sysUserDao;

    //人员列表
    public void getPersonListRequestCheck() {
        String account = dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("account");
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
//        String sex=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
//        String phone=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
//        String roleStr=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("role");
//        String departmentStr=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
//        String userName=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ACCOUNT_IS_BLANK);
        }
        SysUser user = new SysUser();
        user.setAccount(account);
//        user.setUserName(userName);
//        user.setSex(sex);
//        user.setPhone(phone);
//        if (roleStr!=null){
//            Long role=Longs.tryParse(roleStr);
//            if (role==null){
//                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(),PersonManagementReasonOfFailure.ROLE_IS_ILLEGAL);
//            }
//            user.setRole(role);
//        }
//        if (departmentStr!=null){
//            Long department=Longs.tryParse(departmentStr);
//            if (department==null){
//                ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(),PersonManagementReasonOfFailure.DEPARTMENT_IS_ILLEGAL);
//            }
//            user.setDepartment(department);
//        }
        dataCenterService.setData("user", user);
        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);


    }

    //添加人员
    public void addPersonRequestCheck() {
        String account = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("account");//用户设置的account
        String sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String roleStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("role");
        String departmentStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        Long role = Longs.tryParse(roleStr);
        Long department = Longs.tryParse(departmentStr);
        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ACCOUNT_IS_BLANK);
        }
        if (StringUtils.isBlank(sex)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.SEX_IS_BLANK);
        }
        if (StringUtils.isBlank(phone) || !CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.PHONE_IS_ILLEGAL);
        }

        if (StringUtils.isBlank(password)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.PASSWORD_IS_BLANK);
        }
        if (role == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ROLE_IS_ILLEGAL);
        }
        if (department == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.DEPARTMENT_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(userName)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.USERNAME_IS_BLANK);
        }
        Set<Integer> set = Sets.newHashSet();
        set.add(PersonManagementConstants.LIZHI);
        set.add(PersonManagementConstants.ZAIZHI);
        set.add(PersonManagementConstants.LOCK);
        if (!set.contains(status)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.STATUS_IS_ILLEGAL);
        }
        SysUser result = sysUserDao.getByAccountAndPassword(account, null);
        if (result != null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ACCOUNT_EXTISTS);
        }
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSex(sex);
        user.setUserName(userName);
        user.setStatus(status);
        SysRoleAcl sysRoleAcl = new SysRoleAcl();
        sysRoleAcl.setId(role);
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setId(department);
        user.setSysRoleAcl(sysRoleAcl);
        user.setSysDepartment(sysDepartment);
        dataCenterService.setData("user", user);


    }

    public void updatePersonRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        String sex = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("sex");
        String phone = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("phone");
        String roleStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("role");
        String departmentStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("department");
        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        Long id = Longs.tryParse(idStr);
        Long role = Longs.tryParse(roleStr);
        Long department = Longs.tryParse(departmentStr);
        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ID_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(sex)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.SEX_IS_BLANK);
        }
        if (StringUtils.isBlank(phone) || !CheckVariableUtil.isMobile(phone)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.PHONE_IS_ILLEGAL);
        }

        if (StringUtils.isBlank(password)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.PASSWORD_IS_BLANK);
        }
        if (role == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ROLE_IS_ILLEGAL);
        }
        if (department == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.DEPARTMENT_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(userName)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.USERNAME_IS_BLANK);
        }
        Set<Integer> set = Sets.newHashSet();
        set.add(PersonManagementConstants.LIZHI);
        set.add(PersonManagementConstants.ZAIZHI);
        set.add(PersonManagementConstants.LOCK);
        if (!set.contains(status)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.STATUS_IS_ILLEGAL);
        }
        //获取原先数据
        SysUser before = sysUserDao.getCurrentUser(id);
        if (before == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_USER);
        }
        SysUser user = new SysUser();
        //复制原先数据
        BeanUtils.copyProperties(before, user);
        //设置新数据
        user.setId(id);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSex(sex);
        user.setUserName(userName);
        user.setStatus(status);
        SysRoleAcl sysRoleAcl = new SysRoleAcl();
        sysRoleAcl.setId(role);
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setId(department);
        user.setSysRoleAcl(sysRoleAcl);
        user.setSysDepartment(sysDepartment);
        dataCenterService.setData("user", user);

    }

    public void deletePersonRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        Long id = Longs.tryParse(idStr);
        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ID_IS_ILLEGAL);
        }
        dataCenterService.setData("id", id);

    }

    public void updatePasswordRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");
        Long id = Longs.tryParse(idStr);
        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ID_IS_ILLEGAL);
        }
        if (StringUtils.isBlank(password)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.PASSWORD_IS_BLANK);
        }
        //获取原先数据
        SysUser before = sysUserDao.getCurrentUser(id);
        if (before == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_USER);
        }
        SysUser user = new SysUser();
        //复制原先数据
        BeanUtils.copyProperties(before, user);
        //设置需要修改的数据
        user.setId(id);
        user.setPassword(password);
        dataCenterService.setData("user", user);
    }

    public void updateStatusRequestCheck() {
        String idStr = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("id");
        Long id = Longs.tryParse(idStr);
        Integer status = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("status");
        if (id == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ID_IS_ILLEGAL);
        }
        Set<Integer> set = Sets.newHashSet();
        set.add(PersonManagementConstants.LIZHI);
        set.add(PersonManagementConstants.ZAIZHI);
        set.add(PersonManagementConstants.LOCK);
        if (!set.contains(status)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.STATUS_IS_ILLEGAL);
        }
        //获取原先数据
        SysUser before = sysUserDao.getCurrentUser(id);
        if (before == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_USER);
        }
        SysUser user = new SysUser();
        //复制原先数据
        BeanUtils.copyProperties(before, user);
        //设置需要修改的数据
        user.setId(id);
        user.setStatus(status);
        dataCenterService.setData("user", user);

    }

    //获取部门下拉列表
    public void getDepartmentListRequestCheck() {
        String account = dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("account");
        if (StringUtils.isBlank(account)) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.ACCOUNT_IS_BLANK);
        }
        SysUser user = new SysUser();
        user.setAccount(account);
        dataCenterService.setData("user", user);
    }
}
