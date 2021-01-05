package com.mobile.centaur.apis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.mobile.centaur.R;
import com.google.gson.Gson;
import com.mobile.centaur.h5container.Bridge_Constants;
import com.mobile.centaur.h5container.control.Bridge_CommonListener;
import com.mobile.centaur.h5container.control.Bridge_DIDIPayListener;
import com.mobile.centaur.h5container.model.ActionSheetInfo;
import com.mobile.centaur.h5container.model.SdkInfo;
import com.mobile.centaur.h5container.ui.Bridge_WebViewActivity;
import com.mobile.centaur.h5container.utils.Birdge_HashMapUtil;
import com.mobile.centaur.h5container.utils.Bridge_TextUtil;
import com.mobile.centaur.h5container.webview.BridgeHandler;
import com.mobile.centaur.h5container.webview.BridgeWebView;
import com.mobile.centaur.h5container.webview.CallBackFunction;
import com.mobile.centaur.utils.Base64Utils;
import com.mobile.centaur.utils.DeviceInfoUtils;
import com.mobile.centaur.utils.ImageUtils;
import com.mobile.centaur.utils.LogUtils;
import com.mobile.centaur.utils.PermissionsUtil;
import com.mobile.centaur.utils.ProgressUtil;
import com.mobile.centaur.views.ImageItem;
import com.mobile.centaur.widget.ActionSheet;
import com.pizidea.imagepicker.AndroidImagePicker;

import java.io.File;
import java.util.List;


public class Bridge_BaseComponentApis {

    private static final String TAG = "NGC_BaseComponentApis";

    public static BridgeWebView webView;
    public static FragmentActivity activity;
    private static Bridge_CommonListener commonListener;

    public Bridge_BaseComponentApis(BridgeWebView web, FragmentActivity activity, Bridge_CommonListener commonListener) {
        webView = web;
        this.activity = activity;
        this.commonListener = commonListener;
    }

