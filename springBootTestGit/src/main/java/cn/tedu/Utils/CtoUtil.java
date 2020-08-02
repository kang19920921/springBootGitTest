package cn.tedu.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CtoUtil {
	
	
	/**
	 * 去掉中文字符
	 * @param str
	 * @return
	 */
	public static String trimChinese(String str) {
		Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher mat = pat.matcher(str);
		str = mat.replaceAll("");
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(trimChinese("123456中文字符789"));
	}

}
