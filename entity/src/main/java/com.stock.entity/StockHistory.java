package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/14/2016.
 */
@Entity
@Table(name="STOCK_HISTORY")
public class StockHistory implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "STOCK_ID")
    private Integer stockId;

    @Column(name="CLOSE_PRICE")
    private Double closePrice;

    @Column(name="YESTERDAY_CLOSE_PRICE")
    private Double yesterdayClosePrice;

    @Column(name="OPEN_PRICE")
    private Double openPrice;

    @Column(name="HIGHEST_PRICE")
    private Double highestPrice;

    @Column(name="LOWEST_PRICE")
    private Double lowestPrice;

    @Column(name="TURNOVER_VOLUME")
    private Double turnOverVolume;

    @Column(name="TURNOVER_VALUE")
    private Double turnOverValue;

    @Column(name="TRADE_DATE")
    private Date tradeDate;

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

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(Double yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public Double getTurnOverVolume() {
        return turnOverVolume;
    }

    public void setTurnOverVolume(Double turnOverVolume) {
        this.turnOverVolume = turnOverVolume;
    }

    public Double getTurnOverValue() {
        return turnOverValue;
    }

    public void setTurnOverValue(Double turnOverValue) {
        this.turnOverValue = turnOverValue;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }
}
