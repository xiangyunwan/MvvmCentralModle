package com.example.testcentral

import com.example.testcentral.base.ChannelConstantValues
import com.example.testcentral.base.MyConstants
import com.mobile.centaur.base.BaseApplication
import com.mobile.centaur.utils.MMkvUtils
import com.mobile.centaur.utils.SystemUtil
import org.json.JSONObject

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
        MMkvUtils.getInstance(this).initMMkv(SystemUtil.getAppName(this)).importSharedPreferences(
            MyConstants.SP_NAME)

        //收集渠道
        try {
            val channelCode: String = ChannelConstantValues.getChannelCode(this)
            val downloadChannel: String = ChannelConstantValues.getUmengChannel(channelCode)
            val properties = JSONObject()
            //这里示例 DownloadChannel 记录下载商店的渠道(下载渠道)。如果需要多个字段来标记渠道包，请按业务实际需要添加。
            properties.put("DownloadChannel", downloadChannel)
            //使用埋点记录激活事件、渠道追踪，这里激活事件取名为 AppInstall。
//            SensorsDataAPI.sharedInstance().trackInstallation("AppInstall", properties)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}