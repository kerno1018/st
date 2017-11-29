package strategy;


import com.stock.bean.Group;

/**
 * Created by kerno on 16-1-6.
 */
public abstract class State {
    protected Context context;
    protected Group group;

    public State(Group group){
        this.group = group;
    }

    public abstract void handle(StateContext context);

}
