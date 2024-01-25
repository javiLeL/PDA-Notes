package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo =((EditText) findViewById(R.id.editTextCorreoElectronicoRegistrar)).getText().toString();
                String password =((EditText) findViewById(R.id.editTextPasswordRegistrar)).getText().toString();
                if (!correo.isEmpty() && !password.isEmpty()){
                    String nombre = new ModeloBBDD().selectUsuario(getApplicationContext(), correo)[0];
                    String passwdbbdd = new ModeloBBDD().selectUsuario(getApplicationContext(), correo)[1];

                    if(nombre!=null){
                        if (Usuario.getMD5(password).equals(passwdbbdd)) {
                            Intent intent = new Intent(getApplicationContext(), SelectorDeNotas.class);
                            intent.putExtra("correo", correo);
                            intent.putExtra("pass", password);
                            startActivity(intent);
                            // Toast.makeText(MainActivity.this, "Usuario encontrado Entrando", Toast.LENGTH_SHORT).show();
                        } else {
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                            Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Toast.makeText(MainActivity.this, "Usuario no encontrado registrese", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    Toast.makeText(MainActivity.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.textRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registrar.class));
            }
        });
    }
}