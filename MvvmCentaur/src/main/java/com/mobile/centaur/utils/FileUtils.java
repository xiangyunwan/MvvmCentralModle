package com.mobile.centaur.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * created by mengbenming on 2018/12/13
 *
 * @class
 */
public class FileUtils {
    /**
     * 返回以“/”结尾的外部存储根目录，外置卡不可用则返回空字符串
     */
    public static String getExternalRootPath(String type) {
        File file = null;
        if (externalMounted()) {
            file = Environment.getExternalStorageDirectory();
        }
        if (file != null && !TextUtils.isEmpty(type)) {
            file = new File(file, type);
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        String path = "";
        if (file != null) {
            path = FileUtils.separator(file.getAbsolutePath());
        }
        LogUtils.v("external storage root path: ", path);
        return path;
    }


    /**
     * 判断外置存储是否可用
     *
     * @return the boolean
     */
    public static boolean externalMounted() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        LogUtils.w("MEDIA_MOUNTED", "external storage unmounted");
        return false;
    }

    /**
     * 将目录分隔符统一为平台默认的分隔符，并为目录结尾添加分隔符
     */
    public static String separator(String path) {
        String separator = File.separator;
        path = path.replace("\\", separator);
        if (!path.endsWith(separator)) {
            path += separator;
        }
        return path;
    }

    /**
     * 下载的文件的保存路径，必须为外部存储，以“/”结尾
     *
     * @return 诸如 ：/mnt/sdcard/Download/
     */
    public static String getDownloadPath() throws RuntimeException {
        File file;
        if (externalMounted()) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else {
            throw new RuntimeException("外置存储不可用！");
        }
        return FileUtils.separator(file.getAbsolutePath());
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copyGlide(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
