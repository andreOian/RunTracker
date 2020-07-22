package com.example.andre.tabs;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.andre.tabs.Model.Utenti;

/**
 *
 * Classe che modella un timer prima dell'inizo della corsa
 */
public class Conteggio extends AppCompatActivity {
    private String name,surname,sex;
    private int età,id,alt,pes;
    private int addTenSec=0;
    private TextView time;
    private Button add;
    private CountDownTimer mCountDownTimer;
    private final long START_TIME=5*1000;
    private long tempo=START_TIME;
    private Utenti u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteggio);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        name = extras.getString("nome");
        surname = extras.getString("cognome");
        sex = extras.getString("sex");
        età = extras.getInt("eta");
        pes = extras.getInt("peso");
        alt = extras.getInt("altezza");

        u = new Utenti(name,surname,età,sex,pes,alt);

        time = (TextView) findViewById(R.id.text_view_countdown);
        add = (Button) findViewById(R.id.add10sec);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.cancel();
                tempo+=10000;
                update();
                startTimer();
            }
        });

        startTimer();
    }

    /**
     *
     * Metodo che mi fa partire il timer
     */
    private void startTimer() {
        time.setText("10");
        mCountDownTimer = new CountDownTimer(tempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempo=millisUntilFinished;
                update();
            }

            @Override
            public void onFinish() {
                Intent miaIntent = new Intent(Conteggio.this, Corsa.class);
                miaIntent.putExtra("id",id);
                miaIntent.putExtra("nome",u.getNome());
                miaIntent.putExtra("cognome",u.getCognome());
                miaIntent.putExtra("sex",u.getSesso());
                miaIntent.putExtra("altezza",u.getAltezza());
                miaIntent.putExtra("peso",u.getPeso());
                miaIntent.putExtra("eta",u.getEta());
                startActivity(miaIntent);
            }
        };
        mCountDownTimer.start();
    }

    private void update(){
        time.setText(""+tempo/1000);
    }
}
