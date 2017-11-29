package strategy.impl;

import com.stock.bean.BaseFund;
import com.stock.util.DB;
import util.compator.BaseFundCompare;
import strategy.Strategy;

import java.util.*;

/**
 * Created by kerno on 1/4/2016.
 */
public class Fund2PointStrategy extends Strategy {
//    Logger logger = LoggerFactory.getLogger(Fund2PointStrategy.class);

    @Override
    public void advise(List<BaseFund> funds, Integer type) {
        Collections.sort(funds,new BaseFundCompare());
        DB.adviseZQFund.put(type+"",funds);
    }

}
