package com.mobile.centaur.widget.versionmanager;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public class DownLoadManager {

    private static final String MAIN = "main"; //Tag
    private static DownLoadManager instance = new DownLoadManager(); //单例对象

    /**
     * 对外公布的单例对象
     *
     * @return
     */
    public static DownLoadManager getInstance() {
        return instance;
    }

    /**
     * 下载
     *
     * @param uri        url
     * @param listener   下载DownLoadListener监听对象
     * @param targetFile 目标文件
     */
    public void downLoad(final Context context, final String uri, final DownLoadListener listener,
                         final File targetFile) {
        if (MAIN.equalsIgnoreCase(Thread.currentThread().getName())) {
            new Thread() {
                @Override
                public void run() {
                    downloadNewThread(context, uri, listener, targetFile);
                }

                ;
            }.start();
        } else {
            downloadNewThread(context, uri, listener, targetFile);
        }
    }

    /**
     * 新开一个线程执行下载操作
     *
     * @param uri
     * @param listener
     * @param targetFile
     */
    private void downloadNewThread(Context context, String uri, DownLoadListener listener,
                                   File targetFile) {

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        //
        try {
            URL url = new URL(uri);
            //获取连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(60 * 1000);
            connection.setReadTimeout(60 * 1000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //打开连接
            connection.connect();
            //获取内容长度
            int contentLength = connection.getContentLength();

            if (listener != null) {
                listener.onStartLoading(contentLength);
            }

            File parent = targetFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            } else if (!parent.isDirectory()) {
                if (parent.delete()) {
                    parent.mkdirs();
                }
            }

            //输入流
            inputStream = connection.getInputStream();

            //输出流
            boolean old = Build.VERSION.SDK_INT < Build.VERSION_CODES.N;
            fileOutputStream = old ?
                    context.openFileOutput(targetFile.getName(), Context.MODE_WORLD_READABLE)
                    : new FileOutputStream(targetFile);

            byte[] bytes = new byte[1024];
            long totalReaded = 0;
            int temp_Len;
            long currentTime = System.currentTimeMillis();
            while ((temp_Len = inputStream.read(bytes)) != -1) {
                totalReaded += temp_Len;
//                Log.i("XXXX", "run: totalReaded:" + totalReaded);
//                long progress = totalReaded * 100 / contentLength;
//                Log.i("XXXX", "run: progress:" + progress);
                fileOutputStream.write(bytes, 0, temp_Len);

                if (listener != null) {
                    listener.onLoading(totalReaded, ((float) totalReaded) / contentLength,
                            ((float) temp_Len) / System.currentTimeMillis()
                                    - currentTime);
                }
                currentTime = System.currentTimeMillis();
            }

            if (listener != null) {
                listener.onLoadingFinish(contentLength);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onFailure(e.getMessage());
            }

        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 声明DownLoadListener监听器
     */
    public interface DownLoadListener {
        /**
         * 开始下载
         *
         * @param totalSize
         */
        void onStartLoading(long totalSize);

        /**
         * 下载中
         *
         * @param currentSize byte
         * @param percent
         * @param speed       byte/second
         */
        void onLoading(long currentSize, float percent, float speed);

        /**
         * 下载完成
         *
         * @param totalSize
         */
        void onLoadingFinish(long totalSize);

        /**
         * 下载失败
         *
         * @param error
         */
        void onFailure(String error);
    }
}
