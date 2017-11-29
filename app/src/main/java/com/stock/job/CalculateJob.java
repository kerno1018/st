package com.stock.job;

import com.stock.bean.Group;
import com.stock.bean.GroupType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import program.Consumer;
import com.stock.util.DB;

/**
 * Created by kerno on 2016/4/4.
 */
@Component
public class CalculateJob {
    Logger logger = LoggerFactory.getLogger(CalculateJob.class);

    @Scheduled(cron="0/1 * 09-15  * * ? ")
    public void rolling() throws InterruptedException {
        if(DB.getAllStrategy().size() ==0){
            // strategy
            GroupType type = new GroupType();
            type.setId(1);
            type.setName("证券ETF轮动");
            type.setDesc("达到2个点为处发条件");
            // 所有需要轮动的etf母基
            Group group = new Group();
            group.setProp1("161027,161720,163113");
            group.setType(type);
            logger.info("add rolling strategy ZQ.......");
            DB.addStrategy(group);

            GroupType jg = new GroupType();
            jg.setId(2);

            jg.setName("军工ETF轮动");
            jg.setDesc("达到2个点为处发条件");
            // 所有需要轮动的etf母基
            Group jgGroup = new Group();
            jgGroup.setProp1("161024,164402,160630");
            jgGroup.setType(jg);
            logger.info("add rolling strategy JG.......");
            DB.addStrategy(jgGroup);

            GroupType xny = new GroupType();
            xny.setId(3);
            xny.setName("军工ETF轮动");
            xny.setDesc("达到2个点为处发条件");
            // 所有需要轮动的etf母基
            Group xnyGroup = new Group();
            xnyGroup.setProp1("160640,161028");
            xnyGroup.setType(xny);
            logger.info("add rolling strategy energy.......");
            DB.addStrategy(xnyGroup);


//            160640
//            161028

//            GroupType pickUp = new GroupType();
//            pickUp.setId(30);
//            pickUp.setName("捡钱");
//            pickUp.setDesc("达到2个点为处发条件");
//            Group pickUpGroup = new Group();
//            pickUpGroup.setType(pickUp);
//            DB.addStrategy(pickUpGroup);
        }
        new Thread(new Consumer()).start();
    }
}
