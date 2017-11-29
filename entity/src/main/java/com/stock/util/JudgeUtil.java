package com.stock.util;

import com.stock.bean.Keys;
import com.stock.bean.StrategyType;

/**
 * Created by kerno on 2016/4/6.
 */
public class JudgeUtil {
    public static boolean isEagerRolling(){
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_AB || Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_B;
    }
    public static boolean isEagerAB(){
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_AB;
    }
    public static boolean isEagerB(){
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_EAGER_B;
    }
    public static boolean isB(){
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_B;
    }
    public static boolean isAB(){
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_AB;
    }
    public static boolean isConcurrentAB() {
        return Keys.STRATEGY_TYPE == StrategyType.STRATEGY_CONCURRENT_AB;
    }
}
