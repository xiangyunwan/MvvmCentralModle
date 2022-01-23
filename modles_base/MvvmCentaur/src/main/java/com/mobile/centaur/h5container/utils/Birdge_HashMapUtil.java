package com.mobile.centaur.h5container.utils;

import java.util.HashMap;

/**
 * Created by zzz
 */
public class Birdge_HashMapUtil {
    private static HashMap<String, Object> map = new HashMap<>();

    public static void add(String str, Object obj) {
        map.put(str, obj);
    }

    public static Object get(String str) {
        return map.get(str);
    }

    public static void release() {
        map.clear();
    }
}
