package com.example.testcentral.base;


import com.example.testcentral.bean.MyConstants;
import com.mobile.centaur.base.BaseModel;
import com.mobile.centaur.network.NetWorkManager;

/**
 * 网络交互 BaseModle
 */
public class MyBaseModle extends BaseModel {

    protected volatile static MyApiService apiService;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    static {
        if (apiService == null) {

            synchronized (MyApiService.class) {
                apiService = NetWorkManager.getInstance(MyConstants.BASE_URL).getRetrofit().create(MyApiService.class);
            }
        }
    }
}
