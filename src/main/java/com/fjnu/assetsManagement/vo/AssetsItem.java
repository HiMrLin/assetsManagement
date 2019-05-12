package com.fjnu.assetsManagement.vo;

import lombok.Data;

import java.util.List;
@Data
public class AssetsItem {
    private String asssetsName;
    private String kind;
    private String price;
    private Long quantity;
}
