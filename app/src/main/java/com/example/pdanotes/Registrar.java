package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    
        findViewById(R.id.buttonRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre =((EditText) findViewById(R.id.editTextNombreRegistrar)).getText().toString().trim();
                String correo =((EditText) findViewById(R.id.editTextCorreoElectronicoRegistrar)).getText().toString().trim();
                String password =((EditText) findViewById(R.id.editTextPasswordRegistrar)).getText().toString();

                if (!nombre.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
                    boolean isCreado = new ModeloBBDD().insertarUsuario(getApplicationContext(), new Usuario(correo, nombre, password));
                    if(!isCreado){
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Toast.makeText(Registrar.this, "Error al crear el registro", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Registrar.this, "Registro Creado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(Registrar.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
                    v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                }
            }
        });
    }
}