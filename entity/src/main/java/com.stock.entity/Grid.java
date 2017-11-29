package com.stock.entity;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 2016/3/16.
 */
@Entity
@Table(name="GRID")
public class Grid implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="STOCK")
    private String stock;

    @Column(name="SELL_PRICE")
    private Double sellPrice;

    @Column(name="BUY_PRICE")
    private Double buyPrice;

    @Column(name="OPERATE_NUM")
    private Integer operateNum;

    @Column(name="ENABLE")
    private Boolean enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getOperateNum() {
        return operateNum;
    }

    public void setOperateNum(Integer operateNum) {
        this.operateNum = operateNum;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

}
