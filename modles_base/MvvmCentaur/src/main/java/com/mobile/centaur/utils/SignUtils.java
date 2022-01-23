package com.mobile.centaur.utils;

import android.text.TextUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public class SignUtils {
    /**
     * 将HashMap转换成TreeMap
     * @param params
     * @return
     */
    private static TreeMap<String,Object> mapToTreeMap(Map<String,Object> params){
        TreeMap<String,Object> treeMap = new TreeMap<>();
        if (params!=null && !params.isEmpty()){
            for (String key:params.keySet()){
                Object o = params.get(key);
                if (o!=null && !"".equals(o)){
                    treeMap.put(key,o);
                }
            }
        }
        return treeMap;
    }
    private static String getStringA(Map<String,Object> params){
        StringBuilder stringb = new StringBuilder();
        String stringA = "";
        TreeMap<String, Object> treeMap = mapToTreeMap(params);
        if(treeMap!=null && !treeMap.isEmpty()){
            for (String key:treeMap.keySet()){
                Object o = treeMap.get(key);
                //value值为空不参与验签
                if (o!=null && !TextUtils.isEmpty(o.toString())){
                    stringb.append("&"+key+"="+o.toString());
                }
            }
            stringA = stringb.toString();
            stringA = stringA.substring(1);
        }
        return stringA;
    }

    /**
     * 加密验签
     * @param params
     * @param key
     * @return
     */
    public static String signValue(Map<String,Object> params,String key){
        String stringA = getStringA(params);
        String stringSignTemp = stringA+key;
        String signValue = MD5Utils.md5(stringSignTemp).toUpperCase();
        LogUtils.d(stringA);
        LogUtils.d(stringSignTemp);
        LogUtils.d(signValue);
        return signValue;
    }
}
