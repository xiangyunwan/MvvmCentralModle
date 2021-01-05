package com.mobile.centaur.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.mobile.centaur.h5container.model.DeviceInfoBean;

import java.util.Locale;

/**
 * 设备信息类
 */
public class DeviceInfoUtils {

    public static final int TYPE_MODEL = 0;  //手机型号
    public static final int TYPE_IP = 1;  //IP地址
    public static final int TYPE_VERSION = 2;  //版本号
    public static final int TYPE_ID = 3;  //设备ID
    public static final int TYPE_CPUINFO = 4;  //CPU信息
    public static final int TYPE_CARRIER = 5;  //运营商
    public static final int TYPE_MEMORY = 6;  //内存信息
    public static final int TYPE_NETTYPE = 7;  //网络类型


    public static String getDeviceInfoWithType(int type) {

        switch (type) {
            case TYPE_MODEL:
                return android.os.Build.MODEL;
            case TYPE_IP:
                break;
            case TYPE_VERSION:
                return android.os.Build.VERSION.SDK;
            case TYPE_ID:
                return android.os.Build.ID;
            case TYPE_CPUINFO:
                return android.os.Build.CPU_ABI;
            case TYPE_CARRIER:
                break;
            case TYPE_MEMORY:
                break;
            case TYPE_NETTYPE:
                break;
        }

        return null;
    }

    /**
     * 获取当前的运营商
     *
     * @param context
     * @return 运营商名字
     */
    public static String getOperator(Context context) {


        String ProvidersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String IMSI = telephonyManager.getSubscriberId();
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
            return ProvidersName;
        } else {
            return "没有获取到sim卡信息";
        }
    }

    @SuppressLint("MissingPermission")
    public static DeviceInfoBean getDeviceInfo(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();

        deviceInfoBean.model = "生产厂家：" + android.os.Build.BRAND + " 手机型号：" + android.os.Build.MODEL;
        deviceInfoBean.version = android.os.Build.VERSION.RELEASE;
        deviceInfoBean.imei = telephonyManager.getSubscriberId();
        deviceInfoBean.windowWidth = CommonUtil.getScreenWidth(context);
        deviceInfoBean.windowHeight = CommonUtil.getScreenHeight(context);
        deviceInfoBean.language = Locale.getDefault().getLanguage();
        return deviceInfoBean;

    }


}
