package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "record_scrap")

public class RecordScrap {
    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;//资产ID

    @Getter(onMethod_ = {@Id,@Column(name = "scrap_id")})
    private Long scrapId;//报废编号

    @Getter(onMethod_ = {@Column(name = "scrap_time")})
    private String scrapTime;//报废时间

    @Getter(onMethod_ = {@Column(name = "note")})
    private String note;//备注

    @Getter(onMethod_ = {@Column(name = "notifier")})
    private String notifier;//报备者

    @Getter(onMethod_ = {@Column(name = "department")})
    private String department;//报备者

}
