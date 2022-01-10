package com.mobile.centaur.apis;

import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.mobile.centaur.utils.LogUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/6
 **/
public class KernelWeightUtils {
    static Set<String> allDomainSet = new HashSet<>();
    static Set<String> aliyunSet = new HashSet<>();
    static Set<String> ucloudSet = new HashSet<>();
    public static final String DOMAIN_JK = "jianke.com";
    private static final String DOMAIN_FZ = "jianke-fangzhou.com";
    static {
        allDomainSet.add(DOMAIN_JK);
        allDomainSet.add(DOMAIN_FZ);
        //aliyun
        aliyunSet.add("xiangyun.aliyun.com");
        aliyunSet.add("user.aliyun.com");
        aliyunSet.add("h5.aliyun.com");
        //uclound
        ucloudSet.add("xiangyun.com");
        ucloudSet.add("user.com");
        ucloudSet.add("h5.com");
    }

    public static void clearNextDomain(){
        KernelIpWeightSp.getInstance().setNextDomain("");
    }
    public static boolean contains(String domain){
        return allDomainSet.contains(domain);
    }
    public static String getNextDomain(){
        return KernelIpWeightSp.getInstance().getNextDomain();
    }
    /**
     * 将网络报错的，小于200，大于500的 保留一份下次启动使用
     * @param host
     */
    public static void saveNetworkErrorHost(String host){
        if (aliyunSet.contains(host)){
            KernelIpWeightSp.getInstance().setNextDomain(DOMAIN_FZ);
        }else if(ucloudSet.contains(host)){
            KernelIpWeightSp.getInstance().setNextDomain(DOMAIN_JK);
        }
    }
    private static Integer getWeightIndex(List<KernelIpWeight> ipWeights){
        Integer weight = 0;
        //获取list的权重总数
        for (int index=0;index<ipWeights.size();index++){
            weight+=ipWeights.get(index).getWeight();
        }
        //在总权重中抽取随机样本
        int random=(int)Math.floor(Math.random() * weight);
        for (Integer j = 0 ; j < ipWeights.size() ; j ++){
            //如果随机样本 < 对象[j] 中指定的概率数值，则认为此次随机 命中在对象[j]上
            if (random - ipWeights.get(j).getWeight()<0){
                return j;
                //如果随机样本 > 对象[j] 中指定的概率数值 ，则认为此次随机 未命中在对象[j]上 ，刷新随机样本，继续下次循环
            }else{
                random -= ipWeights.get(j).getWeight();
            }
        }
        return 0;//取不到就扔0
    }
//    public static String getWeightDomain(){
//        String domain = DOMAIN_JK;
//        KernelIpWeightApi ipWeightApi = RemoteConstantFactory.getInstance().obtain(KernelIpWeightApi.class);
//        if (ipWeightApi!=null){
//            String ipWeight = ipWeightApi.getIpWeight();
//            if (TextUtils.isEmpty(ipWeight)){
//                return domain;
//            }
//            try {
//                String str = new String(Base64.decode(ipWeight.getBytes(), Base64.DEFAULT));
//                List<KernelIpWeight> ipWeights =  JSON.parseArray(str,KernelIpWeight.class);
//                if (ipWeights!=null && ipWeights.size()>0){
//                    int weightIndex = getWeightIndex(ipWeights);
//                    domain = ipWeights.get(weightIndex).getName();
//                }
//            }catch (Exception e){
//                LogUtils.i("NETWORK_LOG","ipweight解析报错了："+e.getMessage());
//            }
//        }
//        // 做非空判断
//        if (TextUtils.isEmpty(domain) || domain.trim().length() ==0){
//            domain = DOMAIN_JK;
//        }
//        return domain;
//    }
}
