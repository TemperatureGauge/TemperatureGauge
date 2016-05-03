package com.example.theone.temperaturegaugebaby.application;

import android.app.Application;
import android.os.Handler;

import com.example.theone.temperaturegaugebaby.di.components.AppComponent;
import com.example.theone.temperaturegaugebaby.di.components.DaggerAppComponent;
import com.example.theone.temperaturegaugebaby.di.modules.AppModule;
import com.example.theone.temperaturegaugebaby.service.BleManager;

/**
 * Created by xiongxing on 2016/4/11.
 */
public class MyApplication extends Application {
        private static MyApplication instance;
        private  Handler mMainThreadHandler;
        private AppComponent mAppComponent;

        @Override
        public void onCreate() {
            super.onCreate();
            instance = this;
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();

            initBle();
            initHandler();

        }
        public static MyApplication getInstance(){
            return instance;
        }

        public AppComponent getAppComponent() {
            return mAppComponent;
        }

    private void initBle() {
        BleManager.openble(this);
    }

    private void initHandler() {
        this.mMainThreadHandler = new Handler();
    }

    // 对外暴露一个主线程的handelr
    public  Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

}
