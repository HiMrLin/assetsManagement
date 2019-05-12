package com.fjnu.assetsManagement.entity;

import com.fjnu.assetsManagement.vo.AssetsItem;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "transfer_master")
public class TransferMaster {
    @Getter(onMethod_ = {@Id, @Column(name = "transferId")})
    private Long id;//卡片编号，主键

    @Getter(onMethod_ = {@Column(name = "ownerId")})
    private Long ownerId;

    @Getter(onMethod_ = {@Column(name = "currentId")})
    private Long currentId;

    @Getter(onMethod_ = {@Column(name = "state")})
    private Integer state;

    @Getter(onMethod_ = {@Column(name = "time")})
    private String  time;

    @Getter(onMethod_ = {@OneToMany(cascade = CascadeType.ALL),@JoinColumn(name = "transferId")})
    private List<TransferDetail> transferDetailList;

    @Getter(onMethod_ = {@Transient})
    private Boolean alc = true;
}
