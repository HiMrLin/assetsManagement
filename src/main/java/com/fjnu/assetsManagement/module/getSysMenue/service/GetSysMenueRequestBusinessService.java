package com.fjnu.assetsManagement.module.getSysMenue.service;

import com.fjnu.assetsManagement.dao.SysAclModuleDao;
import com.fjnu.assetsManagement.dao.SysDepartmentDao;
import com.fjnu.assetsManagement.dao.SysRoleAclDao;
import com.fjnu.assetsManagement.dao.SysUserDao;
import com.fjnu.assetsManagement.dto.AccessMenuDto;
import com.fjnu.assetsManagement.dto.AccessRouteDto;
import com.fjnu.assetsManagement.dto.MetaDto;
import com.fjnu.assetsManagement.entity.*;
import com.fjnu.assetsManagement.module.getSysMenue.constant.GetSysMenueConstants;
import com.fjnu.assetsManagement.module.getSysMenue.enums.GetSysMenueReasonOfFailure;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ExceptionUtil;
import com.fjnu.assetsManagement.util.LevelUtil;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.vo.SysInfoVo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Longs;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class GetSysMenueRequestBusinessService {
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleAclDao sysRoleAclDao;
    @Autowired
    private SysAclModuleDao sysAclModuleDao;
    @Autowired
    private SysDepartmentDao sysDepartmentDao;


    public void getSysMenueRequestProcess() {
        String account = dataCenterService.getData("account");
        SysUser user = sysUserDao.getByAccountAndPassword(account, null);
        if (user == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), GetSysMenueReasonOfFailure.USERNAME_OR_PASSWORD_IS_WRONG);
        }
        Long roleId = user.getRole();
        Long departmentId = user.getDepartment(); //当前登录用户所属部门id
        SysDepartment sysDepartment = sysDepartmentDao.getDepartmentById(departmentId);//获取当前登录用户的部门，判断是否是总公司
        SysRoleAcl sysRoleAcl = sysRoleAclDao.getAcl(roleId);
        if (sysRoleAcl == null) {
            ExceptionUtil.setFailureMsgAndThrow(dataCenterService.getResponseDataFromDataLocal(), GetSysMenueReasonOfFailure.NO_ACL);
        }
        String[] aclCode = sysRoleAcl.getAclCode().split(GetSysMenueConstants.SEPORATOR);
        List<Long> moduleIds = Lists.newArrayList(); //获取当前角色所拥有的可操控的权限块
        for (String s : aclCode) {
            Long id = Longs.tryParse(s);
            moduleIds.add(id);
        }
        //获取权限对应的权限部门信息
        List<SysAclModule> sysAclModules = sysAclModuleDao.getModuleByIds(moduleIds);
        //生成对应的树

        //构建返回前台的对象
        Multimap<String, AccessMenuDto> accessMenueLeveltMap = ArrayListMultimap.create(); //用于构建树形结构
        Multimap<String, AccessRouteDto> accessRouteLeveltMap = ArrayListMultimap.create();//用于构建树形结构
        List<AccessMenuDto> accessMenueRootList = Lists.newArrayList();//用于构建树形结构
        List<AccessRouteDto> accessRouteRootList = Lists.newArrayList();//用于构建树形结构
        for (SysAclModule sysAclModule : sysAclModules) {
            AccessMenuDto accessMenuDto = new AccessMenuDto();
            AccessRouteDto accessRouteDto = new AccessRouteDto();
            //构建 accessMenuDto
            accessMenuDto.setId(sysAclModule.getId());
            accessMenuDto.setLevel(sysAclModule.getLevel());
            accessMenuDto.setSeq(sysAclModule.getSeq());
            accessMenuDto.setTitle(sysAclModule.getName());
            accessMenuDto.setPath(sysAclModule.getPath());
            accessMenuDto.setIcon(sysAclModule.getIcon());
            //构建 accessRouteDto
            accessRouteDto.setId(sysAclModule.getId());
            accessRouteDto.setLevel(sysAclModule.getLevel());
            accessRouteDto.setSeq(sysAclModule.getSeq());
            accessRouteDto.setName(sysAclModule.getRouteName());
            accessRouteDto.setComponent(sysAclModule.getComponent());
            accessRouteDto.setComponentPath(sysAclModule.getComponentPath());
            accessRouteDto.setPath(sysAclModule.getPath());
            MetaDto metaDto = new MetaDto();
            metaDto.setTitle(sysAclModule.getName());
            metaDto.setCache(sysAclModule.isCache());
            accessRouteDto.setMeta(metaDto);
            //加入list
            if (sysAclModule.getParentId() == 0) { //根节点
                accessMenueRootList.add(accessMenuDto);
                accessRouteRootList.add(accessRouteDto);
            } else {
                accessMenueLeveltMap.put(sysAclModule.getLevel(), accessMenuDto);
                accessRouteLeveltMap.put(sysAclModule.getLevel(), accessRouteDto);
            }
        }

        //转换成树状结构
        transformAccessMenuTree(accessMenueRootList, LevelUtil.ROOT, accessMenueLeveltMap);
        transformAccessRouteTree(accessRouteRootList, LevelUtil.ROOT, accessRouteLeveltMap);

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        SysInfoVo sysInfoVo = new SysInfoVo();
        sysInfoVo.setAccessMenus(accessMenueRootList);
        sysInfoVo.setAccessRoutes(accessRouteRootList);
        sysInfoVo.setUserPermissions(moduleIds);

        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
        ResponseDataUtil.putValueToData(responseData, "sysInfoVo", sysInfoVo);
