package com.fjnu.assetsManagement.entity;


import com.fjnu.assetsManagement.vo.SummaryAssets;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "purchase_detail")
public class PurchaseDetail {

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;


    @Getter(onMethod_ = {@Column(name = "unit_price")})
    private Double unitPrice;

    @Getter(onMethod_ = {@Column(name = "kind")})
    private String kind;

    @Getter(onMethod_ = {@Column(name = "kind_id")})
    private Long kindId;

    @Getter(onMethod_ = {@Column(name = "name")})
    private String name;

    @Getter(onMethod_ = {@Column(name = "quantity")})
    private Long quantity;

    @Getter(onMethod_ = {@ManyToOne(cascade = CascadeType.MERGE), @JoinColumn(name = "master_id")})
    private PurchaseMaster purchaseMaster;

    //对应简单资产，忽略映射
    @Getter(onMethod_ = {@Transient})
    private SummaryAssets summaryAssets = new SummaryAssets();

    @Override
    public int hashCode() {
        return this.quantity.hashCode();
    }


    @Override
    public String toString() {
        return "PurchaseDetail{" +
                "id=" + id +
                ", unitPrice=" + unitPrice +
                ", kind='" + kind + '\'' +
                ", kindId='" + kindId + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", summaryAssets=" + summaryAssets +
                '}';
    }
}
