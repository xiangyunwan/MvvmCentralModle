package com.mobile.centaur.apis;

import com.mobile.centaur.base.BaseApplication;
import com.mobile.centaur.utils.SharedPreferencesUtils;
import com.mobile.centaur.utils.SystemUtil;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/6
 **/
public class KernelIpWeightSp {
    private static final String IPWEIGHT_NEXT = "xiangyun.next";
    private SharedPreferencesUtils sharedPreferences;
    private volatile static KernelIpWeightSp instance;
    private KernelIpWeightSp() {
        sharedPreferences = new SharedPreferencesUtils(BaseApplication.getApplicatonContext(), SystemUtil.getAppName(BaseApplication.getApplicatonContext()));
    }
    public static KernelIpWeightSp getInstance() {
        if (instance == null) {
            synchronized (KernelIpWeightSp.class){
                if (instance == null) {
                    instance = new KernelIpWeightSp();
                }
            }
        }
        return instance;
    }

    /**
     * 如果A域名网络请求报错了，下次打开app请求的时候直接切换为B域名
     * @return
     */
    public String getNextDomain() {
        return sharedPreferences.loadStringKey(IPWEIGHT_NEXT, "");
    }

    public void setNextDomain(String nextDomain) {
        sharedPreferences.saveStringKey(IPWEIGHT_NEXT, nextDomain);
    }

}
