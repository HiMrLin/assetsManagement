package com.fjnu.assetsManagement.module.personManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonManagementRequestService {
    @Autowired
    private PersonManagementRequestCheckService personManagementRequestCheckService;
    @Autowired
    private PersonManagementRequestBusinessService personManagementRequestBusinessService;

    //人员列表
    public void getPersonListRequestProcess() {

        personManagementRequestCheckService.getPersonListRequestCheck();
        personManagementRequestBusinessService.getPersonListRequestProcess();


    }

    //添加人员
    public void addPersonRequestProcess() {
        personManagementRequestCheckService.addPersonRequestCheck();
        personManagementRequestBusinessService.addPersonRequestProcess();

    }

    //修改人员
    public void updatePersonRequestProcess() {
        personManagementRequestCheckService.updatePersonRequestCheck();
        personManagementRequestBusinessService.updatePersonRequestProcess();
    }

    //删除人员
    public void deletePersonRequestProcess() {
        personManagementRequestCheckService.deletePersonRequestCheck();
        personManagementRequestBusinessService.deletePersonRequestProcess();
    }

    //修改密码
    public void updatePasswordRequestProcess() {
        personManagementRequestCheckService.updatePasswordRequestCheck();
        personManagementRequestBusinessService.updatePasswordRequestProcess();
    }

    public void updateStatusRequestProcess() {
        personManagementRequestCheckService.updateStatusRequestCheck();
        personManagementRequestBusinessService.updateStatusRequestProcess();
    }

    //获取可选部门的下拉框列表
    public void getDepartmentListRequestProcess() {
        personManagementRequestCheckService.getDepartmentListRequestCheck();
        personManagementRequestBusinessService.getDepartmentListRequestProcess();

    }
}
