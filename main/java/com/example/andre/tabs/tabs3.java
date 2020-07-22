package com.example.andre.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.andre.tabs.Model.Utenti;

/**
 *
 * Classe tab in cui posso vedere i dati del mio Utente
 */
public class tabs3 extends Fragment {
    private ListView listview;
    private String[] data;
    private String name,surname,sex;
    private int età,pes,alt;
    private Utenti u;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabs3, container, false);

        name = this.getArguments().getString("nome2");
        surname = this.getArguments().getString("cognome2");
        età = this.getArguments().getInt("eta3");
        sex = this.getArguments().getString("sex3");
        pes = this.getArguments().getInt("peso2");
        alt = this.getArguments().getInt("altezza2");

        u = new Utenti(name,surname,età,sex,pes,alt);

        data = new String[]{"Nome: "+u.getNome(),"Cognome: "+u.getCognome(),"Età: "+Integer.toString(u.getEta()),"Sesso: "+u.getSesso(),
                                    "Peso: "+u.getPeso(),"Altezza: "+u.getAltezza()};

        ArrayAdapter<String> a = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,data);
        listview = (ListView) rootView.findViewById(R.id.listview2);
        listview.setAdapter(a);

        return rootView;
    }
}
