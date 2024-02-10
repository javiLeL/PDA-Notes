package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {
    private final String[] PREFIJOS = new String[]{"+1", "+52", "+55", "+54", "+57", "+56", "+58", "+51", "+593", "+53", "+591", "+506", "+507", "+598", "+34", "+49", "+33", "+39", "+44", "+7", "+380", "+48", "+40", "+31", "+32", "+30", "+351", "+46", "+47", "+86", "+91", "+81", "+82", "+62", "+90", "+63", "+66", "+84", "+972", "+60", "+65", "+92", "+880", "+966", "+20", "+27", "+234", "+254", "+212", "+213", "+256", "+233", "+237", "+225", "+221", "+255", "+249", "+218", "+216", "+61", "+64", "+679", "+675", "+676", "+98", "+964", "+962", "+961", "+965", "+971", "+968", "+974", "+973", "+967"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, PREFIJOS));
        spinner.setSelection(14);
        findViewById(R.id.buttonRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre =((EditText) findViewById(R.id.editTextNombreRegistrar)).getText().toString().trim();
                String correo =((EditText) findViewById(R.id.editTextCorreoElectronicoRegistrar)).getText().toString().trim().toLowerCase();
                String telefono =((EditText) findViewById(R.id.editTextTelefonoRegistrar)).getText().toString().trim().toLowerCase();
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
                    } else if (isTelefono(telefono)){
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Snackbar.make(v, "Asegurese de poner un telefono real", Snackbar.LENGTH_LONG).show();
                    } else {
                        boolean isCreado = new ModeloBBDD().insertarUsuario(getApplicationContext(), new Usuario(correo, nombre,spinner.getSelectedItem().toString()+telefono, password));
                        if(!isCreado){
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                            Toast.makeText(Registrar.this, "Usuario ya creado", Toast.LENGTH_SHORT).show();
                        }else {
                            sendEmail(correo, "Informacion persoanl", "Su nombre de usuario: "+nombre+"\nSu telefono es: "+(spinner.getSelectedItem().toString()+telefono)+"\nSu contraseña es: "+password);
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
    boolean isTelefono(String telefono){
        Pattern pattern = Patterns.PHONE;
        Matcher matcher = pattern.matcher(telefono);
        return !matcher.matches();
    }
    boolean isPasswd(String pass){
        return pass.length()<8;
    }
    boolean isNombre(String nombre){
        return nombre.length()<4;
    }
    void sendEmail(String email, String titulo, String texto){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
        intent.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(intent);
    }
}