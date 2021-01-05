package com.mobile.centaur.base;


public class BaseModel {
    //TODO
    //这个地方在业务层要在 继承一次， 实例化apiservice
//    protected static ApiService apiService;

    //初始化httpService
//    static {
//        apiService = NetWorkManager.getInstance().getApiService();
//    }

    public static abstract class InfoCallBack<T> {
        public abstract void successInfo(T data);

//        void failInfo(String message, int errCord);

        public  void failThrowable(Throwable t){};


        public  void complete(String message){};
    }

}
