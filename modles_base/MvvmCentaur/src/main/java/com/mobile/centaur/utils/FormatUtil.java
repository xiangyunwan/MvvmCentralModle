package com.mobile.centaur.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 一些格式的判断
 * 
 */
public class FormatUtil {
	/**
	 * 判断是否是邮箱地址
	 * 
	 * @param value
	 * @return
	 */  
	public static boolean isEmail(String value) {
		Pattern pattern = Pattern
				.compile("^[\\w\\.-]+(\\.[\\w\\.-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是全是数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNumber(String value) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否是float类型数据
	 * 
	 * @author mengpeipei
	 * @param value
	 * @return
	 */
	public static boolean isFloatNumber(String value) {
		 
			Pattern pattern = Pattern.compile("^[0-9|-]?\\d*\\.?\\d*$");
			Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) {
				return true;
			}
			return false;
	}

	/**
	 * 判断是否是手机号
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isMobile(String value) {
		Pattern pattern = Pattern.compile("1\\d{10}");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是否是电话号码
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isPhoneNum(String value) {
		Pattern pattern = Pattern.compile("\\d{7}|\\d{8}|\\d{11}|\\d{3}-\\d{8}|\\d{4}-\\d{7}");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断银行卡号是否合法
	 * 
	 * @param bankNo
	 * @return
	 */
	public static boolean luhmCheck(String bankNo) {
		if (bankNo != null && bankNo.length() > 10) {
			int lastNum = Integer.parseInt(bankNo.substring(
					bankNo.length() - 1, bankNo.length()));
			String first15Num = bankNo.substring(0, bankNo.length() - 1);
			List<String> newArr = new ArrayList<String>();
			for (int i = first15Num.length() - 1; i > -1; i--) { // 前15或18位倒序存进数组
				newArr.add(first15Num.substring(i, i + 1));
			}
			List<Integer> arrJiShu = new ArrayList<Integer>();//
			List<Integer> arrJiShu2 = new ArrayList<Integer>();//
			List<Integer> arrOuShu = new ArrayList<Integer>();//
			for (int i = 0; i < newArr.size(); i++) {
				if ((i + 1) % 2 == 1) {
					if (Integer.parseInt(newArr.get(i)) * 2 < 9) {
						arrJiShu.add(Integer.parseInt(newArr.get(i)) * 2);
					} else {
						arrJiShu2.add(Integer.parseInt(newArr.get(i)) * 2);
					}
				} else {
					arrOuShu.add(Integer.parseInt(newArr.get(i)));
				}

			}
			List<Integer> jishu_child1 = new ArrayList<Integer>();//
			List<Integer> jishu_child2 = new ArrayList<Integer>();//
			for (int h = 0; h < arrJiShu2.size(); h++) {
				jishu_child1.add(arrJiShu2.get(h) % 10);
				jishu_child2.add(arrJiShu2.get(h) / 10);
			}
			int sumJiShu = 0; // 奇数位*2 < 9 的数组之和
			int sumOuShu = 0; // 偶数位数组之和
			int sumJiShuChild1 = 0; // 奇数位*2 >9 的分割之后的数组个位数之和
			int sumJiShuChild2 = 0; // 奇数位*2 >9 的分割之后的数组十位数之和
			int sumTotal = 0;
			for (int m = 0; m < arrJiShu.size(); m++) {
				sumJiShu = sumJiShu + arrJiShu.get(m);
			}
			for (int n = 0; n < arrOuShu.size(); n++) {
				sumOuShu = sumOuShu + arrOuShu.get(n);
			}
			for (int p = 0; p < jishu_child1.size(); p++) {
				sumJiShuChild1 = sumJiShuChild1 + jishu_child1.get(p);
				sumJiShuChild2 = sumJiShuChild2 + jishu_child2.get(p);
			}
			sumTotal = sumJiShu + sumOuShu + sumJiShuChild1 + sumJiShuChild2;
			// 计算Luhm值ֵ
			int k = sumTotal % 10 == 0 ? 10 : sumTotal % 10;
			int luhm = 10 - k;
			if (lastNum == luhm) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static String getStarBankNumber(String number) {
		if (number != null && number.length() > 4) {
			String partNumber = number.substring(number.length() - 4,
					number.length());
			StringBuilder sb = new StringBuilder();
			sb.append("**");
			sb.append(partNumber);
			return sb.toString();
		}
		return "******";
	}
	
	/**
	 * 获取银行卡号
	 * 
	 * @return
	 */
	public static String getBankCardNumber(String bankNum) {
		StringBuffer buffer = new StringBuffer(bankNum);
		int index = 0;
		while (index < buffer.length()) {
			if (buffer.charAt(index) == ' ') {
				buffer.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return buffer.toString();
	}
}
