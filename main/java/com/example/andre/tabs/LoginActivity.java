package com.example.andre.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.andre.tabs.Database.DatabaseHelper;
import com.example.andre.tabs.Model.Utenti;

/**
 *
 * Classe che modella l'aggiunta di un Utente
 */
public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button mEmailSignInButton;
    private AutoCompleteTextView Name,Surname,Eta,Sesso,Peso,Altezza;
    private String nome,cognome,età,sex,pes,alt;
    Spinner spinner;
    DatabaseHelper myDb;

    SQLiteDatabase database;
    String[] allColumns = {DatabaseHelper.COL1,DatabaseHelper.COL2,DatabaseHelper.COL3,DatabaseHelper.COL4,DatabaseHelper.COL5,DatabaseHelper.COL6,DatabaseHelper.COL7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        Name = (AutoCompleteTextView) findViewById(R.id.Nome);
        Surname = (AutoCompleteTextView) findViewById(R.id.Surname);
        Eta = (AutoCompleteTextView) findViewById(R.id.Eta);
        Peso = (AutoCompleteTextView) findViewById(R.id.Peso);
        Altezza = (AutoCompleteTextView) findViewById(R.id.Altezza);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Sex,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        myDb = new DatabaseHelper(this);
        database = myDb.getReadableDatabase();
        AddData();
    }

    /**
     *
     * metodo che aggiunge l'utente
     */
    public  void AddData() {
        mEmailSignInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nome=Name.getText().toString();
                        cognome=Surname.getText().toString();
                        età=Eta.getText().toString();
                        pes=Peso.getText().toString();
                        alt=Altezza.getText().toString();
                        int a=Integer.parseInt(età),b=Integer.parseInt(pes),c=Integer.parseInt(alt);
                        Utenti u = new Utenti(nome,cognome,a,sex,b,c);
                        boolean isInserted = myDb.insertData(u.getNome(),u.getCognome(),u.getEta(),u.getSesso(),u.getPeso(),u.getAltezza());
                        if(isInserted == true)
                            Toast.makeText(LoginActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(LoginActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        Intent miaIntent = new Intent(LoginActivity.this, inizio.class);
                        startActivity(miaIntent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sex=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

