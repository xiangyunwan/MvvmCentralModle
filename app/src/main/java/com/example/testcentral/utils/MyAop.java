package com.example.testcentral.utils;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by zzz
 * on 2021/12/25
 * AOP 打点
 */
@Aspect
public class MyAop {

    @Before("execution(* com.example.testcentral.ui.MainActivity.on*(android.os.Bundle))")
    public void activityBeforeTime(JoinPoint joinPoint) throws Throwable {

        String name = joinPoint.getSignature().getName();
        long time = System.currentTimeMillis();
        Log.d("TAG", "time=" + time+" name="+name);
    }

    @AfterReturning("execution(* com.example.testcentral.ui.MainActivity.on*(android.os.Bundle))")
    public void activityAfterTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long before = System.currentTimeMillis();
        joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        long after = System.currentTimeMillis();
//        Log.d("TAG", "time=" + time+" name="+name);
    }

    @After("call(* com.example.testcentral.ui.MainActivity.showMsg(java.lang.String,java.lang.String))")
    public void activityShowMsg(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        long time = System.currentTimeMillis();
        //提交服务器代码
        Log.d("TAG", "time=" + time+" args="+args[0]);
    }


}
