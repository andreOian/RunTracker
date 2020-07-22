package com.example.andre.tabs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andre.tabs.Database.DatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Classe che mi gestisce la corsa
 */
public class Corsa extends FragmentActivity implements OnMapReadyCallback {
    private String name,surname,sex;
    private int età,id,alt,pes;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private GoogleMap mMap;
    private Geocoder geo;
    private Location loc;
    private double lat, lng,lat2,lng2;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private int comincia=0,pausa=0;
    private int tempo1=0,tempo2=0,tempo3=10000*1000,diffTempo;
    private float velocitàMax1=0,velocitàMax2=0,velMed=0,sommaVel=0;
    private float lungTot=0;
    private Button stop,pause;
    private ArrayList<String> listaDati;
    private ListView listview;
    private  ArrayAdapter<String> a;
    private double calorie=0;
    private double kCal=0;
    private String dataOdierna;
    private int giorn0=16;
    DatabaseHelper myDb;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corsa);

        myDb = new DatabaseHelper(this);
        database = myDb.getReadableDatabase();

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        name = extras.getString("nome");
        surname = extras.getString("cognome");
        sex = extras.getString("sex");
        età = extras.getInt("eta");
        pes = extras.getInt("peso");
        alt = extras.getInt("altezza");



        listaDati = new ArrayList<String>();

        listaDati.add("Durata:"+""+tempo1/60+":"+tempo1%60);
        listaDati.add("Lunghezza(m):0");
        listaDati.add("VelMax(km/h):0");
        listaDati.add("VelMedia(km/h):0");
        listaDati.add("Calore(kcal):0");
        a = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaDati);
        listview = (ListView) findViewById(R.id.lista_dati_corsa);
        listview.setAdapter(a);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        locationListener = new LocationListener() {
            /**
             *
             * Metodo che intercetta il cambio di location
             * @param location
             */
            public void onLocationChanged(Location location) {
                //Prendiamo nota dei dati iniziali della corsa
                /////////////////////////////////////////////////////
                if(pausa==0) {          //Se non cè pausa
                    if(comincia==0) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        comincia++;
                    }
                    else {
                            //Aggiunta dei metri fatti di corsa
                            //////////////////////////////////////////////
                            lat2 = lat;
                            lng2 = lng;
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                            lungTot += distanza(lat, lng, lat2, lng2);
                            //Calcoliamo la velocità max e velocità Media
                            /////////////////////////////////////////////
                            diffTempo = tempo1 - tempo2;
                            if (distanza(lat, lng, lat2, lng2) > 0 && diffTempo != 0 && distanza(lat, lng, lat2, lng2) < 20) {
                                tempo2 = tempo1;
                                //Velocità Max
                                //////////////////////////////////////////////////////////////////
                                velocitàMax1 = (float) ((distanza(lat, lng, lat2, lng2) / diffTempo) * 3.6);
                                if (velocitàMax1 > velocitàMax2) {
                                    velocitàMax2 = velocitàMax1;
                                }
                                //Velocità Media
                                //////////////////////////////////////////////////////////////////
                                velMed += velocitàMax1;
                                sommaVel++;
                                //Calorie bruciate=k*Peso*dist--->(quel k è una funzione che dipende dll'età e dal sesso)
                                /////////////////////////////////////////////////////////////////////////////////////////////
                                if (sex.equals("Uomo")) {
                                    if (età < 11)
                                        kCal = 1.5;
                                    else if (età >= 11 && età < 18)
                                        kCal = 1.3;
                                    else if (età >= 18 && età < 30)
                                        kCal = 1.2;
                                    else if (età >= 30 && età < 60)
                                        kCal = 1.0;
                                    else
                                        kCal = 0.8;
                                } else if (sex.equals("Donna")) {
                                    if (età < 11)
                                        kCal = 1.3;
                                    else if (età >= 11 && età < 18)
                                        kCal = 1.1;
                                    else if (età >= 18 && età < 30)
                                        kCal = 1.0;
                                    else if (età >= 30 && età < 60)
                                        kCal = 0.8;
                                    else
                                        kCal = 0.5;
                                }
                                calorie += (kCal * pes * distanza(lat, lng, lat2, lng2))/2000;
                            }
                    }
                }
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.setMaxZoomPreference(20);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Bottono stop, per stoppare la corsa e aggiornare l'elenco delle corse
        //////////////////////////////////////////////////////////////////////////////
        stop = (Button) findViewById(R.id.Stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inserimento della corsa nel Db
                ///////////////////////////////////////
                String giorno,mese,anno;
                Date data = new Date();
                data.getTime();
                giorno=Integer.toString(data.getDay());
                mese=Integer.toString(data.getMonth()+1);
                anno=Integer.toString(data.getYear()+1900);
                dataOdierna=giorn0+"/"+mese+"/"+anno;
                boolean isInserted = myDb.insertData2(dataOdierna,tempo1/60+":"+tempo1%60,
                        (int)(velMed/sommaVel),(int)velocitàMax2,(int)calorie,id);
                if(isInserted == true)
                    Toast.makeText(Corsa.this,"Dato Inserito",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Corsa.this,"Errore",Toast.LENGTH_LONG).show();

                //Ritorno all'activity Principale
                //////////////////////////////////////////////
                Intent miaIntent = new Intent(Corsa.this, MainActivity.class);
                miaIntent.putExtra("id",id);
                miaIntent.putExtra("nome",name);
                miaIntent.putExtra("cognome",surname);
                miaIntent.putExtra("sex",sex);
                miaIntent.putExtra("altezza",alt);
                miaIntent.putExtra("peso",pes);
                miaIntent.putExtra("eta",età);
                startActivity(miaIntent);
            }
        });

        //Bottono pausa, per mettere in pausa la corsa, e poi farla ripartire
        ///////////////////////////////////////////////////////////////////////////
        pause = (Button) findViewById(R.id.Pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pausa==0) {
                    tempo2=tempo1;
                    tempo3=(10000-tempo1)*1000;
                    mCountDownTimer.cancel();
                    comincia=0;
                    pausa=1;
                    pause.setText("Restart");
                }
                else{
                    startTimer();
                    pausa=0;
                    pause.setText("Pause");
                }
            }
        });

        startTimer();
    }

    /**
     *
     * Metodo che fa partire il timer della corsa
     */
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(tempo3, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Il tempo qui si fa avanzare
                ////////////////////////////////////////////////////////////////
                tempo1= (int) (10000-millisUntilFinished/1000);
                //Aggiorniamo i dati della listview
                //////////////////////////////////////////////////////////////
                listaDati.remove(4);
                listaDati.remove(3);
                listaDati.remove(2);
                listaDati.remove(1);
                listaDati.remove(0);
                listaDati.add("Durata:"+""+tempo1/60+":"+tempo1%60);
                listaDati.add("Lunghezza(m):" + (int)lungTot);
                listaDati.add("VelMax(km/h):" + (int)velocitàMax2);
                if(sommaVel==0)
                    listaDati.add("VelMedia(km/h):" + 0);
                else
                    listaDati.add("VelMedia(km/h):" + (int)(velMed/sommaVel));
                listaDati.add("Calore(kcal):"+(int)calorie);
                a.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    /**
     *
     * Metodo per le google Map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = null;

        if(loc != null) {
            latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        }else{
            latLng = new LatLng(0,0);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    /**
     * Funzione per trovare la distanza tra due punti sul globo dati latitudine e longitudine dei due punti
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    private static double distanza(final double lat1, final double lon1, final double lat2, final double lon2) {
        double R = 6371000f; // Radius of the earth in m
        double dLat = (lat1 - lat2) * Math.PI / 180f;
        double dLon = (lon1 - lon2) * Math.PI / 180f;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d;
    }
}



