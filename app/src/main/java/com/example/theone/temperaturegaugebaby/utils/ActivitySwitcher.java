package com.example.theone.temperaturegaugebaby.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.theone.temperaturegaugebaby.activity.ChoosePhotoActivity;
import com.example.theone.temperaturegaugebaby.activity.MainActivity;
import com.example.theone.temperaturegaugebaby.activity.SplashActivity;

/**
 * Activity跳转工具类
 */
public class ActivitySwitcher
{
    /**
     * 主页-选择照片方式
     * @param activity
     */
	public static void goChoosePhotoAct(Activity activity) {
		Intent intent = new Intent(activity, ChoosePhotoActivity.class);
		activity.startActivity(intent);
		activity.overridePendingTransition(0,
				0);
	}

	


//	public static void goWebAct(Activity activity , String url , String title){
//		Intent intent = new Intent(activity, WebActivity.class);
//	    intent.putExtra("url", url);
//		intent.putExtra("title", title);
//		activity.startActivity(intent);
// }

	/**
	 * splashAct-mainAct
	 * @param splashActivity
	 */
 public static void goMainAct(SplashActivity splashActivity) {
	 Intent intent = new Intent(splashActivity, MainActivity.class);
	 splashActivity.startActivity(intent);
	}
}
