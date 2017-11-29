package strategy;


import com.stock.bean.BaseFund;

import java.util.List;

/**
 * Created by kerno on 1/4/2016.
 */
public class Context {

    private Strategy strategy;

    public Context ( Strategy strategy){
        this.strategy = strategy;
    }

    public void advise(List<BaseFund> funds, Integer type){
        this.strategy.advise(funds,type);
    }

}
