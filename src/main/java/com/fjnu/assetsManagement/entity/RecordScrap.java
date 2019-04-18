package com.fjnu.assetsManagement.entity;


import lombok.Data;

import java.util.Date;

@Data
public class RecordScrap {

    private String assetsId;
    private Date scrapTime;
    private String note;

}
