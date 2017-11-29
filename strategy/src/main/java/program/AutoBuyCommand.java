package program;

import com.stock.bean.Account;
import com.stock.entity.AutoOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.stock.util.Util;

import java.io.IOException;

/**
 * Created by kerno on 2016/3/23.
 */
public class AutoBuyCommand implements Runnable {
    private Logger logger = LoggerFactory.getLogger(AutoBuyCommand.class);
    private Account account;
    private AutoOrder order;
    public AutoBuyCommand(Account account, AutoOrder order){
        this.account = account;
        this.order = order;
    }
    @Override
    public void run() {
        try {
            logger.info("auto buy process start");
            Util.autoBuy(account.getClient(),account.getId(),order.getStock(),order.getPrice(),order.getOperateNum());
            logger.info("auto buy process end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
