package com.fjnu.assetsManagement.entity;


import java.util.Date;

@lombok.Data
public class Assets {

    private String assetsId;
    private Long cardId;
    private String area;
    private String kind;
    private String ascription;
    private String maker;
    private String supplier;
    private Date inTime;
    private Date inAccountTime;
    private Long accountId;
    private Long financeId;
    private Integer assetsState;
    private Integer financeState;
    private Integer inState;
    private String code;
    private Long orderdetailId;

}
