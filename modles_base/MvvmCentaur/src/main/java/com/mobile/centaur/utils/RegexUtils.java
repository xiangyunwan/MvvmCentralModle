package com.mobile.centaur.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isMobilePhone(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() != 11 && str.length() != 10) {
            return false;
        }
        //座机
//        String regExp = "^((0[0-9])|(0[0-9][0-9]))\\d{8}$";
        //手机号
        String regExp = "^1[3-9]\\d{9}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
