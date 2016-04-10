package com.example.theone.temperaturegaugebaby.utils;

import android.content.Context;

/**
 * 获取资源文件
 * 
 * @author 熊星
 * @creattime 2016-4-5上午9:52:50
 * @about
 */
public class GetStrings {

	public static String getStringid(Context context, int id) {
		String string = null;
		string = context.getResources().getString(id);
		return string;
	}
}
