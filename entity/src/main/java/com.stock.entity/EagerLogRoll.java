package com.stock.entity;


import com.stock.util.CacluateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/23/2016.
 */
@Entity
@Table(name="ZQ_EAGER_ROLLING_COUNT")
public class EagerLogRoll implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="STOCKA")
    private String stockA;

    @Column(name="STOCKB")
    private String stockB;
    @Column(name="CURRENT_PRICE_A")
    private Double currentPriceA;
    @Column(name="CURRENT_PRICE_B")
    private Double currentPriceB;

    @Column(name="CHANGE_TO_PRICE_A")
    private Double changeToPriceA;

    @Column(name="CHANGE_TO_PRICE_B")
    private Double changeToPriceB;
    @Column(name="CHANGE_TO_A")
    private String changeToA;
    @Column(name="CHANGE_TO_B")
    private String changeToB;

    @Column(name="ONE_A")
    private Double oneA;
    @Column(name="TWO_A")
    private Double twoA;
    @Column(name="THREE_A")
    private Double threeA;
    @Column(name="FOUR_A")
    private Double fourA;
    @Column(name="FIVE_A")
    private Double fiveA;

    @Column(name="ONE_B")
    private Double oneB;
    @Column(name="TWO_B")
    private Double twoB;
    @Column(name="THREE_B")
    private Double threeB;
    @Column(name="FOUR_B")
    private Double fourB;
    @Column(name="FIVE_B")
    private Double fiveB;

    @Column(name="ADVISE_ONE_A")
    private Double adviseOneA;
    @Column(name="ADVISE_TWO_A")
    private Double adviseTwoA;
    @Column(name="ADVISE_THREE_A")
    private Double adviseThreeA;
    @Column(name="ADVISE_FOUR_A")
    private Double adviseFourA;
    @Column(name="ADVISE_FIVE_A")
    private Double adviseFiveA;

    @Column(name="ADVISE_ONE_B")
    private Double adviseOneB;
    @Column(name="ADVISE_TWO_B")
    private Double adviseTwoB;
    @Column(name="ADVISE_THREE_B")
    private Double adviseThreeB;
    @Column(name="ADVISE_FOUR_B")
    private Double adviseFourB;
    @Column(name="ADVISE_FIVE_B")
    private Double adviseFiveB;

    @Column(name = "TYPES")
    private String type;

    @Column(name="SELL_PREMIUM")
    private String sellPremium;
    @Column(name="CHANGE_PREMIUM")
    private String changePremium;

    @Column(name="CHANGEONE_PREMIUM")
    private Double adviseSellOnePremium;
    @Column(name="CHANGETWO_PREMIUM")
    private Double adviseSellTwoPremium;
    @Column(name="CHANGETHREE_PREMIUM")
    private Double adviseSellThreePremium;
    @Column(name="CHANGEFOUR_PREMIUM")
    private Double adviseSellFourPremium;
    @Column(name="CHANGEFIVE_PREMIUM")
    private Double adviseSellFivePremium;

    @Column(name="OWNONE_PREMIUM")
    private Double ownOnePremium;
    @Column(name="OWNTWO_PREMIUM")
    private Double ownTwoPremium;
    @Column(name="OWNTHREE_PREMIUM")
    private Double ownThreePremium;
    @Column(name="OWNTFOUR_PREMIUM")
    private Double ownFourPremium;
    @Column(name="OWNTFIVE_PREMIUM")
    private Double ownFivePremium;
    @Column(name="OWN_PRICEONEA")
    private Double ownPriceOneA;
    @Column(name="OWN_PRICETWOA")
    private Double ownPriceTwoA;
    @Column(name="OWN_PRICETHREEA")
    private Double ownPriceThreeA;
    @Column(name="OWN_PRICEFOURA")
    private Double ownPriceFourA;
    @Column(name="OWN_PRICEFIVEA")
    private Double ownPriceFiveA;
    @Column(name="OWN_PRICEONEB")
    private Double ownPriceOneB;
    @Column(name="OWN_PRICETWOB")
    private Double ownPriceTwoB;
    @Column(name="OWN_PRICETHREEB")
    private Double ownPriceThreeB;
    @Column(name="OWN_PRICEFOURB")
    private Double ownPriceFourB;
    @Column(name="OWN_PRICEFIVEB")
    private Double ownPriceFiveB;

    @Column(name="ADVISE_PRICEONEA")
    private Double advisePriceOneA;
    @Column(name="ADVISE_PRICETWOA")
    private Double advisePriceTwoA;
    @Column(name="ADVISE_PRICETHREEA")
    private Double advisePriceThreeA;
    @Column(name="ADVISE_PRICETFOURA")
    private Double advisePriceFourA;
    @Column(name="ADVISE_PRICEFIVEA")
    private Double advisePriceFiveA;
    @Column(name="ADVISE_PRICEONEB")
    private Double advisePriceOneB;
    @Column(name="ADVISE_PRICETWOB")
    private Double advisePriceTwoB;
    @Column(name="ADVISE_PRICETHREEB")
    private Double advisePriceThreeB;
    @Column(name="ADVISE_PRICEFOURB")
    private Double advisePriceFourB;
    @Column(name="ADVISE_PRICEFIVEB")
    private Double advisePriceFiveB;

    @Column(name="ADVISE_EAGERPRICEB")
    private Double adviseEagerPriceB;
    @Column(name="ADVISE_EAGERPRICEA")
    private Double adviseEagerPriceA;

    @Column(name="OWN_EAGERPRICB")
    private Double ownEagerPriceB;
    @Column(name="OWN_EAGERPRICA")
    private Double ownEagerPriceA;

    @Column(name="CREATE_TIME")
    private Date createTime;

    public Double getOwnPriceOneA() {
        return ownPriceOneA;
    }

    public void setOwnPriceOneA(Double ownPriceOneA) {
        this.ownPriceOneA = ownPriceOneA;
    }

    public Double getOwnPriceTwoA() {
        return ownPriceTwoA;
    }

    public void setOwnPriceTwoA(Double ownPriceTwoA) {
        this.ownPriceTwoA = ownPriceTwoA;
    }

    public Double getOwnPriceThreeA() {
        return ownPriceThreeA;
    }

    public void setOwnPriceThreeA(Double ownPriceThreeA) {
        this.ownPriceThreeA = ownPriceThreeA;
    }

    public Double getOwnPriceFourA() {
        return ownPriceFourA;
    }

    public void setOwnPriceFourA(Double ownPriceFourA) {
        this.ownPriceFourA = ownPriceFourA;
    }

    public Double getOwnPriceFiveA() {
        return ownPriceFiveA;
    }

    public void setOwnPriceFiveA(Double ownPriceFiveA) {
        this.ownPriceFiveA = ownPriceFiveA;
    }

    public Double getOwnPriceOneB() {
        return ownPriceOneB;
    }

    public void setOwnPriceOneB(Double ownPriceOneB) {
        this.ownPriceOneB = ownPriceOneB;
    }

    public Double getOwnPriceTwoB() {
        return ownPriceTwoB;
    }

    public void setOwnPriceTwoB(Double ownPriceTwoB) {
        this.ownPriceTwoB = ownPriceTwoB;
    }

    public Double getOwnPriceThreeB() {
        return ownPriceThreeB;
    }

    public void setOwnPriceThreeB(Double ownPriceThreeB) {
        this.ownPriceThreeB = ownPriceThreeB;
    }

    public Double getOwnPriceFourB() {
        return ownPriceFourB;
    }

    public void setOwnPriceFourB(Double ownPriceFourB) {
        this.ownPriceFourB = ownPriceFourB;
    }

    public Double getOwnPriceFiveB() {
        return ownPriceFiveB;
    }

    public void setOwnPriceFiveB(Double ownPriceFiveB) {
        this.ownPriceFiveB = ownPriceFiveB;
    }

    public Double getAdvisePriceOneA() {
        return advisePriceOneA;
    }

    public void setAdvisePriceOneA(Double advisePriceOneA) {
        this.advisePriceOneA = advisePriceOneA;
    }

    public Double getAdvisePriceTwoA() {
        return advisePriceTwoA;
    }

    public void setAdvisePriceTwoA(Double advisePriceTwoA) {
        this.advisePriceTwoA = advisePriceTwoA;
    }

    public Double getAdvisePriceThreeA() {
        return advisePriceThreeA;
    }

    public void setAdvisePriceThreeA(Double advisePriceThreeA) {
        this.advisePriceThreeA = advisePriceThreeA;
    }

    public Double getAdvisePriceFourA() {
        return advisePriceFourA;
    }

    public void setAdvisePriceFourA(Double advisePriceFourA) {
        this.advisePriceFourA = advisePriceFourA;
    }

    public Double getAdvisePriceFiveA() {
        return advisePriceFiveA;
    }

    public void setAdvisePriceFiveA(Double advisePriceFiveA) {
        this.advisePriceFiveA = advisePriceFiveA;
    }

    public Double getAdvisePriceOneB() {
        return advisePriceOneB;
    }

    public void setAdvisePriceOneB(Double advisePriceOneB) {
        this.advisePriceOneB = advisePriceOneB;
    }

    public Double getAdvisePriceTwoB() {
        return advisePriceTwoB;
    }

    public void setAdvisePriceTwoB(Double advisePriceTwoB) {
        this.advisePriceTwoB = advisePriceTwoB;
    }

    public Double getAdvisePriceThreeB() {
        return advisePriceThreeB;
    }

    public void setAdvisePriceThreeB(Double advisePriceThreeB) {
        this.advisePriceThreeB = advisePriceThreeB;
    }

    public Double getAdvisePriceFourB() {
        return advisePriceFourB;
    }

    public void setAdvisePriceFourB(Double advisePriceFourB) {
        this.advisePriceFourB = advisePriceFourB;
    }

    public Double getAdvisePriceFiveB() {
        return advisePriceFiveB;
    }

    public void setAdvisePriceFiveB(Double advisePriceFiveB) {
        this.advisePriceFiveB = advisePriceFiveB;
    }

    public String getDisplayPremiumOne(){
        return CacluateUtil.format(ownOnePremium - adviseSellOnePremium);
    }
    public String getDisplayPremiumTwo(){
        return CacluateUtil.format(ownTwoPremium - adviseSellTwoPremium);
    }
    public String getDisplayPremiumThree(){
        return CacluateUtil.format(ownThreePremium - adviseSellThreePremium);
    }
    public String getDisplayPremiumFour(){
        return CacluateUtil.format(ownFourPremium - adviseSellTwoPremium);
    }
    public String getDisplayPremiumFive(){
        return CacluateUtil.format(ownFivePremium - adviseSellFivePremium);
    }

    public String getDisplayOwnSellAPrice(){
        return ownPriceOneA+"(买1)<br>"+ownPriceTwoA+"(买2)";
    }
    public String getDisplayAdviseBuyAPrice(){
        return advisePriceTwoA+"(卖2)<br>"+advisePriceOneA+"(卖1)";
    }
    public String getDisplayOwnSellBPrice(){
        return ownPriceOneB+"(买1)<br>"+ownPriceTwoB+"(买2)";
    }
    public String getDisplayAdviseBuyBPrice(){
        return advisePriceTwoB+"(卖2)<br>"+advisePriceOneB+"(卖1)";
    }
    public Double getDisplayOwnVolumeA(){
        return oneA + twoA;
    }

    public Double getDisplayOwnVolumeB(){
        return oneB + twoB;
    }

    public String getDisplayOwnVolumeAMoney(){
        return CacluateUtil.format((oneA * ownPriceOneA )/10000) + "<br>" + CacluateUtil.format((twoA * ownPriceTwoA)/10000);
    }
    public String getDisplayOwnVolumeBMoney(){
        return CacluateUtil.format((oneB * ownPriceOneB)/10000) + "<br>"+CacluateUtil.format(( twoB * ownPriceTwoB)/10000);
    }

    public Double getDisplayAdviseVolumeA(){
        return adviseOneA + adviseTwoA;
    }

    public Double getDisplayAdviseVolumeB(){
        return adviseOneB + adviseTwoB;
    }

    public String getDisplayAdviseVolumeAMoney(){
        return CacluateUtil.format((adviseTwoA * advisePriceTwoA)/10000) +"<br>"+CacluateUtil.format((adviseOneA * advisePriceOneA)/10000);
    }
    public String getDisplayAdviseVolumeBMoney(){
        return CacluateUtil.format((adviseTwoB * advisePriceTwoB)/10000) +"<br>"+CacluateUtil.format((adviseOneB * advisePriceOneB)/10000);
    }

    public String getDisplayDate(){
        return CacluateUtil.format(createTime);
    }
    public Double getAdviseOneA() {
        return adviseOneA;
    }

    public void setAdviseOneA(Double adviseOneA) {
        this.adviseOneA = adviseOneA;
    }

    public Double getAdviseTwoA() {
        return adviseTwoA;
    }

    public void setAdviseTwoA(Double adviseTwoA) {
        this.adviseTwoA = adviseTwoA;
    }

    public Double getAdviseThreeA() {
        return adviseThreeA;
    }

    public void setAdviseThreeA(Double adviseThreeA) {
        this.adviseThreeA = adviseThreeA;
    }

    public Double getAdviseFourA() {
        return adviseFourA;
    }

    public void setAdviseFourA(Double adviseFourA) {
        this.adviseFourA = adviseFourA;
    }

    public Double getAdviseFiveA() {
        return adviseFiveA;
    }

    public void setAdviseFiveA(Double adviseFiveA) {
        this.adviseFiveA = adviseFiveA;
    }

    public Double getAdviseOneB() {
        return adviseOneB;
    }

    public void setAdviseOneB(Double adviseOneB) {
        this.adviseOneB = adviseOneB;
    }

    public Double getAdviseTwoB() {
        return adviseTwoB;
    }

    public void setAdviseTwoB(Double adviseTwoB) {
        this.adviseTwoB = adviseTwoB;
    }

    public Double getAdviseThreeB() {
        return adviseThreeB;
    }

    public void setAdviseThreeB(Double adviseThreeB) {
        this.adviseThreeB = adviseThreeB;
    }

    public Double getAdviseFourB() {
        return adviseFourB;
    }

    public void setAdviseFourB(Double adviseFourB) {
        this.adviseFourB = adviseFourB;
    }

    public Double getAdviseFiveB() {
        return adviseFiveB;
    }

    public void setAdviseFiveB(Double adviseFiveB) {
        this.adviseFiveB = adviseFiveB;
    }

    public String getSellPremium() {
        return sellPremium;
    }

    public void setSellPremium(String sellPremium) {
        this.sellPremium = sellPremium;
    }

    public String getChangePremium() {
        return changePremium;
    }

    public void setChangePremium(String changePremium) {
        this.changePremium = changePremium;
    }

    public Double getAdviseSellOnePremium() {
        return adviseSellOnePremium;
    }

    public void setAdviseSellOnePremium(Double adviseSellOnePremium) {
        this.adviseSellOnePremium = adviseSellOnePremium;
    }

    public Double getAdviseSellTwoPremium() {
        return adviseSellTwoPremium;
    }

    public void setAdviseSellTwoPremium(Double adviseSellTwoPremium) {
        this.adviseSellTwoPremium = adviseSellTwoPremium;
    }

    public Double getAdviseSellThreePremium() {
        return adviseSellThreePremium;
    }

    public void setAdviseSellThreePremium(Double adviseSellThreePremium) {
        this.adviseSellThreePremium = adviseSellThreePremium;
    }

    public Double getAdviseSellFourPremium() {
        return adviseSellFourPremium;
    }

    public void setAdviseSellFourPremium(Double adviseSellFourPremium) {
        this.adviseSellFourPremium = adviseSellFourPremium;
    }

    public Double getAdviseSellFivePremium() {
        return adviseSellFivePremium;
    }

    public void setAdviseSellFivePremium(Double adviseSellFivePremium) {
        this.adviseSellFivePremium = adviseSellFivePremium;
    }

    public Double getOwnOnePremium() {
        return ownOnePremium;
    }

    public void setOwnOnePremium(Double ownOnePremium) {
        this.ownOnePremium = ownOnePremium;
    }

    public Double getOwnTwoPremium() {
        return ownTwoPremium;
    }

    public void setOwnTwoPremium(Double ownTwoPremium) {
        this.ownTwoPremium = ownTwoPremium;
    }

    public Double getOwnThreePremium() {
        return ownThreePremium;
    }

    public void setOwnThreePremium(Double ownThreePremium) {
        this.ownThreePremium = ownThreePremium;
    }

    public Double getOwnFourPremium() {
        return ownFourPremium;
    }

    public void setOwnFourPremium(Double ownFourPremium) {
        this.ownFourPremium = ownFourPremium;
    }

    public Double getOwnFivePremium() {
        return ownFivePremium;
    }

    public void setOwnFivePremium(Double ownFivePremium) {
        this.ownFivePremium = ownFivePremium;
    }

    public Double getChangeToPriceA() {
        return changeToPriceA;
    }

    public void setChangeToPriceA(Double changeToPriceA) {
        this.changeToPriceA = changeToPriceA;
    }

    public Double getChangeToPriceB() {
        return changeToPriceB;
    }

    public void setChangeToPriceB(Double changeToPriceB) {
        this.changeToPriceB = changeToPriceB;
    }

    public String getChangeToA() {
        return changeToA;
    }

    public void setChangeToA(String changeToA) {
        this.changeToA = changeToA;
    }

    public String getChangeToB() {
        return changeToB;
    }

    public void setChangeToB(String changeToB) {
        this.changeToB = changeToB;
    }

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

    public String getStockA() {
        return stockA;
    }

    public void setStockA(String stockA) {
        this.stockA = stockA;
    }

    public String getStockB() {
        return stockB;
    }

    public void setStockB(String stockB) {
        this.stockB = stockB;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCurrentPriceA() {
        return currentPriceA;
    }

    public void setCurrentPriceA(Double currentPriceA) {
        this.currentPriceA = currentPriceA;
    }

    public Double getCurrentPriceB() {
        return currentPriceB;
    }

    public void setCurrentPriceB(Double currentPriceB) {
        this.currentPriceB = currentPriceB;
    }



    public Double getOneA() {
        return oneA;
    }

    public void setOneA(Double oneA) {
        this.oneA = oneA;
    }

    public Double getTwoA() {
        return twoA;
    }

    public void setTwoA(Double twoA) {
        this.twoA = twoA;
    }

    public Double getThreeA() {
        return threeA;
    }

    public void setThreeA(Double threeA) {
        this.threeA = threeA;
    }

    public Double getFourA() {
        return fourA;
    }

    public void setFourA(Double fourA) {
        this.fourA = fourA;
    }

    public Double getFiveA() {
        return fiveA;
    }

    public void setFiveA(Double fiveA) {
        this.fiveA = fiveA;
    }

    public Double getOneB() {
        return oneB;
    }

    public void setOneB(Double oneB) {
        this.oneB = oneB;
    }

    public Double getTwoB() {
        return twoB;
    }

    public void setTwoB(Double twoB) {
        this.twoB = twoB;
    }

    public Double getThreeB() {
        return threeB;
    }

    public void setThreeB(Double threeB) {
        this.threeB = threeB;
    }

    public Double getFourB() {
        return fourB;
    }

    public void setFourB(Double fourB) {
        this.fourB = fourB;
    }

    public Double getFiveB() {
        return fiveB;
    }

    public void setFiveB(Double fiveB) {
        this.fiveB = fiveB;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Double getAdviseEagerPriceB() {
        return adviseEagerPriceB;
    }

    public void setAdviseEagerPriceB(Double adviseEagerPriceB) {
        this.adviseEagerPriceB = adviseEagerPriceB;
    }

    public Double getAdviseEagerPriceA() {
        return adviseEagerPriceA;
    }

    public void setAdviseEagerPriceA(Double adviseEagerPriceA) {
        this.adviseEagerPriceA = adviseEagerPriceA;
    }

    public Double getOwnEagerPriceB() {
        return ownEagerPriceB;
    }

    public void setOwnEagerPriceB(Double ownEagerPriceB) {
        this.ownEagerPriceB = ownEagerPriceB;
    }

    public Double getOwnEagerPriceA() {
        return ownEagerPriceA;
    }

    public void setOwnEagerPriceA(Double ownEagerPriceA) {
        this.ownEagerPriceA = ownEagerPriceA;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
