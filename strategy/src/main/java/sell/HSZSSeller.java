package sell;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.OwnStock;
import com.stock.bean.Stock;
import com.stock.util.DB;
import com.stock.util.Util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerno on 16-2-3.
 */
public class HSZSSeller implements Runnable {
    private Account account;
    private BaseFund advise;

    public HSZSSeller(BaseFund advise,Account account) {
        this.advise = advise;
        this.account = account;
    }
    @Override
    public void run() {

        List<String> orderNos = new ArrayList<>();
        Stock sellB = DB.getStockInfo(advise.getFundB_id());
        Stock sellA = DB.getStockInfo(advise.getFundA_id());
        OwnStock ownB = account.getOwnStock().get(advise.getFundB_id());
        OwnStock ownA = account.getOwnStock().get(advise.getFundA_id());

        orderNos.add(sell(sellB,ownB,account));
        orderNos.add(sell(sellA,ownA,account));

//        if(!SellUtil.checkSuccessOrNot(false,false,0.0,orderNos,3000,account, infoSell)){
//            return;
//        }



    }





    private String sell(Stock markeyA, OwnStock ownA, Account account){
        String orderNo = "";
        if(markeyA.getAllBuyCount() > ownA.getCanUseCount()){
            try {
                orderNo = Util.sell(account.getClient(),account.getId(),markeyA.getBuyFivePrice(),ownA.getCostPrice(),ownA.getId(),ownA.getCanUseCount().intValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return orderNo;
    }
}
