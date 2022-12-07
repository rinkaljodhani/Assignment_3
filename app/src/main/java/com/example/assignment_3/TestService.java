package com.example.assignment_3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class TestService extends Service {

    private final String TAG = "TestService";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(MyApp.localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

//        Locale locale = LocaleManager.getLocale(getResources());


        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}