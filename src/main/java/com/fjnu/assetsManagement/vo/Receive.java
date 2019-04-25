package com.fjnu.assetsManagement.vo;

import lombok.Data;

//用于存放领用信息
@Data
public class Receive {
    private Long receiveId;
    private String department;
    private String userName;
    private String recorder;
    private String assetsName;
    private String note;
    private String date;
    private String assetsId;
    private String depository;
    private String purpose;
}
