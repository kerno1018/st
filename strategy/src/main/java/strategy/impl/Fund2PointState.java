package strategy.impl;

import antlr.StringUtils;
import com.stock.bean.BaseFund;
import com.stock.bean.Group;
import com.stock.util.DB;
import org.jsoup.helper.StringUtil;
import strategy.Context;
import strategy.State;
import strategy.StateContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kerno on 16-1-6.
 */
public class Fund2PointState extends State {
    public Fund2PointState(Group group) {
        super(group);
    }

    @Override
    public void handle(StateContext stateContext) {
        if(!StringUtil.isBlank(group.getProp1())){
            String[] allCodes = group.getProp1().split(",");
            List<BaseFund> result = new LinkedList<>();
            List<BaseFund> preminumList = new LinkedList<>();
            for(String code : allCodes){
                if(DB.getMj(code) != null){
                    result.add(DB.getMj(code));
                    preminumList.add(DB.getMj(code));
                }
            }
            if(result.size() > 0){
                context = new Context(new Fund2PointStrategy());
                context.advise(result,group.getType().getId());
            }
        }
    }
}
