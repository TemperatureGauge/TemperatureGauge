package com.example.theone.temperaturegaugebaby.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.views.SystemBarTintManager;


public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.blue));
        /**
         * 初始化滑动结束Activity
         *
         */
//		SwipeBackHelper.onCreate(this);
//		SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(true).setSwipeEdgePercent(0.2f).setSwipeSensitivity(0.5f).setClosePercent(0.5f)
//				.setSwipeRelateEnable(true).setSwipeSensitivity(1);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void finishActivity() {
        finish();
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    // 隐藏键盘
    public void hideSoftInput() {
        InputMethodManager imm = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        if (imm != null) {
            View view = this.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }

    // 显示键盘
    public void showSoftInput(View view) {
        InputMethodManager imm = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        if (imm.isActive())
            view.requestFocus();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//		SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppManager.getAppManager().removeActivity(this);
//		SwipeBackHelper.onDestroy(this);
    }

    protected abstract void initView();

    protected abstract void initData();
}
