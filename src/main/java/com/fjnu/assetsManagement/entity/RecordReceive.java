package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "record_receive")
public class RecordReceive {
    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;//资产编码

    @Getter(onMethod_ = {@Column(name = "receive_id")})
    private Long receiveId;//领用编码

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;
}