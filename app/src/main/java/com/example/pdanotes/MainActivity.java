package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText correo, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = findViewById(R.id.editTextCorreoElectronicoRegistrar);
                password = findViewById(R.id.editTextPasswordRegistrar);
                if (!correo.getText().toString().trim().isEmpty() && !password.getText().toString().isEmpty()){
                    String[] usuario =  new ModeloBBDD().selectUsuario(getApplicationContext(), correo.getText().toString().trim());
                    String nombre = usuario[0];
                    String passwdbbdd = usuario[1];

                    if(nombre!=null){
                        if (Usuario.getMD5(password.getText().toString()).equals(passwdbbdd)) {
                            progressDialog = new ProgressDialog(MainActivity.this);
                            Thread charger = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.currentThread().sleep(250);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Intent intent = new Intent(getApplicationContext(), SelectorDeNotas.class);
                                    intent.putExtra("correo", correo.getText().toString().trim());
                                    intent.putExtra("pass", password.getText().toString());
                                    startActivity(intent);
                                }
                            });
                            progressDialog.setTitle("Cargando sus datos");
                            progressDialog.setCancelable(false);
                            progressDialog.setProgressStyle(0);
                            progressDialog.show();
                            charger.start();

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

    @Override
    protected void onRestart() {
        // Al arrancar el layout
        super.onRestart();              // Llamo  al metodo original
        try {
            correo.setText("");             // Se reinicia el texto del correo
            correo.requestFocus();          // Se pone el foco en el correo
            password.setText("");           // Se reinicia el texto de la contraseña
            if (progressDialog != null) {      // Si el dialogo de carga no es null
                progressDialog.cancel();    // Este dejara de funcionar
            }
        }catch (Exception e){

        }
    }
}