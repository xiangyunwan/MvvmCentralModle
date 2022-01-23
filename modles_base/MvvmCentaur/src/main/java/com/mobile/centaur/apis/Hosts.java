package com.mobile.centaur.apis;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/6
 **/
public class Hosts {
    private static final Hosts[] hosts = new Hosts[4];

    private static final String DOMAIN="xiangyun.com";
    public static Hosts get(int env) {
        if (env < 1 || env > 4) {
            env = 3;
        }
        if (hosts[env - 1] != null) {
            return hosts[env - 1];
        }
        hosts[env - 1] = new Hosts(env);
        return hosts[env - 1];
    }

    private Hosts(int env) {
        if (env < 1 || env > 4) {
            env = 3;
        }
        switch (env) {
            case NetConfig.ENVIRONMENT_DEBUG:
                // 开发环境
                MAIN_USER = "https://user-api.d." + DOMAIN + "/";
                BJ_H5 = "https://user-h5-dev." + DOMAIN + "/";
                ALIYUN = "https://data.aliyun.com/";
                break;
            case NetConfig.ENVIRONMENT_TEST:
                // 测试环境
                MAIN_USER = "https://test." + DOMAIN + "/";
                BJ_H5 = "https://testh5." + DOMAIN + "/";
                ALIYUN = "https://data.aliyun.com/";
                break;
            case NetConfig.ENVIRONMENT_PRE_RELEASE:
                // 预发布环境
                MAIN_USER = "https://user-pre." + DOMAIN + "/";
                BJ_H5 = "https://user-h5-pre." + DOMAIN + "/";
                ALIYUN = "https://data.aliyun.com/";
                break;
            default:
                // 生产环境
                MAIN_USER = "https://user-api." + DOMAIN + "/";
                BJ_H5 = "https://user-h5." + DOMAIN + "/";
                ALIYUN = "https://data.aliyun.com/";
                break;
        }
    }

    public final String MAIN_USER;
    // 部分静态h5页面
    public final String BJ_H5;
    // 三方域名
    public final String ALIYUN;

    public static String appendPath(@NonNull String host, @NonNull String path) {
        if (TextUtils.isEmpty(host)) {
            throw new IllegalArgumentException("host must not be null");
        }
        if ((host.endsWith("/") && !path.startsWith("/")) || (!host.endsWith("/") && path.startsWith("/"))) {
            return host + path;
        } else if (!host.endsWith("/") && !path.startsWith("/")) {
            return host + "/" + path;
        } else {
            return host.substring(0, host.length() - 1) + path;
        }
    }
}
