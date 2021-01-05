package com.mobile.centaur.network;


import android.app.Activity;

import com.mobile.centaur.EventBean.ExceptionLogoutEventBean;
import com.mobile.centaur.base.BaseConstants;
import com.mobile.centaur.utils.DialogUtils;
import com.mobile.centaur.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

/**
 * created by zzz on 2020/9/20
 *
 * @class
 */
public class RxSubscriber<T> extends ResourceSubscriber<T> {

    private static final String TAG = "RxSubscriber";


    private Activity mActivity;

    public RxSubscriber(Activity activity) {
        this.mActivity = activity;
    }

    public RxSubscriber() {

    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().post(new ProgressEventBusBean(true));
        if (mActivity != null) {
            DialogUtils.getInstance().showProgressDialog(mActivity);
        }
    }


    @Override
    public void onNext(T t) {
        if (t == null) {

//            onError(new Throwable("page数据为null"));
            LogUtils.e(TAG, ("page数据为null"));
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t != null && t instanceof ApiException) {
            if (((ApiException) t).getErrCode() == BaseConstants.logoutCode) {
                EventBus.getDefault().post(new ExceptionLogoutEventBean(((ApiException) t).getErrCode()));
            }

            LogUtils.e(TAG, "错误信息为 " + "   code:" + ((ApiException) t).getErrCode() + "  message:" + t.getMessage());
        }
        if (mActivity != null) {
            DialogUtils.dismissProgressDiaglog();
        }
    }

    @Override
    public void onComplete() {
        LogUtils.i(TAG, "成功了");
        if (mActivity != null) {
            DialogUtils.dismissProgressDiaglog();
        }
    }
}
