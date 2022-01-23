package com.mobile.centaur.h5container.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    WebViewTitleControl onPageFinishedLis;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
        this.onPageFinishedLis = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url == null) return false;
//        Log.i("khw","=====>>> +"+url);
//
//        try {
//            if (url.startsWith("http:") || url.startsWith("https:"))
//            {
//                url = URLDecoder.decode(url, "UTF-8");
//            }
//            else
//            {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                webView.getContext().startActivity(intent);
//                return true;
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
//            return true;
//        }

        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            webView.getContext().startActivity(intent);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) {
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    // 增加shouldOverrideUrlLoading在api》=24时
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String url = request.getUrl().toString();
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //  Log.i("khw","---onPageFinished-- "+webView.canGoBack());
        onPageFinishedLis.onPageFinished(webView.canGoBack());

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }
}