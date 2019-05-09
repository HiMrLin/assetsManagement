package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "receive_master")
public class ReceiveMaster {
    @Getter(onMethod_ = {@Column(name = "purpose")})
    private String purpose; //用途

    @Getter(onMethod_ = {@Column(name = "receive_time")})
    private String receiveTime;//领用时间

    @Getter(onMethod_ = {@Id,@Column(name = "receive_id")})
    private Long receiveId;//领用编码

    @Getter(onMethod_ = {@Column(name = "note")})
    private String note;//备注

    @Getter(onMethod_ = {@Column(name = "department")})
    private String depart;//部门

    @Getter(onMethod_ = {@Column(name = "get_state")})
    private  Integer getState;//领用状态
}