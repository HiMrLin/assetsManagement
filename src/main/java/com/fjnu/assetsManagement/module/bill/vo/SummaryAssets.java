package com.fjnu.assetsManagement.module.bill.vo;

import lombok.Data;

//简单资产类，用于接受资产部分信息
@Data
public class SummaryAssets {

    private String area;//地区

    private String ascription;//货物归属

    private String maker;//制造商

    private String supplier;//供应商

    private Long accountId;//凭证号

    private String depository;//保管人

    private Integer lifeFactor;//使用年限

    private Integer fixYear;//保修年限
}
