package com.example.andre.tabs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andre.tabs.Conteggio;
import com.example.andre.tabs.Model.Utenti;
import com.example.andre.tabs.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


/**
 *
 * Classe tab in cui sono contenuti la mappa e il bottone di inizio attività
 */
public class tabs2 extends Fragment implements OnMapReadyCallback{
    private String name,surname,sex;
    private int età,id,alt,pes;
    private GoogleMap mMap;
    private Geocoder geo;
    private Location loc;
    private double lat, lng;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button cominciamo;
    private MapView gMapView;
    private Utenti u;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabs2, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        id = this.getArguments().getInt("id3");
        name = this.getArguments().getString("nome3");
        surname = this.getArguments().getString("cognome3");
        sex = this.getArguments().getString("sex3");
        età = this.getArguments().getInt("eta3");
        alt = this.getArguments().getInt("altezza3");
        pes = this.getArguments().getInt("peso3");

        u = new Utenti(name,surname,età,sex,pes,alt);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();

                Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.setMaxZoomPreference(20);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1, 1, locationListener);
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        cominciamo = (Button) rootView.findViewById(R.id.btnFindPath);
        cominciamo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent miaIntent = new Intent(getActivity().getApplication(), Conteggio.class);
                miaIntent.putExtra("id",id);
                miaIntent.putExtra("nome",u.getNome());
                miaIntent.putExtra("cognome",u.getCognome());
                miaIntent.putExtra("eta",u.getEta());
                miaIntent.putExtra("sex",u.getSesso());
                miaIntent.putExtra("altezza",u.getAltezza());
                miaIntent.putExtra("peso",u.getPeso());
                startActivity(miaIntent);
            }
        });

        return rootView;
    }

    /**
     *
     * Classe che mi imposta la mia posizione inizle nella GoogleMap
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
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        private Geocoder geo;
        private Location loc;
        private double lat, lng;
        private LocationManager locationManager;
        private LocationListener locationListener;
        private Button cominciamo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
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
                public void onLocationChanged(Location location) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                        LatLng latLng = new LatLng(lat, lng);
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
}
