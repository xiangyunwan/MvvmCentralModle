package com.mobile.centaur.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.luck.picture.lib.compress.Luban;
import com.mobile.centaur.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    //    public static final int MAX_SIZE = 5 * 1024;
    public static final int MAX_SIZE = 2 * 100;

    public static List<File> FileCompress(Context mContext, List<String> paths) {

        try {
            return Luban.with(mContext).ignoreBy(MAX_SIZE).load(paths).get();
        } catch (IOException e) {
            LogUtils.e(e.getMessage());
            return null;
        }

    }

//    public static void fileCompress(List<File> files, Action action) {
//
//        Context mContext = BaseApplication.getApplicatonContext();
//
//        Observable.just(files)
//                .observeOn(Schedulers.io())
//                .map(fileList -> {
//                    try {
//                        List<String> paths = new ArrayList<>();
//                        List<File> newFiles = new ArrayList<>();
//
//                        for (File file : fileList) {
//                            if (file != null && file.exists()) {
//
//                                if ((file.length() / 1024) > MAX_SIZE) {
//
//                                    paths.add(file.getPath());
//                                } else {
//                                    newFiles.add(file);
//                                }
//                            }
//                        }
//
//                        if (paths != null && !paths.isEmpty()) {  //只压缩大于500 的
//
//                            List<File> temps = Luban.with(mContext).ignoreBy(MAX_SIZE).load(paths).get();
//                            if (temps != null && !temps.isEmpty()) {
//
//                                newFiles.addAll(temps);
//                            }
//                        }
//
//                        return newFiles;
//
//                    } catch (IOException e) {
//                        LogUtils.e(e.getMessage());
//                    }
//                    return null;
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(action);
//
//    }

    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        }
    }

    /**
     * 将内容写入sd卡中
     * @param filename 要写入的文件名
     * @param content  待写入的内容
     * @param append  是否覆盖原来的内容
     */
    public static boolean writeExternal(Context context, String filename, String content,boolean append) {

        //获取外部存储卡的可用状态
        String storageState = Environment.getExternalStorageState();
        try {
            //判断是否存在可用的的SD Card
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {

                //路径： /storage/emulated/0/Android/data/com.yoryky.demo/cache/yoryky.txt
                String filePath = context.getExternalCacheDir().getAbsolutePath() + File.separator;
                filename = filePath+ filename;
                makeFilePath(filePath,filename);
                FileOutputStream outputStream = new FileOutputStream(filename,append);
                outputStream.write(content.getBytes());
                outputStream.close();
                return true;
            }
        }catch (Exception e){}
        return false;
    }

    /**
     * 从sd card文件中读取数据
     * @param filename 待读取的sd card
     * @return
     */
    public static String readExternal(Context context, String filename) {
        StringBuilder sb = new StringBuilder("");
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try {
                String filePath = context.getExternalCacheDir().getAbsolutePath() + File.separator;
                filename = filePath+ filename;
                makeFilePath(filePath,filename);
                //打开文件输入流
                FileInputStream inputStream = new FileInputStream(filename);

                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                //读取文件内容
                while(len > 0){
                    sb.append(new String(buffer,0,len));
                    //继续将数据放到buffer中
                    len = inputStream.read(buffer);
                }
                //关闭输入流
                inputStream.close();
            }catch (Exception e){}
        }
        return sb.toString();
    }

    /**
     * 生成文件
     * @param filePath
     * @param fileName
     * @return
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }
}
