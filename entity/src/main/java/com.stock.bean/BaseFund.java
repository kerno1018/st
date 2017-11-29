package com.stock.bean;


import com.stock.util.DB;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by kerno on 1/4/2016.
 */
public class BaseFund implements Serializable,Cloneable{
    public BaseFund(){}
    private String fundA_id;
    private String fundA_nm;
    private String fundB_id;
    private String fundB_nm;
    private String base_fund_id;
    private String base_fund_nm;
    private Double base_fund_price;
    private String index_id;
    private Double position = Keys.POSITION;
    private Double currentBuyOnePremium;
    private Double currentBuyTwoPremium;
    private Double currentBuyThreePremium;
    private Double currentBuyFourPremium;
    private Double currentBuyFivePremium;
    private Double currentSellOnePremium;
    private Double currentSellTwoPremium;
    private Double currentSellThreePremium;
    private Double currentSellFourPremium;
    private Double currentSellFivePremium;
    private Double currentPremium;
    private Double fundNetValue;
    private Double securitiesIndexIncrease;
    private Stock marketA = null;
    private Stock marketB = null;

    private Double eagerSellOnePreminum;
    private Double eagerBuyOnePreminum;


    public Double getBase_fund_price() {
        return base_fund_price;
    }

    public void setBase_fund_price(Double base_fund_price) {
        this.base_fund_price = base_fund_price;
    }


    public Double getFundNetValue() {
        return fundNetValue;
    }

    public void setFundNetValue(Double fundNetValue) {
        this.fundNetValue = fundNetValue;
    }

