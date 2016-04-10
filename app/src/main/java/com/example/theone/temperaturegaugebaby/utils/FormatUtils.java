package com.example.theone.temperaturegaugebaby.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 作者：陈冬冬
 * 
 * 说明：格式化数据的工具类
 * 
 * 时间：2015-11-17 下午5:22:12
 * 
 */
public class FormatUtils {
	/**
	 * 得到一个格式化的时间
	 * 
	 * @param time
	 *            时间 毫秒
	 * @return 时：分：秒：毫秒
	 */
	public static String getFormatTime(long time) {
		time = time / 1000;
		long second = time % 60;
		long minute = (time % 3600) / 60;
		long hour = time / 3600;

		// 毫秒秒显示两位
		// String strMillisecond = "" + (millisecond / 10);
		// 秒显示两位
		String strSecond = ("00" + second).substring(("00" + second).length() - 2);
		// 分显示两位
		String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
		// 时显示两位
		String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

		return strHour + ":" + strMinute + ":" + strSecond;
	}
	/**
	 * 格式化成1位小数
	 * @param time
	 * @return
	 */
	public static String formatFloat1(double time) {
		// 格式化小数的位数
		String pattern = "0";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(time);
	}
	
	/**
	 * 格式化成1位小数
	 * @param time
	 * @return
	 */
	public static float formatFloat1Tofloat(double time) {
		// 格式化小数的位数
		String pattern = "0.#";
		DecimalFormat df = new DecimalFormat(pattern);
		return Float.parseFloat(df.format(time));
	}
	/**
	 * 格式化成2位小数
	 * @param time
	 * @return
	 */
	public static String formatFloat2(double time) {
		// 格式化小数的位数
		String pattern = "0.0#";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(time);
	}
	
	/**
	 * 将date格式化为yyyy-MM-dd
	 */
	public static String dateToyyyyMMdd(Date date){
		SimpleDateFormat myFm = new SimpleDateFormat("yyyy-MM-dd");
		String format = myFm.format(date);
		return format;
	}
	
	/**
	 * 获取今天，格式为yyyy-MM-dd
	 */
	public static Date getToday(){
		Date date=new Date();
		return date;
	}
}
