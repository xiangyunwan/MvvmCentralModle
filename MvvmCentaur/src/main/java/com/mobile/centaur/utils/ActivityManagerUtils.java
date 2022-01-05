package com.mobile.centaur.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public class ActivityManagerUtils {
    private volatile static ActivityManagerUtils instance;

    private Stack<Activity> mActivityStack;
    private ActivityManagerUtils(){
        if (mActivityStack == null){
            mActivityStack = new Stack<Activity>();
        }
    }
    public static ActivityManagerUtils getInstance(){
        if (instance == null){
            synchronized (ActivityManagerUtils.class){
                if (instance == null){
                    instance = new ActivityManagerUtils();
                }
            }
        }
        return instance;
    }

    public int stackSize() {
        return mActivityStack.size();
    }
    /**
     * 获取当前activity
     *
     * @return 当前activity
     */
    public Activity getCurrentActivity() {
        Activity activity = null;
        try {
            activity = mActivityStack.lastElement();
        } catch (Exception e) {
            return null;
        }
        return activity;
    }
    /**
     * 出栈
     * 暂时没有用到 2014-06-05
     */
    public void popActivity() {
        Activity activity = mActivityStack.lastElement();
        if (null != activity) {
            LogUtils.d("popActivity-->" + activity.getClass().getSimpleName());
            activity.finish();
            mActivityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * 出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (null != activity) {
            LogUtils.d("popActivity-->" + activity.getClass().getSimpleName());
            mActivityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * activity入栈
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
        LogUtils.d("pushActivity-->" + activity.getClass().getSimpleName());
    }
    /**
     * 所有activity出栈
     */
    public void popAllActivities() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = getCurrentActivity();
            if (null == activity) {
                break;
            }
            activity.finish();
            popActivity(activity);
        }
    }
    /**
     * 将上层Activity出栈，直到指定的Activity
     */
    public void popUntilSpecialActivity(Class<?> cls) {
        while (!mActivityStack.isEmpty()) {
            Activity activity = getCurrentActivity();
            if (null == activity || activity.getClass().equals(cls)) {
                break;
            }
            activity.finish();
            popActivity(activity);
        }
    }

    /**
     * NOTO:该方法谨慎使用，类名改变导致调用处需要修改
     * 跳转到某个指定的页面
     * @param className 某个类的简写
     */
    public void popUntilSpecialActivity(String className){
        while (!mActivityStack.isEmpty()) {
            Activity activity = getCurrentActivity();
            if (null == activity || activity.getClass().getSimpleName().equalsIgnoreCase(className)) {
                break;
            }
            activity.finish();
            popActivity(activity);
        }
    }

    /**
     * 指定的activity出栈
     */
    public void popSpecialActivity(Class<?> cls) {
        try {
            Iterator<Activity> iterator = mActivityStack.iterator();
            Activity activity = null;
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (activity.getClass().equals(cls)) {
                    activity.finish();
                    iterator.remove();
                    activity = null;
                }
            }
        } catch (Exception e) {

        }
    }
    /**
     * 遍历目前栈中的activity
     */
    public void peekActivity() {
        for (Activity activity : mActivityStack) {
            if (null == activity) {
                break;
            }
        }
    }

    public boolean contains(Class<? extends Activity> clazz) {
        try {
            Iterator<Activity> iterator = mActivityStack.iterator();
            Activity activity = null;
            while (iterator.hasNext()) {
                activity = iterator.next();
                if (activity.getClass().equals(clazz)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 调用此方法前请务必确认activity在堆栈中，否则将死循环
     * @param cls
     * @return
     */
    public Activity getSpecialActivity(Class<?> cls) {
        for (Activity activity:mActivityStack) {
            if (cls == activity.getClass()) {
                return activity;
            }
        }
        return null;
    }

}
