package com.example.testcentral

import com.example.testcentral.bean.MyConstants
import com.mobile.centaur.base.BaseApplication
import com.mobile.centaur.utils.MMkvUtils
import com.mobile.centaur.utils.SystemUtil

/**
 * Created by xiangyunwan
 * Date: 2020/12/18
 * Time: 6:17 PM
 * Description:
 */
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        //mmkv 替换 sp 初始化
        MMkvUtils.getInstance(this).initMMkv(SystemUtil.getAppName(this)).testImportSharedPreferences(MyConstants.SP_NAME)
    }
}