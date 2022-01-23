package com.mobile.centaur.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.text.TextUtils;

import com.app.mobile.centaur.R;
import com.mobile.centaur.widget.Loading_view;

public class ProgressUtil {

    private ProgressUtil() {
    }

    private volatile static ProgressUtil instance;

    /**
     * 单例模式中获取唯一的Application实例
     *
     * @return
     */
    public static ProgressUtil getInstance() {
        if (instance == null) {
            synchronized (ProgressUtil.class) {
                if (instance == null) {
                    instance = new ProgressUtil();
                }
            }
        }
        return instance;
    }

    private static ProgressDialog infoDialog;


    /**
     * 创建loading框
     */
    public ProgressDialog showDialog(final Activity context, String title, Integer delay) {
        if (context != null) {
            infoDialog = new Loading_view(context, R.style.loading_view);
            if (!TextUtils.isEmpty(title)) {
                infoDialog.setTitle(title);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!context.isFinishing() && !infoDialog.isShowing()) {
                        infoDialog.show(); //延时加载
                    }
                }
            }, delay);

        }
//        else{
//            if (infoDialog!=null&&isLiving(context)){
//                infoDialog.show();
//
//            }
//        }

        return infoDialog;
    }


    private static boolean isLiving(Activity activity) {

        if (activity == null) {
            return false;
        }

        if (activity.isFinishing()) {
            return false;
        }


        return true;
    }

    public Boolean isShowing() {
        if (infoDialog != null) {
            return infoDialog.isShowing();
        }
        return false;
    }

    /**
     * 销毁loading框
     */
    public void dismissDialog() {
        if (infoDialog != null && infoDialog.isShowing()) {
            infoDialog.dismiss();
            infoDialog = null;
        }
    }

}
