package com.mobile.centaur.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class HtmlColorUtil {


	/**
	 * 返回指定颜色的html字体<br/>
	 * 传入color格式不正确时，显示颜色为#999999
	 * @param text
	 * @param colorStr
	 * @return
	 */
	public static String getColorHtmlFont(String text, String colorStr) {

		String color =  StringHelper.isColor(colorStr) ? colorStr : ",\"#999999\"" ;
		String redFontHtmlString = "<font color=" + color + ">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回红色html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getRedHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#DF0024\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回黑色加黑html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getBlackBoldHtmlFont(String text) {
		String redFontHtmlString = "<B><font color=\"#666666\">" + text
				+ "</font></B>";
		return redFontHtmlString;
	}

	/**
	 * 返回绿色html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getGreenHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#6db247\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回红色html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getBaiTiaoRedHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#70b1df\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回红色html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getRedE93A3AHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#e93a3a\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回black999字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getFinanceRedeemHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#999999\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回70b1dfhtml字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getJinkuRedHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#70b1df\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 匹配中文
	 * 
	 * @param str
	 */
	private static String getZhongwen(String str) {
		return Pattern.compile("[\u4E00-\u9FA5]{1,3}").matcher(str)
				.replaceAll("");
	}

	public static String getTextFormatHtml(String text) {
		if(TextUtils.isEmpty(text)){
			return "";
		}
		String reallyNumber = getZhongwen(text);
		String number = HtmlColorUtil.getRedHtmlFont(reallyNumber);
		// 后面单位
		String unit = text.replace(reallyNumber, "");
		String formatText = String.format("%s" + unit, number);
		return formatText;
	}
	/**
	 * 返回蓝色html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getDateBlueHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#359cf5\">" + text
				+ "</font>";
		return redFontHtmlString;
	}
	
	/**
	 * 返回黑色#333333html字体
	 * 
	 * @param text
	 * @return
	 */
	public static String getDateBlack333HtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#333333\">" + text
				+ "</font>";
		return redFontHtmlString;
	}

	/**
	 * 返回橙色#FF801ahtml字体
	 *
	 * @param text
	 * @return
	 */
	public static String getOrangeHtmlFont(String text) {
		String redFontHtmlString = "<font color=\"#FF801a\">" + text
				+ "</font>";
		return redFontHtmlString;
	}
}
