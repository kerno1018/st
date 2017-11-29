package sell;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Stock;
import com.stock.util.DB;
import com.stock.util.Util;

import java.util.Date;

/**
 * Created by kerno on 1/15/2016.
 */
public class EagerBCommand extends BCommand {

    public EagerBCommand(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        super(advise, ownFund, account, sellCount);
    }

    @Override
    protected Double getSellCount() {
        Double sellMoney = (sellB.getSellOnePrice()) * sellCount;
        Double split = sellMoney/(advise.getMarketB().getBuyOnePrice());
        return split;
    }
}
