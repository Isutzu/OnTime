package com.example.oscar.ontime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by oscar on 2/9/17.
 */
public class StopMusicReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (action.equals("com.example.action.STOP_MUSIC"))
        {
            AlarmReceiver.getRingtone().stop();

            AlarmReceiver.getNotification().cancel(0);
        }
    }
}
