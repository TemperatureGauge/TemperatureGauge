package com.example.theone.temperaturegaugebaby.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.theone.temperaturegaugebaby.UUIDS.UUIDS;
import com.example.theone.temperaturegaugebaby.adapter.SoundListAdapter;
import com.example.theone.temperaturegaugebaby.adapter.UserListAdapter;
import com.example.theone.temperaturegaugebaby.application.MyApplication;
import com.example.theone.temperaturegaugebaby.bean.Sound;
import com.example.theone.temperaturegaugebaby.bean.User;
import com.example.theone.temperaturegaugebaby.dialog.UserDialogOne;
import com.example.theone.temperaturegaugebaby.dialog.UserDialogTwo;
import com.example.theone.temperaturegaugebaby.service.BLEDevice;
import com.example.theone.temperaturegaugebaby.service.BleManager;
import com.example.theone.temperaturegaugebaby.service.RFStarBLEService;
import com.example.theone.temperaturegaugebaby.utils.ActivitySwitcher;
import com.example.theone.temperaturegaugebaby.utils.DataUtils;
import com.example.theone.temperaturegaugebaby.utils.DisplayUtils;
import com.example.theone.temperaturegaugebaby.utils.LogUtil;
import com.example.theone.temperaturegaugebaby.utils.StringUtil;
import com.example.theone.temperaturegaugebaby.views.SystemBarTintManager;
import com.example.theone.temperaturegaugebaby.views.ThermometerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangjie.androidbucket.present.ABActionBarActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.simple.eventbus.EventBus;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ABActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, BLEDevice.RFStarBLEBroadcastReceiver {


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
    @Bind(R.id.right_tem_value)
    TextView right_tem_value;
    @Bind(R.id.left_tem_value)
    TextView left_tem_value;
    @Bind(R.id.link_device)
    View link_device;

    private static PopupWindow lpopupWindow;
    private static PopupWindow newuser_popupWindow;

    private RapidFloatingActionHelper rfabHelper;
    private final String KEY = "Dagger 2";
    private TextView tv_duration;
    private UserDialogOne userDialogOne;
    private UserDialogTwo userDialogTwo;
    private static final byte STARTMESURE = (byte)0x01;
    private static final byte STOPMESURE = (byte)0x00;
    private int temp;

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

        InputStream inputStream = getResources().openRawResource(R.raw.temp);
         String tempJson =  StringUtil.getString(inputStream);
        Type type = new TypeToken<Map<String,String>>() {}.getType();
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(tempJson,type);
        for (String key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
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
        final int bottom = y + mark_left.getHeight();

        rl_leftmark.getLocationOnScreen(location);
        viewTranslationYBy(rl_leftmark, bottom - rl_leftmark.getHeight() / 2 - location[1]);
        rl_rightmark.getLocationOnScreen(location);
        viewTranslationYBy(rl_rightmark, bottom - rl_rightmark.getHeight() / 2 - location[1]);

        rl_leftmark.setOnTouchListener(new View.OnTouchListener() {

            float yDOWN = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                int offet = v.getHeight() / 2;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setPressLeft(true);
                        setPressRight(false);
                        yDOWN = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float yMOVE = event.getRawY();
                        LogUtil.LogI("main", "yMOVE = " + yMOVE + "y==" + y);
                        float dy = yMOVE - yDOWN;
                        float moveto = y + offet + dy;
                        if (moveto > top && moveto < bottom) {
                            touchViewTranslation(dy, moveto, top, bottom);
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
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                int offet = v.getHeight() / 2;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setPressLeft(false);
                        setPressRight(true);
                        yDOWN = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float yMOVE = event.getRawY();
                        LogUtil.LogI("main", "yMOVE = " + yMOVE);
                        float dy = yMOVE - yDOWN;
                        float moveto = y + offet + dy;
                        //滑动的位置不超出边缘
                        if (moveto > top && moveto < bottom) {
                            touchViewTranslation(dy, moveto, top, bottom);
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

    @OnClick({R.id.link_device})
    public void setLinkDevice(){
        BleManager.searchBLE(this,getWindow().getDecorView(),this);
    }
    private void touchViewTranslation(float dy,float moveto,int top,int bottom){
        viewTranslationYBy(rl_leftmark, dy);
        //华氏温度89.6--107.6
        float temp = 107.6f - (moveto - top) * 18f / (bottom - top);
        BigDecimal bd = new BigDecimal(temp);
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
        left_tem_value.setText(Float.toString(bd.floatValue()));

        viewTranslationYBy(rl_rightmark, dy);
        //温度范围42--32
        float temp1 = 42 - (moveto - top) * 10 / (bottom - top);
        BigDecimal bd1 = new BigDecimal(temp1);
        bd1 = bd1.setScale(1, BigDecimal.ROUND_HALF_UP);
        right_tem_value.setText(Float.toString(bd1.floatValue()));
    }

    private void viewTranslationYBy(View view,float dy){
        view.animate().translationYBy(dy).setDuration(0);
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
        List<Sound> soundList = new ArrayList<Sound>();
        Sound sound = new Sound();
        sound.setState("1");
        sound.setName("小夜曲");
        soundList.add(sound);
        SoundListAdapter soundListAdapter = new SoundListAdapter(MainActivity.this, soundList);
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
    private static void setSeekBarText(SeekBar seekBar, TextView text, String unit, Context context, int width) {
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
        showUserDialogOne();
    }

    private void showUserDialogOne() {
        UserDialogOne.OnToLoginClickListener lis = new UserDialogOne.OnToLoginClickListener() {

            public void getText(String type,
                                int param) {
            }
        };
        userDialogOne =new UserDialogOne(
                MainActivity.this, lis,
                R.style.auth_dialog);
        userDialogOne.setCanceledOnTouchOutside(true);
        userDialogOne.show();
        //选择当前用户，连接蓝牙
        userDialogOne.findViewById(R.id.bt_linkBle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDialogOne.isShowing()) {
                    userDialogOne.dismiss();
                }
            }
        });

        userDialogOne.findViewById(R.id.bt_newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDialogOne.isShowing()) {
                    userDialogOne.dismiss();
                }
                showUserDialogTwo();
            }
        });
        ListView blelist = (ListView) userDialogOne.findViewById(R.id.blelist);
        List<User> userlist = new ArrayList<User>();
        User user = new User();
        user.setHeadUrl(null);
        user.setName("小度");
        user.setState("1");
        userlist.add(user);
        UserListAdapter userListAdapter = new UserListAdapter(MainActivity.this, userlist);
        blelist.setAdapter(userListAdapter);
    }

    private void showUserDialogTwo() {
        UserDialogTwo.OnToLoginClickListener lis = new UserDialogTwo.OnToLoginClickListener() {

            public void getText(String type,
                                int param) {
            }
        };
        userDialogTwo =new UserDialogTwo(
                MainActivity.this, lis,
                R.style.auth_dialog);
        userDialogTwo.setCanceledOnTouchOutside(true);
        userDialogTwo.show();
        userDialogTwo.findViewById(R.id.bt_newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDialogTwo.isShowing()) {
                    userDialogTwo.dismiss();
                }
            }
        });
        userDialogTwo.findViewById(R.id.iv_newuser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySwitcher.goChoosePhotoAct(MainActivity.this);
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

    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        String characteristicUUID = intent.getStringExtra(RFStarBLEService.RFSTAR_CHARACTERISTIC_ID);
        if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(intent.getAction())) {
            LogUtil.LogI("onReceive","ACTION_GATT_CONNECTED");
        } else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(intent.getAction())) { // 断开
            LogUtil.LogI("onReceive","ACTION_GATT_DISCONNECTED");
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED.equals(intent.getAction())) {
            LogUtil.LogI("onReceive","ACTION_GATT_SERVICES_DISCOVERED");
            if (BleManager.cubicBLEDevice != null) {
                BleManager.cubicBLEDevice.setNotification(UUIDS.SUUID_NOTIFE, UUIDS.CUUID_NOTIFE, true);
            }

            MyApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    BleManager.sendData(STARTMESURE);
                }
            }, 1000);

        } else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(intent.getAction())) {
            byte[] data = intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
            if ( data == null) {
                return;
            }
            DataUtils.showData(data);
            temp = DataUtils.extrackCount(data[0],data[1]);
        }
    }
}
