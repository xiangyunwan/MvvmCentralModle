@file:JvmName("Dependencies")
@file:JvmMultifileClass


//全文参考链接：整洁架构：https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/main/buildSrc/src/main/kotlin/Dependencies.kt
//业余参考：https://segmentfault.com/a/1190000019436677
//kotlindsl与gradle迁移规则：https://cloud.tencent.com/developer/article/1839887
object Kotlin {
    const val kotlinVersion = "1.3.72"
}

object AndroidSdk {
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
    const val compileSdkVersion = 29

    //AndroidStudio 4.2生成的代码里面已经不再需要该属性了
    const val buildToolsVersion = "30.0.2"
}

object AndroidClient {
    const val appId = "com.example.testcentral"
    const val versionCode = 20220105
    const val versionName = "2.2.1"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildPlugins {
    private object Versions {
        const val AGP = "4.1.1"
        const val AGCP = "1.3.1.300"
        const val BUGTAG_P = "2.1.5"
        const val sensorsAnalyticsPlugin="3.3.7"
        const val GREEN_DAO_VERSION="3.3.0"
    }
    val Maven = MavenR()
    class MavenR {
        // aar上传的主机
        val jkMavenPublicProxy = "http://192.168.36.201:8081/repository/maven-public/"
        // 华为
        val huwei = "http://developer.huawei.com/repo/"
    }
    object Plugin{
        const val androidApplication = "com.android.application"
        const val androidLibrary = "com.android.library"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
        const val kotlinKapt = "kotlin-kapt"
        const val sensorsdata = "com.sensorsdata.analytics.android"
        const val huaweiAgconnect = "com.huawei.agconnect"
        const val butterknife = "com.jakewharton.butterknife"
        const val greendao = "org.greenrobot.greendao"
        const val bugtags = "com.bugtags.library.plugin"
    }
    const val AGP = "com.android.tools.build:gradle:${Versions.AGP}"
    const val KGP = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}"
    const val hwAgcp = "com.huawei.agconnect:agcp:${Versions.AGCP}"
    const val butterknife = "com.jakewharton:butterknife-gradle-plugin:${Libraries.Versions.butterknife}"
    const val bugtags = "com.bugtags.library:bugtags-gradle:${Versions.BUGTAG_P}"
    const val sensorsdata = "com.sensorsdata.analytics.android:android-gradle-plugin2:${Versions.sensorsAnalyticsPlugin}"//神策
    const val greendao = "org.greenrobot:greendao-gradle-plugin:${Versions.GREEN_DAO_VERSION}"



}

object Libraries {
    object Versions {
        const val anko = "0.10.7"
        const val appcompat = "1.3.1"
        const val material = "1.2.1"
        const val constraintlayout = "2.0.4"
        const val okhttp = "5.0.0-alpha.3"
        const val retrofit = "2.9.0"
        const val adapterRxjava3 = "2.9.0"
        const val loggingInterceptor = "5.0.0-alpha.3"
        const val gson = "2.8.9"
        const val lottie = "4.2.2"
        const val immersionbar = "2.3.2-beta01"
        const val glide = "4.12.0"
        const val glideCompiler = "4.12.0"
        const val glideTransformations = "4.12.0"

        const val FlycoTabLayout_Lib = "2.1.2@aar"
        const val eventbus = "3.3.1"
        const val multidex = "2.0.1"
        const val zxing = "3.4.1"
        const val fastjson = "1.2.79"
        const val jsbridge = "1.0.4"
        const val BaseRecyclerViewAdapterHelper = "3.0.4"
        const val ViewPagerIndicator = "1.1.9"
        const val mmkv = "1.2.12"
        const val converterGson = "2.9.0"
        const val rxjava3Android = "3.0.0"
        const val rxjava3 = "3.1.3"
        const val greendao = "3.3.0"
        const val luban = "1.0.0"
        const val svgplayer = "2.4.4"
        const val androidDatabaseSqlcipher = "3.5.5"
        //        const val greenDaoUpgradeHelper = "v1.3.0"
        const val arouterCompiler = "1.2.2"


