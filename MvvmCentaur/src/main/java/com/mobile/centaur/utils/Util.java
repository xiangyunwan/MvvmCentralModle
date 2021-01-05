package com.mobile.centaur.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;

import com.mobile.centaur.base.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by codeest on 2016/8/4.
 */
public class Util {
    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     *
     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    /**
     * 检查WIFI是否连接
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    /**
     * 保存文字到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyToClipBoard(Context context, String text) {
        ClipData clipData = ClipData.newPlainText("url", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        ToastUtil.setToast("已复制到剪贴板");
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean listIsEmpty(List<?> list) {
        if (list != null && list.size() != 0) {
            return false;
        }
        return true;
    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public static void closeSoftInputKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示键盘
     *
     * @param context
     */
    public static void openSoftInputKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 时间转换（不带时间）
     *
     * @param date
     * @return
     */
    public static String Date2StringNoHaveTime(Date date) {
        String str;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        str = sdf.format(date);
        return str;
    }

    /**
     * 获取版本名字
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            String version;
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            version = pi.versionName;
            if (version.contains("-")) {
                version = version.substring(0, pi.versionName.indexOf("-"));
            }
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断是否为直辖市
     *
     * @param city
     * @return
     */
    public static boolean isMunicipalityCity(String city) {
        List<String> datas = Arrays.asList("北京市", "上海市", "重庆市", "天津市");
        return datas.contains(city);
    }

    public static String getCityJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 大陆手机号码11位数，匹配格式：第一位为1+后10位任意数
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^1\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断Email合法性
     */
    public static boolean isEmailLegal(String email) {
        if (email == null)
            return false;
        String rule = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(rule);
        matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    /**
     * 判断文件大小
     *
     * @param file
     * @return
     */
    private static long getFolderSize(File file) {
        long size = 0;
        File[] files = file.listFiles();  //获取file目录的所有file
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {      //如果是目录的话，递归
                size = size + getFolderSize(files[i]);  //递归用法
            } else {
                size = size + files[i].length(); //每一个file的长度
            }
        }
        return size;
    }


    /**
     * 获取应用缓存大小
     *
     * @param context
     * @return
     */
    public static String getAppCacheSize(Context context) {
        File file = new File(context.getApplicationContext().getFilesDir().getAbsolutePath());
        if (!file.exists()) {
            return "";
        }
        long cacheSize = getFolderSize(file);
        double Kbyte = cacheSize / 1024;
        double Mbyte = Kbyte / 1024;
        double Gbyte = Mbyte / 1024;
        if (Gbyte > 1) {
            return "" + String.format("%.2f", Mbyte) + "GB";
        } else if (Mbyte < 1) {
            return "" + String.format("%.2f", Kbyte) + "KB";  //保留2位小数，四舍五入
        } else {
            return "" + String.format("%.2f", Mbyte) + "MB";
        }
    }

    /**
     * 删除缓存
     *
     * @param file
     */
    public static void del(File file) {
        if (file.isDirectory()) {   //如果是目录，进行递归
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                del(f);
            }
        } else {
            file.delete();  //不是目录，即文件，直接删除
        }
    }

    /**
     * 检查手机号码
     *
     * @return
     */
//    public static boolean checkPhone(Context context, String num) {
//        if (TextUtils.isEmpty(num)) {
//            ToastUtil.show(context.getString(R.string.lg_no_phone));
//            return false;
//        } else if (!Util.isChinaPhoneLegal(num)) {
//            ToastUtil.show(context.getString(R.string.lg_input_right_phone));
//            return false;
//        }
//        return true;
//    }

    /**
     * 判断定位开关是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isLocationEnable(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }
        return true;
    }

}
