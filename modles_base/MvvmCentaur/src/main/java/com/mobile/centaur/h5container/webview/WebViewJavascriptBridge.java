package com.mobile.centaur.h5container.webview;


public interface WebViewJavascriptBridge {

    void send(String data);

    void send(String data, CallBackFunction responseCallback);


}
