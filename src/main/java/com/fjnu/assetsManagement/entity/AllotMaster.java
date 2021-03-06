package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "allot_master")
public class AllotMaster {
    @Getter(onMethod_ = {@Id, @Column(name = "allot_id")})
    private Long allotId;

    @Getter(onMethod_ = {@Column(name = "department_A")})
    private String owner;

    @Getter(onMethod_ = {@Column(name = "department_B")})
    private String current;

    @Getter(onMethod_ = {@Column(name = "state")})
    private Integer state;

    @Getter(onMethod_ = {@Column(name = "start_time")})
    private String  startTime;

    @Getter(onMethod_ = {@Column(name = "mid_time")})
    private String  midTime;

    @Getter(onMethod_ = {@Column(name = "end_time")})
    private String  endTime;

    @Getter(onMethod_ = {@OneToMany(cascade = CascadeType.ALL),@JoinColumn(name = "allot_id")})
    private List<AllotDetail> allotDetailList;

    @Getter(onMethod_ = {@Transient})
    private Boolean alc = true;
}
