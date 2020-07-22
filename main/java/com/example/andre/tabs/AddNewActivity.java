package com.example.andre.tabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.example.andre.tabs.Database.DatabaseHelper;
import com.example.andre.tabs.Model.Attività;
import com.example.andre.tabs.Model.Utenti;

public class AddNewActivity extends AppCompatActivity {

    private FloatingActionButton floatBott;
    private AutoCompleteTextView Data,Durata,VelMedia,VelMax,Calorie;
    private String nome,cognome,sex;
    private int età,pes,alt,id;
    private String data,durata;
    private int velMedia,velMax,calorie;
    private Utenti u;
    private Attività att;
    DatabaseHelper myDb;

    SQLiteDatabase database;
    String[] allColumns = {DatabaseHelper.COL8,DatabaseHelper.COL9,DatabaseHelper.COL10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        Data = (AutoCompleteTextView) findViewById(R.id.Data);
        Durata = (AutoCompleteTextView) findViewById(R.id.Durata);
        VelMedia = (AutoCompleteTextView) findViewById(R.id.VelMedia);
        VelMax = (AutoCompleteTextView) findViewById(R.id.VelMax);
        Calorie = (AutoCompleteTextView) findViewById(R.id.Calorie);
        floatBott = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

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
        AddData();
    }

    /**
     *
     * Metodo che aggiunge una nuova attività di corsa al DB
     */
     public  void AddData() {
        floatBott.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data = Data.getText().toString();
                        durata = Durata.getText().toString();
                        velMax = Integer.parseInt(VelMax.getText().toString());
                        velMedia = Integer.parseInt(VelMedia.getText().toString());
                        calorie = Integer.parseInt(Calorie.getText().toString());
                        att = new Attività(data,durata,velMedia,velMax,calorie);

                        boolean isInserted = myDb.insertData2(att.getData(),att.getDurata(),att.getVelMedia(),att.getVelMax(),att.getCalorie(),id);
                        if(isInserted == true)
                            Toast.makeText(AddNewActivity.this,"Data Inserted:"+att.getData()+","+att.getDurata(),Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddNewActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        Intent miaIntent = new Intent(AddNewActivity.this, MainActivity.class);
                        miaIntent.putExtra("id",id);
                        miaIntent.putExtra("nome",u.getNome());
                        miaIntent.putExtra("cognome",u.getCognome());
                        miaIntent.putExtra("sex",u.getSesso());
                        miaIntent.putExtra("altezza",u.getAltezza());
                        miaIntent.putExtra("peso",u.getPeso());
                        miaIntent.putExtra("eta",u.getEta());
                        startActivity(miaIntent);
                    }
                }
        );
    }

}
