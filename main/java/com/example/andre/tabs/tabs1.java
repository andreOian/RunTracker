package com.example.andre.tabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andre.tabs.AddNewActivity;
import com.example.andre.tabs.AttivitaRecenti;
import com.example.andre.tabs.Database.DatabaseHelper;
import com.example.andre.tabs.Model.Attività;
import com.example.andre.tabs.Model.Utenti;
import com.example.andre.tabs.R;
import com.example.andre.tabs.Statistiche;
import com.example.andre.tabs.datiCorsa;

import java.util.ArrayList;

/**
 *
 * Classe tab in cui posso vedere le statistiche delle mie corse o aggiungere un attività
 */
public class tabs1 extends Fragment {
    private ArrayList<String> listaCorsa;
    private ArrayList<Integer> listaId;
    private String name,surname,sex,myValue,data,durata;
    private int età,alt,pes,id,calorie,velMax,velMedia;
    private ArrayList<String> listaAtt;
    private ListView listview;
    private Utenti u;
    private Attività att;
    DatabaseHelper myDb;
    SQLiteDatabase database;
    String[] allColumns1 = {DatabaseHelper.COL8,DatabaseHelper.COL9,DatabaseHelper.COL10,DatabaseHelper.COL11,
            DatabaseHelper.COL12,DatabaseHelper.COL13,DatabaseHelper.COL14};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabs1, container, false);

        listaCorsa = new ArrayList<String>();
        listaId =new ArrayList<Integer>();

        Button add = (Button) rootView.findViewById(R.id.addNewAct);
        Button statistiche = (Button) rootView.findViewById(R.id.statistiche);
        TextView text = (TextView) rootView.findViewById(R.id.AttRecenti);
        myDb = new DatabaseHelper(getActivity().getApplication());
        database = myDb.getReadableDatabase();

        id = this.getArguments().getInt("id3");
        name = this.getArguments().getString("nome3");
        surname = this.getArguments().getString("cognome3");
        sex = this.getArguments().getString("sex3");
        età = this.getArguments().getInt("eta3");
        alt = this.getArguments().getInt("altezza3");
        pes = this.getArguments().getInt("peso3");

        u = new Utenti(name,surname,età,sex,pes,alt);

        listaAtt = new ArrayList<String>();

        viewAll();

        ArrayAdapter<String> a = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,listaAtt);
        listview = (ListView) rootView.findViewById(R.id.listaAttività);
        listview.setAdapter(a);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
                for(int i=0;i<listaAtt.size();i++){
                    if(i==position)
                        chooseUt(listaId.get(i));
                }

                Intent miaIntent = new Intent(getActivity().getApplication(), datiCorsa.class);

                miaIntent.putExtra("id",id);
                miaIntent.putExtra("nome",u.getNome());
                miaIntent.putExtra("cognome",u.getCognome());
                miaIntent.putExtra("eta",u.getEta());
                miaIntent.putExtra("sex",u.getSesso());
                miaIntent.putExtra("altezza",u.getAltezza());
                miaIntent.putExtra("peso",u.getPeso());
                miaIntent.putExtra("data",data);
                miaIntent.putExtra("durata",durata);
                miaIntent.putExtra("velMedia",velMedia);
                miaIntent.putExtra("velMax",velMax);
                miaIntent.putExtra("calorie",calorie);
                startActivity(miaIntent);
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miaIntent = new Intent(getActivity().getApplication(), AttivitaRecenti.class);
                startActivity(miaIntent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miaIntent = new Intent(getActivity().getApplication(), AddNewActivity.class);
                miaIntent.putExtra("id4",id);
                miaIntent.putExtra("nome4",u.getNome());
                miaIntent.putExtra("cognome4",u.getCognome());
                miaIntent.putExtra("sex4",u.getSesso());
                miaIntent.putExtra("eta4",u.getEta());
                miaIntent.putExtra("altezza4",u.getAltezza());
                miaIntent.putExtra("peso4",u.getPeso());
                startActivity(miaIntent);
            }
        });

        statistiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miaIntent = new Intent(getActivity().getApplication(), Statistiche.class);
                miaIntent.putExtra("id4",id);
                miaIntent.putExtra("nome4",u.getNome());
                miaIntent.putExtra("cognome4",u.getCognome());
                miaIntent.putExtra("sex4",u.getSesso());
                miaIntent.putExtra("eta4",u.getEta());
                miaIntent.putExtra("altezza4",u.getAltezza());
                miaIntent.putExtra("peso4",u.getPeso());
                startActivity(miaIntent);
            }
        });

        return rootView;
    }

    /**
     *
     * Metodo per riempire il vettore della lista delle attività dello specifico utente
     */
    public void viewAll() {
        Cursor res = myDb.getAllData2();
        res = database.query(DatabaseHelper.TABLE_NAME2, allColumns1, DatabaseHelper.COL14 + "=" + id, null, null, null, null);
        while (res.moveToNext()) {
            listaAtt.add("Id:" + res.getInt(0) + ",Data :" + res.getString(1) + ",Durata :" + res.getString(2) + ",VelMax :" + res.getString(4));
            listaId.add(res.getInt(0));
        }
    }

    /**
     *
     * Metodo per ritrovare l'elemento in tale posizione nella listview delle attività
     * @param pos
     */
    public void chooseUt(int pos) {
        Cursor res = myDb.getAllData2();
        res = database.query(DatabaseHelper.TABLE_NAME2,allColumns1,DatabaseHelper.COL8+"="+pos,null,null,null,null);
        while (res.moveToNext()) {
            data=res.getString(1);
            durata=res.getString(2);
            velMedia=res.getInt(3);
            velMax=res.getInt(4);
            calorie=res.getInt(5);
            att = new Attività(data,durata,velMedia,velMax,calorie);
        }
    }
}
