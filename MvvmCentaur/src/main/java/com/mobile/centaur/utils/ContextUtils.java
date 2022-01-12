package com.mobile.centaur.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.mobile.centaur.base.BaseApplication;

import java.util.List;

/**
 * 功能说明： </br>
 *
 * @author: zhangzhenzhong
 * @version: 1.0
 * @date: 2017/3/8
 * @Copyright (c) 2017. zhangzhenzhong Inc. All rights reserved.
 */
public class ContextUtils {
    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) BaseApplication.getApplicatonContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = BaseApplication.getApplicatonContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    //调用方式：myApplication.registerActivityLifecycleCallbacks(callBacks);
    Application.ActivityLifecycleCallbacks callBacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }
        @Override
        public void onActivityStarted(Activity activity) {
        }
        @Override
        public void onActivityResumed(Activity activity) {
        }
        @Override
        public void onActivityPaused(Activity activity) {
        }
        @Override
        public void onActivityStopped(Activity activity) {
        }
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }
        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };
}
