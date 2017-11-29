package com.stock.log;

import com.stock.bean.BaseFund;
import com.stock.context.SpringContext;
import com.stock.entity.EagerLogRoll;
import com.stock.service.LogService;

import java.util.Date;

/**
 * Created by kerno on 2016/2/5.
 */
public class EagerLogRunner implements Runnable {
    private BaseFund advise;
    private BaseFund own;
    public EagerLogRunner(BaseFund advise, BaseFund own){
        this.advise = advise;
        this.own = own;
    }
    @Override
    public void run() {
        Double one = own.getBuyPremium() - advise.getSellPremium();
        savePriemum(one,"1");
    }

    private  void savePriemum(Double priemum,String type){
        if(priemum >=1.0){
            saveLog(type,priemum,"10");
        } else if(priemum >= 0.9){
                saveLog(type,priemum,"9");
        } else if(priemum >= 0.8){
                saveLog(type,priemum,"8");
        } else if(priemum >= 0.7){
                saveLog(type,priemum,"7");
        } else if(priemum >= 0.6){
                saveLog(type,priemum,"6");
        } else if(priemum >= 0.5){
                saveLog(type,priemum,"5");
        } else if(priemum >= 0.4){
                saveLog(type,priemum,"4");
        } else if(priemum >= 0.3){
                saveLog(type,priemum,"3");
        } else if(priemum >= 0.2){
                saveLog(type,priemum,"2");
        }
    }

    public void saveLog(String type,Double value,String premium){
        EagerLogRoll log = new EagerLogRoll();
        log.setCreateTime(new Date());
        log.setType(value+"");
        log.setFourB(own.getMarketB().getBuyFouro());
        log.setFourA(own.getMarketA().getBuyFouro());
        log.setFiveB(own.getMarketB().getBuyFive());
        log.setFiveA(own.getMarketA().getBuyFive());
        log.setOneA(own.getMarketA().getBuyOne());
        log.setOneB(own.getMarketB().getBuyOne());
        log.setTwoB(own.getMarketB().getBuyTwo());
        log.setTwoA(own.getMarketA().getBuyTwo());
        log.setThreeB(own.getMarketB().getBuyThree());
        log.setThreeA(own.getMarketA().getBuyThree());

        log.setStockB(own.getFundB_id());
        log.setStockA(own.getFundA_id());
        log.setOwnPriceOneA(  own.getMarketA().getBuyOnePrice());
        log.setOwnPriceTwoA(  own.getMarketA().getBuyTwoPrice());
        log.setOwnPriceThreeA(own.getMarketA().getBuyThreePrice());
        log.setOwnPriceFourA( own.getMarketA().getBuyFouroPrice());
        log.setOwnPriceFiveA( own.getMarketA().getBuyFivePrice());
        log.setOwnPriceOneB(  own.getMarketB().getBuyOnePrice());
        log.setOwnPriceTwoB(  own.getMarketB().getBuyTwoPrice());
        log.setOwnPriceThreeB(own.getMarketB().getBuyThreePrice());
        log.setOwnPriceFourB( own.getMarketB().getBuyFouroPrice());
        log.setOwnPriceFiveB( own.getMarketB().getBuyFivePrice());
        log.setOwnEagerPriceA(own.getMarketA().getSellOnePrice());
        log.setOwnEagerPriceB(own.getMarketB().getSellOnePrice());

        log.setOwnOnePremium(own.getEagerBuyOnePreminum());

        log.setChangeToA(advise.getFundA_id());
        log.setChangeToB(advise.getFundB_id());

        log.setAdviseFourB (advise.getMarketB().getBuyFouro());
        log.setAdviseFourA (advise.getMarketA().getBuyFouro());
        log.setAdviseFiveB (advise.getMarketB().getBuyFive());
        log.setAdviseFiveA (advise.getMarketA().getBuyFive());
        log.setAdviseOneA  (advise.getMarketA().getBuyOne());
        log.setAdviseOneB  (advise.getMarketB().getBuyOne());
        log.setAdviseTwoB  (advise.getMarketB().getBuyTwo());
        log.setAdviseTwoA  (advise.getMarketA().getBuyTwo());
        log.setAdviseThreeB(advise.getMarketB().getBuyThree());
        log.setAdviseThreeA(advise.getMarketA().getBuyThree());

        log.setAdvisePriceOneA(  advise.getMarketA().getSellOnePrice());
        log.setAdvisePriceTwoA(  advise.getMarketA().getSellTwoPrice());
        log.setAdvisePriceThreeA(advise.getMarketA().getSellThreePrice());
        log.setAdvisePriceFourA( advise.getMarketA().getSellFourPrice());
        log.setAdvisePriceFiveA( advise.getMarketA().getSellFivePrice());
        log.setAdvisePriceOneB(  advise.getMarketB().getSellOnePrice());
        log.setAdvisePriceTwoB(  advise.getMarketB().getSellTwoPrice());
        log.setAdvisePriceThreeB(advise.getMarketB().getSellThreePrice());
        log.setAdvisePriceFourB( advise.getMarketB().getSellFourPrice());
        log.setAdvisePriceFiveB( advise.getMarketB().getSellFivePrice());
        log.setAdviseEagerPriceA(advise.getMarketA().getBuyOnePrice());
        log.setAdviseEagerPriceB(advise.getMarketB().getBuyOnePrice());

        log.setAdviseSellOnePremium(advise.getEagerSellOnePreminum());

        LogService service = SpringContext.getBean("service.LogService");
        service.saveEagerLog(log);
    }

}
