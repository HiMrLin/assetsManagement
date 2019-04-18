package com.fjnu.assetsManagement.entity;


import lombok.Data;

import java.util.Date;

@Data
public class RecordTransfer {

    private String assetsId;
    private Date tfTime;
    private Long tfId;
    private String operator;
    private String receiver;

}
