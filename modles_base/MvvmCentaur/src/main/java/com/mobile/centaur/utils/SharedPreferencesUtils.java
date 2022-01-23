package com.mobile.centaur.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Descirption: 操纵 sp 文件
 * @Author zzz
 * @Date 2022/1/5
 **/
public class SharedPreferencesUtils {


    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    public SharedPreferencesUtils(Context context, String name) {
        settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    /**
     *  加载当前键值
     * @param key
     * @return
     */
    protected String loadKey(String key) {
        return settings.getString(key, null);
    }

    /**
     * 默认所有的key和value都是String类型
     * @param key
     * @param value
     */
    protected void saveKey(String key, String value) {

        editor.putString(key, value);
        editor.commit();
    }

    public void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }
    public void clear() {
        editor.clear();
        editor.commit();
    }
    public boolean loadBooleanKey(String key, boolean defaultValue) {
        String v = loadKey(key);
        boolean bv;
        if (v == null) {
            bv = defaultValue;
        } else {
            bv = v.equals("TRUE");
        }
        return bv;
    }

    public void saveBooleanKey(String key, boolean value) {
        String v;
        if (value) {
            v = "TRUE";
        } else {
            v = "FALSE";
        }
        saveKey(key, v);
    }

    public int loadIntKey(String key, int defaultValue) {
        String v = loadKey(key);
        int iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Integer.parseInt(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveIntKey(String key, int value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public long loadLongKey(String key, long defaultValue) {
        String v = loadKey(key);
        long iv;
        if (v == null) {
            iv = defaultValue;
        } else {
            try {
                iv = Long.parseLong(v);
            } catch (Exception e) {
                iv = defaultValue;
            }
        }
        return iv;
    }

    public void saveLongKey(String key, long value) {
        String v = String.valueOf(value);
        saveKey(key, v);
    }

    public String loadStringKey(String key, String defaultValue) {
        String v = loadKey(key);
        if (v == null) {
            v = defaultValue;
        }
        return v;
    }

    public void saveStringKey(String key, String value) {
        saveKey(key, value);
    }


    public void saveObj(String key, Object obj) {
        if (obj!=null){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oout = new ObjectOutputStream(out);
                oout.writeObject(obj);
                String value = new String(Base64.encode(out.toByteArray(),Base64.DEFAULT));
                editor.putString(key,value);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            editor.remove(key);
            editor.commit();
        }

    }
    public <T> T getObj(String key){
        String value = settings.getString(key, null);
        if(value != null){
            byte[] valueBytes = Base64.decode(value,Base64.DEFAULT);
            ByteArrayInputStream bin = new ByteArrayInputStream(valueBytes);
            try {
                ObjectInputStream oin = new ObjectInputStream(bin);
                return (T)oin.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}