package strategy;

/**
 * Created by kerno on 16-1-6.
 */
public class StateContext {

    private State state;

    public StateContext(State state){
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request(){
        state.handle(this);
    }

}
