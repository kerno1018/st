package program;

import com.stock.bean.Account;
import com.stock.bean.OwnStock;
import com.stock.entity.Grid;
import com.stock.util.DB;
import org.apache.commons.collections.map.HashedMap;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stock.util.Util;

import java.io.IOException;
import java.util.Map;

/**
 * Created by kerno on 2016/3/23.
 */
public class GridCommand {
    private Logger logger = LoggerFactory.getLogger(GridCommand.class);
    private Account account;
    private Grid grid;
    public GridCommand(Account account, Grid grid){
        this.account = account;
        this.grid = grid;
    }

    public void start() {
        try {
            logger.info("grid process start");
            OwnStock own = account.getOwnStock().get(grid.getId());
            String orderNo ="";
            if(grid.getEnable()){
                logger.info("grid can use count :" + own.getCanUseCount());
                if(own.getCanUseCount() > 0){
                    Double num = grid.getOperateNum() > own.getCanUseCount() ? own.getCanUseCount() : grid.getOperateNum();
                    orderNo = Util.autoSell(account.getClient(),account.getId(),grid.getStock(),grid.getSellPrice(),own.getCostPrice(),num.intValue());
                }else{
                    logger.error("have not enough stock to sell");
                }
            }else{
                if(account.getCanUseMoney() >= grid.getOperateNum() * grid.getBuyPrice()){
                    orderNo = Util.autoBuy(account.getClient(),account.getId(),grid.getStock(),grid.getBuyPrice(),grid.getOperateNum());
                }else{
                    logger.error("have not enough money to buy");
                }
            }
            if(!StringUtil.isBlank(orderNo)){
                Map<Integer,String> order = new HashedMap();
                order.put(grid.getId(),orderNo);
                DB.gridOrder.put(account.getId(),order);
            }
            logger.info("grid process end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
