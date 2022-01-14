package com.mobile.centaur.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.luck.picture.lib.compress.Luban;
import com.mobile.centaur.base.BaseApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * created by zzz
 *
 * @class
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            Log.w(TAG, "SDCARD is not MOUNTED !");
            return false;
        }
    }

    /**
     * 获取SD卡剩余容量（单位Byte）
     *
     * @return
     */
    public static long gainSDFreeSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
            return freeBlocks * blockSize; // 单位Byte
        } else {
            return 0;
        }
    }

    /**
     * 获取SD卡总容量（单位Byte）
     *
     * @return
     */
    public static long gainSDAllSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
            return allBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     *
     * @return
     */
    public static String gainSDCardPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                Log.w(TAG, "SDCARD can not write !");
            }
            return sdcardDir.getPath();
        }
        return "";
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     * @param filePath 文件路径
     */
    public static String readFileByLines(String filePath) throws IOException
    {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), System.getProperty("file.encoding")));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
            {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null){reader.close();}
        }

        return sb.toString();

    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     * @param filePath 文件路径
     * @param encoding 写文件编码
     */
    public static String readFileByLines(String filePath, String encoding) throws IOException
    {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),encoding));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
            {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null){reader.close();}
        }

        return sb.toString();
    }


    /**
     * 保存内容
     * @param filePath 文件路径
     * @param content 保存的内容
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content) throws IOException
    {
        saveToFile(filePath,content, System.getProperty("file.encoding"));
    }

    /**
     * 指定编码保存内容
     * @param filePath 文件路径
     * @param content 保存的内容
     * @param encoding 写文件编码
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content, String encoding) throws IOException
    {
        BufferedWriter writer = null;
        File file = new File(filePath);
        try
        {
            if(!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding));
            writer.write(content);

        } finally
        {
            if (writer != null){writer.close();}
        }
    }

    /**
     * 追加文本
     * @param content 需要追加的内容
     * @param file 待追加文件源
     * @throws IOException
     */
    public static void appendToFile(String content, File file) throws IOException
    {
        appendToFile(content, file, System.getProperty("file.encoding"));
    }

    /**
     * 追加文本
     * @param content 需要追加的内容
     * @param file 待追加文件源
     * @param encoding 文件编码
     * @throws IOException
     */
    public static void appendToFile(String content, File file, String encoding) throws IOException
    {
        BufferedWriter writer = null;
        try
        {
            if(!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding));
            writer.write(content);
        } finally
        {
            if (writer != null){writer.close();}
        }
    }

    /**
     * 判断文件是否存在
     * @param filePath 文件路径
     * @return 是否存在
     * @throws Exception
     */
    public static Boolean isExsit(String filePath)
    {
        Boolean flag = false ;
        try
        {
            File file = new File(filePath);
            if(file.exists())
            {
                flag = true;
            }
        }catch(Exception e){
            System.out.println("判断文件失败-->"+e.getMessage());
        }

        return flag;
    }

    /**
     * 快速读取程序应用包下的文件内容
     *
     * @param context
     *            上下文
     * @param filename
     *            文件名称
     * @return 文件内容
     * @throws IOException
     */
    public static String read(Context context, String filename) throws IOException {
        FileInputStream inStream = context.openFileInput(filename);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

    /**
     * 读取指定目录文件的文件内容
     *
     * @param fileName
     *            文件名称
     * @return 文件内容
     * @throws Exception
     */
    public static String read(String fileName) throws IOException {
        FileInputStream inStream = new FileInputStream(fileName);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

    /***
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param fileName
     *            文件名称
     * @param encoding
     *            文件编码
     * @return 字符串内容
     * @throws IOException
     */
    public static String read(String fileName, String encoding)
            throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fileName), encoding));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }

    /**
     * 读取raw目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param rawFileId
     *            raw文件名id
     * @return
     */
    public static String readRawValue(Context context, int rawFileId) {
        String result = "";
        try {
            InputStream is = context.getResources().openRawResource(rawFileId);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static String readAssetsValue(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static List<String> readAssetsListValue(Context context, String fileName) {
        List<String> list = new ArrayList<String>();
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取SharedPreferences文件内容
     *
     * @param context
     *            上下文
     * @param fileNameNoExt
     *            文件名称（不用带后缀名）
     * @return
     */
    public static Map<String, ?> readShrePerface(Context context, String fileNameNoExt) {
        SharedPreferences preferences = context.getSharedPreferences(
                fileNameNoExt, Context.MODE_PRIVATE);
        if(null == preferences) return null;

        return preferences.getAll();
    }

    /**
     * 写入SharedPreferences文件内容
     *
     * @param context
     *            上下文
     * @param fileNameNoExt
     *            文件名称（不用带后缀名）
     * @param values
     *            需要写入的数据Map(String,Boolean,Float,Long,Integer)
     * @return
     */
    public static void writeShrePerface(Context context, String fileNameNoExt, Map<String, ?> values) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(fileNameNoExt, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (Iterator iterator = values.entrySet().iterator(); iterator.hasNext();)
            {
                Map.Entry<String, ?> entry = (Map.Entry<String, ?>) iterator.next();
                if (entry.getValue() instanceof String) {
                    editor.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {
                    editor.putBoolean(entry.getKey(),(Boolean) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    editor.putFloat(entry.getKey(), (Float) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    editor.putLong(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    editor.putInt(entry.getKey(),(Integer) entry.getValue());
                }
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, String content) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            outStream.write(content.getBytes());
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, byte[] content) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param modeType
     *            文件写入模式（Context.MODE_PRIVATE、Context.MODE_APPEND、Context.
     *            MODE_WORLD_READABLE、Context.MODE_WORLD_WRITEABLE）
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, byte[] content,
                             int modeType) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                    modeType);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定编码将内容写入目标文件
     *
     * @param target
     *            目标文件
     * @param content
     *            文件内容
     * @param encoding
     *            写入文件编码
     * @throws Exception
     */
    public static void write(File target, String content, String encoding)
            throws IOException {
        BufferedWriter writer = null;
        try {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(target, false), encoding));
            writer.write(content);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 指定目录写入文件内容
     * @param filePath 文件路径+文件名
     * @param content 文件内容
     * @throws IOException
     */
    public static void write(String filePath, byte[] content)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            fos.write(content);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 写入文件
     *
     * @param inputStream 下载文件的字节流对象
     * @param filePath 文件的存放路径(带文件名称)
     * @throws IOException
     */
    public static File write(InputStream inputStream, String filePath) throws IOException {
        OutputStream outputStream = null;
        // 在指定目录创建一个空文件并获取文件对象
        File mFile = new File(filePath);
        if (!mFile.getParentFile().exists())
            mFile.getParentFile().mkdirs();
        try {
            outputStream = new FileOutputStream(mFile);
            byte buffer[] = new byte[4 * 1024];
            int lenght = 0 ;
            while ((lenght = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,lenght);
            }
            outputStream.flush();
            return mFile;
        } catch (IOException e) {
            Log.e(TAG, "写入文件失败，原因："+e.getMessage());
            throw e;
        }finally{
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 指定目录写入文件内容
     * @param filePath 文件路径+文件名
     */
    public static void saveAsJPEG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 指定目录写入文件内容
     * @param filePath 文件路径+文件名
     */
    public static void saveAsPNG(Bitmap bitmap, String filePath)
            throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 序列化对象
     * @param rsls 需要序列化的对象
     * @param filename 文件名
     */
    public static synchronized <T> void serializeObject(T rsls, String filename) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(rsls);
            byte[] data = baos.toByteArray();
            OutputStream os = new FileOutputStream(new File(filename));
            os.write(data);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化对象
     * @param filename 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public static synchronized <T> T deserializeObject(String filename) {
        T obj = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                obj = (T) ois.readObject();
            }
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public static void delFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 删除文件夹
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            Log.w(TAG, "文件不存在！");
        }
    }

    /**
     * 获得应用文件路径
     */
    public static String getDiskFileDir(Context context) {
        String filePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filePath = context.getExternalFilesDir(null).getPath();
        } else {
            filePath = context.getCacheDir().getPath();
        }
        return filePath;
    }
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
