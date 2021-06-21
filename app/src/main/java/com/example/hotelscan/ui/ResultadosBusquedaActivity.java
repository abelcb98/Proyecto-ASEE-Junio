package com.example.hotelscan.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.hotelscan.AppContainer;
import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.MyApplication;
import com.example.hotelscan.R;
import com.example.hotelscan.data.network.OnHotelLoadedListener;
import com.example.hotelscan.data.network.OnTokenLoadedListener;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.data.roomdb.Ciudad;
import com.example.hotelscan.data.roomdb.HotelDB;
import com.example.hotelscan.data.modelo.Token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultadosBusquedaActivity extends AppCompatActivity implements OnTokenLoadedListener, MyAdapter.OnItemClickListener, OnHotelLoadedListener {

    public static final String ARG_CIUDAD = "param1";
    public static final String ARG_FECHA_IN="param2";
    public static final String ARG_FECHA_FIN="param3";
    public static final String ARG_NUM_PERSONAS="param4";


    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Token k;
    private String ciudad;
    private String fecha_ini;
    private String fecha_fin;
    private String numPersonas;
    private ProgressBar p;
    private TextView lista_vacia;
    private HotelActivityViewModel mViewModel;
    private Ciudad c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ciudad = getIntent().getStringExtra("Ciudad");
        fecha_ini = getIntent().getStringExtra("FechaInicio");
        fecha_fin = getIntent().getStringExtra("FechaFin");
        numPersonas = getIntent().getStringExtra("Personas");


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        p = (ProgressBar) findViewById(R.id.spinner);
        p.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new ArrayList<>(), this, R.layout.hotel);
        recyclerView.setAdapter(mAdapter);
        lista_vacia = (TextView) findViewById(R.id.lista_vacia);
        lista_vacia.setVisibility(View.INVISIBLE);
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factory).get(HotelActivityViewModel.class);
        mViewModel.getHotels().observe(this, hoteles -> {
            mAdapter.swap(hoteles);

            if (hoteles != null && hoteles.size()!=0) showHotelesDataView();
                    else
                        showLoading();


        });
        loadHotelRepo();


    }

    private void showLoading() {
        p.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    private void showHotelesDataView() {
        p.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

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


    private void loadHotelRepo() {
        HotelDB h = new HotelDB();
        h.setFecha_inicio(fecha_ini);
        h.setFecha_fin(fecha_fin);
        h.setNum_personas(numPersonas);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                c= HotelDatabase.getInstance(ResultadosBusquedaActivity.this).getCiudadDao().findByName(ciudad);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                         h.setCiudad(c.getCod_ciudad());
                        mViewModel.setHotel(h,p);
                    }
                });
            }
        });





    }




    @Override
    public void onItemClick(HotelDB datum) {
        Intent intent = new Intent(ResultadosBusquedaActivity.this, DetallesHotelActivity.class);
        createDataPackage(intent,datum.getID()+"",datum.getNombre(),datum.getPuntuacion(),datum.getImagen(),datum.getPrecio(),datum.getCalle(),datum.getContacto(),datum.getFecha_inicio(),datum.getFecha_fin(),datum.getNum_personas(),datum.getCiudad());
        startActivity(intent);
    }

    @Override
    public void onHotelLoaded(List<HotelDB> hoteles) {
        p.setVisibility(View.GONE);

        runOnUiThread(() -> mAdapter.swap(hoteles));
    }

    @Override
    public void onTokenLoaded(Token token) {
        this.k = token;
    }
}