package com.example.theone.temperaturegaugebaby.application;

import android.app.Application;

import com.example.theone.temperaturegaugebaby.di.components.AppComponent;
import com.example.theone.temperaturegaugebaby.di.components.DaggerAppComponent;
import com.example.theone.temperaturegaugebaby.di.modules.AppModule;

/**
 * Created by xiongxing on 2016/4/11.
 */
public class MyApplication extends Application {

        private AppComponent mAppComponent;

        @Override
        public void onCreate() {
            super.onCreate();
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();

        }

        public AppComponent getAppComponent() {
            return mAppComponent;
        }

}
