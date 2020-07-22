package com.example.andre.tabs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.andre.tabs.Database.DatabaseHelper;
import com.example.andre.tabs.Model.Utenti;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Classe con cui si possono vedere le statistiche di corsa di un determinato periodo
 */
public class Statistiche extends AppCompatActivity {

    int i,min=0,sec=0,totCalorie;
    ArrayList<Date> listaDate,listaDate2,listaDate3,listaDate4;
    ArrayList<Integer> listaCalorie,listaCalorieMese,listaCalorieAnno;
    Date date,controlYear;
    DatabaseHelper myDb;
    SQLiteDatabase database;
    private ArrayList<String> listaAtt,listaAtt2,listaAtt3;
    private AutoCompleteTextView Mese,Anno;
    private String mese,anno;
    private int id,età,alt,pes;
    private String nome,cognome,sex;
    private Button b;
    private int TOGLI_ANNO = 1900;
    private Utenti u;

    String[] allColumns1 = {DatabaseHelper.COL8,DatabaseHelper.COL9,DatabaseHelper.COL10,DatabaseHelper.COL11,
            DatabaseHelper.COL12,DatabaseHelper.COL13,DatabaseHelper.COL14};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id4");
        nome = extras.getString("nome4");
        cognome = extras.getString("cognome4");
        sex = extras.getString("sex4");
        età = extras.getInt("eta4");
        pes = extras.getInt("peso4");
        alt = extras.getInt("altezza4");

        u = new Utenti(nome,cognome,età,sex,pes,alt);

        myDb = new DatabaseHelper(this);
        database = myDb.getReadableDatabase();

        Mese = (AutoCompleteTextView) findViewById(R.id.Mese);
        Anno = (AutoCompleteTextView) findViewById(R.id.Anno);
        final TextView durataTot = (TextView) findViewById(R.id.durata_totale);
        final TextView calorieTot = (TextView) findViewById(R.id.calorie_totali);
        b = (Button) findViewById(R.id.Vai);

        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mese=Mese.getText().toString();
                        anno=Anno.getText().toString();

                        listaAtt = new ArrayList<String>();             //Lista dei tempi iniziale
                        listaAtt2 = new ArrayList<String>();            //Lista delle date inziali
                        listaAtt3 = new ArrayList<String>();            //Lista dei tempi filtrata coi mesi

                        listaCalorie = new ArrayList<Integer>();        //Lista delle calorie
                        listaCalorieMese = new ArrayList<Integer>();
                        listaCalorieAnno = new ArrayList<Integer>();

                        viewAll();                                                 //Recupera i dati delle corse dal db

                        listaDate = new ArrayList<Date>();      //
                        listaDate2 = new ArrayList<Date>();
                        listaDate3 = new ArrayList<Date>();
                        listaDate4 = new ArrayList<Date>();

                        DateFormat sdf = new SimpleDateFormat("mm:ss");     //Imposto il formato data
                        DateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");     //Imposto il formato data

                        try {
                            for(i=0;i<listaAtt2.size();i++) {
                                controlYear = sdf2.parse(listaAtt2.get(i));
                                listaDate2.add(controlYear);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            int a;
                            i=0;
                            if(mese.equals(""))
                                a=0;
                            else
                                a = Integer.parseInt(mese)-1;
                            while(i<listaAtt.size()) {                  //Cominicio la cattura delle date con il filtro dei mesi applicato
                                if((Integer.toString(listaDate2.get(i).getMonth()).equals(Integer.toString(a)) || mese.equals(""))) {
                                    date = sdf.parse(listaAtt.get(i));
                                    listaDate.add(date);                             //Aggiungo le date alla listaDate
                                    listaAtt3.add(listaAtt.get(i));
                                    listaDate3.add(listaDate2.get(i));
                                    listaCalorieMese.add(listaCalorie.get(i));
                                }
                                i++;
                            }

                            i=0;
                            if(anno.equals(""))
                                a=0;
                            else
                                a = Integer.parseInt(anno)-TOGLI_ANNO;
                            while(i<listaAtt3.size()) {             //Cominicio la cattura delle date con il filtro degli anni applicato
                                if((Integer.toString(listaDate3.get(i).getYear()).equals(Integer.toString(a)) || anno.equals(""))) {
                                    date = sdf.parse(listaAtt3.get(i));
                                    listaDate4.add(date);                             //Aggiungo le date alla listaDate
                                    listaCalorieAnno.add(listaCalorieMese.get(i));
                                }
                                i++;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        i=0;                                          //Creo le variabili minuti Totali e secondi Totali da visualizzare nelle statistiche
                        min=0;
                        sec=0;
                        if(listaDate3.size()>1) {
                            while (i < listaDate4.size()) {
                                min += listaDate4.get(i).getMinutes();
                                sec += listaDate4.get(i).getSeconds();
                                i++;
                            }
                        }else if(listaDate4.size()==1){
                            min=listaDate4.get(0).getMinutes();
                            sec=listaDate4.get(0).getSeconds();
                        }
                        min+=(sec/60);          //Aggiusto le variabili min e sec
                        sec = (sec%60);

                        for(i=0,totCalorie=0;i<listaCalorieAnno.size();i++)
                            totCalorie+=listaCalorieAnno.get(i);

                        durataTot.setText(min+":"+sec);
                        calorieTot.setText(Integer.toString(totCalorie));
                    }
                }
        );
    }

    /**
     *
     * Metodo che recupera tutti i dati delle corse dalla tabella CorseUtentiSistema
     */
    public void viewAll() {
        Cursor res = myDb.getAllData2();
        String a=Anno.getText().toString();
        res = database.query(DatabaseHelper.TABLE_NAME2, allColumns1, DatabaseHelper.COL14+"="+id, null, null, null, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            listaAtt.add(res.getString(2));
            listaAtt2.add(res.getString(1));
            listaCalorie.add(res.getInt(5));
            res.moveToNext();
        }
        res.close();
    }
}
