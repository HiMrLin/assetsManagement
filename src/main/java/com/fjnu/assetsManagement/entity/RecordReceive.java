package com.fjnu.assetsManagement.entity;


import com.fjnu.assetsManagement.vo.AssetsItem;
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
    private Long ReceiveId;//领用编码

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;

    @Getter(onMethod_ = {@Transient})
    private AssetsItem assetsItem;
}