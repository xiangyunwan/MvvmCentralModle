package com.mobile.centaur.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

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
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD2 = "yyyy/MM/dd";
    public static String YYYY_MM_DD_DOT = "yyyy.MM.dd";
    public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String YMDHM = "yyyy-MM-dd HH:mm";
    public static String MM_DD_HH_mm = "MM月dd日HH:mm";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String HH_MM = "HH:mm";
    public static String YMD_E = "yyyy-MM-dd E";
    public static String MD = "MM-dd";
    public static String MM_DD = "MM月dd日";
    public static String YY = "yyyy年";
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

    /**
     * 秒 转换为 xx:xx:xx
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:"+unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 解析324324324324数据，计算距离当前的时间值：如--10分钟前
     * @author: luchaoyue
     * @createTime:2017/4/6
     * @lastModify:2017/4/6
     * @param: simpleMode 是否是简单模式，true，是，false：不是
     * @return:
     */
    public static String getDateFormatNew(String srcDate, boolean simpleMode) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(srcDate)) {
            if (!simpleMode) {
                String calculateTimeFroNow = calculateTimeFroNow(Long.valueOf(srcDate));
                if (calculateTimeFroNow != null && calculateTimeFroNow.contains(":")) {
                    String[] times = calculateTimeFroNow.split(":");
                    int length = times.length;
                    for (int i = length - 1; i >= 0; i--) {
                        if (3 == i)
                            sb.append(times[i]).append("天");
                        if (2 == i)
                            sb.append(times[i]).append("时");
                        if (1 == i)
                            sb.append(times[i]).append("分");
                        if (0 == i)
                            sb.append(times[i]).append("秒之前");
                    }
                } else {
                    sb.append(calculateTimeFroNow).append("秒钟之前");
                }
            } else {
                sb.append(calculateTimeFroNowSimple(Long.valueOf(srcDate)));
            }
        }
        String date = sb.toString();
        return date;
    }
    /**
     * 计算给定的时间距离现在多久
     *
     * @param time
     *            给定的时间
     * @return 返回记录现在的时间 如：10:10  10分钟之前
     */
    public static String calculateTimeFroNowSimple(long time) {
        long disTime = (new Date().getTime() - time) / 1000;
        if (disTime < 60 && disTime >= 0) { // 秒
            return new StringBuilder().append(disTime).append("秒钟前").toString();
        } else if (disTime < 60 * 60 && disTime >= 60) { // 秒,分
            return new StringBuilder().append(disTime / 60).append("分钟前").toString();
        } else if (disTime >= 60 * 60 && disTime < 60 * 60 * 24) { // 秒,分， 时
            return new StringBuilder().append(disTime / 3600).append("小时前").toString();
        } else if (disTime >= 60 * 60 * 24) { // 秒,分， 时, 天
            return new StringBuilder().append(disTime / (60 * 60 * 24)).append("天前　").toString();
        }
        return "1分钟前";
    }
    /**
     * 计算给定的时间距离现在多久
     *
     * @param time
     *            给定的时间
     * @return 返回记录现在的时间 如：10:10 10分钟10秒
     */
    public static String calculateTimeFroNow(long time) {
        long disTime = (new Date().getTime() - time) / 1000;
        StringBuilder sb = new StringBuilder();
        if (disTime < 60 && disTime >= 0) { // 秒
            sb.append(disTime);
        } else if (disTime < 60 * 60 && disTime >= 60) { // 秒,分
            sb.append(disTime % 60).append(":").append(disTime / 60);
        } else if (disTime >= 60 * 60 && disTime < 60 * 60 * 24) { // 秒,分， 时
            sb.append(disTime % 3600 % 60).append(":").append(disTime % 3600 / 60).append(":").append(disTime / 3600);
        } else if (disTime >= 60 * 60 * 24) { // 秒,分， 时, 天
            sb.append(disTime % (60 * 60 * 24) % 3600 % 60).append(":")
                    .append(disTime % (60 * 60 * 24) % 3600 / 60).append(":").append(disTime % (60 * 60 * 24) / 3600).append(":")
                    .append(disTime / (60 * 60 * 24));
        }
        return sb.toString();
    }
    /**
     * 获取currentDate7天前的时间
     * @param currentDate
     * @return
     */
    public static Date getBeforeSevenDate(Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-7);
        return parseDate(formatDate(calendar.getTimeInMillis()));
    }

    /**
     * 获取当天时间到23:59:59
     * @return
     */
    public static Date getTodayLastSecondDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
        long timestamp = parseDate(formatDate(calendar.getTimeInMillis())).getTime() - 1000;
        return new Date(timestamp);
    }
    public static String formatDate(String datestr) {
        return formatDate(datestr, YYYY_MM_DD);
    }
    public static String formatDate(long timestamp) {
        return formatDate(new Date(timestamp));
    }

    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
    public static String formatDate(String datestr, String formatString) {
        try {
            Date date = new Date(Long.parseLong(datestr));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String formatDate(long timestamp, String formatString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static String formatDate(Date date, String formatString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(date);
    }


    public static Date parseDate(String datestr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());
        Date date;
        try {
            date = simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            LogUtils.e("DateUtils cannot parse date from " + datestr);
            date = new Date();
        }
        return date;
    }

    /**
     * 获取今天属于周几
     * @return
     */
    public static int getCurrentDayOfWeek(){
//        int dayOfWeek =  Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        int day = dayOfWeek - 1 == 0 ? 7 : dayOfWeek - 1;
//        return day;
        return getCurrentDayOfWeek(Calendar.getInstance());
    }
    public static int getCurrentDayOfWeek(Calendar calendar){
        int dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK);
        int day = dayOfWeek - 1 == 0 ? 7 : dayOfWeek - 1;
        return day;
    }
}
