@file:JvmName("FixedVersion")
@file:JvmMultifileClass
import org.gradle.api.Project

/**
 * 固定版本
 * @note: 按理说版本统一之后应该不需要这个方法了，后面经过测试后可以去掉
 * @param appProject Project
 */
fun fixedVersion(appProject: Project) {
    appProject.configurations.all {
        resolutionStrategy.eachDependency {
            val details = this
            val requested = details.requested
            if (requested.group == "com.android.support") {
                if (!requested.name.startsWith("multidex")) {
                    details.useVersion("28.0.0")
                }
            }
            if (requested.group == "com.qiniu" && requested.name == "qiniu-android-sdk") {
                details.useVersion("7.3.15")
            }
            if (requested.group == "com.github.bumptech.glide" && requested.name == "glide") {
                details.useVersion(Libraries.Versions.glide)
            }
            if (requested.group == "com.alibaba" && requested.name == "fastjson") {
                details.useVersion(Libraries.Versions.fastjson)
            }
            if (requested.group == "com.contrarywind" && requested.name == "Android-PickerView") {
                details.useVersion(Libraries.Versions.AndroidPickerView)
            }
            if (requested.group == "androidx.constraintlayout" && requested.name == "constraintlayout") {
                details.useVersion(Libraries.Versions.constraintlayout)
            }
            // 笔声数据库加密用的3.5.2
            if (requested.group == "net.zetetic" && requested.name == "android-database-sqlcipher") {
                details.useVersion(Libraries.Versions.androidDatabaseSqlcipher)
            }
            //固定exoplayer版本，在2.13.2版本中添加了playing方法，导致api不兼容
            if (requested.group == "com.google.android.exoplayer") {
                if (requested.name == "exoplayer-core" || requested.name == "exoplayer-dash" ||
                    requested.name == "exoplayer-hls" || requested.name == "exoplayer-smoothstreaming"
                ) {
                    details.useVersion("${Libraries.Versions.exoplayerCore}")
                }
            }
        }
    }
}