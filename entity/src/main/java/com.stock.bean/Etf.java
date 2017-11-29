package com.stock.bean;


import com.stock.util.DB;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by kerno on 1/4/2016.
 */
public class Etf implements Serializable{
    public Etf(){}
    private String id;
    private String name;
    private Double price;
    private String index_id;
    private Double currentPremium;


    public Double getPremium(){
        Stock fund = DB.getStockInfo(index_id);
        if(fund == null  || DB.getStockInfo(id) == null){
            return null;
        }

        DecimalFormat df = new DecimalFormat("#.0000");
        Double zszf = (fund.getPrice()-fund.getYesterdayyPrice())/fund.getYesterdayyPrice();
        zszf = Double.valueOf(df.format(zszf));

        Double mjjz = (zszf + 1) * price;
        if(DB.getStockInfo(id).getBuyFivePrice()==0.0){
            return 100.0;
        }
        if(DB.getStockInfo(id).getSellFivePrice()==0.0){
            return 100.0;
        }
        Double mergePrice = Double.valueOf(df.format((DB.getStockInfo(id).getBuyFivePrice())));
        Double currentMergePrice = Double.valueOf(df.format((DB.getStockInfo(id).getBuyOnePrice())));
        currentPremium = Double.valueOf(df.format((currentMergePrice - mjjz) / mjjz))*100;
        return Double.valueOf(df.format((mergePrice - mjjz) / mjjz))*100;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public Double getCurrentPremium() {
        return currentPremium;
    }

    public void setCurrentPremium(Double currentPremium) {
        this.currentPremium = currentPremium;
    }
}
