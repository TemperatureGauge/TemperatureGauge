package com.example.theone.temperaturegaugebaby.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.adapter.SoundListAdapter;
import com.example.theone.temperaturegaugebaby.bean.Sound;
import com.example.theone.temperaturegaugebaby.utils.ActivitySwitcher;
import com.example.theone.temperaturegaugebaby.utils.DisplayUtils;
import com.example.theone.temperaturegaugebaby.utils.LogUtil;
import com.example.theone.temperaturegaugebaby.utils.PopUtils;
import com.example.theone.temperaturegaugebaby.views.SystemBarTintManager;
import com.example.theone.temperaturegaugebaby.views.ThermometerView;
import com.wangjie.androidbucket.present.ABActionBarActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.simple.eventbus.EventBus;

import java.text.DecimalFormat;
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
    @Bind(R.id.thermometerview)
    ThermometerView thermometerview;
    @Bind(R.id.mark_left)
    ImageButton mark_left;
    @Bind(R.id.mark_right)
    ImageButton mark_right;
    @Bind(R.id.unit_f)
    ImageButton unit_f;
    @Bind(R.id.unit_c)
    ImageButton unit_c;
    @Bind(R.id.rl_leftmark)
    RelativeLayout rl_leftmark;
    @Bind(R.id.rl_rightmark)
    RelativeLayout rl_rightmark;

    private static PopupWindow lpopupWindow;
    private static PopupWindow newuser_popupWindow;

    private RapidFloatingActionHelper rfabHelper;
    private final String KEY = "Dagger 2";
    private TextView tv_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.blue));


        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //初始化悬浮菜单
        ininRapidFloatingAction();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //华氏温度的浮标滑动

        int[] location = new int[2];
        mark_left.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        final int top = y;
        final int bottom = y+mark_left.getHeight();
        LogUtil.LogI("main", "top = " + top + "---bottom = " + bottom);
        rl_leftmark.setOnTouchListener(new View.OnTouchListener() {

            float yDOWN = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setPressLeft(true);
                        setPressRight(false);
                        yDOWN = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float yMOVE = event.getRawY();
                        LogUtil.LogI("main", "yMOVE = " + yMOVE);
                        if (yMOVE > top && yMOVE < bottom) {
                            rl_leftmark.animate().translationYBy(yMOVE - yDOWN).setDuration(0);
                            yDOWN = yMOVE;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        setPressLeft(true);
                        return true;
                }

                return false;
            }
        });
        rl_rightmark.setOnTouchListener(new View.OnTouchListener() {

            float yDOWN = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setPressLeft(false);
                        setPressRight(true);
                        yDOWN = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float yMOVE = event.getRawY();
                        LogUtil.LogI("main", "yMOVE = " + yMOVE);
                        if (yMOVE > top && yMOVE < bottom) {
                            rl_rightmark.animate().translationYBy(yMOVE - yDOWN).setDuration(0);
                            yDOWN = yMOVE;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        setPressRight(true);
                        return true;
                }

                return false;
            }
        });
    }

    private void setPressLeft(boolean pressed) {
        mark_left.setPressed(pressed);
        unit_f.setPressed(pressed);
        rl_leftmark.setPressed(pressed);
    }
    private void setPressRight(boolean pressed) {
        mark_right.setPressed(pressed);
        unit_c.setPressed(pressed);
        rl_rightmark.setPressed(pressed);
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

    private void ininRapidFloatingAction() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("Github: wangjiegulu")
                        .setResId(R.drawable.monitoring)
                        .setIconNormalColor(Color.parseColor("#00bef3"))
                        .setIconPressedColor(Color.parseColor("#00bef3"))
                        .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("tiantian.china.2@gmail.com")
                        .setResId(R.drawable.history)
//                        .setDrawable(getResources().getDrawable(R.drawable.ico_test_c))
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
                ActivitySwitcher.goTestListAct(MainActivity.this);
                break;
            case 1:
                ActivitySwitcher.goHistoryAct(MainActivity.this);
                break;
            case 2:
                showSettingDialog();
                break;
            case 3:
                break;

        }
    }

    private void showSettingDialog() {
        View dialog_setting = View.inflate(this, R.layout.pop_setting, null);
        Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(dialog_setting, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        ListView sound_list = (ListView) dialog_setting.findViewById(R.id.sound_list);
        List<Sound> soundList=new ArrayList<Sound>();
        Sound sound=new Sound();
        sound.setState("1");
        sound.setName("小夜曲");
        soundList.add(sound);
        SoundListAdapter soundListAdapter=new SoundListAdapter(MainActivity.this,soundList);
        sound_list.setAdapter(soundListAdapter);
        tv_duration = (TextView) dialog_setting.findViewById(R.id.tv_duration);
        TextView tv_start = (TextView) dialog_setting.findViewById(R.id.tv_start);
        final int tv_startWidth = tv_start.getWidth();
        SeekBar seekBar = (SeekBar) dialog_setting.findViewById(R.id.sb_duration);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBarText(seekBar, tv_duration, "℃", MainActivity.this, tv_startWidth);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 设置SeekBar文字说明的内容与位置
     *
     * @param seekBar
     * @param text
     * @param unit
     */
    private static void setSeekBarText(SeekBar seekBar, TextView text, String unit, Context context,int width) {
        float wid = seekBar.getWidth();
        float tvWid = text.getWidth();
        LinearLayout.LayoutParams paramsStrength = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //目前进度占总最大进度的比例
        float scale = (float) seekBar.getProgress() / (float) seekBar.getMax();
        //文字说明控件左边距=目前进度的长度-文字说明控件text宽度的一半+SeekBar左边距20dp
        int margin = (int) (scale * wid - DisplayUtils.dip2px(context, 20) + DisplayUtils.dip2px(context, 60));
        paramsStrength.leftMargin = margin;
        text.setLayoutParams(paramsStrength);
        if (seekBar.getId() == R.id.sb_duration) {
            float time = (float) seekBar.getProgress() / 3600;
            //格式化小数的位数
            String timeStr = formatFloat(time);
            text.setText(timeStr + unit);
//        }else if(seekBar.getId()==R.id.sb_times){
//            int times = seekBar.getProgress()/100;
//            text.setText(times+unit);
//        }else{
//            text.setText(seekBar.getProgress()+unit);
//        }
        }
    }

    private static String formatFloat(float time) {
        //格式化小数的位数
        String pattern = "0.#";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(time);
    }


    /**
     * 用户点击事件
     */
    @OnClick({R.id.tv_user})
    public void topNameClick() {
        PopUtils.showDevicePop(MainActivity.this, mUserName);
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
