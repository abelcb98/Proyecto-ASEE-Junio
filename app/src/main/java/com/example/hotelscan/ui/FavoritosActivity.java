package com.example.hotelscan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.R;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.data.roomdb.HotelDB;

import java.util.ArrayList;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity  implements AdaptadorMisHoteles.OnHotelClickListener, AdaptadorMisHoteles.OnBorrarlClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorMisHoteles mAdapter;
    private List<HotelDB> hoteles;
    private TextView no_fav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);


        Toolbar toolbar = findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);

        no_fav = (TextView) findViewById(R.id.nofav);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdaptadorMisHoteles(new ArrayList<>(), this,this);
        recyclerView.setAdapter(mAdapter);

        no_fav.setVisibility(View.INVISIBLE);


        //Todo ListadoHoteles
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                hoteles  = HotelDatabase.getInstance(FavoritosActivity.this).getDao().getAll();

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (hoteles.size()!=0) {
                            mAdapter.swap(hoteles);
                            no_fav.setVisibility(View.INVISIBLE);
                        }
                        else {
                            no_fav.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
    }

    private void createDataPackage(Intent data, String id, String nombre, String puntuacion, String imagen, String precio, String calle, String contacto, String fecha_inicio, String fecha_fin, String numPersonas, String ciudad) {
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



    @Override
    public void onItemHotelClick(HotelDB datum) {

        Intent intent = new Intent(FavoritosActivity.this, DetallesHotelActivity.class);
        createDataPackage(intent,datum.getID()+"",datum.getNombre(),datum.getPuntuacion(),datum.getImagen(),datum.getPrecio(),datum.getCalle(),datum.getContacto(),datum.getFecha_inicio(),datum.getFecha_fin(),datum.getNum_personas(),datum.getCiudad());
        startActivity(intent);

    }

    @Override
    public void onBorrarItemClick(HotelDB datum) {

        //Todo BorrarHotel
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                HotelDatabase.getInstance(FavoritosActivity.this).getDao().delete(datum);


                hoteles  = HotelDatabase.getInstance(FavoritosActivity.this).getDao().getAll();

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                            mAdapter.swap(hoteles);

                    }
                });



            }
        });

    }
}