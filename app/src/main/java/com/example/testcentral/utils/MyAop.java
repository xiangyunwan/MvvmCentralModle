package com.mobile.centaur.utils;

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
 */
@Aspect
public class MyAop {

    @Before("execution(* my.test.myapplication.MainActivity.on*(android.os.Bundle))")
    public void activityBeforeTime(JoinPoint joinPoint) throws Throwable {

        String name = joinPoint.getSignature().getName();
        long time = System.currentTimeMillis();
        Log.d("TAG", "time=" + time+" name="+name);
    }

    @AfterReturning("execution(* my.test.myapplication.MainActivity.on*(android.os.Bundle))")
    public void activityAfterTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long before = System.currentTimeMillis();
        joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        long after = System.currentTimeMillis();
//        Log.d("TAG", "time=" + time+" name="+name);
    }

    @After("call(* my.test.myapplication.MainActivity.showMsg(java.lang.String,java.lang.String))")
    public void activityShowMsg(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        long time = System.currentTimeMillis();
        //提交服务器代码
        Log.d("TAG", "time=" + time+" args="+args[0]);
    }


}
