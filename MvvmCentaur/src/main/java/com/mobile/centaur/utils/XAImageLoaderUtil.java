package com.mobile.centaur.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by zzz
 */
public class XAImageLoaderUtil {

    private static XAImageLoaderUtil instance;

    private XAImageLoaderUtil() {

    }

    public static XAImageLoaderUtil getInstance() {
        if (null == instance) {
            synchronized (XAImageLoaderUtil.class) {
                if (null == instance) {
                    instance = new XAImageLoaderUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 加载项目内的图片
     *
     * @param url
     * @param imageView
     */
    public void load(Context context, String url, ImageView imageView) {
//        Glide.with(context).load(url).asBitmap().dontAnimate().into(imageView);
        Glide.with(context).load(url).into(imageView);
    }

}
