package com.mobile.centaur.h5container.control;

import android.content.Context;

import com.mobile.centaur.h5container.webview.CallBackFunction;

/**
 * Created by khw on 2018/6/28.
 */
public interface Bridge_DIDIPayListener {
    void onDiDiPay(String out_trade_id, CallBackFunction function, Context mContext);
}
