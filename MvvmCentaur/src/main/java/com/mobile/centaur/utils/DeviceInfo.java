package com.mobile.centaur.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * @ClassName DeviceInfo
 * @Description
 * @date 2014-3-12 上午11:14:31
 */
public class DeviceInfo implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -240950736752565821L;

	/**
	 * @Fields 屏幕横向分辨率
	 */
	private int screenWidth;

	/**
	 * @Fields 屏幕纵向分辨率
	 */
	private int screenHeight;

	/**
	 * @Fields 屏幕高宽比
	 */
	private float ratio;

	/**
	 * @return the ratio
	 */
	public float getRatio() {
		return ratio;
	}

	/**
	 * @param ratio
	 *            the ratio to set
	 */
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	/**
	 * @Fields 设备型号（哪个厂家的某个机型）
	 */
	private String deviceModel;

	/**
	 * @Fields 当前安装的软件版本
	 */
	private String softVersion;

	/**
	 * @Fields 当前系统版本
	 */
	private String systemVersion;

	/**
	 * @Fields deviceId 设备唯一编号
	 */
	private String deviceID;

	/**
	 * @Fields iccID SIM卡编号
	 */
	private String iccID;

	/**
	 * 签名加密后字符串
	 */
	private String sign;
	
	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth < screenHeight ? screenWidth : screenHeight;
	}

	/**
	 * @param screenWidth
	 *            the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight > screenWidth ? screenHeight : screenWidth;
	}

	/**
	 * @param screenHeight
	 *            the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}

	/**
	 * @param deviceModel
	 *            the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	/**
	 * @return the softVersion
	 */
	public String getSoftVersion() {
		return softVersion;
	}

	/**
	 * @param softVersion
	 *            the softVersion to set
	 */
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	/**
	 * @return the systemVersion
	 */
	public String getSystemVersion() {
		return systemVersion;
	}

	/**
	 * @param systemVersion
	 *            the systemVersion to set
	 */
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		if (deviceID != null) {
			return deviceID;
		}
		return "";
	}

	/**
	 * @param deviceID
	 *            the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	/**
	 * @return the Iccid
	 */
	public String getIccid() {
		if (iccID != null) {
			return iccID;
		}
		return "";
	}

	/**
	 * @param iccId
	 *            the Iccid to set
	 */
	public void setIccid(String iccId) {
		this.iccID = deviceID;
	}

	/**
	 * 获得签名特征值
	 * @return
	 */
	public String getSigntureStr(Context context) {
		if (TextUtils.isEmpty(sign)) {
			sign = DeviceInfoUtil.getSignatureStr(context);
		}
		return sign;
	}
}
