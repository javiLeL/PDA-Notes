package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.pdanotes.bbdd.ModeloBBDD;
import com.example.pdanotes.dto.Usuario;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText correo, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Al tocar le boton de iniciar sesion
        findViewById(R.id.buttonIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se recolectara la informacion necesaria
                correo = findViewById(R.id.editTextCorreoElectronicoRegistrar);
                password = findViewById(R.id.editTextPasswordRegistrar);

                if (!correo.getText().toString().trim().isEmpty() && !password.getText().toString().isEmpty()){ // Si el correo y contraseñas no estan vacios
                    // Se compreobara que el usuario exista
                    // Ya que es un correo se tratara en lower case ya que no importa las mayusculas y se hara un "trim" para eliminar los caracteres en blanco del pricpio y final
                    String[] usuario =  new ModeloBBDD().selectUsuario(getApplicationContext(), correo.getText().toString().trim().toLowerCase());

                    // Los datos recolectados se guardaran en variables para facilitar su manejo
                    String nombre = usuario[0];
                    String passwdbbdd = usuario[1];

                    if(nombre!=null){ // Si su nombre no es null (existe en la base de datos)
                        // Se comprobara su autenticidad a partir de la contraseña
                        // Ya que la contraseña esta guardada en MD5 se pasara por el mismo proceso la contraseña puesta por el usuario y si estas coinciden entonces seran la misma
                        if (Usuario.getMD5(password.getText().toString()).equals(passwdbbdd)) { // Si estas coinciden
                            // Se creara un dialogo con una barra de progreso
                            progressDialog = new ProgressDialog(MainActivity.this);

                            // Se creara un hilo
                            Thread charger = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // Para que carge bien el apartado visual del Dialogo se dormira 250 ms
                                    try {
                                        Thread.currentThread().sleep(250);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }

                                    // Y se lanzara la selecion de notas
                                    Intent intent = new Intent(getApplicationContext(), SelectorDeNotas.class);
                                    // Sele pasara el correo
                                    intent.putExtra("correo", correo.getText().toString().trim());
                                    // Y la contraseña real sin pasar por MD5
                                    intent.putExtra("pass", password.getText().toString());
                                    // Se iniciara el intent
                                    startActivity(intent);
                                }
                            });

                            // Se pondra un mensaje adecuado al dialogo
                            progressDialog.setTitle("Cargando sus datos");
                            // Se hara que este no pueda ser cancelao al ser pulsado fuera de este
                            progressDialog.setCancelable(false);
                            // Se le especificara que la progres bar sea de tipo circular
                            progressDialog.setProgressStyle(0);
                            // Se mostrara el dialogo
                            progressDialog.show();
                            // Se iniciara el hilo que cargara el intent
                            charger.start();
                        } else { // Si su contraseña no coincide
                            // Se mandara un mensaje de error coerente a sus circunstancias
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                            Snackbar.make(v, "Contraseña Incorrecta", Snackbar.LENGTH_SHORT).show();
                        }
                    }else { // Si su nombre no es null (no esta en la base de datos)
                        // Se le mostrara un mensaje con una invitacion a registrarse
                        Snackbar snackbar = Snackbar.make(v, "Usuario no encontrado", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(getColor(R.color.link)) // Se le pasara el color
                                .setAction("registrese", new View.OnClickListener() {   // Se creara una accion
                                    @Override
                                    public void onClick(View v) {
                                        // Si lo pulsa lanzara un intent a la ventana de registrase
                                        startActivity(new Intent(MainActivity.this, Registrar.class));
                                    }
                                });
                        // Se mostrara el mensaje
                        snackbar.show();
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    }
                }else { // Si el correo y contraseñas estan vacios
                    // Se le mostrara un mensaje de error correspondiendte
                    v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    Snackbar.make(v, "No se olvide de rellenar todos los campos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // Al presionar el boton de registrar
        findViewById(R.id.textRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se lanzara un intent hacia la ventana de registrar
                startActivity(new Intent(getApplicationContext(), Registrar.class));
            }
        });


        // Si se presiona el boton de acerca de
        findViewById(R.id.buttonAcercaDe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se lanzara un intent hacia la ventana de Acerca de
                startActivity(new Intent(getApplicationContext(), AcercaDe.class));
            }
        });
    }

    /**
     * Al reiniciar la ventana
     */
    @Override
    protected void onRestart() {
        super.onRestart();              // Llamo  al metodo original
        try {
            correo.setText("");             // Se reinicia el texto del correo
            correo.requestFocus();          // Se pone el foco en el correo
            password.setText("");           // Se reinicia el texto de la contraseña
            if (progressDialog != null) {      // Si el dialogo de carga no es null
                progressDialog.cancel();    // Este dejara de funcionar
            }
        }catch (Exception e){}
    }
}