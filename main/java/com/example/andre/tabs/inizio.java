package com.example.andre.tabs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.andre.tabs.Database.DatabaseHelper;
import com.example.andre.tabs.Model.Utenti;

import java.util.ArrayList;

/**
 *
 * Activity iniziale per la selezione dell'account
 */
public class inizio extends AppCompatActivity {

    private ArrayList<String> listaUt,listString,listInt;
    private ListView listview;
    private String nome,cognome,sex;
    private int età,alt,pes,id;
    private Utenti u;
    DatabaseHelper myDb;
    SQLiteDatabase database;
    String[] allColumns1 = {DatabaseHelper.COL1,DatabaseHelper.COL2,DatabaseHelper.COL3,DatabaseHelper.COL4,DatabaseHelper.COL5,DatabaseHelper.COL6,DatabaseHelper.COL7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inizio);

        FloatingActionButton floatBott = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        myDb = new DatabaseHelper(this);
        database = myDb.getReadableDatabase();
        listaUt = new ArrayList<String>();
        listString = new ArrayList<String>();
        listInt = new ArrayList<String>();

        viewAll();

        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaUt);
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(a);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<listaUt.size();i++){
                    if(i==position)
                        chooseUt(position+1);
                }
                Intent miaIntent = new Intent(inizio.this, MainActivity.class);

                miaIntent.putExtra("id",position+1);
                miaIntent.putExtra("nome",u.getNome());
                miaIntent.putExtra("cognome",u.getCognome());
                miaIntent.putExtra("eta",u.getEta());
                miaIntent.putExtra("sex",u.getSesso());
                miaIntent.putExtra("altezza",u.getAltezza());
                miaIntent.putExtra("peso",u.getPeso());
               //Toast.makeText(inizio.this,nome+","+cognome,Toast.LENGTH_LONG).show();
                startActivity(miaIntent);
            }
        });

        floatBott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miaIntent = new Intent(inizio.this, LoginActivity.class);
                startActivity(miaIntent);
            }
        });
    }

    /**
     *
     * Recupero dal DB gli Utenti del Sistema
     */
    public void viewAll() {
        Cursor res = myDb.getAllData();
        res = database.query(DatabaseHelper.TABLE_NAME,allColumns1,null,null,null,null,null);
        while (res.moveToNext())
            listaUt.add("Id :"+ res.getString(0)+","+"Name :"+ res.getString(1));
    }

    /**
     *
     * Selezioniamo l'Utente richiesto
     * @param pos
     */
    public void chooseUt(int pos) {
        Cursor res = myDb.getAllData();
        res = database.query(DatabaseHelper.TABLE_NAME,allColumns1,DatabaseHelper.COL1+"="+pos,null,null,null,null);
        while (res.moveToNext()) {
            nome = res.getString(1);
            cognome = res.getString(2);
            età = Integer.parseInt(res.getString(3));
            sex = res.getString(4);
            pes = Integer.parseInt(res.getString(5));
            alt = Integer.parseInt(res.getString(6));
            u = new Utenti(nome,cognome,età,sex,pes,alt);
        }
    }

}
