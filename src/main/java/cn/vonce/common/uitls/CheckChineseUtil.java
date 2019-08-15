package cn.vonce.common.uitls;

import java.util.regex.Pattern;

public class CheckChineseUtil {

	public static void main(String[] args) {
		// 纯英文
		String s1 = "Hello,Tom.!@#$%^&*()_+-={}|[];':\"?";
		// 纯中文（不含中文标点）
		String s2 = "你好中国";
		// 纯中文（含中文标点）
		String s3 = "你好，中国。《》：“”‘'；（）【】！￥、";
		// 韩文
		String s4 = "한국어난";
		// 日文
		String s5 = "ぎじゅつ";
		// 特殊字符
		String s6 = "��";
		String s7 = "╃";
		String s8 = "╂";
		// 繁体中文
		String s9 = "蒼老師";
		// 1 使用字符范围判断
		System.out.println("s1是否包含中文：" + hasChineseByRange(s1));// false
		System.out.println("s2是否包含中文：" + hasChineseByRange(s2));// true
		System.out.println("s3是否包含中文：" + hasChineseByRange(s3));// true
		System.out.println("s4是否包含中文：" + hasChineseByRange(s4));// false
		System.out.println("s5是否包含中文：" + hasChineseByRange(s5));// false
		System.out.println("s6是否包含中文：" + hasChineseByRange(s6));// false
		System.out.println("s7是否包含中文：" + hasChineseByRange(s7));// false
		System.out.println("s8是否包含中文：" + hasChineseByRange(s8));// false
		System.out.println("s9是否包含中文：" + hasChineseByRange(s9));// true
		System.out.println("-------分割线-------");
		System.out.println("s1是否全是中文：" + isChineseByRange(s1));// false
		System.out.println("s2是否全是中文：" + isChineseByRange(s2));// true
		System.out.println("s3是否全是中文：" + isChineseByRange(s3));// false
																// 中文标点不在范围内
		System.out.println("s4是否全是中文：" + isChineseByRange(s4));// false
		System.out.println("s5是否全是中文：" + isChineseByRange(s5));// false
		System.out.println("s6是否全是中文：" + isChineseByRange(s6));// false
		System.out.println("s7是否全是中文：" + isChineseByRange(s7));// false
		System.out.println("s8是否全是中文：" + isChineseByRange(s8));// false
		System.out.println("s9是否全是中文：" + isChineseByRange(s9));// true
		System.out.println("-------分割线-------");
		// 2 使用字符范围正则判断（结果同1）
		System.out.println("s1是否包含中文：" + hasChineseByReg(s1));// false
		System.out.println("s2是否包含中文：" + hasChineseByReg(s2));// true
		System.out.println("s3是否包含中文：" + hasChineseByReg(s3));// true
		System.out.println("s4是否包含中文：" + hasChineseByReg(s4));// false
		System.out.println("s5是否包含中文：" + hasChineseByReg(s5));// false
		System.out.println("s6是否包含中文：" + hasChineseByReg(s6));// false
		System.out.println("s7是否包含中文：" + hasChineseByReg(s7));// false
		System.out.println("s8是否包含中文：" + hasChineseByReg(s8));// false
		System.out.println("s9是否包含中文：" + hasChineseByReg(s9));// true
		System.out.println("-------分割线-------");
		System.out.println("s1是否全是中文：" + isChineseByReg(s1));// false
		System.out.println("s2是否全是中文：" + isChineseByReg(s2));// true
		System.out.println("s3是否全是中文：" + isChineseByReg(s3));// false 中文标点不在范围内
		System.out.println("s4是否全是中文：" + isChineseByReg(s4));// false
		System.out.println("s5是否全是中文：" + isChineseByReg(s5));// false
		System.out.println("s6是否全是中文：" + isChineseByReg(s6));// false
		System.out.println("s7是否全是中文：" + isChineseByReg(s7));// false
		System.out.println("s8是否全是中文：" + isChineseByReg(s8));// false
		System.out.println("s9是否全是中文：" + isChineseByReg(s9));// true
		System.out.println("-------分割线-------");
		// 3 使用CJK字符集判断
		System.out.println("s1是否包含中文：" + hasChinese(s1));// false
		System.out.println("s2是否包含中文：" + hasChinese(s2));// true
		System.out.println("s3是否包含中文：" + hasChinese(s3));// true
		System.out.println("s4是否包含中文：" + hasChinese(s4));// false
		System.out.println("s5是否包含中文：" + hasChinese(s5));// false
		System.out.println("s6是否包含中文：" + hasChinese(s6));// false
		System.out.println("s7是否包含中文：" + hasChinese(s7));// false
		System.out.println("s8是否包含中文：" + hasChinese(s8));// false
		System.out.println("s9是否包含中文：" + hasChinese(s9));// true
		System.out.println("-------分割线-------");
		System.out.println("s1是否全是中文：" + isChinese(s1));// false
		System.out.println("s2是否全是中文：" + isChinese(s2));// true
		System.out.println("s3是否全是中文：" + isChinese(s3));// true 中文标点也被包含进来
		System.out.println("s4是否全是中文：" + isChinese(s4));// false
		System.out.println("s5是否全是中文：" + isChinese(s5));// false
		System.out.println("s6是否全是中文：" + isChinese(s6));// false
		System.out.println("s7是否全是中文：" + isChinese(s7));// false
		System.out.println("s8是否全是中文：" + isChinese(s8));// false
		System.out.println("s9是否全是中文：" + isChinese(s9));// true
	}

	/**
	 * 是否包含中文字符<br>
	 * 包含中文标点符号<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		if (str == null) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否全是中文字符<br>
	 * 包含中文标点符号<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		if (str == null) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否是中文字符<br>
	 * 包含中文标点符号<br>
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C) {
			return true;
		} else if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D) {
			return true;
		} else if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		} else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 是否包含汉字<br>
	 * 根据汉字编码范围进行判断<br>
	 * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasChineseByReg(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str).find();
	}

	/**
	 * 是否全是汉字<br>
	 * 根据汉字编码范围进行判断<br>
	 * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseByReg(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否包含汉字<br>
	 * 根据汉字编码范围进行判断<br>
	 * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasChineseByRange(String str) {
		if (str == null) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (c >= 0x4E00 && c <= 0x9FBF) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否全是汉字<br>
	 * 根据汉字编码范围进行判断<br>
	 * CJK统一汉字（不包含中文的，。《》（）“‘'”、！￥等符号）<br>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseByRange(String str) {
		if (str == null) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (c < 0x4E00 || c > 0x9FBF) {
				return false;
			}
		}
		return true;
	}

}
