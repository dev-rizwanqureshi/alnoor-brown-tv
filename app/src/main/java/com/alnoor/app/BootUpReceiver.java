package com.alnoor.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;


public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.debug("onReceive- " + intent.getAction());
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.debug("onReceive- postDelayed start ");
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getAppContext().startActivity(i);
                Logger.debug("onReceive- postDelayed end " );
            }
        }, 2000);
//        }
        Logger.debug("onReceive- end " );
    }
}