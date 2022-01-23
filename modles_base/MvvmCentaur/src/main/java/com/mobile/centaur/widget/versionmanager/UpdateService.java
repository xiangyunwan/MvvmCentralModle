package com.mobile.centaur.widget.versionmanager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.mobile.centaur.BuildConfig;
import com.app.mobile.centaur.R;
import com.mobile.centaur.utils.LogUtils;
import com.mobile.centaur.utils.MD5Utils;
import com.mobile.centaur.utils.ToastUtil;

import java.io.File;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
@SuppressLint("NewApi")
public class UpdateService extends Service {
    private static final String NOTIFY_CHANNEL_ID = "com.jianke.api.UpdateService";
    public static final String BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK = "BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK";
    public static final String BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK_SUCCESS = "BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK_SUCCESS";

    public static boolean isRunning = false; //是否正在运行
    public static final String URL = "url"; //Tag
    public static final String ICON = "icon"; //Tag
    public static final String MD5 = "md5"; //Tag
    private NotificationCompat.Builder builder;
    private Handler handler;//Handler对象
    private int lastPercent = 0;
    private NotificationManager notificationManager;//Class to notify the user of events that happen.
    private AuthInstallApkBroadcastReceiver mAuthInstallApkBroadcastReceiver;
    private String fileName = String.valueOf(System.currentTimeMillis());
    private UpdateListener updateListener;

    private class AuthInstallApkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            installApk();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthInstallApkBroadcastReceiver = new AuthInstallApkBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(UpdateService.BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(mAuthInstallApkBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mAuthInstallApkBroadcastReceiver);
        updateListener = null;
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new UpdateServiceBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        isRunning = true;
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String action = intent.getAction();

        if (!TextUtils.isEmpty(action)) {
            Toast.makeText(getApplicationContext(), "action is null", Toast.LENGTH_SHORT).show();
        } else {
            String url = intent.getStringExtra(URL);
            final String md5 = intent.getStringExtra(MD5);
            int icon = intent.getIntExtra(ICON, android.R.drawable.sym_def_app_icon);
            if (TextUtils.isEmpty(url)) {
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException("获取APK更新地址失败");
                }
                return super.onStartCommand(intent, flags, startId);
            } else {
                startUpdate(url, icon);
            }
            handler = new Handler(getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (builder != null) {
                        switch (msg.what) {
                            case 1:
                                builder.setProgress(100, (Integer) msg.obj, false);
                                builder.setContentText((Integer) msg.obj + "%");
                                if (notificationManager == null || builder == null) {
                                    return;
                                }
                                notificationManager
                                        .notify(R.id.update_notification_id, builder.build());
                                break;
                            case 2:
                                notificationManager.cancel(R.id.update_notification_id);

                                getFileMD5(md5);

                                break;


                            case 3:  //校验md5 结果处理 以及安装

                                String fileHash = (String) msg.obj;
                                LogUtils.d("md5===" + fileHash);
                                // 校验hash 忽略 md5大小写
                                if (msg.arg1 == 1 && (TextUtils.isEmpty(md5) || (!TextUtils.isEmpty(fileHash) && fileHash.equalsIgnoreCase(md5)))) {
                                    isRunning = false;
                                    if(updateListener != null) updateListener.onMd5Checked(getApkPath());
                                    installApk();

                                } else {
                                    isRunning = false;
                                    if(updateListener != null) updateListener.onError("文件错误");
                                    ToastUtil.setToast("文件错误");
                                    stopSelf();
                                }

                                break;

                            case 4:
                                isRunning = false;
                                if(updateListener != null) updateListener.onError(msg.obj + "");
                                ToastUtil.setToast(msg.obj + "");
                                notificationManager.cancel(R.id.update_notification_id);
                                stopSelf();
                                break;

                            default:
                                break;
                        }
                    }
                }
            };
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    /**
     * 校验文件md5
     *
     * @param md5
     */
    private void getFileMD5(String md5) {

        if (!TextUtils.isEmpty(md5)) {

            new Thread(new Runnable() {   //300M 耗时 ≈ 3S
                @Override
                public void run() {
                    try {

                        File file = new File(getApkPath());

                        String fileHash = MD5Utils.digestMD5(file);

                        sendCheckFileMsg(true, fileHash);

                    } catch (Exception e) {
                        e.printStackTrace();
                        sendCheckFileMsg(false, "");
                    }

                }
            }).start();

        } else {  //md5 空算成功

            sendCheckFileMsg(true, "");
        }
    }

