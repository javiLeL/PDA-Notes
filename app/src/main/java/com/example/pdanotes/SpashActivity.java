package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class SpashActivity extends AppCompatActivity {
    final int TIEMPO = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        // Lanzo un hilo el cual movera la progres bar
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                for (int i=1;i<=100;i++){
                    try {
                        // El tiempo que tarda la progres bar en llegar a 100% es gracias a esto:
                        Thread.currentThread().sleep((TIEMPO-100)/100);
                    }catch (Exception e){}
                    progressBar.setProgress(i, true);
                }
            }
        }).start();

        // Se crea un handler que en el tiempo selecionado (5s) hara lo especificado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // En nuestro caso es lanzar un intent a la MainActivity
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                // Se cerrara la esta actividad
                finish();
            }
        }, TIEMPO);

    }
}