package util.compator;


import com.stock.bean.BaseFund;

import java.util.Comparator;

/**
 * Created by kerno on 1/4/2016.
 */
public class BaseFundCompare implements Comparator<BaseFund> {


    public int compare(BaseFund o1, BaseFund o2) {
        o1.calculate();
        o2.calculate();
        Double yj1 = o1.getSellPremium();
        Double yj2 = o2.getSellPremium();
        if(yj1 == null || yj2 == null){
            return -1;
        }
        if( yj1 > yj2){
            return 1;
        }
        if(yj1 < yj2){
            return -1;
        }
        return 0;
    }
}
