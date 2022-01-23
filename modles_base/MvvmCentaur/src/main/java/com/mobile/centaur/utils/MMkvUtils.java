package com.mobile.centaur.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zzz on 2021/10/11.
 *
 */
public class MMkvUtils {
    private static volatile MMkvUtils instance;
    private Context context;
    //用来维护所有的sp文件转换为mmkv的状态。
    private static final String SP_FILE_LIST_NAMES="spFileListNames";

    private MMkvUtils(Context context){
        this.context=context.getApplicationContext();
    }

    public static MMkvUtils getInstance(Context context){
        if (instance==null){
            synchronized(MMkvUtils.class){
                if (instance==null){
                    instance=new MMkvUtils(context);
                }
            }
        }
        return instance;
    }


    public MMkvUtils initMMkv(String appName){
        String dir = context.getFilesDir().getAbsolutePath() +"/mmkv"+context.getPackageName();
        String rootDir = MMKV.initialize(context, dir, new MMKV.LibLoader() {
            @Override
            public void loadLibrary(String libName) {
                ReLinker.loadLibrary(context, libName);
            }
        }, MMKVLogLevel.LevelInfo);
        return this;
    }


    /**
     * 转移旧版本sp中的数据到 mmkv中
     * @param spName
     */
    public MMKV importSpValue(String spName){
        boolean hasImport=MMKV.mmkvWithID(SP_FILE_LIST_NAMES).getBoolean(spName,false);
        if (!hasImport){
            SharedPreferences sharedPreferences=context.getSharedPreferences(spName, MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            MMKV mmkv=MMKV.mmkvWithID(spName);
            mmkv.importFromSharedPreferences(sharedPreferences);
            editor.clear().commit();

            MMKV.mmkvWithID(SP_FILE_LIST_NAMES).putBoolean(spName,true);
        }
        return MMKV.mmkvWithID(spName);
    }


    /**
     * 之前没有存过相关sp文件内容-根据表明判断
     * @param spName
     * @param keyName
     * @param defValue
     * @return
     */
    private String getString(String spName,String keyName,String defValue){
        return MMKV.mmkvWithID(spName).getString(keyName,defValue);
    }

    /**
     * 兼容旧版sp，之前可能存过相关数据的情况下调用
     * 多一个转移导入的过程
     * @param spName
     * @param keyName
     * @param defValue
     * @return
     */
    private String getOldString(String spName,String keyName,String defValue){
        importSpValue(spName);

        return getString(spName,keyName,defValue);
    }


    private boolean getBoolean(String spName,String keyName,Boolean defValue){
        return MMKV.mmkvWithID(spName).getBoolean(keyName,defValue);
    }

    private boolean getOldBoolean(String spName,String keyName,Boolean defValue){
        importSpValue(spName);
        return getBoolean(spName,keyName,defValue);
    }


    private float getFloat(String spName,String keyName,float defValue){
        return MMKV.mmkvWithID(spName).getFloat(keyName,defValue);
    }

    private float getOldFloat(String spName,String keyName,float defValue){
        importSpValue(spName);
        return getFloat(spName,keyName,defValue);
    }


    private int getInt(String spName,String keyName,int defValue){
        return MMKV.mmkvWithID(spName).getInt(keyName,defValue);
    }

    private int getOldInt(String spName,String keyName,int defValue){
        importSpValue(spName);
        return getInt(spName,keyName,defValue);
    }


    private long getLong(String spName,String keyName,long defValue){
        return MMKV.mmkvWithID(spName).getLong(keyName,defValue);
    }

    private long getOldLong(String spName,String keyName,long defValue){
        importSpValue(spName);
        return getLong(spName,keyName,defValue);
    }

    private byte[]  getBytes(String spName,String keyName,@Nullable byte[] defValue){
        return MMKV.mmkvWithID(spName).getBytes(keyName,defValue);
    }

    private byte[]  getOldBytes(String spName,String keyName,@Nullable byte[] defValue){
        importSpValue(spName);
        return getBytes(spName,keyName,defValue);
    }

    private Set<String> getStringSets(String spName, String keyName, Set<String> defValue){
        return MMKV.mmkvWithID(spName).getStringSet(keyName,defValue);
    }

    private Set<String> getOldStringSets(String spName,String keyName,Set<String> defValue){
        importSpValue(spName);
        return getStringSets(spName,keyName,defValue);
    }

    public void saveObj(String spName,String key, Object obj) {
        if (obj!=null){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oout = new ObjectOutputStream(out);
                oout.writeObject(obj);
                String value = new String(Base64.encode(out.toByteArray(),Base64.DEFAULT));
                MMKV.mmkvWithID(spName).putString(key,value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            MMKV.mmkvWithID(spName).remove(key);
        }

    }

    public <T> T getObj(String spName,String key){
        String value = MMKV.mmkvWithID(spName).getString(key, null);
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

    /**
     * TEST
     * @param spName
     */
    public void importSharedPreferences(String spName) {
        SharedPreferences preferences = context.getSharedPreferences(spName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("bool", true);
        editor.putInt("int", Integer.MIN_VALUE);
        editor.putLong("long", Long.MAX_VALUE);
        editor.putFloat("float", -3.14f);
        editor.putString("string", "hello, imported");
        HashSet<String> set = new HashSet<String>();
        set.add("W");
        set.add("e");
        set.add("C");
        set.add("h");
        set.add("a");
        set.add("t");
        editor.putStringSet("string-set", set);
        editor.commit();

        MMKV kv = MMKV.mmkvWithID(spName);
        kv.importFromSharedPreferences(preferences);
        editor.clear().commit();

        Log.i("MMKV", "allKeys: " + Arrays.toString(kv.allKeys()));

        Log.i("MMKV", "bool: " + kv.getBoolean("bool", false));
        Log.i("MMKV", "int: " + kv.getInt("int", 0));
        Log.i("MMKV", "long: " + kv.getLong("long", 0));
        Log.i("MMKV", "float: " + kv.getFloat("float", 0));
        Log.i("MMKV", "double: " + kv.decodeDouble("double"));
        Log.i("MMKV", "string: " + kv.getString("string", null));
        Log.i("MMKV", "string-set: " + kv.getStringSet("string-set", null));
        Log.i("MMKV", "linked-string-set: " + kv.decodeStringSet("string-set", null, LinkedHashSet.class));

        // test @Nullable
        kv.putStringSet("string-set", null);
        kv.putFloat("String-set",0f);

        Log.i("MMKV", "after set null, string-set: " + kv.getStringSet("string-set", null));
    }

    /**
     * 退出mmkv
     */
    public void quitMMkv(){
        MMKV.onExit();
    }




}
