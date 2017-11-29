package strategy;


import com.stock.bean.BaseFund;

import java.util.List;

/**
 * Created by kerno on 1/4/2016.
 */
public abstract class Strategy {

    public abstract void advise(List<BaseFund> funds, Integer type);

}
