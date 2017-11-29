package program;

import com.stock.bean.Group;
import com.stock.util.DB;
import strategy.State;
import strategy.StateContext;
import strategy.impl.PrepareState;

import java.util.Map;

/**
 * Created by kerno on 16-1-4.
 */
public class Consumer implements Runnable{

    @Override
    public void run() {
        Map<Integer, Group> strategys = DB.getAllStrategy();
        for(Integer type : strategys.keySet()){
            State state = new PrepareState(strategys.get(type));
            StateContext stateContext = new StateContext(state);
            stateContext.request();
        }
    }
}
