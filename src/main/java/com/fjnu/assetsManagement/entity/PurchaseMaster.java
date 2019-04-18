package com.fjnu.assetsManagement.entity;


import lombok.Data;

import java.util.Date;

@Data
public class PurchaseMaster {

    private Long id;
    private Long orderNo;
    private Date orderTime;
    private Double totalPrice;
    private String operator;
    private Long state;
    private String remark;


}
