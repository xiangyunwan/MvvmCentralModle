package com.zhangzhenzhong1.tools.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName DeviceInfoUtil 
 * @Description 获取手机基本信息
 * @date 2014-3-13 下午5:03:25
 */
public class DeviceInfoUtil {
	private static DeviceInfo deviceInfo = null;
	private static int ram = 0;

	@SuppressWarnings("unused")
	private static final String TAG = "DeviceInfoUtil";

	public static synchronized DeviceInfo getDeviceInfo(Context context) {
		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo();
			// 获取屏幕分辨率
			DisplayMetrics displayMetrics = new DisplayMetrics();
			WindowManager windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			windowManager.getDefaultDisplay().getMetrics(displayMetrics);
			deviceInfo.setScreenWidth(displayMetrics.widthPixels);
			deviceInfo.setScreenHeight(getRawHeight(context, displayMetrics,
					windowManager));
			deviceInfo.setRatio(((float)getRawHeight(context, displayMetrics,
					windowManager))/displayMetrics.widthPixels);

			// 获取DeviceID
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceInfo.setDeviceID(telephonyManager.getDeviceId());
			//SIM 卡iccid
			deviceInfo.setIccid(telephonyManager.getSimSerialNumber());
			// 获取设备型号
			deviceInfo.setDeviceModel(android.os.Build.MODEL);
			// 获取设备系统版本
			deviceInfo.setSystemVersion(android.os.Build.VERSION.RELEASE);

			// 获取软件版本
			PackageManager packageManager = context.getPackageManager();
			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(
						context.getPackageName(), 0);
				deviceInfo.setSoftVersion(packageInfo.versionName);
			} catch (NameNotFoundException e) {
				deviceInfo.setSoftVersion("");
			}
		}
		return deviceInfo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int getRawHeight(Context context,
								   DisplayMetrics displayMetrics, WindowManager windowManager) {
		Display display = windowManager.getDefaultDisplay();
		Class c = null;
		int height = 0;
		try {
			c = Class.forName("android.view.Display");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method method = null;
		try {
			method = c.getMethod("getRawHeight");
		} catch (SecurityException e) {
//			e.printStackTrace();
		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
		}
		try {
			if (method != null) {
				height = (Integer) method.invoke(display);
			} else {
				height = displayMetrics.heightPixels;
			}
		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
		} catch (InvocationTargetException e) {
//			e.printStackTrace();
		}
		return height;
	}

	public static String getDeviceId(Context context) {
		String android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return android_id;
	}

	public static synchronized int getRam() {
		if (ram == 0) {
			String str1 = "/proc/meminfo";// 系统内存信息文件
			String str2;
			String[] arrayOfString;
			long initial_memory = 0;
			try {
				FileReader localFileReader = new FileReader(str1);
				BufferedReader localBufferedReader = new BufferedReader(
						localFileReader, 8192);
				str2 = localBufferedReader.readLine();
				arrayOfString = str2.split("\\s+");
				for (String num : arrayOfString) {
					Log.i(str2, num + "\t");
				}
				initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘�?024转换为Byte
				localBufferedReader.close();
			} catch (IOException e) {
			}
			ram = (int) (initial_memory / 1024 / 1024);
		}
		return ram;
	}
	
	/**
	 * 获得状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
	
	/**
	 * 获得显示应用部分的高度
	 * @param context
	 * @return
	 */
	public static int getWindowHeight(Context context) {
		return getDeviceInfo(context).getScreenHeight() - getStatusBarHeight(context);
	}
	
	/**
	 * 获取可用内存
	 * @param mContext 上下文
	 * @return
	 */
    public static long gainUnusedMemory(Context mContext) {
        long MEM_UNUSED = 0L;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    /**
     * 获取总内存
     * @return
     */
    public static long gainTotalMemory() {
        long mTotal = 0;
        // /proc/meminfo读出的内核信息进行解析
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
            // beginIndex
            int begin = content.indexOf(':');
            // endIndex
            int end = content.indexOf('k');
            // 截取字符串信息

            content = content.substring(begin + 1, end).trim();
            mTotal = Integer.parseInt(content);
        } catch (Exception e) {
        	Log.e(TAG, "获取总内存失败，原因："+e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return mTotal;
    }
    

    /**
     * 获得RAM内存总的大小, 单位MB
     * @return
     */
    public static synchronized long getRamTotalMemory() {
      String str1 = "/proc/meminfo";// 系统内存信息文件
      String str2;
      String[] arrayOfString;
      long initial_memory = 0;

      try {
        FileReader localFileReader = new FileReader(str1);
        BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
        str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

        arrayOfString = str2.split("\\s+");

        initial_memory = Long.valueOf(arrayOfString[1]).longValue();
        localBufferedReader.close();

      } catch (IOException e) {}
      //单位MB
      return (initial_memory/1024);
    }

	/**
	 * 获取签名
	 * @param context
	 * @return
	 */
    public static String getSignure(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo packageInfo = null;
		Signature[] signatures = null;
		StringBuilder sb = new StringBuilder();
		try {
			packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			/******* 通过返回的包信息获得签名数组 *******/
			signatures = packageInfo.signatures;
			/******* 循环遍历签名数组拼接应用签名 *******/
			for (Signature signature : signatures) {
				sb.append(signature.toCharsString());
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		/************** 得到应用签名 **************/
		return sb.toString();
	}

	/**
	 * 接口上传参数加签名加密值，谨慎修改，与服务器端统一校验方式
	 * @return
	 */
	public static String getSignatureStr(Context context) {
		String result = "";
		try {
			String s = getSignure(context);
			String md5 = MD5Util.stringToMD5(MD5Util.stringToMD5(s));
			String base64 = Base64.encodeBytes(new String(md5 + "_JDJR_Android").getBytes());
			result = base64.substring(base64.length() - 16);
		} catch (Exception e) {
		}
		return result;
	}
    
}