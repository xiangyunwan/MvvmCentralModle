package com.mobile.centaur.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Descirption:
 * @Author zzz
 * @Date 2022/1/5
 **/
public class WeekUtil {
    private static String[] weeks = new String[] { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
    private static String[] weeks2 = new String[] { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

    public static enum Gap {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN
    }

    /**
     * 获取今天相隔gap天之后是星期几
     *
     * @param today
     * @param gap
     *            间隔的天数
     * @return 间隔天数之后是周几
     */
    public static String getOneDayInWeek(String today, int gap) {
        int todayInWeek = getTodayInWeek(today);
        int onDayInWeek = (todayInWeek + gap) % weeks.length;
        return weeks[onDayInWeek];
    }

    private static int getTodayInWeek(String today) {
        int length = -1;
        if (today != null && !"".equals(today) && today.contains("星期")) {
            length = weeks.length;
            for (int i = 0; i < length; i++) {
                if (weeks[i].equals(today)) {
                    return i;
                }
            }
        } else if (today != null && !"".equals(today) && today.contains("周")) {
            length = weeks2.length;
            for (int i = 0; i < length; i++) {
                if (weeks2[i].equals(today)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 是否是晚上（19~7）
     *
     * @return
     */
    public static boolean isNightTime() {
        boolean isNightTime = false;
        int hour = getCurrentHour(true);
        switch (hour) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                isNightTime = true;
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                isNightTime = false;
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                isNightTime = true;
                break;
            default:
                break;
        }
        return isNightTime;
    }

    /**
     * 获取当前的时间，如：09:40
     *
     * @param is24
     *            是否是24小时制
     * @return
     */
    public static String getCurrentTime(boolean is24) {
        return new StringBuilder().append(getCurrentHour(is24)).append(":").append(getCurrentMINUTE()).toString();
    }

    public static int getCurrentHour(boolean is24) {
        if (is24)
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        else {
            return Calendar.getInstance().get(Calendar.HOUR);
        }
    }

    public static int getCurrentMINUTE() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 根据pm2.5指数去获取对应的等级
     *
     * @param pm25Value
     *            pm2.5指数
     * @return 等级:优，良，差等
     */
    public static String getPM25Level(int pm25Value) {
        String pm25Level = null;
        if (pm25Value >= 0 && pm25Value <= 50) {
            pm25Level = "优";
        } else if (pm25Value > 50 && pm25Value <= 100) {
            pm25Level = "良";
        } else if (pm25Value > 100 && pm25Value <= 150) {
            pm25Level = "轻度污染";
        } else if (pm25Value > 150 && pm25Value <= 200) {
            pm25Level = "中度污染";
        } else if (pm25Value > 200 && pm25Value <= 300) {
            pm25Level = "重度污染";
        } else if (pm25Value > 300 && pm25Value <= 500) {
            pm25Level = "严重污染";
        } else { // 大于500
            pm25Level = "已经不能生存了";
        }
        return pm25Level;
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
}
