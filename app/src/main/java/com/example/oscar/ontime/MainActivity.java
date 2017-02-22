package com.example.oscar.ontime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//this is a new branch Simple_UI
// TO DO:
// deactivate CHANGE button when alarm is running
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
    Button btnStartLunch,btnStopAlarm,btnChange;
    TextView tvStartLunch,tvEndLunch,tvDuration,tvCountDownTimer;
    Intent intent;
    private static  final String  BROADCAST_STRING ="com.example.oscar.ontime";
    private static final String MY_PREF_NAME = "USER_TIME";
    private int DEFAULT_DURATION = 30;
    TimePickerDialog tpd;
    MyCountDownTimer mCountDownTimer ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartLunch = (Button)findViewById(R.id.btn_start);
        btnStopAlarm = (Button) findViewById(R.id.btn_stop);
        btnChange = (Button)findViewById(R.id.btn_change);

        tvStartLunch = (TextView)findViewById(R.id.start_lunch_time);
        tvEndLunch = (TextView)findViewById(R.id.end_lunch_time);
        tvCountDownTimer = (TextView)findViewById(R.id.count_down_timer);
        tvCountDownTimer.setText(getUserTimeSelection() + "'");

        btnStopAlarm.setClickable(false);
        intent = new Intent();
        intent.setAction(BROADCAST_STRING);

        String durationTimeLabel = getResources().getString(R.string.duration);
        tvDuration = (TextView)findViewById(R.id.duration_label);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_NAME,1);
        int min = sharedPreferences.getInt("userTimeSelection",DEFAULT_DURATION);

        tvDuration.setText(durationTimeLabel + min +" min");

    }

    /************ startAlarm() ***************/
    public void startAlarm(View view)
    {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Date currentTime = calendar.getTime();


        calendar.add(Calendar.MINUTE,getUserTimeSelection());
        Date alarmSetTime = calendar.getTime();

        pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        String format = " h:mm a";

        DateFormat df = new SimpleDateFormat(format);
        String startLunchLabel= getResources().getString(R.string.lunch_start_label);
        String endLunchLabel = getResources().getString(R.string.lunch_ends_label);

        tvStartLunch.setText(startLunchLabel + ":"+ df.format(currentTime));
        tvEndLunch.setText(endLunchLabel     + ":" + df.format(alarmSetTime));

        btnStartLunch.setClickable(false);
        btnChange.setClickable(false);
        btnStopAlarm.setClickable(true);

        TextView tv = (TextView)findViewById(R.id.count_down_timer);
        mCountDownTimer = new MyCountDownTimer(tv,getUserTimeSelection()*60*1000,1000);
        mCountDownTimer.start();


        Toast.makeText(this,"Enjoy Lunch..!!",Toast.LENGTH_SHORT).show();
    }


    /************ stopAlarm() ***************/
    public void stopAlarm(View view)
    {
        btnStartLunch.setClickable(true);
        if(null != PendingIntent.getBroadcast(getBaseContext(),0,intent,PendingIntent.FLAG_NO_CREATE))
        {
            alarmManager.cancel(pendingIntent);
        }
        if (AlarmReceiver.getRingtone() != null)
        {
            AlarmReceiver.getRingtone().stop();

        }

        tvStartLunch.setText("");
        tvEndLunch.setText("");
        Toast.makeText(this,"Alarm stopped",Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        mCountDownTimer.cancel();
        btnChange.setClickable(true);
        TextView tv = (TextView)findViewById(R.id.count_down_timer);
        tv.setText(String.valueOf(getUserTimeSelection()) + "'");
    }


    /************ changeDuration() ***************/
    public void changeDuration(View view)
    {

        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                String durationTimeLabel = getResources().getString(R.string.duration);
                tvDuration.setText(durationTimeLabel + minute + " min");
                saveUserTimeSelection(minute);
                tvCountDownTimer.setText(String.valueOf(getUserTimeSelection()) + "'");
            }
        };


        tpd = new TimePickerDialog(this,3,timeListener,0,getUserTimeSelection(),true);
        tpd.setTitle("set your lunch duration");
        tpd.show();

    }

    /************ saveUserTimeSelection() ***************/
    public void saveUserTimeSelection(int min)
    {
        SharedPreferences sp = getSharedPreferences(MY_PREF_NAME,1);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("userTimeSelection",min);
        editor.apply();
    }

    /************ getUserTimeSelection() ***************/
    public int getUserTimeSelection()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_NAME,1);
        return sharedPreferences.getInt("userTimeSelection",DEFAULT_DURATION);

    }

}
