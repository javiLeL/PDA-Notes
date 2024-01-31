package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SpashActivity extends AppCompatActivity {
    final int tiempo = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                for (int i=1;i<=100;i++){
                    try {
                        Thread.currentThread().sleep((tiempo-100)/100);
                    }catch (Exception e){

                    }
                    progressBar.setProgress(i, true);
                }
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, tiempo);

    }
}