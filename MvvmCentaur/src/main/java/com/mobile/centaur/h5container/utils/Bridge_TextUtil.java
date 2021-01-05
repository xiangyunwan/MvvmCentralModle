package com.mobile.centaur.h5container.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zzz
 */
public class Bridge_TextUtil {

    public static String getString(JSONObject jsonObject, String key) {
        return jsonObject.getString(key) != null ? jsonObject.getString(key) : "";

    }

    public static int getInteger(JSONObject jsonObject, String key) {
        return jsonObject.getInteger(key) != null ? jsonObject.getInteger(key) : 0;

    }

    public static boolean getBoolean(JSONObject jsonObject, String key) {
        return jsonObject.getBoolean(key) != null ? jsonObject.getBoolean(key) : false;
    }

    public static boolean getBoolean(JSONObject jsonObject, String key, boolean defaultValue) {
        return jsonObject.getBoolean(key) != null ? jsonObject.getBoolean(key) : defaultValue;
    }
}
