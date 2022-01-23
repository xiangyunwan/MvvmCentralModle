package com.mobile.centaur.utils;

import java.text.DecimalFormat;

/**
 * Created by zhangzhenzhong
 * Date: 2020/11/18
 * Time: 13:23
 * Descriptions：
 */
public class SecToTime {
    public static String getSecToTime(int time) {
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
                return timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "59:59:99";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                return timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
//        return Integer.parseInt(unitFormat(hour))+":"+Integer.parseInt(unitFormat(minute))+":"+Integer.parseInt(unitFormat(second));
//        return new VideoDuration(Integer.parseInt(unitFormat(second)), Integer.parseInt(unitFormat(minute)), Integer.parseInt(unitFormat(hour)));
    }


    public static String getSecToSFMTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00时00分00秒";
        else {
            minute = time / 60;
            if (time < 60) {
                return timeStr = unitFormat(time) + "秒";
            } else if (minute < 60) {
                second = time % 60;
                return timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "59时59分99秒";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                return timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
//        return Integer.parseInt(unitFormat(hour))+":"+Integer.parseInt(unitFormat(minute))+":"+Integer.parseInt(unitFormat(second));
//        return new VideoDuration(Integer.parseInt(unitFormat(second)), Integer.parseInt(unitFormat(minute)), Integer.parseInt(unitFormat(hour)));
    }
    public static String getSecToSFMTime(long time) {
        String timeStr = null;
        Long hour = 0l;
        Long minute = 0l;
        Long second = 0l;
        if (time <= 0)
            return "00时00分00秒";
        else {
            minute = time / 60;
            if (time < 60) {
                return timeStr = unitFormat(time) + "秒";
            } else if (minute < 60) {
                second = time % 60;
                return timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "59时59分99秒";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                return timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
//        return Integer.parseInt(unitFormat(hour))+":"+Integer.parseInt(unitFormat(minute))+":"+Integer.parseInt(unitFormat(second));
//        return new VideoDuration(Integer.parseInt(unitFormat(second)), Integer.parseInt(unitFormat(minute)), Integer.parseInt(unitFormat(hour)));
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }
    private static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + i;
        else
            retStr = "" + i;
        return retStr;
    }
    public static String getSecToHour(int time) {
        int h = time / 3600;
        int m = time - h * 3600;
        float percentM = (float) m / (float) 3600;
        // #.#-1位， #.##-2位，#.###-3位
        DecimalFormat df = new DecimalFormat("#.#");
        String obj1 = df.format(percentM);
        double obj2 = Double.parseDouble(obj1);

        return String.valueOf(h + obj2);
    }

    public static String getSecToMinute(int time) {
        int h = time / 60;

        return h + "";
    }
}
