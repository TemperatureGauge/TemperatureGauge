package com.example.theone.temperaturegaugebaby.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * 
 * 作者：陈冬冬
 * 
 * 说明：给TextView设置不同的颜色，大小
 * 
 * 时间：2015-11-13 下午4:13:53
 * 
 */
public class TextStyleUtils {

	/**
	 * 
	 * @param str
	 *            要设置不同大小的字符串
	 * @param text
	 *            整个TextView文本字符串
	 * @param textSize
	 *            要设置字体的大小
	 * @return
	 */
	public static SpannableString setTextSizeStyle(String str, String text, int textSize) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new AbsoluteSizeSpan(textSize, true), text.indexOf(str), text.indexOf(str) + str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return span;
	}

	/**
	 * 
	 * @param str
	 *            要设置不同颜色的字符串
	 * @param text
	 *            整个TextView文本字符串
	 * @param textColor
	 *            要设置字体的颜色
	 * @return
	 */
	public static SpannableString setTextColorStyle(String str, String text, int textColor) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new ForegroundColorSpan(textColor), text.indexOf(str), text.indexOf(str) + str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return span;
	}

	/**
	 * 
	 * @param str
	 *            要设置不同颜色,大小的字符串
	 * @param text
	 *            整个TextView文本字符串
	 * @param textSize
	 *            要设置字体的大小
	 * @param textColor
	 *            要设置字体的颜色
	 * @return
	 */
	public static SpannableString setTextStyle(String str, String text, int textSize, int textColor) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new AbsoluteSizeSpan(textSize, true), text.indexOf(str), text.indexOf(str) + str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		span.setSpan(new ForegroundColorSpan(textColor), text.indexOf(str), text.indexOf(str) + str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		return span;
	}

}
