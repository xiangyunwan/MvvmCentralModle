
package com.example.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;


import com.example.startup.startup.AndroidStartup;
import com.example.startup.startup.Startup;
import com.mobile.centaur.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class Task3 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.d(t+" Task3：学习设计模式");
        SystemClock.sleep(2_000);
        LogUtils.d(t+" Task3：掌握设计模式");
        return null;
    }


    //    执行此任务需要依赖哪些任务
    @Nullable
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }


    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
