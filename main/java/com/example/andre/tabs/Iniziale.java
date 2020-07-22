package com.example.andre.tabs;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 *
 * Activity iniziale con l'immagine della corsa
 */
public class Iniziale extends AppCompatActivity {

    private CountDownTimer mCountDownTimer;
    private final long START_TIME=3*1000;
    private long tempo=START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniziale);

        ImageView v = (ImageView) findViewById(R.id.imageView);
        v.setImageResource(R.drawable.image_run2);

        startTimer();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(tempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempo=millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Intent miaIntent = new Intent(Iniziale.this, inizio.class);
                startActivity(miaIntent);
            }
        };
        mCountDownTimer.start();
    }
}
