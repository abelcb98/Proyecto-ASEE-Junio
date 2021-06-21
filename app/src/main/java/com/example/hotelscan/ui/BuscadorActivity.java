package com.example.hotelscan.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelscan.AppExecutors;
import com.example.hotelscan.R;
import com.example.hotelscan.data.roomdb.HotelDatabase;
import com.example.hotelscan.data.roomdb.Ciudad;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BuscadorActivity extends AppCompatActivity {

    private String mParam1;
    private String mParam2;
    private boolean encontrado= true;
    private static String dateString;
    private String fechaini;
    private String fechafin;
    private static final int SEVEN_DAYS = 604800000;
    private static final String PERSONAS_DEFAULT ="2";
    private static final String HABITACIONES_DEFAULT ="1";
    private Date mDate;
    private EditText ciudad;
    private EditText fecha_inicio;
    private EditText fecha_fin;
    private EditText numPersonas;

    private ImageView error_inicio;
    private ImageView error_fin;
    private ImageButton boton_buscar;
    private Toast info;
    private TextView errorCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        Toolbar toolbar = findViewById(R.id.toolbar_buscar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(null);
        errorCiudad = (TextView) findViewById(R.id.error_ciudadHotel) ;
        errorCiudad.setVisibility(View.INVISIBLE);
        ciudad = (EditText) findViewById(R.id.input_ciudad);
        fecha_inicio = (EditText) findViewById(R.id.input_fecha_inicio);
        fecha_fin = (EditText) findViewById(R.id.input_fecha_fin);
        numPersonas = (EditText) findViewById(R.id.input_num_personas);
        boton_buscar = (ImageButton) findViewById(R.id.boton_Buscar);
        error_inicio = (ImageView) findViewById(R.id.error_image);
        error_fin = (ImageView) findViewById(R.id.error_image2);
        numPersonas.setText(PERSONAS_DEFAULT);

        setDefaultDateTime();




        fecha_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog_fechainicio();
            }
        });

        fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog_fechafin();
            }
        });

        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelDatabase database = HotelDatabase.getInstance(getApplicationContext());

                if (validate()) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Ciudad c = HotelDatabase.getInstance(BuscadorActivity.this).getCiudadDao().findByName(ciudad.getText().toString());
                            if (c == null) {
                                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorCiudad.setVisibility(View.VISIBLE);
                                    }
                                });
                            } else {
                                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(BuscadorActivity.this, ResultadosBusquedaActivity.class);
                                        createDataPackage(intent,ciudad.getText().toString(),fecha_inicio.getText().toString(),fecha_fin.getText().toString(),numPersonas.getText().toString());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }


    private void createDataPackage(Intent data, String ciudad, String fecha_inicio, String fecha_fin, String personas) {
        data.putExtra("Ciudad", ciudad);
        data.putExtra("FechaInicio",fecha_inicio);
        data.putExtra("FechaFin",fecha_fin);
        data.putExtra("Personas",personas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode== RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            ciudad.setText(place.getName());
        }else if (resultCode== AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i("HotelScan", String.valueOf(status.getStatusCode()));
        }
    }

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        Date fDate = new Date ();
        fDate = new Date(fDate.getTime());
        c.setTime(fDate);
        fecha_fin.setText(dateString);
        fechafin=dateString;

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        fecha_inicio.setText(dateString);
        fechaini=dateString;


    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }


    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;

        public static BuscadorActivity.DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            BuscadorActivity.DatePickerFragment fragment = new BuscadorActivity.DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog picker = new DatePickerDialog(getActivity(),
                    listener, year, month, day);
            picker.getDatePicker().setMinDate(c.getTime().getTime());
            return picker;
        }



    }


    private void showDatePickerDialog_fechainicio() {
        BuscadorActivity.DatePickerFragment newFragment = BuscadorActivity.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                setDateString(year, month, day);
                fechaini=dateString;
                fecha_inicio.setText(dateString);
                try {

                    if ( !dateValidation() ){
                        error_inicio.setVisibility(View.VISIBLE);
                        error_fin.setVisibility(View.VISIBLE);
                        boton_buscar.setEnabled(false);
                        info = Toast.makeText(getApplicationContext(),"La fecha inicial no puede ser mayor a la final",Toast.LENGTH_LONG);
                        info.show();

                    }else{
                        error_inicio.setVisibility(View.INVISIBLE);
                        error_fin.setVisibility(View.INVISIBLE);
                        boton_buscar.setEnabled(true);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        newFragment.show(getFragmentManager(), "DatePicker");
    }

    private void showDatePickerDialog_fechafin() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                setDateString(year, month, day);
                fechafin=dateString;
                fecha_fin.setText(dateString);
                try {
                    if ( !dateValidation() ){
                        error_inicio.setVisibility(View.VISIBLE);
                        error_fin.setVisibility(View.VISIBLE);
                        boton_buscar.setEnabled(false);
                        info = Toast.makeText(getApplicationContext(),"La fecha inicial no puede ser mayor a la final",Toast.LENGTH_LONG);
                        info.show();
                    }else{
                        error_inicio.setVisibility(View.INVISIBLE);
                        error_fin.setVisibility(View.INVISIBLE);
                        boton_buscar.setEnabled(true);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        newFragment.show(getFragmentManager(), "DatePicker");

    }


    private boolean dateValidation() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        Date date1 = simpleDateFormat.parse(fechaini);
        Date date2 = simpleDateFormat.parse(fechafin);
        Log.i("FlyScan Abel","fecha 1: "+date1.toString());
        Log.i("FlyScan Abel","fecha 2: "+date2.toString());
        if (date1.before(date2)){ return true; }
        return false;
    }




    private boolean validate() {
        if (ciudad.getText().toString().isEmpty()){
            info = Toast.makeText(getApplicationContext(),"Campo ciudad vacío", Toast.LENGTH_LONG);
            info.show();
            return false;
        }else if (fecha_inicio.getText().toString().isEmpty() || fecha_fin.getText().toString().isEmpty()){
            info = Toast.makeText(getApplicationContext(),"Campo fecha vacío", Toast.LENGTH_LONG);
            info.show();
            return false;
        }else if(numPersonas.getText().toString().isEmpty()){
            info = Toast.makeText(getApplicationContext(),"Campo numero de personas vacío", Toast.LENGTH_LONG);
            info.show();
            return false;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        String nomciudad;
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
        nomciudad = s.getString(SettingsActivity.KEY_CIUDAD,"");

        ciudad.setText(nomciudad);

    }
}