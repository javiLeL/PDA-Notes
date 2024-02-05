package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PoliticasDePrivacidad extends AppCompatActivity {
    TextView seleccionarPolitica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas_de_privacidad);
        seleccionarPolitica = findViewById(R.id.textViewTituloSeleccionarIdioma);
        setText("Seleccione un idioma para leer los terminos y condiciones");
        ((RadioGroup) findViewById(R.id.idiomas)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Toast.makeText(PoliticasDePrivacidad.this, ""+espanol.isSelected(), Toast.LENGTH_SHORT).show();
                if (checkedId==R.id.radioButtonEspano){
                    setText("Seleccione un idioma para leer los terminos y condiciones");
                }else if(checkedId==R.id.radioButtonIngles){
                    setText("Select a language to read the terms and conditions");
                } else if (checkedId==R.id.radioButtonChino) {
                    setText("选择一种语言来阅读条款和条件");
                }
            }
        });

    }
    void setText(String selecionIdioma){
        seleccionarPolitica.setText(selecionIdioma);
    }
}