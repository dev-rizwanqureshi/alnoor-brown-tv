package com.alnoor.app;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bugfender.sdk.Bugfender;


public class MyApplication extends MultiDexApplication {

    private static Context context;

    private static MyApplication sInstance = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        sInstance = this;
        Bugfender.init(this, "M1IZSlavbj6uF4fUbg7tgUsuNBCHIGjX", BuildConfig.DEBUG);
        Bugfender.enableCrashReporting();
        Bugfender.enableUIEventLogging(this);
        Bugfender.enableLogcatLogging();
    }

    // Getter to access Singleton instance
    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}