package com.mobile.centaur.h5container.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.app.mobile.centaur.R;
import com.google.gson.Gson;
import com.google.zxing.activity.NGCCaptureActivity;
import com.mobile.centaur.h5container.Bridge_ApiManager;
import com.mobile.centaur.h5container.Bridge_Constants;
import com.mobile.centaur.h5container.control.Bridge_CommonListener;
import com.mobile.centaur.h5container.model.ScanResult;
import com.mobile.centaur.h5container.utils.Birdge_HashMapUtil;
import com.mobile.centaur.h5container.webview.BridgeWebView;
import com.mobile.centaur.h5container.webview.CallBackFunction;
import com.mobile.centaur.h5container.webview.DefaultHandler;
import com.mobile.centaur.utils.ProgressUtil;
import com.mobile.centaur.utils.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Bridge_WebViewActivity extends FragmentActivity implements View.OnClickListener, Bridge_CommonListener {

    private static final String TAG = "NGC_WebViewActivity";

    private ProgressBar mProgressBar;
    protected BridgeWebView webView;
    private TextView tvTitle, tvSubTitle, option_title, option_point;
    private ImageView iv_back, iv_more, iv_close;
    private RelativeLayout rlTitle;
    private Intent intent;
    private static MyReceived myReceived;
    private IntentFilter intentFilter;
    CallBackFunction function;
    boolean showTitleLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_2f62c9));
        setContentView(R.layout.ngc_activity_webview);
        initStrictMode();
        initView();
        initCustom();
        initParam();
        setUserAgent();

    }

    protected void initCustom() {
    }

    ;

    protected void setUserAgent() {
    }

    ;


    public void initParam() {
        webView.setCloseView(iv_close);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setDefaultHandler(new DefaultHandler());
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + "xionganapp/" + getPackageInfo(Bridge_WebViewActivity.this).versionName + " NGC/1.0");

        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webView.getSettings().setGeolocationDatabasePath(dir);
        webView.getSettings().setDomStorageEnabled(true);
        // 开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
        //设置网页拨号功能支撑
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("tel:")){
//                    Intent intent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(url));
//                    startActivity(intent);
//                } else if(url.startsWith("http:") || url.startsWith("https:")) {
//                    view.loadUrl(url);
//                }
//                return true;
//            }});

        webView.loadUrl(getIntent().getStringExtra(Bridge_Constants.HTML_URL));

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Bridge_Constants.TITLE_NAME))) {
            tvTitle.setText(getIntent().getStringExtra(Bridge_Constants.TITLE_NAME));
        }
        if ((getIntent().getBooleanExtra(Bridge_Constants.TITLE_LOADING, false))) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if ((getIntent().getBooleanExtra(Bridge_Constants.GLOBLE_LOADING, false))) {
            ProgressUtil.getInstance().showDialog(this, "", 0);
        }

        if (getIntent().getIntExtra(Bridge_Constants.TITLE_BACKGROUND, 0) != 0) {
            rlTitle.setBackgroundColor(getIntent().getIntExtra(Bridge_Constants.TITLE_BACKGROUND, 0));
        }

        if (getIntent().getIntExtra(Bridge_Constants.TITLE_TEXT_COLOR, 0) != 0) {
            tvTitle.setTextColor(getIntent().getIntExtra(Bridge_Constants.TITLE_TEXT_COLOR, 0));
        }

        if (getIntent().getIntExtra(Bridge_Constants.TITLE_ICON_BACK, 0) != 0) {
            iv_back.setBackground(getResources().getDrawable(getIntent().getIntExtra(Bridge_Constants.TITLE_ICON_BACK, 0)));
        }

        if (getIntent().getIntExtra(Bridge_Constants.isCanSupportZoom, 0) != 0) {
            //设置true,才能让Webivew支持<meta>标签的viewport属性
            webView.getSettings().setUseWideViewPort(true);
            //设置可以支持缩放
            webView.getSettings().setSupportZoom(true);
            //设置出现缩放工具
            webView.getSettings().setBuiltInZoomControls(true);
            //设定缩放控件隐藏
            webView.getSettings().setDisplayZoomControls(false);
//            webView.setInitialScale(100);

        }

    }

    public void initStrictMode() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }


    protected void initView() {
        mProgressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.webview);
        tvTitle = findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title);
        tvSubTitle = findViewById(R.id.subtitle);
        iv_back = findViewById(R.id.iv_back);
        iv_more = findViewById(R.id.iv_more);
        iv_close = findViewById(R.id.iv_close);
        option_title = findViewById(R.id.option_title);
        option_point = findViewById(R.id.option_point);

        option_title.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        iv_more.setOnClickListener(this);

        Bridge_ApiManager.init(webView, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Birdge_HashMapUtil.release();
        ProgressUtil.getInstance().dismissDialog();
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_close) {
            finish();

        } else if (v.getId() == R.id.iv_back) {

            if (webView.canGoBack()) {
                webView.goBack();

            } else {
                finish();
            }

        } else if (v.getId() == R.id.tv_title) {

            webView.callHandler("titleClick", "", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                }

            });
        } else if (v.getId() == R.id.option_title) {
            webView.callHandler("optionMenu", "", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                }

            });
        }
    }


    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {
                Log.i("khw", "show: onProgressChanged");
                if (!showTitleLoading) {
                    mProgressBar.setVisibility(GONE);
                }
                ProgressUtil.getInstance().dismissDialog();

            } else {
                if ((getIntent().getBooleanExtra(Bridge_Constants.TITLE_LOADING, false))) {
                    if (mProgressBar.getVisibility() == GONE)
                        mProgressBar.setVisibility(VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (TextUtils.isEmpty(getIntent().getStringExtra(Bridge_Constants.TITLE_NAME))) {
                tvTitle.setText(title);
            }
        }
    }

    @Override
    public void xappScanQRCode(CallBackFunction function) {
        this.function = function;

        // 二维码扫码
        Intent intent = new Intent(this, NGCCaptureActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void setTitle(String title) {
        Log.i(TAG, "title = " + title);
        tvTitle.setText(title);
    }

    @Override
    public void setSubTitle(String subTitle) {
        Log.i(TAG, "subtitle = " + subTitle);
        if (!"".equals(subTitle)) {
            tvSubTitle.setVisibility(View.VISIBLE);
            tvSubTitle.setText(subTitle);
        } else {
            tvSubTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void setOptionMenu(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            option_title.setText(jsonObject.getString("title"));
            if ("-1".equals(jsonObject.getString("redDot")) || "0".equals(jsonObject.getString("redDot"))) {
                option_point.setVisibility(View.GONE);
            } else {
                option_point.setText(jsonObject.getString("redDot"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showOptionMenu(Boolean isShow) {
        if (isShow) {
            iv_more.setVisibility(View.VISIBLE);
        } else {
            iv_more.setVisibility(View.GONE);
        }
    }

    @Override
    public void addNotifyListener(String notifyName, CallBackFunction function) {
        if (myReceived != null) {
            function.onCallBack("repeat Notify Listener fail: " + notifyName);
            return;
        }
        myReceived = new MyReceived();
        intentFilter = new IntentFilter();
        intentFilter.addAction(notifyName);
        //动态注册广播
        registerReceiver(myReceived, intentFilter);
        function.onCallBack("add Notify Listener : " + notifyName);
    }

    @Override
    public void removeNotifyListener(String notifyName, CallBackFunction function) {
        //注销动态广播
        if (myReceived == null) {
            function.onCallBack("no Notify Listener remove: " + notifyName);
            return;
        }
        unregisterReceiver(myReceived);
        myReceived = null;
        function.onCallBack("remove Notify Listener success: " + notifyName);
    }

    //    @Override
    public void postNotification(String notifyName, String content, CallBackFunction function) {
        this.function = function;
        intent = new Intent();
        //设置广播的名字（设置Action）
        intent.setAction(notifyName);
        //携带数据
        intent.putExtra("data", content);
        //发送广播（无序广播）
        sendBroadcast(intent);
    }

    @Override
    public void showTitleLoading(boolean show) {
        Log.i("khw", "show: " + show);
        showTitleLoading = show;
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        //扫描结果回调
        if (requestCode == 1000 && resultCode == 0xA1) {

            Bundle bundle = intent.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            int error = 0;
            //将扫描出的信息显示出来
            Log.i("khw", "scanResult: " + scanResult);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("scanResult", scanResult);
//            jsonObject.put("error", error);
            ScanResult sr = new ScanResult();
            sr.error = 0;
            sr.scanRsult = scanResult;

            function.onCallBack(new Gson().toJson(sr));

            //Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show();


        }
    }

    class MyReceived extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            Log.i("MyReceived", "MyReceived接收了" + data);
            function.onCallBack(data);
        }
    }

}
