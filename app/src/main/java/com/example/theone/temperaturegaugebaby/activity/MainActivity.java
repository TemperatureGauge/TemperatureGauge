package com.example.theone.temperaturegaugebaby.activity;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.utils.ActivitySwitcher;
import com.example.theone.temperaturegaugebaby.utils.PopUtils;
import com.wangjie.androidbucket.present.ABActionBarActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ABActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


    //    @Inject
//    SharedPreferences mPreferences;//全局的SharedPreferences
    //不能private或者static
    @Bind(R.id.label_list_sample_rfal)
    RapidFloatingActionLayout rfaLayout;
    @Bind(R.id.label_list_sample_rfab)
    RapidFloatingActionButton rfaButton;
    @Bind(R.id.tv_user)
    TextView mUserName;

    private static PopupWindow lpopupWindow;
    private static PopupWindow newuser_popupWindow;

    private RapidFloatingActionHelper rfabHelper;
    private final String KEY = "Dagger 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化悬浮菜单
        ininRapidFloatingAction();
    }

    private void ininRapidFloatingAction() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("Github: wangjiegulu")
                        .setResId(R.drawable.ico_test_d)
                        .setIconNormalColor(Color.parseColor("#00bef3"))
                        .setIconPressedColor(Color.parseColor("#00bef3"))
                        .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("tiantian.china.2@gmail.com")
                        .setResId(R.drawable.ico_test_c)
                        .setDrawable(getResources().getDrawable(R.drawable.ico_test_c))
                        .setIconNormalColor(Color.parseColor("#00bef3"))
                        .setIconPressedColor(Color.parseColor("#00bef3"))
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(this, 4)))
                        .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("WangJie")
                        .setResId(R.drawable.setting)
                        .setIconNormalColor(Color.parseColor("#00bef3"))
                        .setIconPressedColor(Color.parseColor("#00bef3"))
                        .setLabelColor(0xff056f00)
                        .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("Compose")
                        .setResId(R.drawable.help)
                        .setIconNormalColor(Color.parseColor("#00bef3"))
                        .setIconPressedColor(Color.parseColor("#00bef3"))
                        .setLabelColor(0xff283593)
                        .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();

    }

    /**
     * 浮窗Label的点击事件
     *
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

    }

    /**
     * 浮窗item点击事件
     *
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position) {
            case 0:
                PopUtils.showSetingPop(MainActivity.this);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;

        }
    }


    /**
     * 用户点击事件
     */
    @OnClick({R.id.tv_user})
    public void topNameClick() {
//        PopUtils.showDevicePop(MainActivity.this, mUserName);
        View pop_devivelist = View.inflate(MainActivity.this, R.layout.pop_devivelist, null);
        pop_devivelist.startAnimation(AnimationUtils
                .loadAnimation(MainActivity.this, R.anim.fade_in));
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
        lpopupWindow.showAtLocation(mUserName, Gravity.CENTER, 0, 0);
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
                View pop_newuser = View.inflate(MainActivity.this, R.layout.pop_newuser, null);
                pop_newuser.startAnimation(AnimationUtils
                        .loadAnimation(MainActivity.this, R.anim.fade_in));
                newuser_popupWindow = new PopupWindow(pop_newuser,
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
                newuser_popupWindow.setFocusable(true);
                newuser_popupWindow.showAtLocation(mUserName, Gravity.CENTER, 0, 0);
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
                        ActivitySwitcher.goChoosePhotoAct(MainActivity.this);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
    }
}
