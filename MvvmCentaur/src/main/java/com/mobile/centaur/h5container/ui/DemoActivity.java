package com.mobile.centaur.h5container.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.app.mobile.centaur.R;
import com.mobile.centaur.h5container.webview.BridgeWebView;
import com.mobile.centaur.h5container.webview.DefaultHandler;
import com.mobile.centaur.utils.LogUtils;

/**
 * Created by zzz
 */

public class DemoActivity extends Activity {
    private final String TAG = "MainActivity";

    BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ngc_activity_demo);

        webView = (BridgeWebView) findViewById(R.id.webView);

        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            }
        });
        Log.i("khw", "=========== " + getIntent().getStringExtra("url"));
        LogUtils.d("getStringExtra" + getIntent().getStringExtra("url"));
        webView.loadUrl(getIntent().getStringExtra("url"));
        //new NGC_TestApis(webView); //自定义组件
    }
}
