package com.mobile.centaur.utils;

import android.os.Environment;

import androidx.fragment.app.FragmentActivity;

import java.io.File;

import xcrash.XCrash;

/**
 * 对xCrash 工具进行封装 ,
 * 这里定义了一个静态方式
 */
public class XcrashLogUtils {
    public static void initCrashLog(FragmentActivity activity) {
        if (!PermissionsUtil.checkSdcardPerssion(activity)) {
            return;
        }
        XCrash.InitParameters initParameters = new XCrash.InitParameters();
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mhmt";
        PermissionsUtil.checkSdcardPerssion(activity);
        File fileD = new File(filePath);
        if (!fileD.exists()) {
            fileD.mkdirs();
        }
        initParameters.setLogDir(filePath);
        //usually in: /data/data/PACKAGE_NAME/files/tombstones
        XCrash.init(activity, initParameters);
    }
}