package com.alnoor.app;


import android.util.Log;

import com.bugfender.sdk.Bugfender;

public class Logger {
    public static boolean debug_enabled = true;

    public static void debug (String msg){
        if (debug_enabled) {
            Log.v(" >>>>>> :" + Thread.currentThread().getName(), msg);
            Bugfender.d("PrayerTimes Logs: ",  msg); // you can also use Bugfender to log messages
        }
    }
}