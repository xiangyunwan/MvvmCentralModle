package com.mobile.centaur.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * created by mengbenming on 2018/9/26
 *
 * @class
 */
public class DateUtils {

    private static final String TAG = "DateUtils";

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取ri
     *
     * @return
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取当天时间
     *
     * @return
     */
    public static String getTodayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(new Date());
    }


    /**
     * 输入所要转换的时间输入例如（"2014年06月14日 16:09"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
        String times = null;
        try {
            times = sdr.format(new Date(Long.valueOf(time)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 将10 or 13 位时间戳转为时间字符串
     * convert the number 1407449951 1407499055617 to date/time format timestamp
     *
     * @param str
     * @return
     */
    public static String timeStampToDate(String str, String dateformat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        String timeString;
        if (str.length() == 13) {
            timeString = sdf.format(new Date(Long.parseLong(str)));
        } else {
            timeString = sdf.format(new Date(Integer.parseInt(str) * 1000L));
        }
        return timeString;
    }

    /**
     * 将10 or 13 位时间戳转为时间字符串
     * convert the number 1407449951 1407499055617 to date/time format timestamp
     *
     * @param str
     * @return
     */
    public static String timeSimpleToDate(String str, String dateformat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        String timeString = sdf.format(new Date(Long.parseLong(str)));
        return timeString;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean isToday(String day, String timeFormat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断时间戳星期几
     *
     * @param time
     * @param timeformat
     * @return
     */
    public static String timeStampToWeek(String time, String timeformat) {
        String week = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                week += "星期日";
                break;
            case Calendar.MONDAY:
                week += "星期一";
                break;
            case Calendar.TUESDAY:
                week += "星期二";
                break;
            case Calendar.WEDNESDAY:
                week += "星期三";
                break;
            case Calendar.THURSDAY:
                week += "星期四";
                break;
            case Calendar.FRIDAY:
                week += "星期五";
                break;
            case Calendar.SATURDAY:
                week += "星期六";
                break;
        }
        week = week + timeStampToDate(time, "HH:mm");
        return week;
    }

    /**
     * 将时间戳转String类型转换成long类型（传入的时间戳和时间类型要一致：
     * eg：20181212，格式就应该是yyyyMMdd;
     * 或者：2018-12-11 11:11，格式应该是yyyy-MM-dd HH:mm）
     *
     * @param time
     * @param timeformat
     * @return
     */
    public static long timeStampToLong(String time, String timeformat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(timeformat);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime() / 10;
    }
}
