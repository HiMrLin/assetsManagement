package com.fjnu.assetsManagement.vo;

import lombok.Data;

import java.util.List;

//用于存放领用信息
@Data
public class Receive {
    private Long receiveId;
    private String department;
    private String userName;
    private String recorder;
    private String note;
    private String date;
    private String assetsId;
    private String depository;
    private String purpose;
    private Integer assetsState;
    private String returnTime;
    private String returnName;
    private Long returnId;
    private List<AssetsItem> list;
}
