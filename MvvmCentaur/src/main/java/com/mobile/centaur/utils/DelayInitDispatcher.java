package com.mobile.centaur.utils;

/**
 * Created by zzz
 * on 2021/12/25
 */

import android.os.Looper;
import android.os.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 延迟初始化分发器
 */
public class DelayInitDispatcher<T> {

    private Queue<T> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            // 分批执行的好处在于每一个task占用主线程的时间相对
            // 来说很短暂，并且此时CPU是空闲的，这些能更有效地避免UI卡顿
            if(mDelayTasks.size()>0){
                T task = mDelayTasks.poll();
//                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(T task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
