package com.example.oscar.ontime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


//this is a new branch hide_Notification
// TO DO:
// set a reminder 10 min before the lunch hour(maybe)
// adding vibration to alarm

//        int hora = calendar.get(Calendar.HOUR);
//        int minuto = calendar.get(Calendar.MINUTE);
//        int segundo = calendar.get(calendar.SECOND);
//        int hourMode = calendar.get(calendar.AM_PM);
//        if (hourMode == 0)
//        {
//             AM_PM = " AM";}
//        else
//        {
//             AM_PM = " PM";
//        }


public class MainActivity extends AppCompatActivity
{

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Button btnChange, btnStartStop;
    TextView tvStartLunch, tvEndLunch, tvDuration, tvCountDownTimer, tvTimer;
    Intent intent;
    private static final String BROADCAST_STRING = "com.example.oscar.ontime";
    private static final String MY_PREF_NAME = "USER_TIME";
    private int DEFAULT_DURATION = 30;
    private boolean stopAlarmFlag = false;
    static boolean uiVisible = false;
    TimePickerDialog tpd;
    MyCountDownTimer mCountDownTimer;
    Context context;
    static NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        btnChange = (Button) findViewById(R.id.btn_change);
        btnStartStop = (Button) findViewById(R.id.btn_start_stop);

        tvStartLunch = (TextView) findViewById(R.id.start_lunch_time);
        tvEndLunch = (TextView) findViewById(R.id.end_lunch_time);
        tvCountDownTimer = (TextView) findViewById(R.id.count_down_timer);
        tvCountDownTimer.setText(getUserTimeSelection() + "'");
        tvTimer = (TextView) findViewById(R.id.notification_timer);

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        intent = new Intent();
        intent.setAction(BROADCAST_STRING);

        String durationTimeLabel = getResources().getString(R.string.duration);
        tvDuration = (TextView) findViewById(R.id.duration_label);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_NAME, 1);
        int min = sharedPreferences.getInt("userTimeSelection", DEFAULT_DURATION);

        tvDuration.setText(durationTimeLabel + ": " + min + " min");

        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED );
        win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("LIFE-CYCLE","onResume()");
        nm.cancel(1001);
        MyCountDownTimer.setSendNotification(false);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (stopAlarmFlag)
        {
//            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
  //          remoteViews.setTextViewText(R.id.notification_timer, "veamos");
    //        remoteViews.setImageViewResource(R.id.icon_notification, R.drawable.clock);

            Intent in = new Intent(context, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mCountDownTimer.setSendNotification(true);
        }
    }



    /************* startStopAlarm()***************/
    public void startStopAlarm(View view)
    {
        if (stopAlarmFlag)
        {
            stopAlarm();
            stopAlarmFlag = false;
        } else
        {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            Date currentTime = calendar.getTime();


            calendar.add(Calendar.MINUTE, getUserTimeSelection());
            Date alarmSetTime = calendar.getTime();

            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            String format = " h:mm a";

            DateFormat df = new SimpleDateFormat(format);
            String startLunchLabel = getResources().getString(R.string.lunch_start_label);
            String endLunchLabel = getResources().getString(R.string.lunch_ends_label);

            tvStartLunch.setText(startLunchLabel + ":" + df.format(currentTime));
            tvEndLunch.setText(endLunchLabel + ":" + df.format(alarmSetTime));

            TextView tv = (TextView) findViewById(R.id.count_down_timer);

            mCountDownTimer = new MyCountDownTimer(context, tv, getUserTimeSelection() * 60 * 1000, 1000);
            mCountDownTimer.start();

            btnStartStop.setText("STOP");
            btnStartStop.setTextColor(Color.argb(255,255,64,129));
            btnChange.setVisibility(View.INVISIBLE);
            stopAlarmFlag = true;

            Toast.makeText(this, "Enjoy Lunch..!!", Toast.LENGTH_SHORT).show();
        }
    }


    /************* stopAlarm()***************/
    public void stopAlarm()
    {
        if (null != PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_NO_CREATE))
        {
            alarmManager.cancel(pendingIntent);
        }
        if (AlarmReceiver.getRingtone() != null)
        {
            AlarmReceiver.getRingtone().stop();

        }

        tvStartLunch.setText("");
        tvEndLunch.setText("");
        Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1001);
        mCountDownTimer.cancel();
        TextView tv = (TextView) findViewById(R.id.count_down_timer);
        tv.setText(String.valueOf(getUserTimeSelection()) + "'");

        btnStartStop.setText(getResources().getString(R.string.start_alarm));
        btnStartStop.setTextColor(Color.BLACK);
        btnChange.setVisibility(View.VISIBLE);


    }


    /************ changeDuration() ***************/
    public void changeDuration(View view)
    {

        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener()
        {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                String durationTimeLabel = getResources().getString(R.string.duration);
                tvDuration.setText(durationTimeLabel + ": " + minute + " min");
                saveUserTimeSelection(minute);
                tvCountDownTimer.setText(String.valueOf(getUserTimeSelection()) + "'");
            }
        };


        tpd = new TimePickerDialog(this, 3, timeListener, 0, getUserTimeSelection(), true);
        tpd.setTitle("set your lunch duration");
        tpd.show();

    }

    /************* saveUserTimeSelection() ***************/
    public void saveUserTimeSelection(int min)
    {
        SharedPreferences sp = getSharedPreferences(MY_PREF_NAME, 1);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("userTimeSelection", min);
        editor.apply();
    }

    /************* getUserTimeSelection() ***************/
    public int getUserTimeSelection()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_NAME, 1);
        return sharedPreferences.getInt("userTimeSelection", DEFAULT_DURATION);

    }
}
