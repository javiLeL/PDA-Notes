package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        String fecha = getIntent().getExtras().getString("fecha");
        String hora = getIntent().getExtras().getString("hora");
        ((TextView) findViewById(R.id.textViewFechaNoti)).setText(fecha);
        ((TextView) findViewById(R.id.textViewHoraNoti)).setText(hora);
    }
}