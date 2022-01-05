package com.mobile.centaur.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


public class NotificationClickReceiver extends BroadcastReceiver {
    public static final String EXTRA_ISPRESCRIPTION = "extra_isprescription";
    public static final String EXTRA_EXTRA = "EXTRA_EXTRA";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("NotifiClickReceiver","[NotificationClickReceiver] 用户点击打开了通知");

        //开药申请点击事件，需要刷新
        boolean isPrescription = intent.getBooleanExtra(EXTRA_ISPRESCRIPTION, false);
        if (isPrescription){

        }

        String msgExtra = intent.getStringExtra(EXTRA_EXTRA);   //通知栏点击
        if (!TextUtils.isEmpty(msgExtra)) {

        }
    }
}