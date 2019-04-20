package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "assets")
public class Assets {


    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long cardId;//卡片编号，主键

    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;//资产编码

    @Getter(onMethod_ = {@Column(name = "area")})
    private String area;//地区

    @Getter(onMethod_ = {@Column(name = "kind")})
    private String kind;//类别

    @Getter(onMethod_ = {@Column(name = "ascription")})
    private String ascription;//货物归属

    @Getter(onMethod_ = {@Column(name = "maker")})
    private String maker;//制造商

    @Getter(onMethod_ = {@Column(name = "supplier")})
    private String supplier;//供应商

    @Getter(onMethod_ = {@Column(name = "in_time")})
    private Date inTime;//入库时间

    @Getter(onMethod_ = {@Column(name = "in_account_time")})
    private Date inAccountTime;//入账时间

    @Getter(onMethod_ = {@Column(name = "account_id")})
    private Long accountId;//凭证号

    @Getter(onMethod_ = {@Column(name = "finance_id")})
    private Long financeId;//财务编码

    @Getter(onMethod_ = {@Column(name = "assets_state")})
    private Integer assetsState;//资产状态

    @Getter(onMethod_ = {@Column(name = "finance_state")})
    private Integer financeState;//财务审核状态

    @Getter(onMethod_ = {@Column(name = "in_state")})
    private Integer inState;//入库审核状态

    @Getter(onMethod_ = {@Column(name = "code")})
    private String code;//条形码

    @Getter(onMethod_ = {@Column(name = "orderdetail_id")})
    private Long orderDetailId;//详表ID

    @Getter(onMethod_ = {@Column(name = "assets_name")})
    private String assetsName;//货物名称

    @Getter(onMethod_ = {@Column(name = "depository")})
    private String depository;//保管人

    @Getter(onMethod_ = {@Column(name = "life_factor")})
    private Integer lifeFactor;//使用年限

    @Getter(onMethod_ = {@Column(name = "fix_year")})
    private Integer fixYear;//保修年限

}
