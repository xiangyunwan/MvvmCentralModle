package com.mobile.centaur.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private static DeviceInfo deviceInfo = null;
    private static int ram = 0;

    @SuppressWarnings("unused")
    private static final String TAG = "DeviceInfoUtil";

    public static synchronized DeviceInfo getDeviceInfo(Context context) {
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();
            // 获取屏幕分辨率
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            deviceInfo.setScreenWidth(displayMetrics.widthPixels);
            deviceInfo.setScreenHeight(getRawHeight(context, displayMetrics,
                    windowManager));
            deviceInfo.setRatio(((float)getRawHeight(context, displayMetrics,
                    windowManager))/displayMetrics.widthPixels);

            // 获取DeviceID
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceInfo.setDeviceID(telephonyManager.getDeviceId());
            //SIM 卡iccid
            deviceInfo.setIccid(telephonyManager.getSimSerialNumber());
            // 获取设备型号
            deviceInfo.setDeviceModel(android.os.Build.MODEL);
            // 获取设备系统版本
            deviceInfo.setSystemVersion(android.os.Build.VERSION.RELEASE);

            // 获取软件版本
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(
                        context.getPackageName(), 0);
                deviceInfo.setSoftVersion(packageInfo.versionName);
            } catch (PackageManager.NameNotFoundException e) {
                deviceInfo.setSoftVersion("");
            }
        }
        return deviceInfo;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static int getRawHeight(Context context,
                                   DisplayMetrics displayMetrics, WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Class c = null;
        int height = 0;
        try {
            c = Class.forName("android.view.Display");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = c.getMethod("getRawHeight");
        } catch (SecurityException e) {
//			e.printStackTrace();
        } catch (NoSuchMethodException e) {
//			e.printStackTrace();
        }
        try {
            if (method != null) {
                height = (Integer) method.invoke(display);
            } else {
                height = displayMetrics.heightPixels;
            }
        } catch (IllegalArgumentException e) {
//			e.printStackTrace();
        } catch (IllegalAccessException e) {
//			e.printStackTrace();
        } catch (InvocationTargetException e) {
//			e.printStackTrace();
        }
        return height;
    }

    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static synchronized int getRam() {
        if (ram == 0) {
            String str1 = "/proc/meminfo";// 系统内存信息文件
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;
            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(
                        localFileReader, 8192);
                str2 = localBufferedReader.readLine();
                arrayOfString = str2.split("\\s+");
                for (String num : arrayOfString) {
                    Log.i(str2, num + "\t");
                }
                initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘�?024转换为Byte
                localBufferedReader.close();
            } catch (IOException e) {
            }
            ram = (int) (initial_memory / 1024 / 1024);
        }
        return ram;
    }

    /**
     * 获得状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获得显示应用部分的高度
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        return getDeviceInfo(context).getScreenHeight() - getStatusBarHeight(context);
    }

    /**
     * 获取可用内存
     * @param mContext 上下文
     * @return
     */
    public static long gainUnusedMemory(Context mContext) {
        long MEM_UNUSED = 0L;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    /**
     * 获取总内存
     * @return
     */
    public static long gainTotalMemory() {
        long mTotal = 0;
        // /proc/meminfo读出的内核信息进行解析
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
        } catch (Exception e) {
            Log.e(TAG, "获取总内存失败，原因："+e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return mTotal;
    }


    /**
     * 获得RAM内存总的大小, 单位MB
     * @return
     */
    public static synchronized long getRamTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");

            initial_memory = Long.valueOf(arrayOfString[1]).longValue();
            localBufferedReader.close();

        } catch (IOException e) {}
        //单位MB
        return (initial_memory/1024);
    }

    /**
     * 获取签名
     * @param context
     * @return
     */
    public static String getSignure(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        Signature[] signatures = null;
        StringBuilder sb = new StringBuilder();
        try {
            packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            for (Signature signature : signatures) {
                sb.append(signature.toCharsString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        /************** 得到应用签名 **************/
        return sb.toString();
    }

    /**
     * 接口上传参数加签名加密值，谨慎修改，与服务器端统一校验方式
     * @return
     */
//    public static String getSignatureStr(Context context) {
//        String result = "";
//        try {
//            String s = getSignure(context);
//            String md5 = MD5Utils.stringToMD5(MD5Utils.stringToMD5(s));
//            String base64 = JrBase64.encodeBytes(new String(md5 + "_JDJR_Android").getBytes());
//            result = base64.substring(base64.length() - 16);
//        } catch (Exception e) {
//        }
//        return result;
//    }
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

//    @SuppressLint("MissingPermission")
//    public static DeviceInfoBean getDeviceInfo(Context context) {
//
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
//
//        deviceInfoBean.model = "生产厂家：" + android.os.Build.BRAND + " 手机型号：" + android.os.Build.MODEL;
//        deviceInfoBean.version = android.os.Build.VERSION.RELEASE;
//        deviceInfoBean.imei = telephonyManager.getSubscriberId();
//        deviceInfoBean.windowWidth = CommonUtil.getScreenWidth(context);
//        deviceInfoBean.windowHeight = CommonUtil.getScreenHeight(context);
//        deviceInfoBean.language = Locale.getDefault().getLanguage();
//        return deviceInfoBean;
//
//    }

    /**
     * 接口上传参数加签名加密值，谨慎修改，与服务器端统一校验方式
     * @return
     */
    public static String getSignatureStr(Context context) {
        String result = "";
        try {
            String s = getSignure(context);
            String md5 = MD5Utils.stringToMD5(MD5Utils.stringToMD5(s));
            String base64 = JrBase64.encodeBytes(new String(md5 + "_JDJR_Android").getBytes());
            result = base64.substring(base64.length() - 16);
        } catch (Exception e) {
        }
        return result;
    }


}
