package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "purchase_master")
public class PurchaseMaster {

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;

    @Getter(onMethod_ = {@Column(name = "order_no")})
    private Long orderNo;

    @Getter(onMethod_ = {@Column(name = "order_time")})
    private Date orderTime;

    @Getter(onMethod_ = {@Column(name = "total_price")})
    private Double totalPrice;

    @Getter(onMethod_ = {@Column(name = "operator")})
    private String operator;

    @Getter(onMethod_ = {@Column(name = "state")})
    private Long state;

    @Getter(onMethod_ = {@Column(name = "remark")})
    private String remark;


//    @Getter(onMethod_= {@OneToMany(cascade = CascadeType.ALL), @JoinColumn(name = "master_id")})
//    private List<PurchaseDetail> purchaseDetailList = new ArrayList<>();

    //不能使用set,会出现栈溢出
    @Getter(onMethod_ = {@OneToMany(cascade = CascadeType.ALL), @JoinColumn(name = "master_id")})
    private Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();


    @Override
    public String toString() {
        return "PurchaseMaster{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", orderTime=" + orderTime +
                ", totalPrice=" + totalPrice +
                ", operator='" + operator + '\'' +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                ", purchaseDetailSet=" + purchaseDetailSet +
                '}';
    }
}
