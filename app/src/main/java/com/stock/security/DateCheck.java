package com.stock.security;

import com.stock.job.util.DateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
public class DateCheck {
    public static final String method = "execution(* com.stock.job.*.rolling(..))";

    @Around(method)
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        Object object = null;
        if(DateUtil.isValidTime()){
            object = pjp.proceed();
        }
        return object;
    }

}  