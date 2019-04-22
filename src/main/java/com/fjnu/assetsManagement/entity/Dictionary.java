package com.fjnu.assetsManagement.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dictionary")
public class Dictionary {


    @Getter(onMethod_ = {@Id, @Column(name = "id"), @GeneratedValue(strategy = GenerationType.IDENTITY)})
    private Long id;//主键ID

    @Getter(onMethod_ = {@Column(name = "kind")})
    private String kind;//类别
}
