package com.mobile.centaur.h5container.control;

import com.mobile.centaur.h5container.webview.CallBackFunction;

public interface Bridge_CommonListener {

    void xappScanQRCode(CallBackFunction function);

    void setTitle(String title);

    void setSubTitle(String subTitle);

    void setOptionMenu(String data);

    void showOptionMenu(Boolean isShow);

    void addNotifyListener(String notifyName, CallBackFunction function);

    void removeNotifyListener(String notifyName, CallBackFunction function);

    void postNotification(String notifyName, String content, CallBackFunction function);

    void showTitleLoading(boolean show);
}