    /**
     * 获取设备信息
     */
    public static void getSystemInfo() {

        webView.registerHandler("getSystemInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "getSystemInfo , data from web = " + data);
                if (PermissionsUtil.checkPhonePerssion(activity)) {
                    function.onCallBack(JSONObject.toJSONString(DeviceInfoUtils.getDeviceInfo(activity)));
                }
            }
        });
    }

    /**
     * 获取定位信息
     */
    public static void getCurrentLocation() {

        webView.registerHandler("getCurrentLocation", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = getCurrentLocation, data from web = " + data);
                if (!activity.isFinishing() && PermissionsUtil.checkLocPerssion(activity)) {
//                    LocationUtil.getInstance().initLocation(activity, function);
                }
            }

        });
    }

    /**
     * 点击导航栏标题触发回调
     */
    public static void titleClick() {

        webView.registerHandler("titleClick", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = titleClick, data from web = " + data);

                function.onCallBack("标题");
            }

        });
    }

    /**
     * 点击导航栏副标题触发回调
     */
    public static void subtitleClick() {

        webView.registerHandler("subtitleClick", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = subtitleClick, data from web = " + data);
                commonListener.setSubTitle(data);
                function.onCallBack("副标题");
            }

        });
    }

    /**
     * 当调用setOptionMenu接口，自定义了导航栏右上角按钮以后，点击按钮时触发该事件
     */
    public static void setOptionMenuClick() {

        webView.registerHandler("setOptionMenu", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = optionMenu, data from web = " + data);
                commonListener.setOptionMenu(data);
                function.onCallBack("自定义导航栏右上角按钮");
            }

        });
    }

    /**
     * 当调用setOptionMenu接口，自定义了导航栏右上角按钮以后，点击按钮时触发该事件
     */
    public static void optionMenuClick() {


        webView.registerHandler("optionMenu", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = optionMenu, data from web = " + data);

                function.onCallBack("自定义导航栏右上角按钮");
            }

        });
    }

    /**
     * 设置页面的标题栏，包括主标题，副标题以及标题菜单项
     * 注意：由于苹果的ATS限制，image URL必须为https链接或base64，http链接会被忽略
     */
    public static void setTitle() {

        webView.registerHandler("setTitle", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                commonListener.setTitle(Bridge_TextUtil.getString(jsonObject, "title"));
                function.onCallBack("标题");

            }

        });
    }

    /**
     * 设置页面的标题栏，包括主标题，副标题以及标题菜单项
     * 注意：由于苹果的ATS限制，image URL必须为https链接或base64，http链接会被忽略
     */
    public static void setSubTitle() {

        webView.registerHandler("setSubTitle", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = setSubTitle, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                commonListener.setSubTitle(jsonObject.getString("subtitle") != null ? jsonObject.getString("subtitle") : "");
                function.onCallBack("副标题");
            }

        });
    }

    /**
     * 设置标题栏右边的按钮属性。 该接口仅负责设置，需要额外调用showOptionMenu保证该按钮的显示。
     */
    public static void setOptionMenu() {

        webView.registerHandler("setOptionMenu", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = showOptionMenu, data from web = " + data);
                commonListener.showOptionMenu(true);
                function.onCallBack("设置标题栏右边按钮");
            }

        });
    }

    /**
     * 显示标题栏右边的按钮属性
     */
    public static void showOptionMenu() {

        webView.registerHandler("showOptionMenu", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = showOptionMenu, data from web = " + data);
                commonListener.showOptionMenu(true);
                function.onCallBack("显示了标题栏右边按钮");
            }

        });
    }

    /**
     * 隐藏标题栏右边的按钮属性。
     */
    public static void hideOptionMenu() {

        webView.registerHandler("hideOptionMenu", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = showOptionMenu, data from web = " + data);
                commonListener.showOptionMenu(false);
                function.onCallBack("");
            }

        });
    }

    /**
     * 显示Loading
     */
    public static void showLoading() {

        webView.registerHandler("showLoading", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                Boolean autoHide = Bridge_TextUtil.getBoolean(jsonObject, "autoHide", true);
                Boolean cancelable = Bridge_TextUtil.getBoolean(jsonObject, "cancelable", true);
                String title = Bridge_TextUtil.getString(jsonObject, "title");
                int delay = Bridge_TextUtil.getInteger(jsonObject, "delay");

                Log.i(TAG, "autoHide: " + autoHide + " canceable: " + cancelable);
                ProgressUtil.getInstance().showDialog(activity, title, delay);
                function.onCallBack("显示Loading：");
            }

        });
    }

    /**
     * 隐藏Loading
     */
    public static void hideLoading() {

        webView.registerHandler("hideLoading", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                ProgressUtil.getInstance().dismissDialog();
                function.onCallBack("隐藏Loading：");
            }

        });
    }


    /**
     * 显示TitleLoading
     */
    public static void showTitleLoading() {

        webView.registerHandler("showTitleLoading", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);
                commonListener.showTitleLoading(true);
                function.onCallBack("显示TitleLoading");
            }

        });
    }

    /**
     * 隐藏TitleLoading
     */
    public static void hideTitleLoading() {

        webView.registerHandler("hideTitleLoading", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                commonListener.showTitleLoading(false);
                function.onCallBack("隐藏TitleLoading");
            }

        });
    }


    /**
     * 显示Alert
     */
    public static void alert() {

        webView.registerHandler("alert", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);

                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle(jsonObject.getString("title") != null ? jsonObject.getString("title") : "")
                        .setMessage(jsonObject.getString("message") != null ? jsonObject.getString("message") : "")
                        .setPositiveButton(jsonObject.getString("button") != null ? jsonObject.getString("button") : "", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                function.onCallBack("点击了确定：");
                            }
                        })
                        .create();
                if (!activity.isFinishing()) {
                    dialog.show();
                }

            }
        });
    }

    /**
     * 显示Confirm
     */
    public static void confirm() {

        webView.registerHandler("confirm", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);


                JSONObject jsonObject = JSON.parseObject(data);
                new AlertDialog.Builder(activity)
                        .setTitle(Bridge_TextUtil.getString(jsonObject, "title"))
                        .setMessage(Bridge_TextUtil.getString(jsonObject, "message"))
                        .setNegativeButton(Bridge_TextUtil.getString(jsonObject, "cancelButton"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("action", 0);
                                function.onCallBack(jsonObject.toJSONString());
                            }
                        })
                        .setPositiveButton(jsonObject.get("okButton").toString(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("action", 1);
                                function.onCallBack(jsonObject.toJSONString());
                            }
                        })
                        .create().show();

            }
        });
    }

    /**
     * 显示Toast
     */
    public static void toast() {

        webView.registerHandler("toast", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                Toast.makeText(activity, Bridge_TextUtil.getString(jsonObject, "content"), Toast.LENGTH_LONG).show();
                function.onCallBack("展示了toast：");

            }
        });
    }

    /**
     * 显示actionSheet
     */
    public static void actionSheet() {

        webView.registerHandler("actionSheet", new BridgeHandler() {

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);
                ActionSheetInfo actionSheetInfo = new Gson().fromJson(data, ActionSheetInfo.class);
                activity.setTheme(R.style.ActionSheetStyleiOS7);
                ActionSheet.createBuilder(activity, activity.getFragmentManager())
                        .setCancelButtonTitle(actionSheetInfo.cancelBtn)
                        .setOtherButtonTitles(actionSheetInfo.btns)
                        .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                        function.onCallBack(JSONObject.toJSONString("取消"));
                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("index", index);
                        function.onCallBack(JSONObject.toJSONString(jsonObject));
                    }
                }).show();

            }
        });
    }

    /**
     * pushWindow
     * 跳转一个新的页面
     */
    public static void pushWindow() {

        webView.registerHandler("pushWindow", new BridgeHandler() {

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                Intent intent = new Intent(activity, Bridge_WebViewActivity.class);
                intent.putExtra(Bridge_Constants.HTML_URL, Bridge_TextUtil.getString(jsonObject, "url"));
                intent.putExtra(Bridge_Constants.TITLE_LOADING, false);
                intent.putExtra(Bridge_Constants.GLOBLE_LOADING, true);
                activity.startActivity(intent);

            }

        });
    }

    /**
     * popWindow
     * 关闭
     */
    public static void popWindow() {


        webView.registerHandler("popWindow", new BridgeHandler() {

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);
                activity.finish();
            }

        });
    }


    /**
     * popTo
     * 关闭
     */
    public static void popTo() {

        webView.registerHandler("popTo", new BridgeHandler() {

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);
                activity.finish();
            }

        });
    }

    /**
     * addNotifyListener
     * 添加native通知的监听
     */
    public static void addNotifyListener() {
        webView.registerHandler("addNotifyListener", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                commonListener.addNotifyListener(Bridge_TextUtil.getString(jsonObject, "name"), function);

            }
        });
    }

    /**
     * removeNotifyListener
     * 删除native通知的监听
     */
    public static void removeNotifyListener() {
        webView.registerHandler("removeNotifyListener", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);
                JSONObject jsonObject = JSON.parseObject(data);
                commonListener.removeNotifyListener(Bridge_TextUtil.getString(jsonObject, "name"), function);

            }

        });
    }

    /**
     * postNotification
     * 发送通知
     */
    public static void postNotification() {

        webView.registerHandler("postNotification", new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb2, data from web = " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                ((Bridge_WebViewActivity) activity).postNotification(Bridge_TextUtil.getString(jsonObject, "name"), Bridge_TextUtil.getString(jsonObject, "data"), function);
            }
        });
    }

    /**
     * 扫一扫
     */
    public static void xappCallPhotoAction() {

        webView.registerHandler("xappScanQRCode", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = xappScanQRCode, data from web = " + data);

                if (PermissionsUtil.checkCamPerssion(activity)) {
                    commonListener.xappScanQRCode(function);
                }
            }

        });
    }

    /**
     * 添加照片
     */
    public static void photo() {

        webView.registerHandler("photo", new BridgeHandler() {

            @Override
            public void handler(final String data, final CallBackFunction function) {

                Log.i(TAG, "photo= " + data);

                if (PermissionsUtil.checkCamPerssion(activity)) {
                    AndroidImagePicker.getInstance().pickSingle(activity, true, new AndroidImagePicker.OnImagePickCompleteListener() {
                        @Override
                        public void onImagePickComplete(List<ImageItem> items) {
                            try {
                                //拍照保存路径
                                File cameraFile = new File(Bridge_Constants.PIC_DIR, "ngc" + System.currentTimeMillis() + ".jpg");

                                cameraFile.getParentFile().mkdirs();
                                File file = new File(items.get(0).getPath());
                                Uri selectedImage = Uri.fromFile(file);
                                if (!TextUtils.isEmpty(selectedImage.getAuthority())) {
                                    file = ImageUtils.bitmapToString(ImageUtils.getFilePath(activity, selectedImage).getAbsolutePath(), cameraFile.getAbsolutePath());
                                } else {
                                    file = ImageUtils.bitmapToString(selectedImage.getPath(), cameraFile.getAbsolutePath());
                                }

                                JSONObject jsonObject = JSON.parseObject(data);
                                if (jsonObject.getString("dataType").equals("dataURL")) {
                                    JSONObject json = new JSONObject();
                                    json.put("dataURL", Base64Utils.encode(Base64Utils.getBytes(file.toString())));
                                    json.put("originalFileURL", file.toString());
                                    function.onCallBack(JSON.toJSONString(json));

                                } else if (jsonObject.getString("dataType").equals("fileURL")) {
                                    JSONObject json = new JSONObject();
                                    json.put("fileURL", file.toString());
                                    json.put("originalFileURL", file.toString());
                                    function.onCallBack(JSON.toJSONString(json));

                                }
                            } catch (Exception e) {
                                LogUtils.e("selectImg", "list is empty");
                            }
                        }
                    });
                }


            }

        });
    }

    /**
     * 滴滴支付
     */
    public static void didiPay() {

        webView.registerHandler("xappPayment", new BridgeHandler() {

            @Override
            public void handler(final String data, final CallBackFunction function) {

                Log.i(TAG, "xappPayment= " + data);

                JSONObject jsonObject = JSON.parseObject(data);
                SdkInfo sdkInfo = jsonObject.getObject("sdkInfo", SdkInfo.class);
                int payType = jsonObject.getInteger("payType");

                if (payType == 3) {

                    if (Birdge_HashMapUtil.get(Bridge_Constants.HASHMAP_KEY_DIDI) != null &&
                            Birdge_HashMapUtil.get(Bridge_Constants.HASHMAP_KEY_DIDI) instanceof Bridge_DIDIPayListener) {
                        ((Bridge_DIDIPayListener) Birdge_HashMapUtil.get(Bridge_Constants.HASHMAP_KEY_DIDI)).onDiDiPay(sdkInfo.out_trade_id, function, activity);
                    }

                } else {
                    function.onCallBack("暂时不支持此种支付");
                }


            }

        });

    }


}
