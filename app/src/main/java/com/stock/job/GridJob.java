//package com.stock.job;
//
//import com.stock.bean.Account;
//import com.stock.bean.OrderInfo;
//import com.stock.entity.Grid;
//import com.stock.job.util.DateUtil;
//import com.stock.service.GridService;
//import com.stock.util.DB;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import program.GridCommand;
//
//import java.util.Map;
//
///**
// * Created by kerno on 1/14/2016.
// */
//@Component
//public class GridJob {
//    private Logger logger = LoggerFactory.getLogger(GridJob.class);
//    @Autowired
//    private GridService service;
//
//    @Scheduled(cron="0/30 * 08-15 * * ? ")
//    public void prepare(){
//        service.prepareDate();
//    }
//
//    @Scheduled(cron="0/2 * 09-15 * * ? ")
//    public void rolling(){
//        if(DB.grids.size()>0 && DateUtil.isValidTime()){
//            for(Grid grid : DB.grids.values()){
////                if((grid.getBuyPrice() >= DB.getStockInfo(grid.getStock()).getSellOnePrice()-5 && DB.getStockInfo(grid.getStock()).getSellFivePrice()>0.0 && !grid.getEnable()) ||
////                        (grid.getSellPrice() <= DB.getStockInfo(grid.getStock()).getBuyOnePrice()+5 && DB.getStockInfo(grid.getStock()).getBuyFivePrice()>0.0 && grid.getEnable())){
//                if((grid.getBuyPrice() >= DB.getStockInfo(grid.getStock()).getPrice() - 1 && !grid.getEnable()) ||
//                        (grid.getSellPrice() <= DB.getStockInfo(grid.getStock()).getPrice() + 1 && grid.getEnable())){
//                    if(DB.getAccount(grid.getUserId().toString()) != null){
//                        new GridCommand(DB.getAccount(grid.getUserId().toString()),grid).start();
//                        // after execute
//                        //1.remove it from grids to avoid duplicate execute
//                        DB.grids.remove(grid.getId());
//                        //2. update it status to keep circle
//                        service.updateGridStatusById(grid.getId(),!grid.getEnable());
//                        //3. reset all data
//                        service.prepareDate();
//                    }else{
//                        logger.info(" -Auto Grid - account "+grid.getUserId()+" haven't login can not do auto buy process");
//                    }
//                }
//            }
//        }
//    }
//
//    @Scheduled(cron="0 1 15 * * ? ")
//    public void confirmDealResult(){
//        if(DB.gridOrder.size() > 0 ){
//            for(String user : DB.gridOrder.keySet()){
//                DB.getAccount(user).addVersion();
//            }
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Account account = null;
//            for(String user : DB.gridOrder.keySet()){
//                Map<Integer,String> gridOrder = DB.gridOrder.get(user);
//                account = DB.getAccount(user);
//                account.subtractVersion();
//                if(account.getDealOrderSuccess().size() > 0){
//                    for(Integer gridId : gridOrder.keySet()){
//                        boolean pass = false;
//                        for(OrderInfo info : account.getDealOrderSuccess()){
//                            if(info.getOrderNo().equals(gridOrder.get(gridId))){
//                                pass = true;
//                            }
//                        }
//                        if(!pass){
//                            service.reverseGridStatusById(gridId);
//                        }
//                    }
//                }else{
//                    for(Integer gridId : gridOrder.keySet()){
//                        service.reverseGridStatusById(gridId);
//                    }
//                }
//            }
//        }
//    }
//
//}
