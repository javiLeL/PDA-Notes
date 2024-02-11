package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class NotificacionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Se recogera la informacion que se le ha pasado por el pending intent
        String fecha = getIntent().getExtras().getString("fecha");
        String hora = getIntent().getExtras().getString("hora");

        // Se le pondra la informacion correspondiente a los textos que se mostraran
        // La fecha al campo fecha
        ((TextView) findViewById(R.id.textViewFechaNoti)).setText(fecha);
        // Y la hora al campo hora
        ((TextView) findViewById(R.id.textViewHoraNoti)).setText(hora);
    }
}