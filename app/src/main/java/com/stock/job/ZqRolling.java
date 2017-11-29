package com.stock.job;

import com.stock.bean.Account;
import com.stock.bean.BaseFund;
import com.stock.bean.Group;
import com.stock.context.SpringContext;
import com.stock.entity.User;
import com.stock.job.util.ConditionUtil;
import com.stock.login.AutoLogin;
import com.stock.service.UserService;
import com.stock.util.JudgeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.stock.util.DB;
import org.springframework.util.StringUtils;
import sell.*;
import com.stock.bean.Keys;

import java.util.*;

/**
 * Created by kerno on 1/14/2016.
 */
@Component
public class ZqRolling {
    Logger logger = LoggerFactory.getLogger(ZqRolling.class);
    @Autowired
    private SpringContext context;

    @Scheduled(cron="0/2 * 09-15 * * ? ")
    public void rolling(){
        if(DB.adviseZQFund.size() ==0 || DB.adviseZQFund.get("1") == null){
            return;
        }
        Map<String, Account> allAccount = DB.getAllAccount();
        try {
            for (Account account : allAccount.values()) {
                if (!account.getStatus()) {
                    logger.warn(account.getId() + "account timeout pl z check");
                    if(account.getLockAccountVersion() == 0){
                        account.setLockAccountVersion(1);
                        UserService service = SpringContext.getBean("service.UserService");
                        User user = service.getUserInfo(Integer.valueOf(account.getId()));
                        service.lockAccount(user);
                        if(user.getAuto() != null && user.getAuto() && !StringUtils.isEmpty(user.getLoginAccount()) && !StringUtils.isEmpty(user.getLoginPassword())){
                            new Thread(new AutoLogin(service,user)).start();
                        }
                    }
                    continue;
                }
                if (account.getOwnStock() == null) {
                    continue;
                }

                for(Group group : DB.getAllStrategy().values()){
                    String groups = DB.getStrategy(group.getType().getId()).getProp1();
                    if(StringUtils.isEmpty(groups)){
                        continue;
                    }
                    for (String groupId : groups.split(",")) {
                        if(DB.getMj(groupId) == null){
                            continue;
                        }
                        BaseFund ownFund = DB.getMj(groupId).clone();
                        if (account.getOwnStock().keySet().contains(ownFund.getFundA_id()) || account.getOwnStock().keySet().contains(ownFund.getFundB_id())) {
                            BaseFund adviseFund = DB.adviseZQFund.get(group.getType().getId().toString()).get(0).clone();
                            adviseFund.calculate();
                            ownFund.calculate();
                            Double adviseYJ = adviseFund.getSellPremium();
                            Double adviseYJ2 = adviseFund.getCurrentSellTwoPremium();
                            Double ownYJ = ownFund.getBuyPremium();
                            Double ownYJ2 = ownFund.getCurrentBuyTwoPremium();

                            if(JudgeUtil.isEagerRolling()){
                                 adviseYJ = adviseFund.getEagerBuyOnePreminum();
                                 ownYJ = ownFund.getEagerSellOnePreminum();
                            }

                            if(JudgeUtil.isB()){
                                if(!ConditionUtil.checkB(account,ownFund,adviseFund,ownYJ,adviseYJ)){
                                    continue;
                                }
                            }else if(JudgeUtil.isEagerB()){
                                if(!ConditionUtil.checkEagerB(account,ownFund,adviseFund,ownYJ,adviseYJ)){
                                    continue;
                                }
                            }else if(JudgeUtil.isAB() || JudgeUtil.isConcurrentAB()){
                                if(!ConditionUtil.checkAB(account,ownFund,adviseFund,ownYJ,adviseYJ)){
                                    continue;
                                }
                            }else if(JudgeUtil.isEagerAB()){
                                if(!ConditionUtil.checkEagerAB(account,ownFund,adviseFund,ownYJ,adviseYJ)){
                                    continue;
                                }
                            }else{
                                logger.error(" strategy error ");
                                continue;
                            }
                            if(group.getType().getId() == 3 && ownYJ-adviseYJ<Keys.CONDITION_PREMINUM_ENERGY){
                                continue;
                            }
                            // five count
                            if(JudgeUtil.isEagerRolling() && (ownYJ - adviseYJ < Keys.CONDITION_PREMINUM)){
                                continue;
                            }
                            if(!JudgeUtil.isEagerRolling() && ((ownYJ - adviseYJ < Keys.CONDITION_PREMINUM) || (ownYJ2 - adviseYJ2)<=0)){
                                continue;
                            }
                            if (Keys.DEBUG) {
                                return;
                            }
                            if(checkVolume(ownFund,adviseFund,account)){
                                int sellCount = DealLogBuilder.getMinSellVolume(ownFund,account);
                                if(JudgeUtil.isB()){
                                    account.substractCanUseMoney(ownFund.getMarketB().getBuyTwoPrice()*sellCount);
                                    account.getOwnStock().get(ownFund.getFundB_id()).subUseCount(sellCount);
                                    new Thread(new BCommand(adviseFund, ownFund, account, sellCount*1.0)).start();
                                }else if(JudgeUtil.isEagerB()){
                                    account.substractCanUseMoney(ownFund.getMarketB().getSellOnePrice()*sellCount);
                                    account.getOwnStock().get(ownFund.getFundB_id()).subUseCount(sellCount);
                                    new Thread(new EagerBCommand(adviseFund, ownFund, account, sellCount*1.0)).start();
                                }else if(JudgeUtil.isConcurrentAB()){
                                    account.substractCanUseMoney((ownFund.getMarketA().getBuyTwoPrice()+ownFund.getMarketB().getBuyTwoPrice())*sellCount);
                                    account.getOwnStock().get(ownFund.getFundA_id()).subUseCount(sellCount);
                                    account.getOwnStock().get(ownFund.getFundB_id()).subUseCount(sellCount);
                                    new Thread(new ConcurrentCommonCommand(adviseFund, ownFund, account, sellCount*1.0)).start();
                                }else if(JudgeUtil.isAB()){
                                    account.getOwnStock().get(ownFund.getFundA_id()).subUseCount(sellCount);
                                    account.getOwnStock().get(ownFund.getFundB_id()).subUseCount(sellCount);
                                    new Thread(new ABCommand(adviseFund, ownFund, account, sellCount*1.0)).start();
                                }else if(JudgeUtil.isEagerAB()){
                                    account.substractCanUseMoney((ownFund.getMarketA().getSellOnePrice()+ownFund.getMarketB().getSellOnePrice())*sellCount);
                                    account.getOwnStock().get(ownFund.getFundA_id()).subUseCount(sellCount);
                                    account.getOwnStock().get(ownFund.getFundB_id()).subUseCount(sellCount);
                                    new Thread(new EagerABCommand(adviseFund, ownFund, account, sellCount*1.0)).start();
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private Boolean checkVolume(BaseFund ownFund,BaseFund adviseFund,Account account){

        int willSellVolume = DealLogBuilder.getMinSellVolume(ownFund,account);
        boolean pass = true;
        // double side of sellTwo and buyTwo gt than owned
        int willBuyVolume = DealLogBuilder.getMinBuyVolume(adviseFund,ownFund,account);
        // will skip check A if is Strategy B
        if(JudgeUtil.isB()){
            pass = pass & ownFund.getMarketB().getAllBuyCount() > willSellVolume;
            pass = pass & adviseFund.getMarketB().getAllSellCount() > willBuyVolume;
        }else if(JudgeUtil.isAB()){
            pass = pass & ownFund.getMarketA().getAllBuyCount() > willSellVolume;
            pass = pass & adviseFund.getMarketA().getAllSellCount() > willBuyVolume;
            pass = pass & ownFund.getMarketB().getAllBuyCount() > willSellVolume;
            pass = pass & adviseFund.getMarketB().getAllSellCount() > willBuyVolume;
        }else if (JudgeUtil.isConcurrentAB()){
            pass = pass & ownFund.getMarketA().getAllBuyCount() > willSellVolume;
            pass = pass & adviseFund.getMarketA().getAllSellCount() > willBuyVolume;
            pass = pass & ownFund.getMarketB().getAllBuyCount() > willSellVolume;
            pass = pass & adviseFund.getMarketB().getAllSellCount() > willBuyVolume;
            pass = pass & account.getCanUseMoney() >= (adviseFund.getMarketA().getSellTwoPrice()+adviseFund.getMarketB().getSellTwoPrice()) * willBuyVolume;
        }else if(JudgeUtil.isEagerB()){
            pass = pass & account.getCanUseMoney() >= adviseFund.getMarketB().getBuyOnePrice() * willBuyVolume;
        }else if(JudgeUtil.isEagerAB()){
            pass = pass & account.getCanUseMoney() >= (adviseFund.getMarketA().getBuyOnePrice()+adviseFund.getMarketB().getBuyOnePrice()) * willBuyVolume;
        }
        return pass;
    }
}
