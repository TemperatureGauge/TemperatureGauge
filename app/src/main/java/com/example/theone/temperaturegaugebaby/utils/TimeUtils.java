package com.example.theone.temperaturegaugebaby.utils;

import android.text.format.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mjd on 2014/12/11.
 */
public class TimeUtils {
//	static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * unix时间戳 转化成 北京时间
	 * 
	 * @param secondTime
	 *            long
	 * @return String "yyyy-MM-dd HH:mm:ss"
	 * */
	public static String unixTimeToBeijingTime(long secondTime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		f.setTimeZone(TimeZone.getTimeZone("GTM"));
		return f.format(new Date(secondTime * 1000l));
	}

	public static String unixTimeToBeijingTime(long secondTime, SimpleDateFormat f) {

//		f.setTimeZone(TimeZone.getTimeZone("GTM"));
		return f.format(new Date(secondTime * 1000l));
	}

	/**
	 * 获取当前的时间（UNIX时间戳形式）
	 * 
	 * @return long
	 * */
	public static long getCurrentTimeUnix() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取当前的时间（北京时间形式）
	 * 
	 * @return String "yyyy-MM-dd HH:mm:ss"
	 * */
	public static String getCurrentTimeBeijing() {
		return unixTimeToBeijingTime(getCurrentTimeUnix());
	}

	public static String getCurrentTimeBeijing(SimpleDateFormat f) {
		return unixTimeToBeijingTime(getCurrentTimeUnix(), f);
	}

	/**
	 * 将 yyyy-MM-dd HH:mm:ss 时间转化成 long 时间
	 * */
	public static long changeStrDateToLongDate(String strDate) {

		Date date = null;
		try {

			date = f.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime() / 1000;
	}

	/**
	 * 将 yyyy年MM月dd日 时间转化成 long 时间
	 * 
	 * */
	public static long changeStrDateToLongDate2(String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime() / 1000;
	}

	/**
	 * 将yyyy-MM-dd 时间转化成 long 时间
	 * 
	 * */
	public static long changeStrDateToLongDate3(String strDate) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime() / 1000;
	}

	public static String getTimeBefore(String strDate) {
		long m = getCurrentTimeUnix() - changeStrDateToLongDate(strDate);
		if (m < 60) {
			return "1分钟前";
		} else if (60 <= m && m < 3600) {
			return (int) (m / 60) + "分钟前";
		} else if (3600 <= m && m < 86400) {
			return (int) (m / 3600) + "小时前";
		} else if (86400 <= m) {
			return (int) (m / 86400) + "天前";
		}
		return null;

	}

