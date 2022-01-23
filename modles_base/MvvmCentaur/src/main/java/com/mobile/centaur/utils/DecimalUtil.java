package com.mobile.centaur.utils;

import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * 
 * @author zzz
 * 
 */
public class DecimalUtil {

	/**
	 * 最多保留几位小数，就用几个#，最少位就用0来确定
	 */
	private static DecimalFormat mAmountFormat = new DecimalFormat(
			",###,##0.00");
	
	/**
	 * 最多保留几位小数，就用几个#，最少位就用0来确定
	 */
	private static DecimalFormat mAmountFormat1 = new DecimalFormat(
			",###,##0");

	/**
	 * 金钱格式化(保留两位小数点)
	 * 
	 * @author wyqiuchunlong
	 * @param number
	 * @return
	 */
	public static String format(BigDecimal number) {
		if (number == null) {
			return "0.00";
		}
		return number.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}
	/**
	 * 金钱格式化(加逗号)
	 * 
	 * @author wyqiuchunlong
	 * @param number
	 * @return
	 */
	public static String amountFormat(BigDecimal number) {
		if (number == null) {
			return "0.00";
		}
		return mAmountFormat.format(number);
	}
	
	/**
	 * 金钱格式化(加逗号)
	 * 
	 * @author wyqiuchunlong
	 * @param number
	 * @return
	 */
	public static String amountFormat(BigDecimal number, boolean hasDot) {
		if (number == null) {
			return "0.00";
		}
		String result = mAmountFormat.format(number);
		String s = result;
		if(result.contains(".")){
			s = s.substring(0, s.lastIndexOf("."));
		}
		return hasDot ? result : s;
	}
	
	/**
	 * 金钱格式化
	 * 
	 * @author wyqiuchunlong
	 * @param number
	 * @return
	 */
	public static String format(BigDecimal number, int scale) {
		if (number == null) {
			StringBuilder zero = new StringBuilder("0.");
			for (int i = 0; i < scale; i++) {
				zero.append("0");
			}
			return zero.toString();
		}
		return number.setScale(scale, BigDecimal.ROUND_DOWN).toString();
	}

	/**
	 * 金钱格式化
	 * 
	 * @author wyqiuchunlong
	 * @param number
	 * @return
	 */
	public static String format(double number) {
		return mAmountFormat.format(number);
		// return String.format("%.2f", number);
	}

	/**
	 * 分转元
	 * 
	 * @param fen
	 *            分
	 * @return
	 */
	public static BigDecimal toYuan(int fen) {
		return BigDecimal.valueOf(fen).setScale(2, BigDecimal.ROUND_DOWN)
				.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN);
	}

	/**
	 * 元转分
	 * 
	 * @param yuan
	 *            元
	 * @return
	 */
	public static int toFen(BigDecimal yuan) {
		return yuan.multiply(new BigDecimal(100))
				.setScale(0, BigDecimal.ROUND_DOWN).intValueExact();
	}

	/**
	 * 字符串解析为符合标准的字符串
	 * 
	 * @param value
	 *            未格式化的字符串，小数点后两位的数字会截取
	 * @return
	 */
	public static BigDecimal parse(String value) {
		BigDecimal result = new BigDecimal(value);
		return result.setScale(2, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 金额格式化
	 * 
	 * @author wyqiuchunlong
	 * @param amount
	 * @return
	 */
	public static String amountFromat(BigDecimal amount) {

		if (amount == null) {
			amount = BigDecimal.ZERO;
		}
		amount.setScale(2, BigDecimal.ROUND_DOWN);
		// 金额为0，直接返回
		if (amount.signum() == 0) {
			return "0.00";
		}
		return mAmountFormat.format(amount);
	}

	/**
	 * 设置输入框中只能输入小数点后两位小数
	 */
	public  static void setDotNum(CharSequence s, EditText editText) {
		if (s.toString().contains(".")) {
			if (s.length() - 1 - s.toString().indexOf(".") > 2) {
				s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
				editText.setText(s);
				editText.setSelection(s.length());// 光标在小数点后两位时设置光标在最后一位
			}
		}
		if (s.toString().trim().substring(0).equals(".")) {
			// s = "0" + s;
			// editText.setText(s.toString());
			// ed_bianxianlilv.setSelection(2);
		}
		if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
			if (s.toString().startsWith("00")) {
				return;
			}
			if (!s.toString().substring(1, 2).equals(".")) {
				editText.setText(s.subSequence(0, 1));
				return;
			}
		}
	}
}