        const val wechatSdk = "5.5.8"
        const val X5WEBVIEW = "2.0.1.1"
        //        const val rxbinding = "0.4.0"
//        const val easydeviceinfo = "2.0.2"
        const val glideOkhttp3Integration = "1.5.0"
        const val bugtagsLib = "3.1.3"

        //        const val androidGifDrawable = "1.2.3"
//        const val baseAdapter = "3.0.3"
//        const val citypickerview = "1.0.0"
        const val AndroidPickerView = "4.1.9"
        const val badgeview = "1.1.3"
        const val butterknife = "10.2.1"
        const val butterknifeCompiler = "10.2.1"
        //        const val retrofit2FastJsonConverter = "1.2"
        const val converterFastjsonAndroid = "2.1.0"
        const val sensorsAnalyticsSDK = "5.3.1"
        const val hmsPush = "5.1.1.301"
        const val gtsdk = "3.1.4.0"
        const val gtc = "3.1.0.0"
        const val gtHW = "3.0.1"
        const val gtXM = "3.0.2"
        const val gtOppo = "3.0.2"
        const val gtVivo = "3.0.3"
        const val SmartRefreshLayout = "1.1.0"
        const val jsoup = "1.13.1"
        const val switchbutton = "2.0.0"
        const val IMAGEPICKER = "2.1.0.0"
        const val EasyPopup = "1.1.2"
        const val ucrop = "2.2.2"
        const val legacySupportV4 = "1.0.0"

        const val richtext = "3.0.8"
        const val statusbarutil = "1.5.1"
        const val jkQiniu = "1.0.3"
        const val socketIoClient = "1.0.0"
        const val exoplayerCore = "2.9.1"
        const val exoplayerHls = "2.9.1"
        const val exoplayerDash = "2.9.1"
        const val smoothstreaming = "2.9.1"
        const val DanmakuFlameMaster = "0.6.4"
        const val ndkbitmapArmv7a = "0.6.4"
        const val lifecycleViewmodelKtx = "2.2.0"
        const val lifecycleExtensions = "2.0.0"

        const val aspectjrt = "1.8.10"
        const val coreKtx = "1.3.2"
        const val voiceSdk = "3.3.1"
        const val annotation = "1.2.0-alpha01"
        const val circleimageview = "2.2.0"
        const val materialDialogs = "0.9.6.0"
        const val easypopup = "1.1.2"
        const val flexbox = "1.1.0"
        const val shadow = "0.0.4-beta3"
        const val vhallVssSDK = "0.0.1"
        const val vHSaaSSDK = "0.0.6"
        const val vHsupport = "0.0.1"
        const val aliyunPlayer = "4.5.0-full"
        const val alivcConan = "0.9.5"
        const val transition = "1.2.0"
        const val recyclerview = "1.0.0"
        const val expandableTextView = "1.0.3"
        const val qiniu = "7.3.15"
        const val viewpager2 = "1.0.0"

        const val buglySdk = "4.0.0"
    }
    const val anko = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val rxjava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxjava3}"
    const val adapterRxjava3 = "com.squareup.retrofit2:adapter-rxjava3:${Versions.adapterRxjava3}"
    const val rxjava3Android = "io.reactivex.rxjava3:rxandroid:${Versions.rxjava3Android}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val zxing = "com.google.zxing:core:${Versions.zxing}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val immersionbar = "com.gyf.immersionbar:immersionbar:${Versions.immersionbar}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideTransformations = "jp.wasabeef:glide-transformations:${Versions.glideTransformations}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
    const val glideOkhttp3Integration = "com.github.bumptech.glide:okhttp3-integration:${Versions.glideOkhttp3Integration}"
    const val flycoTabLayout_Lib = "com.flyco.tablayout:FlycoTabLayout_Lib:${Versions.FlycoTabLayout_Lib}"
    const val eventbus = "org.greenrobot:eventbus:${Versions.eventbus}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val fastjson = "com.alibaba:fastjson:${Versions.fastjson}"
    const val jsbridge = "com.github.lzyzsd:jsbridge:${Versions.jsbridge}"
    const val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.BaseRecyclerViewAdapterHelper}"
    const val viewPagerIndicator = "com.shizhefei:ViewPagerIndicator:${Versions.ViewPagerIndicator}"
    const val mmkv = "com.tencent:mmkv-static:${Versions.mmkv}"
    const val greendao = "org.greenrobot:greendao:${Versions.greendao}"
    const val luban = "com.jianke:Luban:${Versions.luban}"
    const val svgplayer = "com.github.yyued:SVGAPlayer-Android:${Versions.svgplayer}"
    const val arouterCompiler = "com.alibaba:arouter-compiler:${Versions.arouterCompiler}"



    const val wechatSdk = "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:${Versions.wechatSdk}"
    const val x5webview = "com.jianke:X5WEBVIEW:${Versions.X5WEBVIEW}"
    const val bugtagsLib = "com.bugtags.library:bugtags-lib:${Versions.bugtagsLib}"
    //    const val androidGifDrawable = "pl.droidsonroids.gif:android-gif-drawable:${Versions.androidGifDrawable}"
