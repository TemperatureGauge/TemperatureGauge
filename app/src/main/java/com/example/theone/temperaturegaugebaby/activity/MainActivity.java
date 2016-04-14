package com.example.theone.temperaturegaugebaby.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.theone.temperaturegaugebaby.R;
import com.wangjie.androidbucket.present.ABActionBarActivity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends ABActionBarActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{


    //    @Inject
//    SharedPreferences mPreferences;//全局的SharedPreferences
    //不能private或者static
    @Bind(R.id.label_list_sample_rfal)
    RapidFloatingActionLayout rfaLayout;
    @Bind(R.id.label_list_sample_rfab)
    RapidFloatingActionButton rfaButton;
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
                        .setResId(R.mipmap.ico_test_d)
                        .setIconNormalColor(Color.parseColor("#FFBBFF"))
                        .setIconPressedColor(Color.parseColor("#FFBBFF"))
                        .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("tiantian.china.2@gmail.com")
                        .setResId(R.mipmap.ico_test_c)
                        .setDrawable(getResources().getDrawable(R.mipmap.ico_test_c))
                        .setIconNormalColor(Color.parseColor("#FFBBFF"))
                        .setIconPressedColor(Color.parseColor("#FFBBFF"))
                        .setLabelColor(Color.WHITE)
                        .setLabelSizeSp(14)
                        .setLabelBackgroundDrawable(ABShape.generateCornerShapeDrawable(0xaa000000, ABTextUtil.dip2px(this, 4)))
                        .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("WangJie")
                        .setResId(R.mipmap.setting)
                        .setIconNormalColor(Color.parseColor("#FFBBFF"))
                        .setIconPressedColor(Color.parseColor("#FFBBFF"))
                        .setLabelColor(0xff056f00)
                        .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
//                        .setLabel("Compose")
                        .setResId(R.mipmap.help)
                        .setIconNormalColor(Color.parseColor("#FFBBFF"))
                        .setIconPressedColor(Color.parseColor("#FFBBFF"))
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

    }
}
