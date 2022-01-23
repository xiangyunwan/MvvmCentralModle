package com.mobile.centaur.utils;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Created by dell on 2017/4/3.
 */

public class StringUtils {
    /**
     * 返回非空的字符串
     * @param str
     * @return
     */
    public static String returnNoNullString(String str){
        return TextUtils.isEmpty(str)?"":str;
    }
    private  static Object getValueFromJSONObject(JSONObject jsonObject,String key){
        if (jsonObject!=null){
            try {
                return jsonObject.get(key);
            } catch (JSONException e) {
                LogUtils.e(e.getMessage());
            }
        }
        return null;
    }
    public static String getStringValueFromJSONObject(JSONObject jsonObject,String key){
        Object valueFromJSONObject = getValueFromJSONObject(jsonObject, key);
        if (valueFromJSONObject!=null){
            return valueFromJSONObject.toString();
        }
        return null;
    }

    /**
     * 转换string到int
     * @param currentState
     * @return
     */
    public static int formatStringValueToInt(String currentState){
        int currentStateInt = -1;
        try{
            currentStateInt = Integer.parseInt(currentState);
        }catch (Exception e){
        }
        return currentStateInt;
    }

    public static boolean formatStringValueToBoolean(String boleanValue){
        boolean isBoolean  = false;
        try{
            isBoolean = Boolean.parseBoolean(boleanValue);
        }catch (Exception e){
        }
        return isBoolean;
    }

    /**
     * 去除 字符串中的 /r  /n 换行符
     *
     * @param string
     * @return
     */
    public static String replaceLineBreaks(String string) {

        if (TextUtils.isEmpty(string)) {

            return string;
        }

        return string.replace("\r", "").replace("\n", "");
    }

    /**
     * 将得到的金额/100
     * @param stringMoney
     * @return
     */
    public static int formatStringMoneyDivision100(String stringMoney) {
        if (TextUtils.isEmpty(stringMoney)){
            return 0;
        }else{
            return (Integer.parseInt(stringMoney))/100;
        }
    }
    /**
     * 将金额除100
     * @return
     */
    public static int intDivision100(int money) {
        return money / 100;
    }
    /**
     * 将金额乘100
     * @return
     */
    public static int intMultiply100(int money) {
        return money * 100;
    }

    /**
     * 将得到的金额*100
     * @param stringMoney
     * @return
     */
    public static String formatStringMoneyMulti100(String stringMoney) {
        if (TextUtils.isEmpty(stringMoney)){
            return 0+"";
        }else{
            return ((Integer.parseInt(stringMoney))*100)+"";
        }
    }

    /**
     * 获取网络请求价格
     * @param moneyStr
     * @return
     */
    public static String getRequestFormatMoney(String moneyStr){
        if (TextUtils.isEmpty(moneyStr)) return null;
        else {
            try {
                return ((Integer.parseInt(moneyStr))*100)+"";
            }catch (Exception e){
                LogUtils.e(e.toString());
            }
        }
        return null;
    }

    /**
     * 对空字符串进行修剪
     * @param originalStr
     * @return
     */
    public static String trimStr(String originalStr){
        return TextUtils.isEmpty(originalStr)?originalStr:originalStr.trim();
    }

    /**
     * 判断是否是数字字符串
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 传入金额字符串（单位为分）,处理成单位为元，保留两位小数
     */
    public static String formMoney(String moneyStr){
        if (moneyStr==null){
            return "--";
        }else {
            try {
                BigDecimal bgMoney = new BigDecimal(moneyStr);
                BigDecimal bigDecimal = bgMoney.divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
                return bigDecimal.toString();
            }catch (Exception e){
                LogUtils.e(e.toString());
            }
        }
        return "";
    }

    public static String fenToYuan(String amount){
        NumberFormat format = NumberFormat.getInstance();
        try{
            Number number = format.parse(amount);
            double temp = number.doubleValue() / 100.0;
            format.setGroupingUsed(false);
            if (number.doubleValue()%100>0){
// 设置返回的小数部分所允许的最大位数
                format.setMaximumFractionDigits(2);
            }else{
                // 设置返回的小数部分所允许的最大位数
                format.setMaximumFractionDigits(0);
            }
            amount = format.format(temp);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return amount;
    }


    /**
     * 检查两个字符串是否相等，空字符串与null视为相等
     * @param origin
     * @param target
     * @return
     */
    public static boolean checkEqualsWithNull(String origin,String target){
        if ((origin==null || TextUtils.equals("",origin)) && (target==null||TextUtils.equals("",target))){
            return true;
        }else {
            return TextUtils.equals(origin,target);
        }
    }

    /**
     * 检查Url是不是null或者是否以‘/’字符结尾
     * @param url
     * @return
     */
    public static String checkNullAndSuffix(String url,String defaultValue){
        if (TextUtils.isEmpty(url)){
            return defaultValue;
        }else{
            return url.endsWith("/")?url:url+"/";
        }
    }

}
