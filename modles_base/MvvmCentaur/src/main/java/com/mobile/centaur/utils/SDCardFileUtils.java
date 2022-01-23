package com.mobile.centaur.utils;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/

public class SDCardFileUtils {
    /**
     * 判断是否插入SD卡，SD卡是否可用
     *
     * @return
     */
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED) && Environment.getExternalStorageDirectory().canWrite()) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean sdCardIsExist() {
        if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }
    public static String getSDCardPath() {
        if (sdCardIsExist()) {
            return Environment.getExternalStorageDirectory().getPath() + "/";
        }
        return null;
    }
    public static String writeFile2SDCard(String filePath) throws IOException {
        String fileDir = mkDir2SDCard(getFileDir(filePath));
        String fileFinalPath = fileDir + getFileName(filePath);
        File file = new File(fileFinalPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return fileFinalPath;
    }
    public static String mkDir2SDCard(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath;
    }
    private static String getFileName(String filePath) {
        int index = 0;
        String tmpName = "";
        if ((index = filePath.lastIndexOf("/")) != -1) {
            tmpName = filePath.substring(index + 1);
        }
        return tmpName.contains(".") ? tmpName : "";
    }
    private static String getFileDir(String filePath) {
        if (filePath.startsWith(getSDCardPath())) {
            return filePath.replace(getFileName(filePath), "");
        }
        return getSDCardPath() + filePath.replace(getFileName(filePath), "");
    }
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }
        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }
        if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
            return false;
        } else {
            return true;
        }
    }
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }
        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }
        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (null != exception) {
            throw exception;
        }
    }
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
    public static Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            @SuppressWarnings("unchecked") Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Map<String, Object>> getList(String json) {
        List<Map<String, Object>> list = null;
        try {
            String jsonString = json;
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void unZip(Context context, String assetName, String outputDirectory) throws IOException {
        File file = new File(outputDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        byte[] buffer = new byte[1024 * 1024];
        int count = 0;
        while (zipEntry != null) {
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }
    public static String getAppRoot(Context context) {
        File root = context.getExternalFilesDir(null);
        String extStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(extStoragePath);
        sb.append("/");
        sb.append(root);
        String path = sb.toString();
        return path;
    }
    public static String formatPath(String path) {
        path = path.replace('\\', '/');
        while (path.length() > 0 && path.startsWith("/")) {
            path = path.substring(1);
        }
        while (path.length() > 0 && path.endsWith("/")) {
            int len = path.length();
            path = path.substring(0, len - 1);
        }
        return path;
    }
}
