package com.example.pdanotes;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author  JaviLeL
 * @version 1.0.1
 */
public class Ajustes extends PreferenceActivity {
    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se le pasa el menu contextual que se quiere que maneje
        addPreferencesFromResource(R.xml.menu_preferencias);
    }
}