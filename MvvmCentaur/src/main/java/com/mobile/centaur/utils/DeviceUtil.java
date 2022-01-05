package com.mobile.centaur.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.UUID;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public class DeviceUtil {
    private static volatile DeviceUtil mInstance = null;

    private static final String TAG = "DeviceUtil";

    private SharedPreferencesUtils sharedPreferencesUtils;

    private String deviceId;

    public static final String FILE_NAME = "device_info_pre";

    public static final String DEVICE_ID = "deveceId";

    public static final String DEVICE_OA_ID = "DEVICE_OA_ID";

    public Context mContext;

    private DeviceUtil(Context mContext) {
        this.mContext = mContext;
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils(mContext, FILE_NAME);
        }
    }

    public static DeviceUtil getInstance(Context mContext) {
        if (mInstance == null) {
            synchronized (DeviceUtil.class) {
                if (mInstance == null) {
                    mInstance = new DeviceUtil(mContext);
                }
            }
        }
        return mInstance;
    }

    public void init(final OnDeviceIdListener deviceIdListener) {
        String OAID = sharedPreferencesUtils.loadStringKey(DEVICE_OA_ID, null);
        if (!TextUtils.isEmpty(OAID)) {
            onInitComplete(deviceIdListener);
            return;
        }
    }


    /**
     * 获取deviceid
     *
     * @return
     */
    public String getUniqueDeviceId() {
        if (!TextUtils.isEmpty(deviceId)) {
            sharedPreferencesUtils.saveStringKey(DEVICE_ID, deviceId);
            LogUtils.d("内存:deviceId:" + deviceId);
            return deviceId;
        }

        deviceId = sharedPreferencesUtils.loadStringKey(DEVICE_ID, null);
        LogUtils.d("内存:deviceId->" + deviceId);
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }

        deviceId = sharedPreferencesUtils.loadStringKey(DEVICE_OA_ID, null);
        LogUtils.i(TAG, "OAID：deviceId == " + deviceId);
        if (!TextUtils.isEmpty(deviceId)) {
            sharedPreferencesUtils.saveStringKey(DEVICE_ID, deviceId);
            return deviceId;
        }
        deviceId = UUID.randomUUID().toString();
        LogUtils.d("UUID:deviceId->" + deviceId);
        sharedPreferencesUtils.saveStringKey(DEVICE_ID, deviceId);
        return deviceId;
    }

    private void onInitComplete(OnDeviceIdListener deviceIdListener) {
        if (deviceIdListener != null) {
            deviceIdListener.onDeviceIdInitComplete();
        }
    }

    public interface OnDeviceIdListener {
        void onDeviceIdInitComplete();
    }
}
