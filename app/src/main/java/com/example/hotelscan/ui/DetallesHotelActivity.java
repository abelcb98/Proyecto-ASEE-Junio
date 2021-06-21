package com.example.hotelscan.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.R;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.data.roomdb.HotelDB;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetallesHotelActivity extends AppCompatActivity {

    private  String nombre ;
    private  String puntuacion ;
    private  String precio ;
    private String imagenes;
    private  String direccion;
    private  String contacto;
    private String fechaini;
    private String fechafin;
    private String numpersonas;
    private String id;
    private String ciudad;
    private boolean favorito;


    private TextView nombre_h;
    private TextView precio_hotel;
    private TextView direccion_hotel;
    private TextView contacto_hotel;
    private ImageView[] estrellas = new ImageView[5];
    private TextView rate_nd;
    private CollapsingToolbarLayout c;
    private ImageView correcto;
    private TextView aniadido;
    private Bitmap bmp;
    private Button fav;
    private long insertado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_hotel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        nombre = getIntent().getStringExtra("Nombre");
        puntuacion = getIntent().getStringExtra("Puntuacion");
        precio = getIntent().getStringExtra("Precio");
        imagenes = getIntent().getStringExtra("Imagen");
        direccion = getIntent().getStringExtra("Calle");
        contacto = getIntent().getStringExtra("Contacto");
        id = getIntent().getStringExtra("Id");
        fechaini = getIntent().getStringExtra("FechaInicio");
        fechafin = getIntent().getStringExtra("FechaFin");
        numpersonas = getIntent().getStringExtra("numPersonas");
        ciudad = getIntent().getStringExtra("Ciudad");




        correcto = (ImageView) findViewById(R.id.correcto);
        aniadido = (TextView) findViewById(R.id.aniadido);
        fav = (Button) findViewById(R.id.boton_guardar);
        precio_hotel = (TextView) findViewById(R.id.precio_hotel);
        direccion_hotel = (TextView) findViewById(R.id.calle_hotel);
        contacto_hotel = (TextView) findViewById(R.id.calle_hotel);
        rate_nd = (TextView) findViewById(R.id.rate_nd);
        estrellas[0] = (ImageView) findViewById(R.id.estrella1);
        estrellas[1] = (ImageView) findViewById(R.id.estrella2);
        estrellas[2] = (ImageView) findViewById(R.id.estrella3);
        estrellas[3] = (ImageView) findViewById(R.id.estrella4);
        estrellas[4] = (ImageView) findViewById(R.id.estrella5);
        c = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        correcto.setVisibility(View.INVISIBLE);
        aniadido.setVisibility(View.INVISIBLE);
        fav.setVisibility(View.INVISIBLE);



        if (imagenes != null) {


            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(imagenes);
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BitmapDrawable d = new BitmapDrawable( bmp);
                                c.setBackground(d);
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });}

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final HotelDB h;
                h = HotelDatabase.getInstance(DetallesHotelActivity.this).getDao().getHotel(nombre,fechaini,fechafin);
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (h == null){
                            fav.setVisibility(View.VISIBLE);
                            Log.i("HotelScan","1");
                        }else{
                            correcto.setVisibility(View.VISIBLE);
                            aniadido.setVisibility(View.VISIBLE);
                            Log.i("HotelScan","2");
                        }
                    }
                });
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        insertado= HotelDatabase.getInstance(DetallesHotelActivity.this).getDao().insert(new HotelDB(nombre,puntuacion,precio,direccion,contacto, 1,fechaini, fechafin, numpersonas,imagenes,ciudad));
                        // Añadir Notificaciones Hotel
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (insertado != 0){
                                    fav.setVisibility(View.INVISIBLE);
                                    correcto.setVisibility(View.VISIBLE);
                                    aniadido.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
            }
        });


        for (int i = 0; i < 5; i++) {
            estrellas[i].setVisibility(View.INVISIBLE);
        }

        rate_nd.setVisibility(View.INVISIBLE);



            if (puntuacion != null){
                if (!puntuacion.equals("¿?")) {
                    for (int i = 0; i < Integer.parseInt(puntuacion); i++) {
                        estrellas[i].setVisibility(View.VISIBLE);
                    }
                } else {
                    rate_nd.setVisibility(View.VISIBLE);
                }
                Log.i("MESSAGE","puntuacion != null");
            }
            Log.d("FlyScan","Se ejecuta correctamente detalles 4");
            if(precio != null) {
                if (!precio.equals(""))
                    precio_hotel.setText(precio);
                Log.i("MESSAGE","precio != null");
            }
            if(direccion != null) {
                if (!direccion.equals(""))
                    direccion_hotel.setText(direccion);
                Log.i("MESSAGE","direccion != null");
            }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contacto !=null) {
                    String tel = "tel:" + contacto;
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse(tel));
                    startActivity(i);
                }else{
                    Toast info;
                    info = Toast.makeText(getApplicationContext(),"El telefono no está disponible",Toast.LENGTH_LONG);

                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(nombre);
        }

}
}