package strategy.impl;

import com.stock.bean.BaseFund;
import com.stock.bean.Group;
import com.stock.util.DB;
import strategy.Context;
import strategy.State;
import strategy.StateContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kerno on 16-1-6.
 */
public class FundPickUp2PointState extends State {
    public FundPickUp2PointState(Group group) {
        super(group);
    }

    @Override
    public void handle(StateContext stateContext) {
        List<BaseFund> result = new ArrayList<>();
        for(BaseFund fund : DB.getAllMJ().values()){
            if(DB.getStockInfo(fund.getFundA_id()).getDealMoney() == null || DB.getStockInfo(fund.getFundA_id()).getDealMoney() == null){
                continue;
            }
            if(DB.getStockInfo(fund.getFundA_id()).getDealMoney()>=800 || DB.getStockInfo(fund.getFundA_id()).getDealMoney()>=800){
                result.add(fund);
            }
        }
        if(result.size() > 0){
            context = new Context(new FundPickUp2PointStrategy());
            context.advise(result,group.getType().getId());
        }
    }
}
