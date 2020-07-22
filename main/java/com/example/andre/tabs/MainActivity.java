package com.example.andre.tabs;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import com.example.andre.tabs.Model.Utenti;
import com.example.andre.tabs.R;
import com.example.andre.tabs.tabs1;
import com.example.andre.tabs.tabs2;
import com.example.andre.tabs.tabs3;


/**
 *
 * Activity principale del sistema, contenitore dei 3 tabs
 */
public class MainActivity extends AppCompatActivity {

    private tabs1 cazzo;
    private Button bottone;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public String nome,cognome,sex;
    public int età,altezza,pes,id;
    private Utenti u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        nome = extras.getString("nome");
        cognome = extras.getString("cognome");
        età = extras.getInt("eta");
        sex = extras.getString("sex");
        altezza = extras.getInt("altezza");
        pes = extras.getInt("peso");

        u= new Utenti(nome,cognome,età,sex,pes,altezza);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    tabs1 tab1 = new tabs1();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("id3",id);
                    bundle2.putString("nome3", u.getNome() );  //Gli passiamo gli argomenti che provengono dalla login per popolare la list del tab3
                    bundle2.putString("cognome3", u.getCognome() );
                    bundle2.putInt("eta3",u.getEta());
                    bundle2.putString("sex3",u.getSesso());
                    bundle2.putInt("altezza3", u.getAltezza() );
                    bundle2.putInt("peso3", u.getPeso() );
                    tab1.setArguments(bundle2);
                    return tab1;
                case 1:
                    tabs2 tab2 = new tabs2();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("id3",id);
                    bundle3.putString("nome3", u.getNome() );  //Gli passiamo gli argomenti che provengono dalla login per popolare la list del tab3
                    bundle3.putString("cognome3",  u.getCognome() );
                    bundle3.putInt("eta3",u.getEta());
                    bundle3.putString("sex3",u.getSesso());
                    bundle3.putInt("altezza3", u.getAltezza() );
                    bundle3.putInt("peso3", u.getPeso() );
                    tab2.setArguments(bundle3);
                    return tab2;
                case 2:
                    tabs3 tab3 = new tabs3();
                    Bundle bundle = new Bundle();
                    bundle.putString("nome2", u.getNome() );  //Gli passiamo gli argomenti che provengono dalla login per popolare la list del tab3
                    bundle.putString("cognome2",  u.getCognome() );
                    bundle.putInt("eta3",u.getEta());
                    bundle.putString("sex3",u.getSesso());
                    bundle.putInt("altezza2", u.getAltezza() );
                    bundle.putInt("peso2", u.getPeso() );
                    tab3.setArguments(bundle);
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
}
