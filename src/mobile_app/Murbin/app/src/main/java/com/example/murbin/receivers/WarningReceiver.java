package com.example.murbin.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.murbin.services.StreetlightWarningService;

public class WarningReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
            context.startService(new Intent(context, StreetlightWarningService.class));
        }
    }
}
