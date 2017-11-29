package sell;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Stock;

/**
 * Created by kerno on 1/15/2016.
 */
public class EagerABCommand extends ConcurrentCommonCommand {


    public EagerABCommand(BaseFund advise, BaseFund ownFund, Account account, Double sellCount) {
        super(advise, ownFund, account, sellCount);
    }

    @Override
    protected Double getSellCount() {
        Stock adviseA = advise.getMarketA();
        Stock adviseB = advise.getMarketB();
        Double sellMoney = (sellA.getSellOnePrice()+sellB.getSellOnePrice()) * sellCount;
        Double split = sellMoney/(adviseA.getBuyOnePrice() + adviseB.getBuyOnePrice() );
        return split;
    }
}


