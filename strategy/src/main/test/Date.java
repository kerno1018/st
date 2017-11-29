import com.stock.bean.Account;
import org.junit.Test;
import com.stock.util.Util;

import java.io.IOException;
import java.util.Random;

/**
 * Created by kerno on 2016/1/24.
 */
public class Date {
//    static String cookie="__v3_c_sesslist_10891=ebbxljvrhv_cze%252Ceb99c9st70_czc%252Cczb; __v3_c_isactive_10891=1; __v3_c_uactiveat_10891=1453806751046; __v3_c_pv_10891=1; __v3_c_session_10891=1453806745146355; __v3_c_today_10891=1; __v3_c_review_10891=2; __v3_c_last_10891=1453806745146; __v3_c_visitor=1453558564978861; Hm_lvt_1c5bee4075446b613f382dd399824571=1453558567,1453597214,1453806752; Hm_lpvt_1c5bee4075446b613f382dd399824571=1453806752; __v3_c_session_at_10891=1453806763426; JSESSIONID=QQ8KWnJJL5JTv32s1kb29pdyVNh9whPpQvQf5JxCh79yR6dhdhxz!-1304249068; MyBranchCodeList=4211; countType=Z; BranchName=%u6E56%u5317%u5B9C%u660C%u56DB%u65B0%u8DEF%u8BC1%u5238%u8425%u4E1A%u90E8";
      static String cookie="__v3_c_uactiveat_10891=1453806751046; __v3_c_review_10891=5; __v3_c_last_10891=1453939053265; __v3_c_visitor=1453558564978861; Hm_lvt_1c5bee4075446b613f382dd399824571=1453597214,1453806752,1453809275,1453939034; JSESSIONID=5svhW2JJCxHJyQM3HhL27l32kydnm8k7Pyz63lGYvjQMJXs3ZcDl!-1576350502; MyBranchCodeList=4211; countType=Z; BranchName=%u6E56%u5317%u5B9C%u660C%u56DB%u65B0%u8DEF%u8BC1%u5238%u8425%u4E1A%u90E8";
    @Test
    public void main() throws IOException, InterruptedException {
//        List<OrderInfo> list = Util.getTurnOverInfo(ClientFactory.getClient(cookie),"",null);
        Account account = new Account();
        account.setId("1");
        account.setCookie(cookie);
        account.initClient();
        Util.sell(account.getClient(),account.getId(),1.2,0.1,"150236",100);
//        new Thread(new versionTest(account)).start();
//
//        while (true){
//            System.out.println(account.getDealOrderSuccess().size());
//            Thread.sleep(2000);
//        }

//        Util.getResponseByUrl(Keys.SZ_URL);
    }

    @Test
    public void tt(){
//        String response = Util.getResponseByUrl(Keys.SZ_URL);
////        response = Util.getResponseByUrl(Keys.STRATEGE_ZQ_ETF_ROLL_URL_MJ);
//        System.out.println(response);
        boolean t = true;
        while(t){

            for(int i=0;i<1000;i++){
                if(i==99){
                    System.out.println("break");
                    break;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t);
        }



    }
    private void aa(Double s){
        s +=100;
    }

    @Test
    public void checkWhile(){
        String key = "webTradeAction.do?method=searchDealDetailToday&qryflag=1&poststr=20160308|222319";
        System.out.println(key.substring(key.indexOf("&")));
    }

    class versionTest implements Runnable{
        private Account account;
        public versionTest(Account account){
            this.account = account;
        }
        @Override
        public void run() {
            while (true){
                account.addVersion();
                try {
                    Thread.sleep(new Random().nextInt(3)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.subtractVersion();
            }
        }
    }

@Test
    public void time(){
    System.out.println(new java.util.Date(1458742498000l));
}

}
