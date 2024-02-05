package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                CheckBox checkBox = findViewById(R.id.checkBoxTerminosYCondiciones);

                if (!nombre.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
                    if (isCorreo(correo)){
                        Snackbar.make(v, "Asegurese de poner su correo real", Snackbar.LENGTH_LONG).show();
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    } else if (isPasswd(password)) {
                        Snackbar snackbar = Snackbar.make(v, "Asegurese de poner una contraseña mas larga", Snackbar.LENGTH_LONG);
                        snackbar.setAction("como minimo 8 letras", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    } else if (isNombre(nombre)) {

                        Snackbar snackbar = Snackbar.make(v, "Asegurese de poner un nombre mas largo", Snackbar.LENGTH_LONG);
                        snackbar.setAction("como minimo de 4 letras", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    } else if(!checkBox.isChecked()) {
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Snackbar.make(v, "Asegurese de Aceptar nuestros terminos y condiciones", Snackbar.LENGTH_LONG).show();
                    } else {
                        boolean isCreado = new ModeloBBDD().insertarUsuario(getApplicationContext(), new Usuario(correo, nombre, password));
                        if(!isCreado){
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                            Toast.makeText(Registrar.this, "Error al crear el registro", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Registrar.this, "Registro Creado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }else {
                    Snackbar.make(v, "No se olvide de rellenar todos los campos", Snackbar.LENGTH_SHORT).show();
                    v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                }
            }
        });
        findViewById(R.id.textViewVerPoliticas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PoliticasDePrivacidad.class));
            }
        });

        // Contextual
        registerForContextMenu(findViewById(R.id.editTextNombreRegistrar));
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        TextInputEditText editTextNombre = findViewById(R.id.editTextNombreRegistrar);
        if(item.getItemId() == R.id.set_red){
            editTextNombre.setTextColor(Color.RED);
        } else if (item.getItemId() == R.id.set_upper) {
            editTextNombre.setText(editTextNombre.getText().toString().toUpperCase());
        } else if (item.getItemId() == R.id.set_loweer) {
            editTextNombre.setText(editTextNombre.getText().toString().toLowerCase());
        }
        return super.onContextItemSelected(item);
    }
    boolean isCorreo(String correo){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(correo);
        return !matcher.matches();
    }
    boolean isPasswd(String pass){
        return pass.length()<8;
    }
    boolean isNombre(String nombre){
        return nombre.length()<4;
    }
}