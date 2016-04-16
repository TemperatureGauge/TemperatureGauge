package com.example.theone.temperaturegaugebaby.utils;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.activity.MainActivity;

/**
 * Created by xiongxing on 2016/4/16.
 */
public class PopUtils {

    private static PopupWindow lpopupWindow;
    private static PopupWindow newuser_popupWindow;

    public static void showDevicePop(final MainActivity mainActivity, final View view) {
        View pop_devivelist = View.inflate(mainActivity, R.layout.pop_devivelist, null);
        pop_devivelist.startAnimation(AnimationUtils
                .loadAnimation(mainActivity, R.anim.fade_in));
        lpopupWindow = new PopupWindow(pop_devivelist,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        lpopupWindow.setTouchable(true);
        lpopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        lpopupWindow.setFocusable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        lpopupWindow.setBackgroundDrawable(new BitmapDrawable());
        lpopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        lpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                devicelist.clear();
            }
        });
        //选择当前用户，连接蓝牙
        pop_devivelist.findViewById(R.id.bt_linkBle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lpopupWindow.isShowing()) {
                    lpopupWindow.dismiss();
                }
            }
        });

        pop_devivelist.findViewById(R.id.bt_newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lpopupWindow.isShowing()) {
                    lpopupWindow.dismiss();
                }

                showNewUserPop(mainActivity, view);
            }
        });
        ListView blelist = (ListView) pop_devivelist.findViewById(R.id.blelist);
    }

    /**
     * 新建用户pop
     *
     * @param mainActivity
     * @param view
     */
    private static void showNewUserPop(final MainActivity mainActivity, View view) {

        View pop_newuser = View.inflate(mainActivity, R.layout.pop_newuser, null);
        pop_newuser.startAnimation(AnimationUtils
                .loadAnimation(mainActivity, R.anim.fade_in));
        newuser_popupWindow = new PopupWindow(pop_newuser,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        newuser_popupWindow.setFocusable(true);
        newuser_popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        newuser_popupWindow.setTouchable(true);
        newuser_popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        newuser_popupWindow.setBackgroundDrawable(new BitmapDrawable());
        newuser_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        pop_newuser.findViewById(R.id.bt_newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newuser_popupWindow.isShowing()) {
                    newuser_popupWindow.dismiss();
                }
            }
        });
        pop_newuser.findViewById(R.id.iv_newuser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySwitcher.goChoosePhotoAct(mainActivity);
            }
        });
    }

    /**
     * 设置pop
     * @param mainActivity
     * @param item
     */
    public static void showSetingPop(final MainActivity mainActivity) {
        View pop_setting = View.inflate(mainActivity, R.layout.pop_setting, null);
    }
}