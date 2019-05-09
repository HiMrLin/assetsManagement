package com.fjnu.assetsManagement.entity;


import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "purchase_master")
public class PurchaseMaster {

    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;//主键

    @Getter(onMethod_ = {@Column(name = "order_no")})
    private String orderNo;//采购单号

    @Getter(onMethod_ = {@Column(name = "order_time")})
    private String orderTime;//采购时间

    @Getter(onMethod_ = {@Column(name = "total_price")})
    private String totalPrice;//总价


    @Getter(onMethod_ = {@Column(name = "operator")})
    private String operator;//操作员

    @Getter(onMethod_ = {@Column(name = "operator_id")})
    private Long operatorId;//操作员id

    @Getter(onMethod_ = {@Column(name = "state")})
    private Long state;//状态

    @Getter(onMethod_ = {@Column(name = "remark")})
    private String remark;//备注

    @Getter(onMethod_ = {@Column(name = "entry_time")})
    private String entryTime;//入库时间

    @Getter(onMethod_ = {@Column(name = "in_time")})
    private String inTime;//入账时间

    @Getter(onMethod_ = {@Column(name = "entry_operator")})
    private String entryOperator;//入账操作员

    @Getter(onMethod_ = {@Column(name = "entry_operator_id")})
    private String entryOperatorId;//入账操作员id

    @Getter(onMethod_ = {@Column(name = "in_operator")})
    private String inOperator;//入库操作员

    @Getter(onMethod_ = {@Column(name = "in_operator_id")})
    private Long inOperatorId;//入库操作员id

    @Getter(onMethod_ = {@Column(name = "exist_state")})
    private Integer existState;//逻辑存在状态


    //不能使用set,会出现栈溢出
    @Getter(onMethod_ = {@OneToMany(cascade = CascadeType.ALL), @JoinColumn(name = "master_id")})
    private Set<PurchaseDetail> purchaseDetailSet = new HashSet<>();


    @Override
    public String toString() {
        return "PurchaseMaster{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", operator='" + operator + '\'' +
                ", operatorId=" + operatorId +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", inTime='" + inTime + '\'' +
                ", entryOperator='" + entryOperator + '\'' +
                ", entryOperatorId='" + entryOperatorId + '\'' +
                ", inOperator='" + inOperator + '\'' +
                ", inOperatorId=" + inOperatorId +
                ", existState=" + existState +
                ", purchaseDetailSet=" + purchaseDetailSet +
                '}';
    }
}
