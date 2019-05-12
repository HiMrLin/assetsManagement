package com.fjnu.assetsManagement.entity;

import com.fjnu.assetsManagement.vo.AssetsItem;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transfer_detail")
public class TransferDetail {
    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;

    @Getter(onMethod_ = {@Column(name = "transferId")})
    private Long transferId;

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;

    @Getter(onMethod_ = {@Transient})
    private AssetsItem assetsItem;
}
