package com.fjnu.assetsManagement.entity;

import com.fjnu.assetsManagement.vo.AssetsItem;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
@Data
@Entity
@Table(name = "allot_detail")
public class AllotDetail {
    @Getter(onMethod_ = {@Column(name = "assets_id")})
    private String assetsId;

    @Getter(onMethod_ = {@Column(name = "allot_id")})
    private Long allotId;

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;

    @Getter(onMethod_ = {@Transient})
    private AssetsItem assetsItem;
}
