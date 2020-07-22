package com.example.andre.tabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.andre.tabs.Model.Attività;
import com.example.andre.tabs.Model.Utenti;

/**
 *
 * Classe per l'activity dati della corsa
 */
public class datiCorsa extends AppCompatActivity {

    private String[] dati;
    private ListView listview;
    private int id,età,pes,alt,velMedia,velMax,calorie;
    private String nome,cognome,sex,data,durata;
    private Utenti u;
    private Attività att;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati_corsa);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        nome = extras.getString("nome");
        cognome = extras.getString("cognome");
        sex = extras.getString("sex");
        età = extras.getInt("eta");
        pes = extras.getInt("peso");
        alt = extras.getInt("altezza");
        data = extras.getString("data");
        durata = extras.getString("durata");
        velMedia = extras.getInt("velMedia");
        velMax = extras.getInt("velMax");
        calorie = extras.getInt("calorie");

        u = new Utenti(nome,cognome,età,sex,pes,alt);
        att = new Attività(data,durata,velMedia,velMax,calorie);

        dati = new String[]{"Nome: "+u.getNome(),"Cognome: "+u.getCognome(),"Data: "+att.getData(),"Durata: "+att.getDurata(),
                "Velocità media: "+att.getVelMedia(),"Velocità max: "+att.getVelMax(),"Calorie: "+att.getCalorie()};

        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dati);
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(a);
    }
}
