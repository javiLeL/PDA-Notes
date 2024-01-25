package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SelectorDeNotas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_de_notas);
        String correo = getIntent().getExtras().getString("correo");
        String pass = getIntent().getExtras().getString("pass");

    }
}