package com.example.testcentral.base;


import com.app.mobile.centaur.BuildConfig;
import com.mobile.centaur.apis.Hosts;
import com.mobile.centaur.base.BaseModel;
import com.mobile.centaur.network.NetWorkManager;

/**
 * 网络切换
 */
public class ExternalApiModels extends BaseModel {

    protected volatile static DefaultApiService apiService;
    private static AiliyunApiService mAiliyunApiService;
    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static DefaultApiService getDefaultApiService(){
        if (apiService == null) {
            synchronized (DefaultApiService.class) {
                if (apiService==null){
                    apiService = NetWorkManager.getInstance(Hosts.get(BuildConfig.ENV).MAIN_USER).getRetrofit().create(DefaultApiService.class);
                }
            }
        }
        return apiService;
    }


    public static AiliyunApiService getBjMainUserApi() {
        if (mAiliyunApiService == null) {
            synchronized (AiliyunApiService.class) {
                if (mAiliyunApiService == null) {
                    mAiliyunApiService = NetWorkManager.getInstance(Hosts.get(BuildConfig.ENV).ALIYUN).getRetrofit().create(AiliyunApiService.class);
                }
            }
        }
        return mAiliyunApiService;
    }
}