    private void sendCheckFileMsg(boolean success, String hash) {

        Message msg = Message.obtain();
        msg.what = 3;
        msg.arg1 = success ? 1 : 0;
        msg.obj = hash;
        handler.sendMessage(msg);

    }

    private void installApk() {
        File file = new File(getApkPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !getPackageManager().canRequestPackageInstalls()) {
            ToastUtil.setToast("请授权安装应用");
            requestAutoInstallApk();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(this, getPackageName() + ".updateprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            intent.addCategory("android.intent.category.DEFAULT");
        }
        startActivity(intent);
        stopSelf();
    }

    private void requestAutoInstallApk() {
        isRunning = false;
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BROADCAST_UPDATE_VERSION_AUTH_INSTALL_APK));
    }

    /**
     * 开始下载
     *
     * @param url apk的url
     */
    private void startUpdate(String url, int icon) {
        createNotification(icon); //创建通知栏进度
        startDownLoad(url);
    }

    /**
     * 创建通知栏进度
     */
    private void createNotification(int icon) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFY_CHANNEL_ID,
                    "TAG",
                    NotificationManager.IMPORTANCE_DEFAULT

            );
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setDescription("update version");
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.cancel(R.id.update_notification_id);
        builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFY_CHANNEL_ID);
        builder.setSmallIcon(icon).setContentTitle("正在下载 ...")
                .setContentText("0%");
        builder.setPriority(2 | Notification.DEFAULT_ALL);
        builder.setProgress(100, 0, false);
        builder.setOnlyAlertOnce(true);
        builder.setOngoing(true);

        if (notificationManager == null) {
            return;
        }
        notificationManager.notify(R.id.update_notification_id, builder.build());
    }

    /**
     * 开始下载
     *
     * @param url
     */
    private void startDownLoad(String url) {
        DownLoadManager.getInstance().downLoad(this, url, new DownLoadManager.DownLoadListener() {

            @Override
            public void onStartLoading(long totalSize) {
                // Do nothing because of auto-generated
                if(updateListener != null) updateListener.onStart(totalSize);
            }

            @Override
            public void onLoading(long currentSize, float percent, float speed) {

                int tempPercent = (int) (percent * 100);
                if (tempPercent >= 0 && lastPercent != tempPercent) {  //避免频繁调用通知

                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = tempPercent;
                    handler.sendMessage(msg);

                    lastPercent = tempPercent;
                    if(updateListener != null) updateListener.onLoading(currentSize, percent, speed);
                }
            }

            @Override
            public void onLoadingFinish(long totalSize) {
                Message msg = Message.obtain();
                msg.what = 2;
                handler.sendMessage(msg);
                if(updateListener != null) updateListener.onLoadingFinish(totalSize);
            }

            @Override
            public void onFailure(String error) {

                Message msg = Message.obtain();
                msg.what = 4;
                msg.obj = error;
                handler.sendMessage(msg);
            }
        }, new File(getApkPath()));
    }

    /**
     * 获取apk下载路径
     *
     * @return
     */
    private String getApkPath() {
        boolean old = Build.VERSION.SDK_INT < Build.VERSION_CODES.N;
        return old ?
                getFileStreamPath(fileName + ".apk").getAbsolutePath()
                : getAppRootDir() + fileName + ".apk";
    }

    /**
     * 获取sdcard的绝对路径
     *
     * @return
     */
    public String getSDcardDir() {
        String sdcardPath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdcardPath = getApplicationContext().getFilesDir().getAbsolutePath();
        }

        return sdcardPath;
    }

    /**
     * 获取应用跟目录
     *
     * @return
     */
    public String getAppRootDir() {
        return getFilesDir().getAbsolutePath() + "/";
    }

    public class UpdateServiceBinder extends Binder {

        public UpdateService getService(){
            return UpdateService.this;
        }
    }
}