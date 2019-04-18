package com.fjnu.assetsManagement.entity;


import lombok.Data;

import java.util.Date;

@Data
public class RecordReceive {

    private String assetsId;
    private Long userId;
    private String purpose;
    private Date receiveTime;
    private Long receiveId;


}
