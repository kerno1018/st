package program;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kerno on 2016/2/5.
 */
public class AccountPremiumRecord {

    public static volatile Map<String,Map<String,Integer>> record = new ConcurrentHashMap<>();



}
