package com.example.oscar.ontime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by oscar on 2/22/17.
 */
public class StopAlarmActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.stop_alarm_activity);
    }

    /*************** stopAlarm() ****************/
    public void stopAlarm(View view)
    {
        if (AlarmReceiver.getRingtone() != null)
        {
            AlarmReceiver.getRingtone().stop();
            finish();

        }
    }
}
