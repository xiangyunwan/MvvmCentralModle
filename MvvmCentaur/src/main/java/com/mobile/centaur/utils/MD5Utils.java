package com.mobile.centaur.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    public static String md5(String plainText) {
        if(plainText==null){
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param digest
     * @return
     */
    private static String byteArrayToHexString(byte[] digest) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buffer.append(byteToHexString(digest[i]));
        }
        return buffer.toString();
    }
    /**
     * 字节转十六进制字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        // int d1 = n/16;
        int d1 = (b & 0xf0) >> 4;
        // int d2 = n%16;
        int d2 = b & 0xf;
        return hexDigits[d1] + hexDigits[d2];
    }
    public static String digestMD5(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            String s = Integer.toHexString(0xff & result[i]);
            if (s.length() < 2) {
                sb.append('0');
            }
            sb.append(s);
        }
        String res = sb.toString();
        return res;
    }
    public static String digestMD5(File file) throws Exception {
        String res = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[4096];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                md.update(buffer, 0, length);
            }
            byte[] result = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                String s = Integer.toHexString(0xff & result[i]);
                if (s.length() < 2) {
                    sb.append('0');
                }
                sb.append(s);
            }
            res = sb.toString();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // do nothing
            }
        }
        return res;
    }
}