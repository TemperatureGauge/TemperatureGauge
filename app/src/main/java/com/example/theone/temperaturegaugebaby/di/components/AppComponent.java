package com.example.theone.temperaturegaugebaby.di.components;

import android.content.Context;

import com.example.theone.temperaturegaugebaby.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by weilu on 2016/1/27.
 */
@Singleton
@Component(
        modules ={ AppModule.class
    }
)
public interface AppComponent {

    Context getAppContext();
//    UserComponent createUserComponent(UserModule userModule);
}
