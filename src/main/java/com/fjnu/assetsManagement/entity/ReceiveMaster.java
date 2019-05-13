package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@Entity
@Table(name = "receive_master")
public class ReceiveMaster {
    @Getter(onMethod_ = {@Column(name = "purpose")})
    private String purpose; //用途

    @Getter(onMethod_ = {@Column(name = "receive_time")})
    private String receiveTime;//领用时间

    @Getter(onMethod_ = {@Id,@Column(name = "receive_id")} )
    private Long ReceiveId;

    @Getter(onMethod_ = { @OneToMany(cascade = CascadeType.ALL),@JoinColumn(name = "receive_id")})
    private List<RecordReturn> recordReturn;

    @Getter(onMethod_ = {@OneToMany(cascade = CascadeType.ALL),@JoinColumn(name = "receive_id")})
    private List<RecordReceive> recordReceive;//领用编码

    @Getter(onMethod_ = {@Column(name = "note")})
    private String note;//备注

    @Getter(onMethod_ = {@Column(name = "department")})
    private String depart;//部门

    @Getter(onMethod_ = {@Column(name = "user_id")})
    private  Long userId;//领用状态

    @Getter(onMethod_ = {@Column(name = "get_state")})
    private  Integer getState;//领用状态
}
