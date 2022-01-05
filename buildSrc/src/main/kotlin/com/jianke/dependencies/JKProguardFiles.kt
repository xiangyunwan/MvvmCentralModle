@file:JvmName("ProguardFiles")
@file:JvmMultifileClass

//不用添加包名，这样就不会生成包名
//package com.jianke.dependencies

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import java.io.File

fun getJKProguardFiles(currentProject: Project): Array<out Any> {
    return getProguardFileList(currentProject).toArray()
}

fun getJKProguardFiles(currentProject: Project, sdkProguardFiles: List<String>?): Array<out Any> {
    return getProguardFileList(currentProject, sdkProguardFiles).toArray()
}

fun getJKProguardFiles(
    currentProject: Project,
    sdkProguardFiles: List<String>?,
    moduleProguardFile: String
): Array<out Any> {
    return getProguardFileList(currentProject, sdkProguardFiles, moduleProguardFile).toArray()
}

/**
 * 配置在consumerProguardFiles处
 * @description 获取sdk混淆文件所在的位置
 * @param currentProject Project 当前module 每个module取到的值都不一样
 * @param sdkProguardFiles List<String>? 当前module使用混淆sdk的列表
 * @param moduleProguardFile String 默认混淆文件，只包含当前module中需要keep的class
 * @param proguardProjectDir String 混淆文件所在的根目录
 * @param proguardSubDir String 混淆文件所在的子目录
 * @return ArrayList<File>
 */
private fun getProguardFileList(
    currentProject: Project,
    sdkProguardFiles: List<String>? = ArrayList<String>(),
    moduleProguardFile: String = "consumer-rules.pro",
    proguardProjectDir: String = "JiankeProguard",
    proguardSubDir: String = "proguard"
): ArrayList<File> {
    val moduleName = currentProject.name
    //取当前项目路径下默认的proguardFile
    val consumerRules = currentProject.file(moduleProguardFile)
    if (!consumerRules.exists()) {
        log(currentProject, LogLevel.ERROR, "当前模块${moduleName}传入的文件${moduleProguardFile} 不存在")
    }
    // 获取项目根目录
    val rootParentPath: String = currentProject.rootDir.parent
    println("rootParentPath:${rootParentPath}")
    // 获取proguard文件所在的目录
    val proguardDir =
        File(rootParentPath + File.separator + proguardProjectDir + File.separator + proguardSubDir)
    if (sdkProguardFiles == null || sdkProguardFiles.isEmpty()) {
        log(currentProject, LogLevel.ERROR, "当前模块${moduleName}没有三方sdk混淆文件！！！")
    }
    val proguardFiles: ArrayList<File> = ArrayList<File>()
    proguardFiles.add(consumerRules)
    for (fileName in sdkProguardFiles!!) {
        val proguardFile = File(proguardDir.absolutePath + File.separator + fileName)
        if (proguardFile.exists()) {
            proguardFiles.add(proguardFile)
        } else {
            log(currentProject, LogLevel.ERROR, "当前模块${moduleName}传入的文件${fileName}不存在")
        }
    }
    return proguardFiles

}



fun getJKProguardFilesWithDefaultProguardFile(currentProject: Project, defaultProguardFile:File?): Array<out Any> {
    return getProguardFilesWithDefaultProguardFile(currentProject, defaultProguardFile).toArray()
}
fun getJKProguardFilesWithDefaultProguardFile(currentProject: Project, defaultProguardFile:File?, sdkProguardFiles: List<String>): Array<out Any> {
    return getProguardFilesWithDefaultProguardFile(currentProject, defaultProguardFile, sdkProguardFiles).toArray()
}
fun getJKProguardFilesWithDefaultProguardFile(currentProject: Project, defaultProguardFile:File?,  sdkProguardFiles: List<String>,moduleProguardFile: String): Array<out Any>{
    return getProguardFilesWithDefaultProguardFile(currentProject, defaultProguardFile, sdkProguardFiles,moduleProguardFile).toArray()
}
/**
 * 配置在proguardFiles处
 * @param currentProject Project
 * @param defaultProguardFile File
 * @param sdkProguardFiles List<String>?
 * @param moduleProguardFile String
 * @param proguardProjectDir String
 * @param proguardSubDir String
 * @return MutableList<File>
 */
