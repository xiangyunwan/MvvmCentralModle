package com.mobile.centaur.apis;

import android.text.TextUtils;

import com.mobile.centaur.base.BaseApplication;
import com.mobile.centaur.utils.SharedPreferencesUtils;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/6
 **/
public class Domain {
    private static String domain;
    private static String weightDomain;

    public static String getDomain() {
        if (TextUtils.isEmpty(domain)) {
            domain = new SharedPreferencesUtils(BaseApplication.getApplicatonContext(),"domain_service").loadStringKey("domain", "xiangyun.com");
        }
        return domain;
    }


    /**
     * 取有权重的域名
     * @return
     */
//    public static String getDomainWithWeight(){
//        if (TextUtils.isEmpty(weightDomain)){
//            String nextDomain = KernelWeightUtils.getNextDomain();
//            if (!TextUtils.isEmpty(nextDomain) ){
//                KernelWeightUtils.clearNextDomain();
//                if (KernelWeightUtils.contains(nextDomain)){
//                    weightDomain = nextDomain;
//                    LogUtils.i(LogUtils.NETWORK_LOG,"Domain:getDomainWithWeight:启动错误上次报错应该切换的域名："+weightDomain);
//                }
//            }
//            if (TextUtils.isEmpty(weightDomain)){
//                weightDomain = KernelWeightUtils.getWeightDomain();
//            }
//            LogUtils.i(LogUtils.NETWORK_LOG,"Domain:getDomainWithWeight:weightDomain："+weightDomain);
//        }
//        //做非空判断
//        if (TextUtils.isEmpty(weightDomain) || weightDomain.trim().length() ==0){
//            domain = KernelWeightUtils.DOMAIN_JK;
//        }
//        return weightDomain;
//    }
    /*
    public static void fetchDomain(boolean isDebug) {
        if (!Utils.isMainProcess(ContextManager.getContext())) {
            return;
        }
        Observable.just(DomainApiClient.getApiList(isDebug))
                .delay(10, TimeUnit.SECONDS, Schedulers.computation())
                .flatMap(Observable::from)
                .flatMap(domainInfoObservable -> domainInfoObservable)
                .subscribeOn(Schedulers.computation())
                .takeFirst(domainInfo -> domainInfo != null)
                .subscribe(domainInfo -> {
                    if (domainInfo == null) {
                        return;
                    }
                    String domainName = domainInfo.getDomainName();
                    if (!TextUtils.equals(getDomain(), domainName) && !TextUtils.isEmpty(domainName)) {
                        new SharedPreferencesUtils(ContextManager.getContext(),"domain_service").saveStringKey("domain", domainName);
                        LogUtils.i("save new domain \"" + domainName + "\" from \"" + domainInfo.getHost() + "\"");
                    }
                }, LogUtils::e);
    }

     */
}
