package com.mobile.centaur.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.mobile.centaur.base.BaseApplication;


public class ToastUtil {
    public static Toast toast;


    @SuppressLint("ShowToast")
    public static void setToast(String str) {

        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
