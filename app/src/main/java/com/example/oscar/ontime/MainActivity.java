package com.example.oscar.ontime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
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
    TextView tvStartLunch,tvEndLunch;
    Intent intent;
    private static  final String  BROADCAST_STRING ="com.example.oscar.ontime";

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
    }


    public void startAlarm(View view)
    {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Date currentTime = calendar.getTime();

        calendar.add(Calendar.SECOND,10);
        Date alarmSetTime = calendar.getTime();

        pendingIntent = PendingIntent.getBroadcast(getBaseContext(),0,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


        DateFormat df = new SimpleDateFormat("h:mm:ss a");
        tvStartLunch.setText("Lunch starts at :"+ df.format(currentTime));

        tvEndLunch.setText("Lunch ends at:" + df.format(alarmSetTime));
        btnStartLunch.setClickable(false);


        Toast.makeText(this,"Enjoy Lunch..!!",Toast.LENGTH_SHORT).show();
    }


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


}
