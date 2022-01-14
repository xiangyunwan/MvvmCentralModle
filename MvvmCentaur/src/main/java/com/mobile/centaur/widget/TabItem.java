package com.mobile.centaur.views;

import androidx.annotation.DrawableRes;

/**
 * Created by zhangzhenzhong
 * Date: 2020/10/21
 * Time: 15:03
 * Description：
 */
public interface TabItem {
    String getName();

    @DrawableRes
    int getStaticRes();

    @DrawableRes
    int getAnimateRes();

    String getTabType();
}
