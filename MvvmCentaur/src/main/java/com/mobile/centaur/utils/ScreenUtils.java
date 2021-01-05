package com.mobile.centaur.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * created by mengbenming on 2018/9/17
 *
 * @class describe
 */
public class ScreenUtils {
    private static DisplayMetrics sDisplay;

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static double getStatusHeight(Context context) {
        return Math.ceil(25 * context.getResources().getDisplayMetrics().density);
    }

//    public static int dp2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }

    /**
     * Convert a dp float value to pixels
     *
     * @param context
     * @param dp
     * @return the responsive pixels
     */
    public static int dp2px(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return Math.round(px);
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public static float getPicFaceSize(int typeMemberSize) { // 根据字号判断图片的3中缩放状态
        float scaleSize = 1;
        switch (typeMemberSize) {
            case 0:
                scaleSize = (float) 0.9;
                break;
            case 1:
                scaleSize = (float) 1;
                break;
            case 2:
                scaleSize = (float) 1.1;
                break;
            default:
                break;
        }
        return scaleSize;
    }

    public static float getPicSizeByScreen(int screenWidth) { // 根据屏幕分辨率判断图片的缩放比例
        float scaleSize = 1;
        if (screenWidth <= 480) {
            return (float) (scaleSize * 0.5);
        } else if (screenWidth <= 720) {
            return (float) (scaleSize * 0.65);
        } else if (screenWidth <= 1080) {
            return (float) (scaleSize * 0.8);
        } else {
            return (float) (scaleSize * 0.95);
        }
    }


//    public static int getScreenHeight() {
//        if (sDisplay == null) {
//            sDisplay = MyApplication.getContext().getResources().getDisplayMetrics();
//        }
//        int height = sDisplay.heightPixels;
//        return height;
//    }
//
//    public static int getScreenWidth() {
//        if (sDisplay == null) {
//            sDisplay = MyApplication.getContext().getResources().getDisplayMetrics();
//        }
//        int width = sDisplay.widthPixels;
//        return width;
//    }
}