//        ResponseDataUtil.putValueToData(responseData, "userName", );
//        ResponseDataUtil.putValueToData(responseData, "account", user.getAccount());
//        ResponseDataUtil.putValueToData(responseData, "userId", user.getId());
//        ResponseDataUtil.putValueToData(responseData, "userCompanyType", sysDepartment.getParentId());
//        ResponseDataUtil.putValueToData(responseData, "userCompanyId", sysDepartment.getId());
//        ResponseDataUtil.putValueToData(responseData, "userRole", );
//        ResponseDataUtil.putValueToData(responseData, "userPermissions", moduleIds);
//        ResponseDataUtil.putValueToData(responseData, "accessMenus", accessMenueRootList);
//        ResponseDataUtil.putValueToData(responseData, "accessRoutes", accessRouteRootList);
    }


    // level:0, 0, all 0->0.1,0.2
    // level:0.1

    // level:0.2
    private void transformAccessMenuTree(List<AccessMenuDto> deptLevelList, String level, Multimap<String, AccessMenuDto> levelDeptMap) {
        // 遍历该层的每个元素
        for (AccessMenuDto deptLevelDto : deptLevelList) {
            // 下一层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<AccessMenuDto> tempDeptList = (List<AccessMenuDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 按seq从小到大排序
                tempDeptList.sort(Comparator.comparingInt(AccessMenuDto::getSeq));
                // 设置下一层部门
                deptLevelDto.setChildren(tempDeptList);
                // 进入到下一层处理
                transformAccessMenuTree(tempDeptList, nextLevel, levelDeptMap);

            }
        }
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1

    // level:0.2
    private void transformAccessRouteTree(List<AccessRouteDto> deptLevelList, String level, Multimap<String, AccessRouteDto> levelDeptMap) {
        // 遍历该层的每个元素
        for (AccessRouteDto deptLevelDto : deptLevelList) {
            // 下一层级
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            List<AccessRouteDto> tempDeptList = (List<AccessRouteDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 按seq从小到大排序
                tempDeptList.sort(Comparator.comparingInt(AccessRouteDto::getSeq));
                // 设置下一层部门
                deptLevelDto.setChildren(tempDeptList);
                // 进入到下一层处理
                transformAccessRouteTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }
}
