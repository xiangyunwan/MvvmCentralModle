package com.mobile.centaur.base;


public class BaseModel {
    public static abstract class InfoCallBack<T> {
        public abstract void successInfo(T data);

//        void failInfo(String message, int errCord);

        public  void failThrowable(Throwable t){};


        public  void complete(String message){};
    }

}