	/**
	 * 根据日期获得当前日期是周几
	 * 
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return 1,2,3,4,5,6,7 周一，周二，周三，周四，周五，周六，周日
	 * */
	public static int getWeekByDate(String date) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(5, 7));
		int day = Integer.valueOf(date.substring(8, 10));
		return getWeek(year, month, day);
	}

	/**
	 * 根据日期获得当前日期是周几
	 *
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return 周一，周二，周三，周四，周五，周六，周日
	 * */
	public static String getWeekByDate2(String date) {
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(5, 7));
		int day = Integer.valueOf(date.substring(8, 10));

		int w = getWeek(year, month, day);
		String week = null;
		switch (w) {
		case 1:
			week = "周一";
			break;
		case 2:
			week = "周二";
			break;
		case 3:
			week = "周三";
			break;
		case 4:
			week = "周四";
			break;
		case 5:
			week = "周五";
			break;
		case 6:
			week = "周六";
			break;
		case 7:
			week = "周日";
			break;
		}

		return week;
	}

	/**
	 * 获取周几
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return int
	 * */
	public static int getWeek(int year, int month, int day) {
		if (month == 1 || month == 2) {
			month += 12;
			year--;
		}
		int w = (day + 2 * month + 3 * (month + 1) / 5 + year + year / 4 - year / 100 + year / 400) % 7;
		return w + 1;
	}

	public static int getWeekByDate(String date, String myFm) throws ParseException {
		SimpleDateFormat fm = new SimpleDateFormat(myFm);
		Date mdate = fm.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mdate);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(String date) {
		if (date != null && !"".equalsIgnoreCase(date)) {
			int yearIndex = date.indexOf("年");
			int monthIndex = date.indexOf("月");
			int dayIndex = date.indexOf("日");
			int year = Integer.valueOf(date.substring(0, yearIndex));
			int month = Integer.valueOf(date.substring(yearIndex + 1, monthIndex));
			/*
			 * if(month<10){ //
			 * 
			 * }
			 */
			int day = Integer.valueOf(date.substring(monthIndex + 1, dayIndex));
			return getWeek(year, month, day);
		} else {
			return 0;
		}
	};

	/**
	 * 
	 * @param time
	 * @return long型 秒值
	 */
	public static long getSeconds(String time) {
		if (time != null) {
			if (time.contains(":")) {
				String[] my = time.split(":");
				int hour = Integer.parseInt(my[0]);
				int min = Integer.parseInt(my[1]);
				int sec = Integer.parseInt(my[2]);
				long totalSec = (hour * 3600 + min * 60 + sec);
				return totalSec;
			} else {
				return Long.parseLong(time);
			}
		} else {
			return 0;
		}

	}

	/**
	 * 
	 * @param sec
	 *            秒值
	 * @return 时：分：秒格式时间
	 */
	public static String getDateInSeconds(long sec) {
		int hours = (int) (sec / 3600);
		if (hours >= 24) {
			hours = hours % 24;
		}
		String hour = String.valueOf(hours);
		String min = String.valueOf((sec % 3600) / 60);
		String second = String.valueOf(sec % 60);
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		if (second.length() == 1) {
			second = "0" + second;
		}
		return hour + ":" + min + ":" + second;
	}

	/**
	 * 
	 * @param sec
	 *            秒值
	 * @return 时:分 格式时间
	 */
	public static String getDateInMin(long sec) {
		int hours = (int) (sec / 3600);
		if (hours >= 24) {
			hours = hours % 24;
		}
		String hour = String.valueOf(hours);
		String min = String.valueOf((sec % 3600) / 60);
		String second = String.valueOf(sec % 60);
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		if (second.length() == 1) {
			second = "0" + second;
		}
		return hour + ":" + min;
	}

	/**
	 * 
	 * @param sec
	 * @return 以小时为单位的 格式的时间
	 */
	public static String getDateInHours(long sec, boolean setMinValue) {
		float hours = sec / 3600f;
		DecimalFormat format = new DecimalFormat("#.#");
		String temp = format.format(hours);
		if (setMinValue && Float.parseFloat(temp) < 0.1f && Float.parseFloat(temp) > 0f) {
			temp = "0.1";
		}
		return temp;
	}

	public static int getAge(String birthday) {
		int age = 0;
		if (birthday != null) {

			Date d = new Date();
			age = d.getYear() - Integer.parseInt(birthday.substring(0, 4));
		} else {

		}
		return age;
	}

	public static String getMill2Hour(long millSec) {
		float time = (millSec / 3600f);
		if (time <= 0.1f) {
			time = 0.10f;
		}
		String timeStr = String.valueOf(time);
		if (timeStr.length() - timeStr.indexOf(".") < 2) {
			timeStr = timeStr + 0;
		}
		return timeStr.substring(0, timeStr.indexOf(".") + 2);
	}

	public static boolean isToday(String date) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = new Date();
		try {
			date2 = sdFormat.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return DateUtils.isToday(date2.getTime());
	}

	public static String getToday() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = new Date(System.currentTimeMillis());
		return sdFormat.format(date2);
	}

	/**
	 * 判断某个日期是否是上周的
	 * 
	 * @param lastTime
	 * @return
	 */
	public static boolean isLastWeek(long lastTime) {

		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);

		// 如果是本周周一保存的直接返回false
		if (areSameDay(lastTime, cal.getTimeInMillis())) {
			return false;
		}
		if (cal.getTimeInMillis() > lastTime) {
			// 上周
			return true;
		} else {
			// 本周
			return false;
		}
	}

	/**
	 * 判断两天是否是同一天
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean areSameDay(long time1, long time2) {
		Calendar calDateA = Calendar.getInstance();
		calDateA.setTimeInMillis(time1);

		Calendar calDateB = Calendar.getInstance();
		calDateB.setTimeInMillis(time2);

		return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
				&& calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取本周周日的时间
	 * 
	 * @return
	 */
	public static long sunLongTime() {
		Calendar cal = Calendar.getInstance();
		// Log.e("TimeUtils", "今天的日期: " + cal.getTime());
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		// Log.e("TimeUtils", "本周第一天: " + cal.getTime());
		cal.add(Calendar.DATE, 6);
		// Log.e("TimeUtils", "本周末:"+cal.getTime());
		return cal.getTimeInMillis();
	}

	public static boolean isCurrentWeek(String fileName) {
		// 传入的时间格式 ：2015-10-29main.coollang 大于等于本周一的时间即是本周的日期
		// 获得字符串中的时间

		String dateStr = fileName.substring(0, fileName.indexOf("m"));
		SimpleDateFormat myFm = new SimpleDateFormat("yyyy-MM-dd");
		Date monday = getMondayOfThisWeek();
		String mondayStr = myFm.format(monday);

		Date date = null;
		try {
			date = myFm.parse(dateStr);
			monday = myFm.parse(mondayStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			return date.getTime() >= monday.getTime();
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return 本周一的Date对象
	 */
	public static Date getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c.getTime();
	}

	/**
	 *
	 * @param fileName name 根据数据文件名称 返回所在周的起始和终止日期
	 * 
	 */
	public static String getWhichWeek(String fileName) {
		String dateStr = fileName.substring(0, fileName.indexOf("m"));
		SimpleDateFormat myFm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = myFm.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		String monday = "";
		String sunday = "";
		if (dayOfWeek == 1) { // 周日
			monday = unixTimeToBeijingTime(c.getTimeInMillis() / 1000 - 5 * 24 * 3600, new SimpleDateFormat("yyyy年MM月dd"));
			sunday = unixTimeToBeijingTime(c.getTimeInMillis() / 1000 + 24 * 3600, new SimpleDateFormat("yyyy年MM月dd"));
		} else {
			monday = unixTimeToBeijingTime(c.getTimeInMillis() / 1000 + (3 - dayOfWeek) * 24 * 3600, new SimpleDateFormat("yyyy年MM月dd"));
			sunday = unixTimeToBeijingTime(c.getTimeInMillis() / 1000 + (9 - dayOfWeek) * 24 * 3600, new SimpleDateFormat("yyyy年MM月dd"));
		}
		return monday + "-" + sunday;
	}

	/**
	 * 
	 * @param str 根据年份 和 第几周 获取 本周的起始和终止日期
	 * 
	 */
	public static String getWeekFirst(String str) {
		String[] datedd = str.split(" ");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(datedd[0]), 0, 1);
		int weeks = 0;
		while ((weeks = calendar.get(Calendar.WEEK_OF_YEAR)) <= Integer.parseInt(datedd[1])) {
			calendar.add(Calendar.MONTH, 1);
			// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
		}
		calendar.add(Calendar.MONTH, -1);
		// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
		while ((weeks = calendar.get(Calendar.WEEK_OF_YEAR)) < Integer.parseInt(datedd[1])) {
			calendar.add(Calendar.DATE, 1);
		}
		if(str.contentEquals("2016 1")){
			return "12/28-01/03";
		}
		String dateString = Integer.toString(calendar.get(Calendar.YEAR));
		String dateString1 = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String dateString2 = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH) + 1);
		String toMonth =dateString1;
		int i = Integer.parseInt(dateString1);
		int yearr=Integer.parseInt(dateString);
		int week = calendar.get(Calendar.WEEK_OF_MONTH);
		int add = 0;
		switch (week) {
		case 1:add = 6;
			break;
		case 2:add = 5;
			break;
		case 3:add = 4;
			break;
		case 4:add = 3;
			break;
		case 5:add = 2;
			break;
		case 6:add = 1;
			break;
		case 7:add = 0;
			break;
		default:
			break;
		}
		String toDay =Integer.toString(Integer.parseInt(dateString2)+6);
		int daynum = 30;
		if(i == 1||i == 3||i == 5||i == 7||i == 8||i == 10||i == 12){
			daynum = 31;
		}else if (i == 2) {
			if(yearr%4==0&&yearr%100!=0||yearr%400==0){
				daynum = 29;
			}else {
				daynum = 28;
			}
		}
		if(Integer.parseInt(toDay)>daynum){
			toDay = Integer.toString(Integer.parseInt(toDay)-31);
			toMonth = Integer.toString(Integer.parseInt(toMonth)+1);
		}
		if(Integer.parseInt(toMonth)>12){
			toMonth = "1";
		}
		if(Integer.parseInt(dateString1)<10){
			dateString1 = "0"+dateString1;
		}
		if(Integer.parseInt(dateString2)<10){
			dateString2 = "0"+dateString2;
		}
		if(Integer.parseInt(toMonth)<10){
			toMonth = "0"+toMonth;
		}
		if(Integer.parseInt(toDay)<10){
			toDay = "0"+toDay;
		}
		String dtr = dateString1+"/"+dateString2+"-"+toMonth+"/"+toDay;
		return dateString+"年"+dateString1+"月";
	}

	/**
	 * 
	 * @param duration
	 * @param duration2
	 * @return
	 */
	public static boolean compareTime(String duration, String duration2) {
		String firstTime = duration.substring(0, duration.indexOf("-"));
		String secondTime = duration2.substring(0, duration2.indexOf("-"));
		SimpleDateFormat myFm = new SimpleDateFormat("yyyy年MM月dd");
		try {
			Date firDate = myFm.parse(firstTime);
			Date secDate = myFm.parse(secondTime);
			return firDate.after(secDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean compareTime(String duration, String duration2, String myFmStr) {
		SimpleDateFormat myFm = new SimpleDateFormat(myFmStr);
		try {
			Date firDate = myFm.parse(duration.replace("main.coollang", ""));
			Date secDate = myFm.parse(duration2.replace("main.coollang", ""));
			return firDate.after(secDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean compareTimeWithFormat(String firstTime, String secondTime, String fmStr) {
		SimpleDateFormat myFm = new SimpleDateFormat(fmStr);
		try {
			Date firDate = myFm.parse(firstTime);
			Date secDate = myFm.parse(secondTime);
			// LogUtils.w("=========结果====="+firDate.after(secDate));
			return firDate.after(secDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param currentDay
	 * @return
	 * 
	 *         获取本周的临近时间
	 */
/*	public static String getNearData(String currentDay) {
		// 遍历文件获取本周文件存储到数组
		File file = new File(URLConstants.maindatalistPath);
		if (file.exists()) { // 文件存在
			File[] listFiles = file.listFiles();
			List<String> weekList = new ArrayList<String>();
			for (int i = 0; i < listFiles.length; i++) {
				if (isCurrentWeek(listFiles[i].getName())) {
					weekList.add(listFiles[i].getName());
				}
			}
			// 遍历完成
			if (weekList.size() == 0) { // 无本周数据
				return "false";
			} else if (weekList.size() == 1) {
				return "true=" + weekList.get(0);
			} else {
				String temp = weekList.get(0);
				for (int i = 1; i < weekList.size() - 1; i++) {
					// 比较起始日期 获取最大值
					String temp2 = weekList.get(i);
					if (TimeUtils.compareTime(temp, temp2, "yyyy-MM-dd")) {// 比较取大值
						temp = temp2;
					}
				}
				return "true=" + temp;
			}
		} else {
			return "false";
		}
	}*/

	/**
	 * 获取两个日期之间的间隔天数
	 * 
	 * @return
	 */
	public static int getGapCount(String start, String end, String myFm) {
		SimpleDateFormat sf = new SimpleDateFormat(myFm);
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sf.parse(start);
			endDate = sf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (startDate == null || endDate == null) {
			return 0;
		} else {
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTime(startDate);
			fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			fromCalendar.set(Calendar.MINUTE, 0);
			fromCalendar.set(Calendar.SECOND, 0);
			fromCalendar.set(Calendar.MILLISECOND, 0);

			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTime(endDate);
			toCalendar.set(Calendar.HOUR_OF_DAY, 0);
			toCalendar.set(Calendar.MINUTE, 0);
			toCalendar.set(Calendar.SECOND, 0);
			toCalendar.set(Calendar.MILLISECOND, 0);
			return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
		}
	}

	public static String getNDayAfter(String time, int n) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy年MM月dd"); // 字符串转换
		Date date = null;
		try {
			date = formatDate.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) {
			return "";
		} else {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(date.getTime());
			c.add(Calendar.DATE, n);// 天后的日期
			return formatDate.format(c.getTime());
		}
	}

	/**
	 * 
	 * @param weekDataBean
	 * @param weekDataBean2
	 * @return
	 */
	 /*public static List<String> getTempNum(WeekDataBean weekDataBean, WeekDataBean weekDataBean2){
		List<String> list = new ArrayList<String>();
		String first = weekDataBean.duration;
		String second = weekDataBean2.duration;
		first = first.substring(first.indexOf("-") + 1, first.length());
		second = second.substring(0, second.indexOf("-"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd");
		int num = getGapCount(first, second, "yyyy年MM月dd");
		if (num == 0) {
			return list;
		} else {
			for (int i = 0; i < num / 7; i++) {
				String start = getNDayAfter(first, 1);
				String end = getNDayAfter(first, 7);
				list.add(start + "-" + end);
				first = end;
			}
			return list;
		}
	}*/
	
	/**
	 * 当前年份
	 * @return
	 */
	public static String getCurYear(){
		long curTime = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(curTime);
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	/**
	 * 当前第几周
	 * @return
	 */
	public static String getCurWeek(){
		long curTime = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(curTime);
		return String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
	}
}
