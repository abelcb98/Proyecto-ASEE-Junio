package com.example.hotelscan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hotelscan.AppContainer;
import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.MyApplication;
import com.example.hotelscan.R;
import com.example.hotelscan.data.network.OnHotelLoadedListener;
import com.example.hotelscan.data.roomdb.Dataset_Ciudades;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.data.roomdb.HotelDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, OnHotelLoadedListener {

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private HotelActivityViewModel mViewModel;
    private TextView recientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);
        Dataset_Ciudades dataset = new Dataset_Ciudades();

        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);





        HotelDatabase.getInstance(this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                HotelDatabase.getInstance(MainActivity.this).getCiudadDao().deleteAll();
                HotelDatabase.getInstance(MainActivity.this).getCiudadDao().insertList(dataset.getDataSet());
            }
        });

        recientes = (TextView) findViewById(R.id.no_disponible);
        recientes.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.homeRV);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new ArrayList<>(), this, R.layout.hotel);
        recyclerView.setAdapter(mAdapter);
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factory).get(HotelActivityViewModel.class);
        mViewModel.getmRecentHotels().observe(this, hoteles -> {
            mAdapter.swap(hoteles);

            if (hoteles==null || hoteles.size()==0)
                recientes.setVisibility(View.VISIBLE);
            else
                recientes.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscar:
                Intent i = new Intent (this, BuscadorActivity.class);
                startActivity(i);
                return true;
            case R.id.fav:
                Intent intent = new Intent (this, FavoritosActivity.class);
                startActivity(intent);
                return true;
            case R.id.ajustes:
                Intent settings = new Intent (this, SettingsActivity.class);

                startActivity(settings);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onItemClick(HotelDB datum) {
        Intent intent = new Intent(MainActivity.this, DetallesHotelActivity.class);
        createDataPackage(intent,datum.getID()+"",datum.getNombre(),datum.getPuntuacion(),datum.getImagen(),datum.getPrecio(),datum.getCalle(),datum.getContacto(),datum.getFecha_inicio(),datum.getFecha_fin(),datum.getNum_personas(),datum.getCiudad());
        startActivity(intent);
    }

    @Override
    public void onHotelLoaded(List<HotelDB> hoteles) {

    }


    private void createDataPackage(Intent data, String id, String nombre, String puntuacion, String imagen, String precio, String calle, String contacto, String fecha_inicio, String fecha_fin, String numPersonas,String ciudad) {
        data.putExtra("Ciudad", ciudad);
        data.putExtra("FechaInicio",fecha_inicio);
        data.putExtra("FechaFin",fecha_fin);
        data.putExtra("Id",id);
        data.putExtra("Nombre", nombre);
        data.putExtra("Puntuacion",puntuacion);
        data.putExtra("Imagen",imagen);
        data.putExtra("Precio",precio);
        data.putExtra("Calle",calle);
        data.putExtra("Contacto",contacto);
        data.putExtra("NumPersonas",numPersonas);

    }
}