//    const val baseAdapter = "com.zhy:base-adapter:${Versions.baseAdapter}"
    const val androidDatabaseSqlcipher = "net.zetetic:android-database-sqlcipher:${Versions.androidDatabaseSqlcipher}"
    //    const val greenDaoUpgradeHelper = "com.github.yuweiguocn:GreenDaoUpgradeHelper:${Versions.greenDaoUpgradeHelper}"
//    const val citypickerview = "liji.library.dev:citypickerview:${Versions.citypickerview}"
    const val AndroidPickerView = "com.contrarywind:Android-PickerView:${Versions.AndroidPickerView}"
    const val badgeview = "q.rorbin:badgeview:${Versions.badgeview}"
    //    const val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
    const val butterknifeCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterknifeCompiler}"
    const val converterFastjsonAndroid = "org.ligboy.retrofit2:converter-fastjson-android:${Versions.converterFastjsonAndroid}"
    const val sensorsAnalyticsSDK = "com.sensorsdata.analytics.android:SensorsAnalyticsSDK:${Versions.sensorsAnalyticsSDK}"
    const val hmsPush = "com.huawei.hms:push:${Versions.hmsPush}"// 华为推送
    const val gtsdk = "com.getui:gtsdk:${Versions.gtsdk}"       // 个推SDK
    const val gtc = "com.getui:gtc:${Versions.gtc}"             // 个推核心组件
    const val gtHW = "com.getui.opt:hwp:${Versions.gtHW}"       // 华为
    const val gtXM = "com.getui.opt:xmp:${Versions.gtXM}"       // 小米
    const val gtOppo = "com.assist-v3:oppo:${Versions.gtOppo}"  // oppo
    const val gtVivo = "com.assist-v3:vivo:${Versions.gtVivo}"  // vivo
    const val SmartRefreshLayout = "com.scwang.smartrefresh:SmartRefreshLayout:${Versions.SmartRefreshLayout}"
    const val SmartRefreshHeader = "com.scwang.smartrefresh:SmartRefreshHeader:${Versions.SmartRefreshLayout}"
    const val jsoup = "org.jsoup:jsoup:${Versions.jsoup}"
    const val switchbutton = "com.kyleduo.switchbutton:library:${Versions.switchbutton}"
    const val IMAGEPICKERAPI = "com.jianke:IMAGEPICKERAPI:${Versions.IMAGEPICKER}"
    const val IMAGEPICKER = "com.jianke:IMAGEPICKER:${Versions.IMAGEPICKER}@aar"
    const val EasyPopup = "com.github.zyyoona7:EasyPopup:${Versions.EasyPopup}"
    const val ucrop = "com.github.yalantis:ucrop:${Versions.ucrop}"
    const val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"

    //kotlin sdk
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Kotlin.kotlinVersion}"
    const val kotlinCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Kotlin.kotlinVersion}"
    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Kotlin.kotlinVersion}"

    const val richtext = "com.zzhoujay.richtext:richtext:${Versions.richtext}"
    const val statusbarutil = "com.jaeger.statusbarutil:library:${Versions.statusbarutil}"
    const val jkQiniu = "com.jianke:QINIU:${Versions.jkQiniu}"
    const val socketIoClient = "io.socket:socket.io-client:${Versions.socketIoClient}"
    const val exoplayerCore = "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayerCore}"
    const val exoplayerHls = "com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayerHls}"
    const val exoplayerDash = "com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayerDash}"
    const val smoothstreaming = "com.google.android.exoplayer:exoplayer-smoothstreaming:${Versions.smoothstreaming}"
    const val ctiaoDanmakuFlameMaster = "com.github.ctiao:DanmakuFlameMaster:${Versions.DanmakuFlameMaster}"
    const val ctiaoNdkbitmapArmv7a = "com.github.ctiao:ndkbitmap-armv7a:${Versions.ndkbitmapArmv7a}"
    const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"


    const val aspectjrt = "org.aspectj:aspectjrt:${Versions.aspectjrt}"

    const val voiceSdk = "io.agora.rtc:voice-sdk:${Versions.voiceSdk}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
    const val circleimageview = "de.hdodenhof:circleimageview:${Versions.circleimageview}"
    const val materialDialogs = "com.afollestad.material-dialogs:commons:${Versions.materialDialogs}"
    const val easypopup = "com.github.zyyoona7:EasyPopup:${Versions.easypopup}"
    const val flexbox = "com.google.android:flexbox:${Versions.flexbox}"
    const val shadow = "com.loopeer.lib:shadow:${Versions.shadow}"
    //vhall
    const val vhallVssSDK = "com.vhall:VhallVssSDK:${Versions.vhallVssSDK}"
    const val vHSaaSSDK = "com.vhall:VHSaaSSDK:${Versions.vHSaaSSDK}"
    const val vHsupport = "com.vhall:VHSaaSSDK-support:${Versions.vHsupport}"

    const val aliyunPlayer = "com.aliyun.sdk.android:AliyunPlayer:${Versions.aliyunPlayer}"
    const val alivcConan = "com.alivc.conan:AlivcConan:${Versions.alivcConan}"
    const val transition = "androidx.transition:transition:${Versions.transition}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val expandableTextView = "cn.carbs.android:ExpandableTextView:${Versions.expandableTextView}"
    const val qiniu = "com.qiniu:qiniu-android-sdk:${Versions.qiniu}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"


    const val buglySdk = "com.tencent.bugly:crashreport:${Versions.buglySdk}"
}
object TestLibraries {
    private object Versions {
        const val junit4 = "4.12"
        const val testExtJunit = "1.1.1"
        const val espressoCore = "3.2.0"
        const val testRunner = "1.0.2"
        const val testRules = "1.0.2"
        const val espressoContrib = "3.2.0"
        const val espressoIdlingResource = "3.2.0"
        const val espressoIntents = "3.2.0"
        const val mockitoCore = "2.8.9"
        const val powermockModuleJunit4 = "2.0.0"
        const val powermockModuleJunit4Rule = "2.0.0"
        const val powermockApiMockito2 = "2.0.0"
        const val powermockClassloadingXstream = "2.0.0"
    }
    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testExtJunit  = "androidx.test.ext:junit:${Versions.testExtJunit}"
    const val  espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val  testRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val  testRules = "com.android.support.test:rules:${Versions.testRules}"
    const val  espressoContrib = "com.android.support.test.espresso:espresso-contrib:${Versions.espressoContrib}"
    const val  espressoIdlingResource = "com.android.support.test.espresso:espresso-idling-resource:${Versions.espressoIdlingResource}"
    const val  espressoIntents = "com.android.support.test.espresso:espresso-intents:${Versions.espressoIntents}"
    // Mockito
    const val  mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    // PowerMock
    const val  powermockModuleJunit4 = "org.powermock:powermock-module-junit4:${Versions.powermockModuleJunit4}"
    const val  powermockModuleJunit4Rule = "org.powermock:powermock-module-junit4-rule:${Versions.powermockModuleJunit4Rule}"
    const val  powermockApiMockito2 = "org.powermock:powermock-api-mockito2:${Versions.powermockApiMockito2}"
    const val  powermockClassloadingXstream = "org.powermock:powermock-classloading-xstream:${Versions.powermockClassloadingXstream}"


}
object DevLibraries {
    private object Versions {
        const val leakCanary = "1.5.4"
    }
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}

