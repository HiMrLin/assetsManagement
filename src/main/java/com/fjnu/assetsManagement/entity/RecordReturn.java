package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "record_return")
public class RecordReturn {
    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;
    @Getter(onMethod_ = {@Column(name = "return_name")})
    private String returnName;
    @Getter(onMethod_ = {@Column(name = "return_time")})
    private String returnTime;
    @Getter(onMethod_ = {@Id,@Column(name = "return_id")})
    private Long returnId;

}
