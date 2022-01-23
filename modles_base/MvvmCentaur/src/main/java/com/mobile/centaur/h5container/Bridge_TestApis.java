package com.mobile.centaur.h5container;

import com.mobile.centaur.apis.Bridge_BaseComponentApis;
import com.mobile.centaur.h5container.webview.BridgeHandler;
import com.mobile.centaur.h5container.webview.CallBackFunction;
import com.mobile.centaur.utils.DeviceInfoUtils;

public class Bridge_TestApis {

    /**
     * 测试方法
     */
    public static void test() {

        Bridge_BaseComponentApis.webView.registerHandler("test", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                function.onCallBack("测试：" + DeviceInfoUtils.getDeviceInfoWithType(0));
            }

        });
    }

}
