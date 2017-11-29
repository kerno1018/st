package com.stock.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kerno on 2016/4/9.
 */
@Entity
@Table(name="STOCK_VALUE")
public class StockValue {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="CODE")
    private String code;
    @Column(name="STOCK_ID")
    private Integer stockId;
    @Column(name="DEAL_VOLUME")
    private Double dealVolume;
    @Column(name="FOLLOW_MARKET_VALUE")
    private Double followMarketValue;
    @Column(name="PE")
    private Double pe;
    @Column(name="EACH_WORTH")
    private Double eachWorth;
    @Column(name="TOTAL_EQUIP")
    private Double totalEquip;
    @Column(name="TURN_OVER")
    private Double turnOver;
    @Column(name="TOTAL_MARKET_VALUE")
    private Double totalMarketValue;
    @Column(name="PB")
    private Double pb;
    @Column(name="EACH_NET_ASSET")
    private Double eachNetAsset;
    @Column(name="FOLLOW_EQUIP")
    private Double followEquip;
    @Column(name="CREATE_DATE")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDealVolume() {
        return dealVolume == null? 0.0 : dealVolume;
    }

    public void setDealVolume(Double dealVolume) {
        this.dealVolume = dealVolume;
    }

    public Double getFollowMarketValue() {
        return followMarketValue == null? 0.0 : followMarketValue;
    }

    public void setFollowMarketValue(Double followMarketValue) {
        this.followMarketValue = followMarketValue;
    }

    public Double getPe() {
        return pe == null? 0.0 : pe;
    }

    public void setPe(Double pe) {
        this.pe = pe;
    }

    public Double getEachWorth() {
        return eachWorth == null? 0.0 : eachWorth;
    }

    public void setEachWorth(Double eachWorth) {
        this.eachWorth = eachWorth;
    }

    public Double getTotalEquip() {
        return totalEquip == null? 0.0 : totalEquip;
    }

    public void setTotalEquip(Double totalEquip) {
        this.totalEquip = totalEquip;
    }

    public Double getTurnOver() {
        return turnOver == null? 0.0 : turnOver;
    }

    public void setTurnOver(Double turnOver) {
        this.turnOver = turnOver;
    }

    public Double getTotalMarketValue() {
        return totalMarketValue == null? 0.0 : totalMarketValue;
    }

    public void setTotalMarketValue(Double totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public Double getPb() {
        return pb == null? 0.0 : pb;
    }

    public void setPb(Double pb) {
        this.pb = pb;
    }

    public Double getEachNetAsset() {
        return eachNetAsset == null? 0.0 : eachNetAsset;
    }

    public void setEachNetAsset(Double eachNetAsset) {
        this.eachNetAsset = eachNetAsset;
    }

    public Double getFollowEquip() {
        return followEquip == null? 0.0 : followEquip;
    }

    public void setFollowEquip(Double followEquip) {
        this.followEquip = followEquip;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
