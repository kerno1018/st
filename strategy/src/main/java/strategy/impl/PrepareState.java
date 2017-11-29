package strategy.impl;

import com.stock.bean.Group;
import strategy.State;
import strategy.StateContext;

/**
 * Created by kerno on 16-1-6.
 */
public class PrepareState extends State {
    public PrepareState(Group group) {
        super(group);
    }

    @Override
    public void handle(StateContext context) {
        if(group.getType().getId()==3){
            context.setState(new FundPickUp2PointState(group));
        }else{
            context.setState(new Fund2PointState(group));
        }
        context.request();
    }
}
