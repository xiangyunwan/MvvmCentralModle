package com.mobile.centaur.utils;

import android.content.res.Resources;

/**
 * @author lidong
 * @date 2019/10/28 13:51
 * @describe
 * 调用方式： 重写 application 和 baseactivity 中 的 getResources（）方法
 *       1、
         @Override
         public Resources getResources() {
         return FontChangeUtils.forbidFontChange(super.getResources());
         }
        2、

 */
public class FontChangeUtils {
    /**
     * 禁止app字体随系统字体大小而改变
     * @param resources
     * @return
     */
    public static Resources forbidFontChange(Resources resources){
        if (resources != null && resources.getConfiguration()!=null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
