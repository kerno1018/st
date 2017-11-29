package com.stock.entity;

import com.stock.util.CacluateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/14/2016.
 */
@Entity
@Table(name="MONTH_TO_MONTH")
public class MonthToMonth implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="STOCK_CODE")
    private String code;
    @Column(name="LIMIT_UP")
    private Integer limitUp;
    @Column(name="LIMIT_DOWN")
    private Integer limitDown;
    @Column(name="LIMIT_UP_FIVE")
    private Integer limitUp5;
    @Column(name="LIMIT_DOWN_FIVE")
    private Integer limitDown5;

    @Column(name="Y_LIMIT_UP")
    private Integer ylimitUp;
    @Column(name="Y_LIMIT_DOWN")
    private Integer ylimitDown;
    @Column(name="Y_LIMIT_UP_FIVE")
    private Integer ylimitUp5;
    @Column(name="Y_LIMIT_DOWN_FIVE")
    private Integer ylimitDown5;
    @Column(name="PRICE")
    private Double price;
    @Column(name="POSITION")
    private Integer position;
    @Column(name="DEAL_DATE")
    private Date dealDate;
    @Transient
    private Double ratioUp;
    @Transient
    private Double ratioUp5;
    @Transient
    private Double ratioDown;
    @Transient
    private Double ratioDown5;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLimitUp() {
        return limitUp;
    }

    public void setLimitUp(Integer limitUp) {
        this.limitUp = limitUp;
    }

    public Integer getLimitDown() {
        return limitDown;
    }

    public void setLimitDown(Integer limitDown) {
        this.limitDown = limitDown;
    }

    public Integer getLimitUp5() {
        return limitUp5;
    }

    public void setLimitUp5(Integer limitUp5) {
        this.limitUp5 = limitUp5;
    }

    public Integer getLimitDown5() {
        return limitDown5;
    }

    public void setLimitDown5(Integer limitDown5) {
        this.limitDown5 = limitDown5;
    }

    public Integer getYlimitUp() {
        return ylimitUp;
    }

    public void setYlimitUp(Integer ylimitUp) {
        this.ylimitUp = ylimitUp;
    }

    public Integer getYlimitDown() {
        return ylimitDown;
    }

    public void setYlimitDown(Integer ylimitDown) {
        this.ylimitDown = ylimitDown;
    }

    public Integer getYlimitUp5() {
        return ylimitUp5;
    }

    public void setYlimitUp5(Integer ylimitUp5) {
        this.ylimitUp5 = ylimitUp5;
    }

    public Integer getYlimitDown5() {
        return ylimitDown5;
    }

    public void setYlimitDown5(Integer ylimitDown5) {
        this.ylimitDown5 = ylimitDown5;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getRatioUp() {
        if(limitUp != null && ylimitUp != null){
            if(limitUp ==0 || ylimitUp ==0){
                return "- -";
            }
            return CacluateUtil.format(((limitUp - ylimitUp) / Double.valueOf(ylimitUp))*100);
        }
        return "- -";
    }

    public String getRatioUp5() {
        if(limitUp5 != null && ylimitUp5 != null){
            if(limitUp5 ==0 || ylimitUp5 ==0){
                return "- -";
            }
            return CacluateUtil.format(((limitUp5 - ylimitUp5) / Double.valueOf(ylimitUp5))*100);
        }
        return "- -";
    }

    public String getRatioDown() {
        if(limitDown != null && ylimitDown != null){
            if(limitDown ==0 || ylimitDown ==0){
                return "- -";
            }
            return CacluateUtil.format(((limitDown - ylimitDown) / Double.valueOf(ylimitDown))*100);
        }
        return "- -";
    }

    public String getRatioDown5() {
        if(limitDown5 != null && ylimitDown5 != null){
            if(limitDown5 ==0 || ylimitDown5 ==0){
                return "- -";
            }
            return CacluateUtil.format(((limitDown5 - ylimitDown5) / Double.valueOf(ylimitDown5))*100);
        }
        return "- -";
    }
    @Transient
    public String getShowDate(){
        return CacluateUtil.format(dealDate);
    }
}
