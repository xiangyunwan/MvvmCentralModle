package com.mobile.centaur.apis;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.mobile.centaur.h5container.Bridge_Constants;
import com.mobile.centaur.h5container.control.Bridge_CommonListener;
import com.mobile.centaur.h5container.webview.BridgeHandler;
import com.mobile.centaur.h5container.webview.BridgeWebView;
import com.mobile.centaur.h5container.webview.CallBackFunction;
import com.mobile.centaur.utils.ImageUtils;
import com.mobile.centaur.utils.LogUtils;
import com.mobile.centaur.utils.PermissionsUtil;
import com.mobile.centaur.views.ImageItem;
import com.pizidea.imagepicker.AndroidImagePicker;

import java.io.File;
import java.util.List;

public class Bridge_BaseFunctionApis {

    private static final String TAG = "NGC_BaseFunctionApis";

    public static BridgeWebView webView;
    public static FragmentActivity activity;
    private static Bridge_CommonListener commonListener;

    public Bridge_BaseFunctionApis(BridgeWebView web, FragmentActivity activity, Bridge_CommonListener commonListener) {
        webView = web;
        this.activity = activity;
        this.commonListener = commonListener;
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
            public void handler(String data, CallBackFunction function) {

                Log.i(TAG, "photo= " + data);

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
                        } catch (Exception e) {
                            LogUtils.e("selectImg", "list is empty");
                        }
                    }
                });
                function.onCallBack("隐藏Loading：");
            }

        });
    }

}
