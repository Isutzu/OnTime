package com.example.oscar.ontime;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class MainActivity extends AppCompatActivity
{

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Button btnStartLunch;
    TextView tvStartLunch,tvEndLunch,tvDuration;
    Intent intent;
    private static  final String  BROADCAST_STRING ="com.example.oscar.ontime";
    private static final String MY_PREF_NAME = "USER_TIME";
    private int DEFAULT_DURATION = 30;
    private int min = DEFAULT_DURATION;
    TimePickerDialog tpd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartLunch = (Button)findViewById(R.id.btn_start);
        tvStartLunch = (TextView)findViewById(R.id.start_lunch_time);
        tvEndLunch = (TextView)findViewById(R.id.end_lunch_time);


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
//        int hora = calendar.get(Calendar.HOUR);
//       int minuto = calendar.get(Calendar.MINUTE);
//        int segundo = calendar.get(calendar.SECOND);
//        int hourMode = calendar.get(calendar.AM_PM);
//        if (hourMode == 0)
//        {
//             AM_PM = " AM";}
//        else
//        {
//             AM_PM = " PM";
//        }
        Date currentTime = calendar.getTime();


        calendar.add(Calendar.MINUTE,min);
        Date alarmSetTime = calendar.getTime();

        pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        String format = "h:mm:ss:a";

        DateFormat df = new SimpleDateFormat(format);
        String startLunchLabel= getResources().getString(R.string.lunch_start_label);
        String endLunchLabel = getResources().getString(R.string.lunch_ends_label);

        tvStartLunch.setText(startLunchLabel + df.format(currentTime));

        tvEndLunch.setText(endLunchLabel + df.format(alarmSetTime));
        btnStartLunch.setClickable(false);


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
            btnStartLunch = (Button)findViewById(R.id.btn_start);
            tvStartLunch.setText("");
            tvEndLunch.setText("");

        }

        Toast.makeText(this,"Alarm stopped",Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
    }


    /************ changeDuration() ***************/
    public void changeDuration(View view)
    {
        TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {

                min = minute;
                String durationTimeLabel = getResources().getString(R.string.duration);
                tvDuration.setText(durationTimeLabel + minute + " min");
                saveUserTimeSelection(min);

            }
        };

        tpd = new TimePickerDialog(this,2,timeListener,0,min,true);
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

}
