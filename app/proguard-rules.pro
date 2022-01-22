# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5                       # 代码混淆的压缩比例，值介于0-7，默认5
-verbose                                    # 混淆时记录日志
-dontoptimize                               # 不优化输入的类文件
-dontshrink                                 # 关闭压缩
-dontpreverify                              # 关闭预校验(作用于Java平台，Android不需要，去掉可加快混淆)
#-dontobfuscate                              # 关闭混淆 必须打开不然就废了
-ignorewarnings                             # 忽略警告
-dontwarn com.squareup.okhttp.**            # 指定类不输出警告信息
-dontusemixedcaseclassnames                 # 混淆后类型都为小写
-dontskipnonpubliclibraryclasses            # 不跳过非公共的库的类
#-printmapping mapping.txt                   # 生成原类名与混淆后类名的映射文件mapping.txt
-printmapping ./mapping/mapping.txt # 输出mapping.txt文件
-printseeds ./mapping/seeds.txt # 输出seeds.txt文件
-printusage ./mapping/usage.txt # 输出usage.txt文件
#-useuniqueclassmembernames                  # 把混淆类中的方法名也混淆
-allowaccessmodification                    # 优化时允许访问并修改有修饰符的类及类的成员
-renamesourcefileattribute SourceFile       # 将源码中有意义的类名转换成SourceFile，用于混淆具体崩溃代码
-keepattributes SourceFile,LineNumberTable  # 保留行号
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod # 避免混淆注解、内部类、泛型、匿名类
-optimizations !code/simplification/cast,!field/ ,!class/merging/   # 指定混淆时采用的算法
-printconfiguration ./build/outputs/mapping/full-config.txt # 输出所有规则叠加后的混淆规则
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#kotlin-reflect
-keep class kotlin.** { *; }
-keep class org.jetbrains.** { *; }
-keep class com.httprequest.** {*;}

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
#-assumenoexternalsideeffects class java.lang.StringBuilder {
#    public java.lang.StringBuilder();
#    public java.lang.StringBuilder(int);
#    public java.lang.StringBuilder(java.lang.String);
#    public java.lang.StringBuilder append(java.lang.Object);
#    public java.lang.StringBuilder append(java.lang.String);
#    public java.lang.StringBuilder append(java.lang.StringBuffer);
#    public java.lang.StringBuilder append(char[]);
#    public java.lang.StringBuilder append(char[], int, int);
#    public java.lang.StringBuilder append(boolean);
#    public java.lang.StringBuilder append(char);
#    public java.lang.StringBuilder append(int);
#    public java.lang.StringBuilder append(long);
#    public java.lang.StringBuilder append(float);
#    public java.lang.StringBuilder append(double);
#    public java.lang.String toString();
#}
#-assumenoexternalreturnvalues public final class java.lang.StringBuilder {
#    public java.lang.StringBuilder append(java.lang.Object);
#    public java.lang.StringBuilder append(java.lang.String);
#    public java.lang.StringBuilder append(java.lang.StringBuffer);
#    public java.lang.StringBuilder append(char[]);
#    public java.lang.StringBuilder append(char[], int, int);
#    public java.lang.StringBuilder append(boolean);
#    public java.lang.StringBuilder append(char);
#    public java.lang.StringBuilder append(int);
#    public java.lang.StringBuilder append(long);
#    public java.lang.StringBuilder append(float);
#    public java.lang.StringBuilder append(double);
#}


-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
