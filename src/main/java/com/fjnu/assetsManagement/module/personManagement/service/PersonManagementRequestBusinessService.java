package com.fjnu.assetsManagement.module.personManagement.service;

import com.fjnu.assetsManagement.dao.SysDepartmentDao;
import com.fjnu.assetsManagement.dao.SysRoleAclDao;
import com.fjnu.assetsManagement.dao.SysUserDao;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.entity.SysDepartment;
import com.fjnu.assetsManagement.entity.SysRoleAcl;
import com.fjnu.assetsManagement.entity.SysUser;
import com.fjnu.assetsManagement.module.personManagement.enums.PersonManagementReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.fjnu.assetsManagement.util.LevelUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.SysUserVo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonManagementRequestBusinessService {
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleAclDao sysRoleAclDao;
    @Autowired
    private SysDepartmentDao sysDepartmentDao;


    public void getPersonListRequestProcess() {
        //获取登录用户信息
        SysUser condition = dataCenterService.getData("user");//条件查询所有的条件
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }
        String account = condition.getAccount();
        SysUser currentLoginUser = sysUserDao.getByAccountAndPassword(account, null);
        if (currentLoginUser == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_USER);
        }
        Long roleId = currentLoginUser.getRole();
        Long departmentId = currentLoginUser.getDepartment(); //当前登录用户所属部门id
        SysDepartment sysDepartment = sysDepartmentDao.getDepartmentById(departmentId);//获取当前登录用户的部门
        SysRoleAcl sysRoleAcl = sysRoleAclDao.getAcl(roleId);//获取当前登录用户的角色对应权限信息
        //判断当前用户的角色是不是管理员，否则无权查询
        if (sysRoleAcl == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_ACL);
        }
        if (!sysRoleAcl.getRoleName().equals("admin")) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_ACL);
        }
        //条件查询
//        condition.setAccount(null);
        //只能查询当前部门下的人，先获取当前部门及其子部门的id
//        Long conditionDepartmentId=departmentId;
//        if (condition.getDepartment()!=null){
//            conditionDepartmentId=condition.getDepartment();
//        }
        String level = sysDepartment.getLevel();
        String newLevel = level + LevelUtil.SEPARATOR + departmentId;
        List<SysDepartment> childDepartment = sysDepartmentDao.getChildrenDepartment(newLevel);
        List<Long> departmentIds = childDepartment.stream().map(SysDepartment::getId).collect(Collectors.toList());
        departmentIds.add(departmentId);
        List<SysUser> userList = sysUserDao.getAllUserByDepartmentByPage(departmentIds, pageNum, pageSize);
        int total = sysUserDao.totalCount(departmentIds);//数据总条数
        List<SysUserVo> userVoList = Lists.newArrayList();
        for (SysUser user : userList) {
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(user, sysUserVo);
            userVoList.add(sysUserVo);
        }

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "userList", userVoList);
        ResponseDataUtil.putValueToData(responseData, "total", total);
        ResponseDataUtil.putValueToData(responseData, "pageNum", pageNum);
        ResponseDataUtil.putValueToData(responseData, "pageSize", pageSize);

    }


    public void addPersonRequestProcess() {
        SysUser user = dataCenterService.getData("user");
        sysUserDao.addUser(user);
    }

    public void updatePersonRequestProcess() {
        updateData();
    }

    public void deletePersonRequestProcess() {
        Long id = dataCenterService.getData("id");
        sysUserDao.removeUser(id);
    }

    public void updatePasswordRequestProcess() {
        updateData();
    }

    private void updateData() {
        SysUser user = dataCenterService.getData("user");
        sysUserDao.updateUser(user);
    }

    public void updateStatusRequestProcess() {
        updateData();
    }

    public void getDepartmentListRequestProcess() {
        //获取登录用户信息
        SysUser condition = dataCenterService.getData("user");//条件查询所有的条件
        String account = condition.getAccount();
        SysUser currentLoginUser = sysUserDao.getByAccountAndPassword(account, null);
        if (currentLoginUser == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), PersonManagementReasonOfFailure.NO_USER);
        }
        Long departmentId = currentLoginUser.getDepartment(); //当前登录用户所属部门id
        SysDepartment sysDepartment = sysDepartmentDao.getDepartmentById(departmentId);//获取当前登录用户的部门
        //查询子部门
        String level = sysDepartment.getLevel();
        String newLevel = level + LevelUtil.SEPARATOR + departmentId;
        List<SysDepartment> childDepartment = sysDepartmentDao.getChildrenDepartment(newLevel);
        List<SysDepartment> departmentList = Lists.newArrayList();
        departmentList.add(sysDepartment);//加入当前部门
        if (childDepartment != null) {//加入子部门
            departmentList.addAll(childDepartment);
        }
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "departmentList", departmentList);

    }
}