fun getProguardFilesWithDefaultProguardFile(
    currentProject: Project,
    defaultProguardFile:File?,
    sdkProguardFiles: List<String>? = ArrayList<String>(),
    moduleProguardFile: String = "consumer-rules.pro",
    proguardProjectDir: String = "JiankeProguard",
    proguardSubDir: String = "proguard" ): ArrayList<File> {
//    log(currentProject,LogLevel.ERROR, "当前模块名称：${defaultProguardFile.absolutePath}")
    //打印所有参数：
    println("----------------------------------------------")
    println("----------------------------------------------")
    println("currentProject:${currentProject}")
    println("defaultProguardFile:${defaultProguardFile}")
    println("sdkProguardFiles:${sdkProguardFiles}")
    println("moduleProguardFile:${moduleProguardFile}")
    println("proguardProjectDir:${proguardProjectDir}")
    println("proguardSubDir:${proguardSubDir}")
    println("----------------------------------------------")
    println("----------------------------------------------")
    val fileList:ArrayList<File> = ArrayList()
    if (defaultProguardFile!=null && defaultProguardFile.exists()){
        fileList.add(defaultProguardFile)
    }else{
        log(currentProject,LogLevel.ERROR, "当前模块${currentProject.name}打包成aar时，defaultProguardFile(proguard-android-optimize.txt)不存在")
    }
    // 合并了sdk和module混淆文件的文件列表
    val moduleAndSdkFiles = getProguardFileList(currentProject, sdkProguardFiles, moduleProguardFile, proguardProjectDir, proguardSubDir)
    fileList.addAll(moduleAndSdkFiles)
    return fileList
}

//ext.getJKProguardFilesWithDefaultProguardFile = {
//    Project currentProject,
//    File defaultProguardFile,
//    List<String> sdkProguardFiles = new ArrayList<>(),
//    String moduleProguardFile = "consumer-rules.pro",
//    String proguardProjectDir = "JiankeProguard",
//    String proguardSubDir = "proguard" ->
//    log(LogLevel.ERROR, "当前模块名称：${defaultProguardFile.absolutePath}")
//    List<File> fileList = new ArrayList<>()
//    if (defaultProguardFile.exists()) {
//        fileList.add(defaultProguardFile)
//    } else {
//        log(LogLevel.ERROR, "当前模块${currentProject.name}打包成aar时，defaultProguardFile(proguard-android-optimize.txt)不存在")
//    }
//
//    Object[] sdkFiles = getProguardFiles(currentProject, proguardProjectDir, proguardSubDir, sdkProguardFiles, moduleProguardFile)
//    fileList.addAll(sdkFiles)
//    return fileList.toArray()
//}
/**
 * 在编译时强烈提醒用户哪些混淆文件不存在
 */
fun log(project: Project, level: LogLevel, logStr: String) {
    val logger = project.logger
    logger.log(level, "\n")
    logger.log(level, "==============start===============")
    logger.log(level, "==================================")
    logger.log(level, logStr)
    logger.log(level, "==================================")
    logger.log(level, "==============end=================")
}
object ProguardConfig{
    // AndroidSDK默认混淆文件，在所有混淆中这句必须有
    const val DefaultProguardFile = "proguard-android-optimize.txt"
    // 默认将内容打包到aar中的混淆配置，子模块统一使用，只有主模块用proguard-project.txt
    const val ConsumerRules = "consumer-rules.pro"
}
/**
 * 三方sdk混淆文件
 */
object ThirdSDK {
    val GIFDRAWBLE = "android-gif-drawable-rules.pro"
    val AROUTER = "arouter-rules.pro"
    val AGORARTC = "agora-rules.pro"
    val ALIVC = "aliyun-player-rules.pro"

    //实名认证
    val ALIRPA = "aliyun-rpa-rules.pro"
    val BUGTAGS = "bugtags-rules.pro"
    val BUTTERKNIFE = "butterknife-rules.pro"
    val CITYPICKERVIEW = "citypickerview-rules.pro"

    // 闪验
    val CMIC = "cmic-rules.pro"
    val EVENTBUS = "eventbus-rules.pro"
    val EXOPLAYER = "exoplayer-core-rules.pro"
    val FASTJSON = "fastjson-rules.pro"
    val GETUI = "getui-rules.pro"
    val GLIDE = "glide-rules.pro"
    val GLIDETRANSFORMATIONS = "glide-transformations-rules.pro"
    val GREENDAO = "greendao-rules.pro"
    val GREENDAOUPGRADEHELPER = "greendaoupgradehelper-rules.pro"
    val GSON = "gson-rules.pro"
    val JSOUP = "jsoup-rules.pro"
    val MATERIALPROGRESSBAR = "material-progressbar-rules.pro"
    val OAID = "oaid-rules.pro"
    val OKHTTP3 = "okhttp3-okio-rules.pro"
    val QINIU = "qiniu-rules.pro"
    val QQ = "qq-rules.pro"
    val RETROFIT = "retrofit-rules.pro"
    val RXJAVA = "rxjava-rxandroid-rules.pro"
    val SENSORANALYTICS = "sensor-analytics-rules.pro"
    val SQLCIPHER = "sqlcipher-rules.pro"
    val SVGPLAYER = "svgplayer-android-rules.pro"
    val UCROP = "ucrop-rules.pro"
    val VHALL = "vhall-rules.pro"
    val WEBRTC = "webrtc-rules.pro"
    val WECHAT = "wechat-rules.pro"
    val ZXING = "zxing-rules.pro"
    val TENCENTX5 = "tencent-x5-rules.pro"
    val BUGLY = "bugly.pro"
}