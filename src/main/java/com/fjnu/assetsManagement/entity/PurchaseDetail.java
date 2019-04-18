package com.fjnu.assetsManagement.entity;

import lombok.Data;

@Data
public class PurchaseDetail {
    private Long id;
    private Long masterId;
    private Double unitPrice;
    private String kind;
    private String name;
    private Long quantity;
}
