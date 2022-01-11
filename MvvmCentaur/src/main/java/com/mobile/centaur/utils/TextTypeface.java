package com.zhangzhenzhong1.tools.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 导入字体类，创建粗细体。
 * TextView tv.setTypeface(TextTypeface.createBoldStyle(context, TextTypeface.STYLE.ROBOTO));
 * @author gaixutian
 */
public class TextTypeface {
	public static enum STYLE{
			ROBOTO;
	};
	
	static Typeface mBoldType;
	static Typeface mNormalType;
	static Typeface mRobotoType4Bold;
	
	public static Typeface createBoldStyle(Context ctx, STYLE style){
		String textFileName = null ;
		if(style == STYLE.ROBOTO){
			if(mBoldType!=null) return mBoldType;
			textFileName = "Roboto-Light.ttf";
		}
		 mBoldType = Typeface.createFromAsset(ctx.getAssets(), textFileName);
		return mBoldType;
	}
	
	public static Typeface createNormalStyle(Context ctx, STYLE style){
		String textFileName = null ;
		if(style == STYLE.ROBOTO){
			if(mNormalType!=null) return mNormalType;
			textFileName = "Roboto-Thin.ttf";
		}
		 mNormalType = Typeface.createFromAsset(ctx.getAssets(), textFileName);
		return mNormalType;
	}
	
	public static Typeface createRobotoType4BoldStyle(Context ctx, STYLE style){
		String textFileName = null ;
		if(style == STYLE.ROBOTO){
			if(mRobotoType4Bold!=null) return mRobotoType4Bold;
			textFileName = "Roboto-Bold.ttf";
		}
		mRobotoType4Bold = Typeface.createFromAsset(ctx.getAssets(), textFileName);
		return mRobotoType4Bold;
	}
	
	
	/**
	 * 给传递的目标控件设置Roboto-Light字体
	 * @param mContext 上下文
	 * @param views 需要设置字体的控件
	 */
	public static void configRobotoLight(Context mContext, TextView... views){
		if(null != views && views.length > 0 ){
			Typeface mTypeface = TextTypeface.createBoldStyle(mContext, TextTypeface.STYLE.ROBOTO);
			for (TextView view : views) {
				view.setTypeface(mTypeface);
			}
		}
	}

	/**
	 * 给传递的目标控件设置Roboto-Thin字体
	 * @param mContext 上下文
	 * @param views 需要设置字体的控件
	 */
	public static void configRobotoThin(Context mContext, TextView... views){
		if(null != views && views.length > 0 ){
			Typeface mTypeface = TextTypeface.createNormalStyle(mContext, TextTypeface.STYLE.ROBOTO);
			for (TextView view : views) {
				view.setTypeface(mTypeface);
			}
		}
	}

	/**
	 * 给传递的目标控件设置Roboto-Bold字体
	 * @param mContext 上下文
	 * @param views 需要设置字体的控件
	 */
	public static void configRobotoBold(Context mContext, TextView... views){
		if(null != views && views.length > 0 ){
			Typeface mTypeface = TextTypeface.createRobotoType4BoldStyle(mContext, TextTypeface.STYLE.ROBOTO);
			for (TextView view : views) {
				view.setTypeface(mTypeface);
			}
		}
	}
	/**
	 * 给传递的目标控件设置OpenSans-Light字体
	 * @param mContext 上下文
	 * @param views 需要设置字体的控件
	 */
	public static void configOpenSansLight(Context mContext, TextView... views){
		if(null != views && views.length > 0 ){
			Typeface mTypeface = TextTypeface.createRobotoType4BoldStyle(mContext, TextTypeface.STYLE.ROBOTO);
			for (TextView view : views) {
				view.setTypeface(mTypeface);
			}
		}
	}
}
