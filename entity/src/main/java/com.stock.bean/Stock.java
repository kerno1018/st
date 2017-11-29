package com.stock.bean;


import com.stock.util.JudgeUtil;

/**
 * Created by kerno on 1/8/2016.
 */
public class Stock implements Cloneable{
    public Stock(){}
    public Stock(String info){
        id = info.substring(13,info.indexOf("="));
        int prex = 21;
        if("HSI".equals(id)){
            prex=18;
        }
        info = info.substring(prex,info.lastIndexOf("\""));
        String[] fields = info.split(",");
        name = fields[0];
        openPrice =         Double.valueOf(fields[1]);
        yesterdayyPrice =   Double.valueOf(fields[2]);
        price =             Double.valueOf(fields[3]);
        highPrice =         Double.valueOf(fields[4]);
        lowPrice =          Double.valueOf(fields[5]);
        if("HSI".equals(id)){
            return;
        }
        if(fields.length<20){
            return;
        }
        buyOnePrice =       Double.valueOf(fields[6]);
        sellOnePrice =      Double.valueOf(fields[7]);
        dealCount =         Double.valueOf(fields[8])/100;
        dealMoney =         Double.valueOf(fields[9])/10000;
        buyOne =            Double.valueOf(fields[10]);
        buyOnePrice =       Double.valueOf(fields[11]);
        buyTwo =            Double.valueOf(fields[12]);
        buyTwoPrice =       Double.valueOf(fields[13]);
        buyThree =          Double.valueOf(fields[14]);
        buyThreePrice =     Double.valueOf(fields[15]);
        buyFouro =          Double.valueOf(fields[16]);
        buyFouroPrice =     Double.valueOf(fields[17]);
        buyFive =           Double.valueOf(fields[18]);
        buyFivePrice=       Double.valueOf(fields[19]);
        sellOne =           Double.valueOf(fields[20]);
        sellOnePrice =      Double.valueOf(fields[21]);
        sellTwo =           Double.valueOf(fields[22]);
        sellTwoPrice =      Double.valueOf(fields[23]);
        sellThree =         Double.valueOf(fields[24]);
        sellThreePrice =    Double.valueOf(fields[25]);
        sellFour =          Double.valueOf(fields[26]);
        sellFourPrice =     Double.valueOf(fields[27]);
        sellFive =          Double.valueOf(fields[28]);
        sellFivePrice =     Double.valueOf(fields[29]);
        date = fields[30];
        time = fields[31];
    }

    private String name;
    private String id;
    private Double openPrice;
    private Double yesterdayyPrice;
    private Double price;
    private Double highPrice;
    private Double lowPrice;
    private Double buyOnePrice;
    private Double sellOnePrice;
    private Double dealCount;//stock
    private Double dealMoney;//yuan;
    private Double buyOne;
    private Double sellOne;
    private Double buyTwo;
    private Double buyTwoPrice;
    private Double buyThree;
    private Double buyThreePrice;
    private Double buyFouro;
    private Double buyFouroPrice;
    private Double buyFive;
    private Double buyFivePrice;
    private Double sellTwo;
    private Double sellTwoPrice;
    private Double sellThree;
    private Double sellThreePrice;
    private Double sellFour;
    private Double sellFourPrice;
    private Double sellFive;
    private Double sellFivePrice;
    private  String date;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getYesterdayyPrice() {
        return yesterdayyPrice;
    }

