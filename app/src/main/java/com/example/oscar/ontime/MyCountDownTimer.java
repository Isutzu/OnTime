package com.example.oscar.ontime;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by oscar on 2/16/17.
 */

public class MyCountDownTimer extends CountDownTimer
{
    private TextView tvCount;

    public MyCountDownTimer(TextView tvCountTimerText, long millisInFuture, long countDownInterval)
    {
        super(millisInFuture,countDownInterval);
        this.tvCount = tvCountTimerText;
    }



    @Override
    public void onTick(long millisUntilFinished)
    {
        tvCount.setText("" + ((millisUntilFinished / 60000)+"'" + ":"+(millisUntilFinished % 60000 / 1000) +"''" ));
    }

    @Override
    public void onFinish()
    {
        tvCount.setText("done!");

    }


}
