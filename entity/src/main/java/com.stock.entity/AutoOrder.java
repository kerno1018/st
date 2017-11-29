package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 2016/3/16.
 */
@Entity
@Table(name="AUTO_ORDER")
public class AutoOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="STOCK")
    private String stock;

    @Column(name="PRICE")
    private Double price;

    @Column(name="OPERATE_NUM")
    private Integer operateNum;

    @Column(name="OPERATE_TIME")
    private String operateTime;

    @Column(name="STATUS")
    private Boolean executeStatus;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getOperateNum() {
        return operateNum;
    }

    public void setOperateNum(Integer operateNum) {
        this.operateNum = operateNum;
    }

    public Boolean getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(Boolean executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
