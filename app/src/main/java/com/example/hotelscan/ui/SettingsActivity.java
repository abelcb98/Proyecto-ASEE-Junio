package com.example.hotelscan.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.hotelscan.R;

public class SettingsActivity extends PreferenceActivity {

    public final static String KEY_CIUDAD = "pref_ciudad";
    public final static String KEY_IDIOMA = "pref_idioma";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }



}

