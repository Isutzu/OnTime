package com.example.oscar.ontime;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by oscar on 2/16/17.
 */

public class MyCountDownTimer extends CountDownTimer
{
    TextView tvCount;

    public MyCountDownTimer(TextView tvCountTimerText, long millisInFuture, long countDownInterval)
    {
        super(millisInFuture,countDownInterval);
        this.tvCount = tvCountTimerText;
    }



    @Override
    public void onTick(long millisUntilFinished)
    {
        tvCount.setText("" + ((millisUntilFinished / 60000)));
    }

    @Override
    public void onFinish()
    {
        tvCount.setText("done!");
    }


}
