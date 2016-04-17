package com.example.theone.temperaturegaugebaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.example.theone.temperaturegaugebaby.R;
import com.example.theone.temperaturegaugebaby.utils.ActivitySwitcher;
import com.example.theone.temperaturegaugebaby.utils.ImageTools;
import com.example.theone.temperaturegaugebaby.views.Gifview;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiongxing on 2016/4/16.
 */
public class SplashActivity extends Activity {

    @Bind(R.id.gif)
    Gifview gif;

    private final static int GIF_COMPLETE=0;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GIF_COMPLETE:
//                    gif.setPaused(true);
                    ActivitySwitcher.goMainAct(SplashActivity.this);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        gif.setMovieResource(R.raw.loading);
        Message msg=new Message();
        msg.what=0;
        mHandler.sendMessageDelayed(msg,3000);
        if (gif.isPaused()) {
            ActivitySwitcher.goMainAct(this);
        }
        ImageTools.initImageLoader(this);
    }
}