    public void calculate(){
        Stock fund = DB.getStockInfo(index_id);
        if(fund == null || position == null || DB.getStockInfo(fundA_id) == null || DB.getStockInfo(fundB_id) == null){
            setAllPresistNull();
        }
        try{
            DecimalFormat df = new DecimalFormat("#.0000");
            securitiesIndexIncrease = (fund.getPrice()-fund.getYesterdayyPrice())/fund.getYesterdayyPrice();
            securitiesIndexIncrease = Double.valueOf(df.format(securitiesIndexIncrease));

            fundNetValue = (securitiesIndexIncrease * position + 1) * base_fund_price;
            if(DB.getStockInfo(fundA_id).getBuyTwoPrice()==0.0 || DB.getStockInfo(fundB_id).getBuyTwoPrice()==0.0){
                setAllPresistmax();
                return;
            }
            if(DB.getStockInfo(fundA_id).getSellTwoPrice()==0.0 || DB.getStockInfo(fundB_id).getSellTwoPrice()==0.0){
                setAllPresistmax();
                return;
            }
            Double mergePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getPrice() + DB.getStockInfo(fundB_id).getPrice())/2));
            Double mergeBuyOnePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyOnePrice() + DB.getStockInfo(fundB_id).getBuyOnePrice())/2));
            Double mergeBuyTwoPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyTwoPrice() + DB.getStockInfo(fundB_id).getBuyTwoPrice())/2));
            Double mergeBuyThreePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyThreePrice() + DB.getStockInfo(fundB_id).getBuyThreePrice())/2));
            Double mergeBuyFourPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyFouroPrice() + DB.getStockInfo(fundB_id).getBuyFouroPrice())/2));
            Double mergeBuyFivePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyFivePrice() + DB.getStockInfo(fundB_id).getBuyFivePrice())/2));
            Double mergeEagerBuyPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getBuyOnePrice() + DB.getStockInfo(fundB_id).getBuyOnePrice())/2));

            Double mergeSellOnePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellOnePrice() + DB.getStockInfo(fundB_id).getSellOnePrice())/2));
            Double mergeSellTwoPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellTwoPrice() + DB.getStockInfo(fundB_id).getSellTwoPrice())/2));
            Double mergeSellThreePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellThreePrice() + DB.getStockInfo(fundB_id).getSellThreePrice())/2));
            Double mergeSellFourPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellFourPrice() + DB.getStockInfo(fundB_id).getSellFourPrice())/2));
            Double mergeSellFivePrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellFivePrice() + DB.getStockInfo(fundB_id).getSellFivePrice())/2));

            Double mergeEagerSellPrice = Double.valueOf(df.format((DB.getStockInfo(fundA_id).getSellOnePrice() + DB.getStockInfo(fundB_id).getSellOnePrice())/2));

            currentPremium = Double.valueOf(df.format((mergePrice - fundNetValue) / fundNetValue))*100;
            currentBuyOnePremium = Double.valueOf(df.format((mergeBuyOnePrice - fundNetValue) / fundNetValue))*100;
            currentBuyTwoPremium = Double.valueOf(df.format((mergeBuyTwoPrice - fundNetValue) / fundNetValue))*100;
            currentBuyThreePremium = Double.valueOf(df.format((mergeBuyThreePrice - fundNetValue) / fundNetValue))*100;
            currentBuyFourPremium = Double.valueOf(df.format((mergeBuyFourPrice - fundNetValue) / fundNetValue))*100;
            currentBuyFivePremium = Double.valueOf(df.format((mergeBuyFivePrice - fundNetValue) / fundNetValue))*100;

            currentSellOnePremium = Double.valueOf(df.format((mergeSellOnePrice - fundNetValue) / fundNetValue))*100;
            currentSellTwoPremium = Double.valueOf(df.format((mergeSellTwoPrice - fundNetValue) / fundNetValue))*100;
            currentSellThreePremium = Double.valueOf(df.format((mergeSellThreePrice - fundNetValue) / fundNetValue))*100;
            currentSellFourPremium = Double.valueOf(df.format((mergeSellFourPrice - fundNetValue) / fundNetValue))*100;
            currentSellFivePremium = Double.valueOf(df.format((mergeSellFivePrice - fundNetValue) / fundNetValue))*100;

            eagerBuyOnePreminum = Double.valueOf(df.format((mergeEagerBuyPrice - fundNetValue) / fundNetValue))*100;
            eagerSellOnePreminum = Double.valueOf(df.format((mergeEagerSellPrice - fundNetValue) / fundNetValue))*100;
        }catch(Exception ex){
            System.out.println("fund_id: "+base_fund_id);
            System.out.println("index_id: "+index_id);
        }

    }

    public void setAllPresistmax() {
        currentBuyOnePremium=100.0;
        currentBuyTwoPremium=100.0;
        currentBuyThreePremium=100.0;
        currentBuyFourPremium=100.0;
        currentBuyFivePremium=100.0;
        currentSellOnePremium=100.0;
        currentSellTwoPremium=100.0;
        currentSellThreePremium=100.0;
        currentSellFourPremium=100.0;
        currentSellFivePremium=100.0;
        eagerBuyOnePreminum = 100.0;
        eagerSellOnePreminum = 100.0;

    }

    public void setAllPresistNull() {
        currentBuyOnePremium=null;
        currentBuyTwoPremium=null;
        currentBuyThreePremium=null;
        currentBuyFourPremium=null;
        currentBuyFivePremium=null;
        currentSellOnePremium=null;
        currentSellTwoPremium=null;
        currentSellThreePremium=null;
        currentSellFourPremium=null;
        currentSellFivePremium=null;
        eagerBuyOnePreminum = null;
        eagerSellOnePreminum = null;
    }

    public Double getCurrentPremium() {
        return currentPremium;
    }

    public Double getCurrentBuyOnePremium() {
        return currentBuyOnePremium;
    }

    public Double getCurrentBuyTwoPremium() {
        return currentBuyTwoPremium;
    }

    public Double getCurrentBuyThreePremium() {
        return currentBuyThreePremium;
    }

    public Double getCurrentBuyFourPremium() {
        return currentBuyFourPremium;
    }

    public Double getCurrentBuyFivePremium() {
        return currentBuyFivePremium;
    }

    public Double getCurrentSellOnePremium() {
        return currentSellOnePremium;
    }

    public Double getCurrentSellTwoPremium() {
        return currentSellTwoPremium;
    }

    public Double getCurrentSellThreePremium() {
        return currentSellThreePremium;
    }

    public Double getCurrentSellFourPremium() {
        return currentSellFourPremium;
    }

    public Double getCurrentSellFivePremium() {
        return currentSellFivePremium;
    }

    public Stock getMarketA(){
        if(marketA == null){
            try {
                marketA = DB.getStockInfo(fundA_id).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return marketA;
    }

    public Double getSecuritiesIndexIncrease() {
        return securitiesIndexIncrease;
    }

    public void setSecuritiesIndexIncrease(Double zszf) {
        this.securitiesIndexIncrease = zszf;
    }

    public void setMarketA(Stock marketA) {
        this.marketA = marketA;
    }

    public void setMarketB(Stock marketB) {
        this.marketB = marketB;
    }

    public Stock getMarketB(){
        if(marketB == null){
            try {
                marketB = DB.getStockInfo(fundB_id).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return marketB;
    }

    public Double getPosition() {
        return position == null ? Keys.POSITION : position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public String getFundA_id() {
        return fundA_id;
    }

    public String getMappedFundA(){
        return DB.getMappedId(fundA_id);
    }
    public String getMappedFundB(){
        return DB.getMappedId(fundB_id);
    }

    public void setFundA_id(String fundA_id) {
        this.fundA_id = fundA_id;
    }

    public String getFundA_nm() {
        return fundA_nm;
    }

    public void setFundA_nm(String fundA_nm) {
        this.fundA_nm = fundA_nm;
    }

    public String getFundB_id() {
        return fundB_id;
    }

    public void setFundB_id(String fundB_id) {
        this.fundB_id = fundB_id;
    }

    public String getFundB_nm() {
        return fundB_nm;
    }

    public void setFundB_nm(String fundB_nm) {
        this.fundB_nm = fundB_nm;
    }

    public String getBase_fund_id() {
        return base_fund_id;
    }

    public void setBase_fund_id(String base_fund_id) {
        this.base_fund_id = base_fund_id;
    }

    public String getBase_fund_nm() {
        return base_fund_nm;
    }

    public void setBase_fund_nm(String base_fund_nm) {
        this.base_fund_nm = base_fund_nm;
    }

    @Override
    public BaseFund clone() throws CloneNotSupportedException {
        return (BaseFund) super.clone();
    }

    public Double getBuyPremium() {
        return currentBuyOnePremium;
    }

    public Double getSellPremium(){
        return currentSellOnePremium;
    }

    public void setCurrentPremium(Double currentPremium) {
        this.currentPremium = currentPremium;
    }

    public void setCurrentSellOnePremium(Double currentSellOnePremium) {
        this.currentSellOnePremium = currentSellOnePremium;
    }

    public void setCurrentBuyOnePremium(Double currentBuyOnePremium) {
        this.currentBuyOnePremium = currentBuyOnePremium;
    }

    public Double getEagerBuyOnePreminum() {
        return eagerBuyOnePreminum;
    }

    public void setEagerBuyOnePreminum(Double eagerBuyOnePreminum) {
        this.eagerBuyOnePreminum = eagerBuyOnePreminum;
    }

    public Double getEagerSellOnePreminum() {
        return eagerSellOnePreminum;
    }

    public void setEagerSellOnePreminum(Double eagerSellOnePreminum) {
        this.eagerSellOnePreminum = eagerSellOnePreminum;
    }
}
