package com.stock.entity;

import com.stock.util.CacluateUtil;
import org.apache.commons.httpclient.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/12/2016.
 */
@Entity
@Table(name="PICK_MONEY")
public class PickMoney implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name = "FUND")
    private String fund;
    @Column(name = "BUY_PRICE_ONE_A")
    private Double buyPriceOneA;
    @Column(name = "BUY_PRICE_ONE_B")
    private Double buyPriceOneB;
    @Column(name = "BUY_PRICE_TWO_A")
    private Double buyPriceTwoA;
    @Column(name = "BUY_PRICE_TWO_B")
    private Double buyPriceTwoB;
    @Column(name = "BUY_VOLUME_ONE_A")
    private Double buyOneVolumeA;
    @Column(name = "BUY_VOLUME_ONE_B")
    private Double buyOneVolumeB;
    @Column(name = "BUY_VOLUME_TWO_A")
    private Double buyTwoVolumeA;
    @Column(name = "BUY_VOLUME_TWO_B")
    private Double buyTwoVolumeB;
    @Column(name = "SELL_PRICE_ONE_A")
    private Double sellPriceOneA;
    @Column(name = "SELL_PRICE_ONE_B")
    private Double sellPriceOneB;
    @Column(name = "SELL_PRICE_TWO_A")
    private Double sellPriceTwoA;
    @Column(name = "SELL_PRICE_TWO_B")
    private Double sellPriceTwoB;
    @Column(name = "SELL_VOLUME_ONE_A")
    private Double sellOneVolumeA;
    @Column(name = "SELL_VOLUME_ONE_B")
    private Double sellOneVolumeB;
    @Column(name = "SELL_VOLUME_TWO_A")
    private Double sellTwoVolumeA;
    @Column(name = "SELL_VOLUME_TWO_B")
    private Double sellTwoVolumeB;
    @Column(name = "BUY_PREMIUM")
    private Double buyPremium;
    @Column(name = "SELL_PREMIUM")
    private Double sellPremium;
    @Column(name = "BUY_SII")
    private Double buySII;
    @Column(name = "SELL_SII")
    private Double sellSII;
    @Column(name="CREATE_TIME")
    private Date createTIME;

    @Transient
    private String inCome;

    public String getInCome(){
        double avgYesterdayPrice  = ((getBuyPriceOneA() + getBuyPriceTwoA())/2 + (getBuyPriceOneB() + getBuyPriceTwoB())/2);
        double todayPrice = ((getSellPriceOneA()+getSellPriceOneB())/2 + (getSellPriceOneB()+getSellPriceTwoB())/2);
        return  ((todayPrice - avgYesterdayPrice)/avgYesterdayPrice * 100) +"%";
    }

    public String getDisplayDate(){
        return CacluateUtil.format(createTIME);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public Double getBuyPriceOneA() {
        return buyPriceOneA == null ? 0.0 : buyPriceOneA;
    }

    public void setBuyPriceOneA(Double buyPriceOneA) {
        this.buyPriceOneA = buyPriceOneA;
    }

    public Double getBuyPriceOneB() {
        return buyPriceOneB == null ? 0.0 : buyPriceOneB;
    }

    public void setBuyPriceOneB(Double buyPriceOneB) {
        this.buyPriceOneB = buyPriceOneB;
    }

    public Double getBuyPriceTwoA() {
        return buyPriceTwoA == null ? 0.0 : buyPriceTwoA;
    }

    public void setBuyPriceTwoA(Double buyPriceTwoA) {
        this.buyPriceTwoA = buyPriceTwoA;
    }

    public Double getBuyPriceTwoB() {
        return buyPriceTwoB == null ? 0.0 : buyPriceTwoB;
    }

    public void setBuyPriceTwoB(Double buyPriceTwoB) {
        this.buyPriceTwoB = buyPriceTwoB;
    }

    public Double getBuyOneVolumeA() {
        return buyOneVolumeA;
    }

    public void setBuyOneVolumeA(Double buyOneVolumeA) {
        this.buyOneVolumeA = buyOneVolumeA;
    }

    public Double getBuyOneVolumeB() {
        return buyOneVolumeB;
    }

    public void setBuyOneVolumeB(Double buyOneVolumeB) {
        this.buyOneVolumeB = buyOneVolumeB;
    }

    public Double getBuyTwoVolumeA() {
        return buyTwoVolumeA;
    }

    public void setBuyTwoVolumeA(Double buyTwoVolumeA) {
        this.buyTwoVolumeA = buyTwoVolumeA;
    }

    public Double getBuyTwoVolumeB() {
        return buyTwoVolumeB;
    }

    public void setBuyTwoVolumeB(Double buyTwoVolumeB) {
        this.buyTwoVolumeB = buyTwoVolumeB;
    }

    public Double getSellPriceOneA() {
        return sellPriceOneA == null ? 0.0 : sellPriceOneA;
    }

    public void setSellPriceOneA(Double sellPriceOneA) {
        this.sellPriceOneA = sellPriceOneA;
    }

    public Double getSellPriceOneB() {
        return sellPriceOneB == null ? 0.0 : sellPriceOneB;
    }

    public void setSellPriceOneB(Double sellPriceOneB) {
        this.sellPriceOneB = sellPriceOneB;
    }

    public Double getSellPriceTwoA() {
        return sellPriceTwoA == null ? 0.0 : sellPriceTwoA;
    }

    public void setSellPriceTwoA(Double sellPriceTwoA) {
        this.sellPriceTwoA = sellPriceTwoA;
    }

    public Double getSellPriceTwoB() {
        return sellPriceTwoB == null ? 0.0 : sellPriceTwoB;
    }

    public void setSellPriceTwoB(Double sellPriceTwoB) {
        this.sellPriceTwoB = sellPriceTwoB;
    }

    public Double getSellOneVolumeA() {
        return sellOneVolumeA;
    }

    public void setSellOneVolumeA(Double sellOneVolumeA) {
        this.sellOneVolumeA = sellOneVolumeA;
    }

    public Double getSellOneVolumeB() {
        return sellOneVolumeB;
    }

    public void setSellOneVolumeB(Double sellOneVolumeB) {
        this.sellOneVolumeB = sellOneVolumeB;
    }

    public Double getSellTwoVolumeA() {
        return sellTwoVolumeA;
    }

    public void setSellTwoVolumeA(Double sellTwoVolumeA) {
        this.sellTwoVolumeA = sellTwoVolumeA;
    }

    public Double getSellTwoVolumeB() {
        return sellTwoVolumeB;
    }

    public void setSellTwoVolumeB(Double sellTwoVolumeB) {
        this.sellTwoVolumeB = sellTwoVolumeB;
    }

    public Double getBuyPremium() {
        return buyPremium;
    }

    public void setBuyPremium(Double buyPremium) {
        this.buyPremium = buyPremium;
    }

    public Double getSellPremium() {
        return sellPremium;
    }

    public void setSellPremium(Double sellPremium) {
        this.sellPremium = sellPremium;
    }

    public Double getBuySII() {
        return buySII;
    }

    public void setBuySII(Double buySII) {
        this.buySII = buySII;
    }

    public Double getSellSII() {
        return sellSII;
    }

    public void setSellSII(Double sellSII) {
        this.sellSII = sellSII;
    }

    public Date getCreateTIME() {
        return createTIME;
    }

    public void setCreateTIME(Date createTIME) {
        this.createTIME = createTIME;
    }

    public void setInCome(String inCome) {
        this.inCome = inCome;
    }
}
