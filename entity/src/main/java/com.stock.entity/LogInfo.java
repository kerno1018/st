package com.stock.entity;


import antlr.StringUtils;
import com.stock.util.CacluateUtil;
import org.jsoup.helper.StringUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kerno on 1/23/2016.
 */
@Entity
@Table(name="ZQ_ROLLING_LOG")
public class LogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="SELL_STOCKA")
    private String sellStockA;

    @Column(name="SELL_STOCKB")
    private String sellStockB;

    @Column(name="BUY_STOCKA")
    private String buyStockA;

    @Column(name="BUY_STOCKB")
    private String buyStockB;

    @Column(name="SELL_PRICEA")
    private Double sellPriceA;

    @Column(name="SELL_PRICEB")
    private Double sellPriceB;

    @Column(name="BUY_PRICEA")
    private Double buyPriceA;

    @Column(name="BUY_PRICEB")
    private Double buyPriceB;

    @Column(name="YJ")
    private Double yj;

    @Column(name="DEAL_BUYPREMINUM")
    private Double dealBuyPreminum;
    @Column(name="DEAL_SELLPREMINUM")
    private Double dealSellPreminum;
    @Column(name="REAL_BUYFUNDNETVALUE")
    private Double realBuyFNV;
    @Column(name="REAL_SELLFUNDNETVALUE")
    private Double realSellFNV;

    @Column(name="FREE_MONEY")
    private Double freeMoney;
    @Column(name="SELLONE_A")
    private Double sellOneA;
    @Column(name="SELLTWO_A")
    private Double sellTwoA;
    @Column(name="SELLTHREE_A")
    private Double sellThreeA;
    @Column(name="SELLFOUR_A")
    private Double sellFourA;
    @Column(name="SELLFIVE_A")
    private Double sellFiveA;

    @Column(name="SELLONE_B")
    private Double sellOneB;
    @Column(name="SELLTWO_B")
    private Double sellTwoB;
    @Column(name="SELLTHREE_B")
    private Double sellThreeB;
    @Column(name="SELLFOUR_B")
    private Double sellFourB;
    @Column(name="SELLFIVE_B")
    private Double sellFiveB;

    @Column(name="BUYONE_A")
    private Double buyOneA;
    @Column(name="BUYTWO_A")
    private Double buyTwoA;
    @Column(name="BUYTHREE_A")
    private Double buyThreeA;
    @Column(name="BUYFOUR_A")
    private Double buyFourA;
    @Column(name="BUYFIVE_A")
    private Double buyFiveA;

    @Column(name="BUYONE_B")
    private Double buyOneB;
    @Column(name="BUYTWO_B")
    private Double buyTwoB;
    @Column(name="BUYTHREE_B")
    private Double buyThreeB;
    @Column(name="BUYFOUR_B")
    private Double buyFourB;
    @Column(name="BUYFIVE_B")
    private Double buyFiveB;
    @Column(name="THEORYBUY_COUNT")
    private String theoryBuyCount;

    @Column(name="THEORYSELL_COUNT")
    private String theorySellCount;

    @Column(name="REALBUY_COUNT")
    private String realBuyCount;

    @Column(name="REALSELL_COUNT")
    private String realSellCount;

    @Column(name="COUNT_MONEY")
    private Double countMoney;
    @Column(name="THEORY_COUNT_MONEY")
    private Double theoryCountMoney;
    @Column(name = "BUY_FUNDNETVALUE")
    private Double buyFundNetValue;
    @Column(name = "SELL_FUNDNETVALUE")
    private Double sellFundNetValue;
    @Column(name="BUY_SECURITIESINDEXINCREASE")
    private Double buySII;
    @Column(name="SELL_SECURITIESINDEXINCREASE")
    private Double sellSII;

    @Column(name="SELLPRICE_ONEA")
    private Double sellPriceOneA;
    @Column(name="SELLPRICE_TWOA")
    private Double sellPriceTwoA;
    @Column(name="SELLPRICE_THREEA")
    private Double sellPriceThreeA;
    @Column(name="SELLPRICE_FOURA")
    private Double sellPriceFourA;
    @Column(name="SELLPRICE_FIVEA")
    private Double sellPriceFiveA;
    @Column(name="SELLPRICE_ONEB")
    private Double sellPriceOneB;
    @Column(name="SELLPRICE_TWOB")
    private Double sellPriceTwoB;
    @Column(name="SELLPRICE_THREEB")
    private Double sellPriceThreeB;
    @Column(name="SELLPRICE_FOURB")
    private Double sellPriceFourB;
    @Column(name="SELLPRICE_FIVEB")
    private Double sellPriceFiveB;

    @Column(name="BUYPRICE_ONEA")
    private Double buyPriceOneA;
    @Column(name="BUYPRICE_TWOA")
    private Double buyPriceTwoA;
    @Column(name="BUYPRICE_THREEA")
    private Double buyPriceThreeA;
    @Column(name="BUYPRICE_FOURA")
    private Double buyPriceFourA;
    @Column(name="BUYPRICE_FIVEA")
    private Double buyPriceFiveA;
    @Column(name="BUYPRICE_ONEB")
    private Double buyPriceOneB;
    @Column(name="BUYPRICE_TWOB")
    private Double buyPriceTwoB;
    @Column(name="BUYPRICE_THREEB")
    private Double buyPriceThreeB;
    @Column(name="BUYPRICE_FOURB")
    private Double buyPriceFourB;
    @Column(name="BUYPRICE_FIVEB")
    private Double buyPriceFiveB;

    @Column(name="SELL_POSITION")
    private Double sellPosition;
    @Column(name="BUY_POSITION")
    private Double buyPosition;
    @Column(name="BUY_PREMINUMONE")
    private Double buyPremiumOne;
    @Column(name="BUY_PREMINUMTWO")
    private Double buyPremiumTwo;
    @Column(name="BUY_PREMINUMTHREE")
    private Double buyPremiumThree;
    @Column(name="BUY_PREMINUMFOUR")
    private Double buyPremiumFour;
    @Column(name="BUY_PREMINUMFIVE")
    private Double buyPremiumFive;

    @Column(name="SELL_PREMINUMONE")
    private Double sellPremiumOne;
    @Column(name="SELL_PREMINUMTWO")
    private Double sellPremiumTwo;
    @Column(name="SELL_PREMINUMTHREE")
    private Double sellPremiumThree;
    @Column(name="SELL_PREMINUMFOUR")
    private Double sellPremiumFour;
    @Column(name="SELL_PREMINUMFIVE")
    private Double sellPremiumFive;

    @Column(name="CREATE_DATE")
    private Date createDate;
    @Column(name="SELL_TIME")
    private Date sellTime;
    @Column(name="BUY_TIME")
    private Date buyTime;

    public Date getSellTime() {
        return sellTime;
    }
    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }
    public Date getBuyTime() {
        return buyTime;
    }

    public Double getRealBuyFNV() {
        return realBuyFNV;
    }

    public void setRealBuyFNV(Double realBuyFNV) {
        this.realBuyFNV = realBuyFNV;
    }

    public Double getRealSellFNV() {
        return realSellFNV;
    }

    public void setRealSellFNV(Double realSellFNV) {
        this.realSellFNV = realSellFNV;
    }

    public Double getDisaplyRealPremium(){
        if(realSellFNV == null || realBuyFNV == null){
            return 0.0;
        }
        return ((((getBuyPriceA()+getBuyPriceB())/2 - realSellFNV)/realSellFNV) - (((getBuyPriceA()+getBuyPriceB())/2 - realBuyFNV)/realBuyFNV) ) * 100;
    }

    public String getDisplayIncome(){
       return CacluateUtil.format((Double.valueOf(realSellCount)*getSellPriceB() +Double.valueOf(realSellCount)*getSellPriceA())  * (getDisaplyRealPremium()/100));
    }


    public String getDisplayDealPriemum(){
        return CacluateUtil.format(dealSellPreminum - dealBuyPreminum);
    }
    public String getDisplayDealBuyAvgPrice(){
        return CacluateUtil.format(getBuyPriceA())+"A<br>"+CacluateUtil.format(getBuyPriceB())+"B";
    }

    public String getDisplayDealSellAvgPrice(){
        return CacluateUtil.format(getBuyPriceA()) +"A<br>"+CacluateUtil.format(getBuyPriceB())+"B";
    }


    public String getDisplayOwnSellAPrice(){
        return sellPriceOneA+"买1<br>"+sellPriceTwoA+"买2";
    }
    public String getDisplayAdviseBuyAPrice(){
        return buyPriceTwoA+"卖2<br>"+buyPriceOneA+"卖1";
    }
    public String getDisplayOwnSellBPrice(){
        return sellPriceOneB+"买1<br>"+sellPriceTwoB+"买2";
    }
    public String getDisplayAdviseBuyBPrice(){
        return buyPriceTwoB+"卖2<br>"+buyPriceOneB+"卖1";
    }
    public String getDisplayOwnVolumeAMoney(){
        return ((Double)((getSellOneA() * getSellPriceOneA() )/10000)).intValue() + "<br>" + ((Double)((getSellTwoA() * getSellPriceTwoA())/10000)).intValue();
    }
    public String getDisplayOwnVolumeBMoney(){
        return ((Double)((getSellOneB() * getSellPriceOneB())/10000)).intValue() + "<br>"+ ((Double)(( getSellTwoB() * getSellPriceTwoB())/10000)).intValue();
    }
    public String getDisplayAdviseVolumeAMoney(){
        return ((Double)((getBuyTwoA() * getBuyPriceTwoA())/10000)).intValue() +"<br>"+((Double)((getBuyOneA() * getBuyPriceOneA())/10000)).intValue();
    }
    public String getDisplayAdviseVolumeBMoney(){
        return ((Double)((getBuyTwoB() * getBuyPriceTwoB())/10000)).intValue() +"<br>"+((Double)((getBuyOneB() * getBuyPriceOneB())/10000)).intValue();
    }

    public String getDisplaySellFundNetValue(){
        return CacluateUtil.format(sellFundNetValue);
    }
    public String getDisplayBuyFundNetValue(){
        return CacluateUtil.format(buyFundNetValue);
    }
    public String getDisplaySellTime(){
        return CacluateUtil.shortFormat(sellTime);
    }
    public String getDisplayBuyTime(){
        return CacluateUtil.shortFormat(buyTime);
    }


    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getRealBuyCount() {
        return realBuyCount;
    }

    public void setRealBuyCount(String realBuyCount) {
        this.realBuyCount = realBuyCount;
    }

    public String getRealSellCount() {
        return realSellCount;
    }

    public void setRealSellCount(String realSellCount) {
        this.realSellCount = realSellCount;
    }

    public Double getBuyPremiumOne() {
        return buyPremiumOne;
    }

    public void setBuyPremiumOne(Double buyPremiumOne) {
        this.buyPremiumOne = buyPremiumOne;
    }

    public Double getBuyPremiumTwo() {
        return buyPremiumTwo;
    }

    public void setBuyPremiumTwo(Double buyPremiumTwo) {
        this.buyPremiumTwo = buyPremiumTwo;
    }

    public Double getBuyPremiumThree() {
        return buyPremiumThree;
    }

    public void setBuyPremiumThree(Double buyPremiumThree) {
        this.buyPremiumThree = buyPremiumThree;
    }

    public Double getBuyPremiumFour() {
        return buyPremiumFour;
    }

    public void setBuyPremiumFour(Double buyPremiumFour) {
        this.buyPremiumFour = buyPremiumFour;
    }

    public Double getBuyPremiumFive() {
        return buyPremiumFive;
    }

    public void setBuyPremiumFive(Double buyPremiumFive) {
        this.buyPremiumFive = buyPremiumFive;
    }

    public Double getSellPremiumOne() {
        return sellPremiumOne;
    }

    public void setSellPremiumOne(Double sellPremiumOne) {
        this.sellPremiumOne = sellPremiumOne;
    }

    public Double getSellPremiumTwo() {
        return sellPremiumTwo;
    }

    public void setSellPremiumTwo(Double sellPremiumTwo) {
        this.sellPremiumTwo = sellPremiumTwo;
    }

    public Double getSellPremiumThree() {
        return sellPremiumThree;
    }

    public void setSellPremiumThree(Double sellPremiumThree) {
        this.sellPremiumThree = sellPremiumThree;
    }

    public Double getSellPremiumFour() {
        return sellPremiumFour;
    }

    public void setSellPremiumFour(Double sellPremiumFour) {
        this.sellPremiumFour = sellPremiumFour;
    }

    public Double getSellPremiumFive() {
        return sellPremiumFive;
    }

    public void setSellPremiumFive(Double sellPremiumFive) {
        this.sellPremiumFive = sellPremiumFive;
    }

    public String getTheoryBuyCount() {
        return theoryBuyCount;
    }

    public void setTheoryBuyCount(String theoryBuyCount) {
        this.theoryBuyCount = theoryBuyCount;
    }

    public String getTheorySellCount() {
        return theorySellCount;
    }

    public void setTheorySellCount(String theorySellCount) {
        this.theorySellCount = theorySellCount;
    }

    public String getSellStockA() {
        return StringUtil.isBlank(sellStockA) ? sellStockB:sellStockA;
    }

    public void setSellStockA(String sellStockA) {
        this.sellStockA = sellStockA;
    }

    public String getSellStockB() {
        return StringUtil.isBlank(sellStockB)?sellStockA:sellStockB;
    }

    public void setSellStockB(String sellStockB) {
        this.sellStockB = sellStockB;
    }

    public String getBuyStockA() {
        return buyStockA;
    }

    public void setBuyStockA(String buyStockA) {
        this.buyStockA = buyStockA;
    }

    public String getBuyStockB() {
        return buyStockB;
    }

    public void setBuyStockB(String buyStockB) {
        this.buyStockB = buyStockB;
    }

    public Double getSellPriceA() {
        return sellPriceA == null ?0.0:sellPriceA;
    }

    public void setSellPriceA(Double sellPriceA) {
        this.sellPriceA = sellPriceA;
    }

    public Double getSellPriceB() {
        return sellPriceB == null?0.0:sellPriceB;
    }

    public void setSellPriceB(Double sellPriceB) {
        this.sellPriceB = sellPriceB;
    }

    public Double getBuyPriceA() {
        return buyPriceA == null ? 0.0:buyPriceA;
    }

    public void setBuyPriceA(Double buyPriceA) {
        this.buyPriceA = buyPriceA;
    }

    public Double getBuyPriceB() {
        return buyPriceB==null?0.0:buyPriceB;
    }

    public void setBuyPriceB(Double buyPriceB) {
        this.buyPriceB = buyPriceB;
    }

    public Double getBuyFundNetValue() {
        return buyFundNetValue;
    }

    public void setBuyFundNetValue(Double buyFundNetValue) {
        this.buyFundNetValue = buyFundNetValue;
    }

    public Double getSellFundNetValue() {
        return sellFundNetValue;
    }

    public void setSellFundNetValue(Double sellFundNetValue) {
        this.sellFundNetValue = sellFundNetValue;
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

    public Double getSellPosition() {
        return sellPosition;
    }

    public void setSellPosition(Double sellPosition) {
        this.sellPosition = sellPosition;
    }

    public Double getBuyPosition() {
        return buyPosition;
    }

    public void setBuyPosition(Double buyPosition) {
        this.buyPosition = buyPosition;
    }

    public Double getSellPriceOneA() {
        return sellPriceOneA == null ? 0.0 :sellPriceOneA;
    }

    public void setSellPriceOneA(Double sellPriceOneA) {
        this.sellPriceOneA = sellPriceOneA;
    }

    public Double getSellPriceTwoA() {
        return sellPriceTwoA == null ? 0.0 :sellPriceTwoA;
    }

    public void setSellPriceTwoA(Double sellPriceTwoA) {
        this.sellPriceTwoA = sellPriceTwoA;
    }

    public Double getSellPriceThreeA() {
        return sellPriceThreeA;
    }

    public void setSellPriceThreeA(Double sellPriceThreeA) {
        this.sellPriceThreeA = sellPriceThreeA;
    }

    public Double getSellPriceFourA() {
        return sellPriceFourA;
    }

    public void setSellPriceFourA(Double sellPriceFourA) {
        this.sellPriceFourA = sellPriceFourA;
    }

    public Double getSellPriceFiveA() {
        return sellPriceFiveA;
    }

    public void setSellPriceFiveA(Double sellPriceFiveA) {
        this.sellPriceFiveA = sellPriceFiveA;
    }

    public Double getSellPriceOneB() {
        return sellPriceOneB == null ? 0.0 :sellPriceOneB;
    }

    public void setSellPriceOneB(Double sellPriceOneB) {
        this.sellPriceOneB = sellPriceOneB;
    }

    public Double getSellPriceTwoB() {
        return sellPriceTwoB == null ? 0.0 :sellPriceTwoB;
    }

    public void setSellPriceTwoB(Double sellPriceTwoB) {
        this.sellPriceTwoB = sellPriceTwoB;
    }

    public Double getSellPriceThreeB() {
        return sellPriceThreeB;
    }

    public void setSellPriceThreeB(Double sellPriceThreeB) {
        this.sellPriceThreeB = sellPriceThreeB;
    }

    public Double getSellPriceFourB() {
        return sellPriceFourB;
    }

    public void setSellPriceFourB(Double sellPriceFourB) {
        this.sellPriceFourB = sellPriceFourB;
    }

    public Double getSellPriceFiveB() {
        return sellPriceFiveB;
    }

    public void setSellPriceFiveB(Double sellPriceFiveB) {
        this.sellPriceFiveB = sellPriceFiveB;
    }

    public Double getBuyPriceOneA() {
        return buyPriceOneA == null ? 0.0 :buyPriceOneA;
    }

    public void setBuyPriceOneA(Double buyPriceOneA) {
        this.buyPriceOneA = buyPriceOneA;
    }

    public Double getBuyPriceTwoA() {
        return buyPriceTwoA == null ? 0.0 :buyPriceTwoA;
    }

    public void setBuyPriceTwoA(Double buyPriceTwoA) {
        this.buyPriceTwoA = buyPriceTwoA;
    }

    public Double getBuyPriceThreeA() {
        return buyPriceThreeA;
    }

    public void setBuyPriceThreeA(Double buyPriceThreeA) {
        this.buyPriceThreeA = buyPriceThreeA;
    }

    public Double getBuyPriceFourA() {
        return buyPriceFourA;
    }

    public void setBuyPriceFourA(Double buyPriceFourA) {
        this.buyPriceFourA = buyPriceFourA;
    }

    public Double getBuyPriceFiveA() {
        return buyPriceFiveA;
    }

    public void setBuyPriceFiveA(Double buyPriceFiveA) {
        this.buyPriceFiveA = buyPriceFiveA;
    }

    public Double getBuyPriceOneB() {
        return buyPriceOneB == null ? 0.0 :buyPriceOneB;
    }

    public void setBuyPriceOneB(Double buyPriceOneB) {
        this.buyPriceOneB = buyPriceOneB;
    }

    public Double getBuyPriceTwoB() {
        return buyPriceTwoB == null ? 0.0 :buyPriceTwoB;
    }

    public void setBuyPriceTwoB(Double buyPriceTwoB) {
        this.buyPriceTwoB = buyPriceTwoB;
    }

    public Double getBuyPriceThreeB() {
        return buyPriceThreeB;
    }

    public void setBuyPriceThreeB(Double buyPriceThreeB) {
        this.buyPriceThreeB = buyPriceThreeB;
    }

    public Double getBuyPriceFourB() {
        return buyPriceFourB;
    }

    public void setBuyPriceFourB(Double buyPriceFourB) {
        this.buyPriceFourB = buyPriceFourB;
    }

    public Double getBuyPriceFiveB() {
        return buyPriceFiveB;
    }

    public void setBuyPriceFiveB(Double buyPriceFiveB) {
        this.buyPriceFiveB = buyPriceFiveB;
    }

    public Double getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Double countMoney) {
        this.countMoney = countMoney;
    }

    public Double getTheoryCountMoney() {
        return theoryCountMoney;
    }

    public void setTheoryCountMoney(Double theoryCountMoney) {
        this.theoryCountMoney = theoryCountMoney;
    }

    public Double getDealBuyPreminum() {
        return dealBuyPreminum;
    }

    public void setDealBuyPreminum(Double dealBuyPreminum) {
        this.dealBuyPreminum = dealBuyPreminum;
    }

    public Double getDealSellPreminum() {
        return dealSellPreminum;
    }

    public void setDealSellPreminum(Double dealSellPreminum) {
        this.dealSellPreminum = dealSellPreminum;
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

    public Double getYj() {
        return yj;
    }

    public void setYj(Double yj) {
        this.yj = yj;
    }

    public Double getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(Double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public Double getSellOneA() {
        return sellOneA == null ? 0.0 : sellOneA;
    }

    public void setSellOneA(Double sellOneA) {
        this.sellOneA = sellOneA;
    }

    public Double getSellTwoA() {
        return sellTwoA == null ? 0.0 :sellTwoA;
    }

    public void setSellTwoA(Double sellTwoA) {
        this.sellTwoA = sellTwoA;
    }

    public Double getSellThreeA() {
        return sellThreeA;
    }

    public void setSellThreeA(Double sellThreeA) {
        this.sellThreeA = sellThreeA;
    }

    public Double getSellFourA() {
        return sellFourA;
    }

    public void setSellFourA(Double sellFourA) {
        this.sellFourA = sellFourA;
    }

    public Double getSellFiveA() {
        return sellFiveA;
    }

    public void setSellFiveA(Double sellFiveA) {
        this.sellFiveA = sellFiveA;
    }

    public Double getSellOneB() {
        return sellOneB == null ? 0.0 :sellOneB;
    }

    public void setSellOneB(Double sellOneB) {
        this.sellOneB = sellOneB;
    }

    public Double getSellTwoB() {
        return sellTwoB == null ? 0.0 :sellTwoB;
    }

    public void setSellTwoB(Double sellTwoB) {
        this.sellTwoB = sellTwoB;
    }

    public Double getSellThreeB() {
        return sellThreeB;
    }

    public void setSellThreeB(Double sellThreeB) {
        this.sellThreeB = sellThreeB;
    }

    public Double getSellFourB() {
        return sellFourB;
    }

    public void setSellFourB(Double sellFourB) {
        this.sellFourB = sellFourB;
    }

    public Double getSellFiveB() {
        return sellFiveB;
    }

    public void setSellFiveB(Double sellFiveB) {
        this.sellFiveB = sellFiveB;
    }

    public Double getBuyOneA() {
        return buyOneA == null ? 0.0 :buyOneA;
    }

    public void setBuyOneA(Double buyOneA) {
        this.buyOneA = buyOneA;
    }

    public Double getBuyTwoA() {
        return buyTwoA == null ? 0.0 :buyTwoA;
    }

    public void setBuyTwoA(Double buyTwoA) {
        this.buyTwoA = buyTwoA;
    }

    public Double getBuyThreeA() {
        return buyThreeA;
    }

    public void setBuyThreeA(Double buyThreeA) {
        this.buyThreeA = buyThreeA;
    }

    public Double getBuyFourA() {
        return buyFourA;
    }

    public void setBuyFourA(Double buyFourA) {
        this.buyFourA = buyFourA;
    }

    public Double getBuyFiveA() {
        return buyFiveA;
    }

    public void setBuyFiveA(Double buyFiveA) {
        this.buyFiveA = buyFiveA;
    }

    public Double getBuyOneB() {
        return buyOneB == null ? 0.0 :buyOneB;
    }

    public void setBuyOneB(Double buyOneB) {
        this.buyOneB = buyOneB;
    }

    public Double getBuyTwoB() {
        return buyTwoB == null ? 0.0 :buyTwoB;
    }

    public void setBuyTwoB(Double buyTwoB) {
        this.buyTwoB = buyTwoB;
    }

    public Double getBuyThreeB() {
        return buyThreeB;
    }

    public void setBuyThreeB(Double buyThreeB) {
        this.buyThreeB = buyThreeB;
    }

    public Double getBuyFourB() {
        return buyFourB;
    }

    public void setBuyFourB(Double buyFourB) {
        this.buyFourB = buyFourB;
    }

    public Double getBuyFiveB() {
        return buyFiveB;
    }

    public void setBuyFiveB(Double buyFiveB) {
        this.buyFiveB = buyFiveB;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