    public void setYesterdayyPrice(Double yesterdayyPrice) {
        this.yesterdayyPrice = yesterdayyPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getBuyOnePrice() {
        if(Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_AB || Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_B ){
            return buyOnePrice + Keys.EAGER_PREX;
        }else{
            return buyOnePrice;
        }
    }

    public void setBuyOnePrice(Double buyOnePrice) {
        this.buyOnePrice = buyOnePrice;
    }

    public Double getSellOnePrice() {
        if(JudgeUtil.isEagerRolling()){
            return sellOnePrice - Keys.EAGER_PREX;
        }else{
            return sellOnePrice;
        }
    }

    public void setSellOnePrice(Double sellOnePrice) {
        this.sellOnePrice = sellOnePrice;
    }

    public Double getDealCount() {
        return dealCount;
    }

    public void setDealCount(Double dealCount) {
        this.dealCount = dealCount;
    }

    public Double getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(Double dealMoney) {
        this.dealMoney = dealMoney;
    }

    public Double getBuyOne() {
        return buyOne;
    }

    public void setBuyOne(Double buyOne) {
        this.buyOne = buyOne;
    }

    public Double getSellOne() {
        return sellOne;
    }

    public void setSellOne(Double sellOne) {
        this.sellOne = sellOne;
    }

    public Double getBuyTwo() {
        return buyTwo;
    }

    public void setBuyTwo(Double buyTwo) {
        this.buyTwo = buyTwo;
    }

    public Double getBuyTwoPrice() {
        return buyTwoPrice;
    }

    public void setBuyTwoPrice(Double buyTwoPrice) {
        this.buyTwoPrice = buyTwoPrice;
    }

    public Double getBuyThree() {
        return buyThree;
    }

    public void setBuyThree(Double buyThree) {
        this.buyThree = buyThree;
    }

    public Double getBuyThreePrice() {
        return buyThreePrice;
    }

    public void setBuyThreePrice(Double buyThreePrice) {
        this.buyThreePrice = buyThreePrice;
    }

    public Double getBuyFouro() {
        return buyFouro;
    }

    public void setBuyFouro(Double buyFouro) {
        this.buyFouro = buyFouro;
    }

    public Double getBuyFouroPrice() {
        return buyFouroPrice;
    }

    public void setBuyFouroPrice(Double buyFouroPrice) {
        this.buyFouroPrice = buyFouroPrice;
    }

    public Double getBuyFive() {
        return buyFive;
    }

    public void setBuyFive(Double buyFive) {
        this.buyFive = buyFive;
    }

    public Double getBuyFivePrice() {
        return buyFivePrice;
    }

    public void setBuyFivePrice(Double buyFivePrice) {
        this.buyFivePrice = buyFivePrice;
    }

    public Double getSellTwo() {
        return sellTwo;
    }

    public void setSellTwo(Double sellTwo) {
        this.sellTwo = sellTwo;
    }

    public Double getSellTwoPrice() {
        return sellTwoPrice;
    }

    public void setSellTwoPrice(Double sellTwoPrice) {
        this.sellTwoPrice = sellTwoPrice;
    }

    public Double getSellThree() {
        return sellThree;
    }

    public void setSellThree(Double sellThree) {
        this.sellThree = sellThree;
    }

    public Double getSellThreePrice() {
        return sellThreePrice;
    }

    public void setSellThreePrice(Double sellThreePrice) {
        this.sellThreePrice = sellThreePrice;
    }

    public Double getSellFour() {
        return sellFour;
    }

    public void setSellFour(Double sellFour) {
        this.sellFour = sellFour;
    }

    public Double getSellFourPrice() {
        return sellFourPrice;
    }

    public void setSellFourPrice(Double sellFourPrice) {
        this.sellFourPrice = sellFourPrice;
    }

    public Double getSellFive() {
        return sellFive;
    }

    public void setSellFive(Double sellFive) {
        this.sellFive = sellFive;
    }

    public Double getSellFivePrice() {
        return sellFivePrice;
    }

    public void setSellFivePrice(Double sellFivePrice) {
        this.sellFivePrice = sellFivePrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getAllBuyCount(){
        return (buyOne + buyTwo)/2;
    }

    public Double getAllSellCount(){
        return (sellOne + sellTwo)/2;
    }

    @Override
    protected Stock clone() throws CloneNotSupportedException {
        return (Stock) super.clone();
    }
}
