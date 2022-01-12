package com.mobile.centaur.utils;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符串处理相关类
 */
public class StringHelper {
	
	/**
	 * 判断是否含有中文
	 * @param str 目标字符串
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		try {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
			if (m.find()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 得到 全拼
	 * 
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 得到首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getHeadChar(String str) {

		String convert = "";
		char word = str.charAt(0);
		String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
		if (pinyinArray != null) {
			convert += pinyinArray[0].charAt(0);
		} else {
			convert += word;
		}
		return convert.toUpperCase();
	}

	/**
	 * 得到中文首字母缩写
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert.toUpperCase();
	}

	/**
	 * 处理后台返回类似#FFFFFF_0.4的格式转化为#66FFFFFF的颜色格式
	 * @param strColor
	 * @param defaultColor
	 * @return int 转化后的色值
	 */
	public static int getColorWithAlpha(String strColor, String defaultColor) {
		if (TextUtils.isEmpty(strColor)) {
			return Color.parseColor(defaultColor);
		}
		if (isColor(strColor)) {
			return Color.parseColor(strColor.trim());
		}
		
		float alpha = 0;
		if(strColor.contains("_")) { // 将类似#FFFFFF_0.4的格式转化为#66FFFFFF
			String[] split = strColor.split("_");
			if(split.length > 1) {
				strColor = split[0];
				alpha = Float.parseFloat(split[1]);
			}
			if(strColor.startsWith("#") && strColor.length() == 7) {
				strColor = strColor.replace("#", "#"+ Integer.toHexString((int) (alpha * 255 + 0.5f)));
				return Color.parseColor(strColor);
			}
		}
		
		return Color.parseColor(defaultColor);
	}
	
	/**
	 * 判断从接口拿到的color是否可用，可用的话返回可用的color，否则返回默认的
	 * @param strColor
	 * @param defaultColor
	 * @return
	 */
	public static int getColor(String strColor, String defaultColor) {
		String color = isColor(strColor) ? strColor.trim() : defaultColor;
		return Color.parseColor(color);
	}
	
	/**
	 * 判断从接口拿到的color是否可用，可用的话返回可用的color，
	 * 否则返回-1,特别注意，当返回-1时不要使用！所以使用前需要判断
	 * @param strColor
	 * @return
	 */
	public static int getColor(String strColor) {
		if(isColor(strColor)){
			return Color.parseColor(strColor.trim());
		}
		return -1;
	}
	
	/**
	 * 判断从接口拿到的color是否可用，可用的话返回可用的color，否则返回默认的
	 * @param strColor 接口数据
	 * @param defaultColor 默认颜色
	 * @return
	 */
	public static int getColor(String strColor, int defaultColor) {
		return isColor(strColor) ? Color.parseColor(strColor.trim()) : defaultColor;
	}
	
	/**
	 * 判断是否是Color的格式，即#后3、6、8位16进制的格式，返回true后使用str要做trim()过滤空格
	 * @param str
	 * @return 是否是颜色格式
	 */
	public static boolean isColor(String str) {
		if(TextUtils.isEmpty(str)) {
			return false;
		}
		str = str.trim();
		if(!str.startsWith("#") || (str.length() != 4 && str.length() != 7 && str.length() != 9)) {
			return false;
		}
		
		String pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}|[A-Fa-f0-9]{8})$";
		return Pattern.matches(pattern, str);
	}
	
	public static boolean isNumeric(String str){
		if(TextUtils.isEmpty(str)) {
			return false;
		}
		
		for (int i = str.length()-1 ; i >= 0; i--) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 电话号码使用*隐藏部分数字
	 * @param num
	 * @return
	 */
	public static String getHiddenPhoneNum(String num) {
		if(TextUtils.isEmpty(num)) {
			return "";
		}
		
		char[] cs = num.toCharArray();
		int len = cs.length;
		if(num.length() >= 11) {
			for(int i = len - 5; i >= len - 8 ; i--) {
				cs[i] = '*';
			}
		} else if (num.length() > 7) {
			for(int i = 3; i <= len - 5; i ++) {
				cs[i] = '*';
			}
		} else {
			return num;
		}
		
		return new String(cs);
	}
	
	/**
	 * 姓名使用*隐藏部分文字
	 * @param name
	 * @return
	 */
	public static String getHiddenRealName(String name) {
		if(TextUtils.isEmpty(name)) {
			return "";
		}
		
		int len = name.length();
		if(len <= 1) {
			return name;
		}
		
		return "*" + name.substring(1);
	}
	
	/**
	 * 身份证号使用*隐藏部分数字
	 * @param num
	 * @return
	 */
	public static String getHiddenIdentityNum(String num) {
		if(TextUtils.isEmpty(num)) {
			return "";
		}
		
		int l = num.length();
		if(l <= 7) {
			return num;
		}
		
		char[] cs = num.toCharArray();
		int len = cs.length;
		for(int i = 3; i <= len - 5; i ++) {
			cs[i] = '*';
		}
		
		return new String(cs);
	}
	
	/**
	 * 匹配短信中间的6个数字（验证码等）
	 * 
	 * @param patternContent
	 * @return
	 */
	public static String patternSmsCode(String patternContent) {
		if (TextUtils.isEmpty(patternContent)) {
			return "";
		}
		Pattern p = Pattern.compile("(?<!\\d)\\d{6}(?!\\d|\\.)");
		Matcher matcher = p.matcher(patternContent);
		String result = "";
		if (matcher.find()) {
			result = matcher.group();
		}
		return result;
	}

	/**
	 * 设置Text中部分文字变色
	 * @param text
	 * @param markText
	 * @param markColor
	 */
	public static SpannableStringBuilder setMarkedTextColor(String text, String markText, String markColor) {
		if (text == null) {
			text = "";
		}
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		if (isColor(markColor)) {
			ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(markColor));
			int start = text.indexOf(markText);
			if (start >= 0)
				builder.setSpan(span, start, start + markText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		return builder;
	}


	/**
	 * 文字中包含类似“#123#”，以成对#来标记变色的
	 *
	 * @param statusMsg
	 * @param view
	 */
	public static void displayStatusMsg(String statusMsg, TextView view , String color) {
		if (TextUtils.isEmpty(statusMsg)) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
			StringBuilder stringBuilder = new StringBuilder();
			if (statusMsg.contains("#")) {
				// 兼容多个变色区域
				String str[] = statusMsg.split("#");
				for (int i = 0; i < str.length; i++) {
					if (i % 2 == 0) {
						stringBuilder.append(str[i] == null ? " " : str[i]);
					} else {
						String colorString =
								HtmlColorUtil.getColorHtmlFont(str[i] == null ? " " : str[i], color);
						stringBuilder.append(colorString);
					}
				}
				view.setText(Html.fromHtml(stringBuilder.toString()));
			} else {
				view.setText(statusMsg);
			}
		}
	}
}
