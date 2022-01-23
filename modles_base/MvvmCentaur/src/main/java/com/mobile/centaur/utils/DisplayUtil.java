package com.mobile.centaur.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @ClassName DisplayUtil 
 * @Description
 * @author zzz
 * @date 2014-3-15 下午4:01:21
 */
public class DisplayUtil {

	/**
	 * @Description 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 * @param context
	 *            环境
	 * @return int 转化后的px值
	 */
	public static int dipToPx(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * @Description 根据手机的分辨率从 px(像素) 的单位 转成为 dip
	 * @param context
	 *            环境
	 * @param pxValue
	 *            需要转换的像素值
	 * @return int 转化后的dip值
	 */
	public static int pxToDip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * @param context
	 * @param pxValue
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * @param spValue
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
    /**
     * This method converts dp unit to equivalent pixels, depending on device
     * density. NEEDS UTILS TO BE INITIALIZED BEFORE USAGE.
     * 
     * @param dp A value in dp (density independent pixels) unit. Which we need
     *            to convert into pixels
     * @return A float value to represent px equivalent to dp depending on
     *         device density
     */
    public static float convertDpToPixel(Context context, float dp) {
    	 Resources res = context.getResources();
    	 DisplayMetrics mMetrics = res.getDisplayMetrics();
//        if (mMetrics == null) {
//
//            Log.e("MPChartLib-Utils",
//                    "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
//            return dp;
//            // throw new IllegalStateException(
//            // "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...).");
//        }
//
//        DisplayMetrics metrics = mMetrics;
        float px = dp * (mMetrics.densityDpi / 160f);
        return px;
    }
	/**
	 * 获取当前分辨率下指定单位对应的像素大小（根据设备信息）
	 * px,dip,sp -> px<br><br>
	 * 
	 * 用法:getRawSize(context,TypedValue.COMPLEX_UNIT_DIP,itemSpaceDp)<br><br>
	 *
	 * 代码摘自：TextView.setTextSize()
	 * @param mContext 上下文
	 * @param unit  TypedValue.COMPLEX_UNIT_*
	 * @param size
	 * @return
	 */
	public static int getRawSize(Context mContext, int unit, float size) {
		Resources r;
		if (mContext == null)
			r = Resources.getSystem();
		else
			r = mContext.getResources();
		
		return (int) TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
	}
}
