package com.mobile.centaur.base;

public interface BaseConstants {
    String newsFragment = "newsFragment";

    //Header
    String API_NET_PRIVATE = "NwyzaZg4qTLx3b3mlwuy6nDp1JSzIKH2";
    String API_KEY = "i4TSNuSOuVqfnjgIPhbM44AbRL7ivofR";   //api请求公钥
    String API_DEVICE = "2";   //App设备类型(1:iOS 2:Android 3:PC 4:M站 5:微信小程序 )
    String API_APP_VERSION = "9000000";   //App版本号
    String API_APP_TYPE = "11";   //App类型(11:美好明天 5:设计学院 8:颜值会计 20:万题库 12:美好自考 )
    //-----SP_KEY-------
    String USER_ID = "user_id";//访问需登录的接口时需要
    String API_TOKEN = "_api_token";      //登录后服务器返回的信息，请求时回传到服务器，验证用户登录，访问需登录的接口时需要


    String ACTIVITY_TRANSITION = "activity_transition";
    int ACTION_FRESH_DATA = 111;
    String ISAUTO = "isauto";
    String ZUOTI_TYPE = "zuoti_type";
    int logoutCode = 15;
}
