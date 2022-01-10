package com.example.testcentral.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.mobile.centaur.utils.LogUtils;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by zzz on 2022/1/10.
 * 渠道号
 */
public class ChannelConstantValues {
    private static Map<String, String> channelMap = new HashMap<String, String>();
    static {
        channelMap.put("10001", "jianke");
        channelMap.put("10002", "yingyongbao");
        channelMap.put("10003", "oppo");
        channelMap.put("10004", "vivo");
//        channelMap.put("10005", "umeng");
//        channelMap.put("10006", "pp");
        channelMap.put("10007", "huawei");
        channelMap.put("10008", "baidu");
        channelMap.put("10009", "xiaomi");
        channelMap.put("10010", "360");
        channelMap.put("10011", "jianke_m");
        channelMap.put("10012", "meizu");
//        channelMap.put("10013", "jinli");
//        channelMap.put("10014", "sanxing");
//        channelMap.put("10015", "sougou");
//        channelMap.put("10016", "lenovomm");
//        channelMap.put("10017", "chuizi");
    }
    /**
     * 渠道ID
     * @param context
     * @return
     */
    public static String getChannelCode(Context context) {
        String channel = "10001";
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (info.metaData.containsKey("CHANNEL_CODE")){
                Object channel_code = info.metaData.get("CHANNEL_CODE");
                if (channel_code!=null){
                    channel = channel_code.toString();
                }
            }
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
        }
        return channel;
    }

    /**
     * 获取友盟渠道名称
     * @param channelCode
     * @return
     */
    public static String getUmengChannel(String channelCode) {
        if (TextUtils.isEmpty(channelCode)) return null;
        if (channelMap.containsKey(channelCode)) {
            return channelMap.get(channelCode);
        } else {
            return null;
        }
    }
}
