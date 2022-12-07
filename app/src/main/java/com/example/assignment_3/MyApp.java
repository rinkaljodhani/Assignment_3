package com.example.assignment_3;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class MyApp extends Application {

    public static final String TAG = "App";

    public static LocaleManager localeManager;
    @Override
    protected void attachBaseContext(Context base) {
        localeManager = new LocaleManager(base);
        super.attachBaseContext(localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }

    /*public void onCreate(){
        super.onCreate();

        LocaleUtils.setLocale(new Locale(Locale.getDefault().getLanguage()));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }*/
}
