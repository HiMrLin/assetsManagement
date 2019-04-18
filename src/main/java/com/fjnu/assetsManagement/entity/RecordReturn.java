package com.fjnu.assetsManagement.entity;


import lombok.Data;

import java.util.Date;

@Data
public class RecordReturn {

    private String assetId;
    private Long userId;
    private Date returnTime;
    private Long returnId;

}
