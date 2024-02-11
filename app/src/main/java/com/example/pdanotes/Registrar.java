package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pdanotes.bbdd.ModeloBBDD;
import com.example.pdanotes.dto.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class Registrar extends AppCompatActivity {
    // Creo un array de strings con todos los prefijos de moviles
    private final String[] PREFIJOS = new String[]{"+1", "+52", "+55", "+54", "+57", "+56", "+58", "+51", "+593", "+53", "+591", "+506", "+507", "+598", "+34", "+49", "+33", "+39", "+44", "+7", "+380", "+48", "+40", "+31", "+32", "+30", "+351", "+46", "+47", "+86", "+91", "+81", "+82", "+62", "+90", "+63", "+66", "+84", "+972", "+60", "+65", "+92", "+880", "+966", "+20", "+27", "+234", "+254", "+212", "+213", "+256", "+233", "+237", "+225", "+221", "+255", "+249", "+218", "+216", "+61", "+64", "+679", "+675", "+676", "+98", "+964", "+962", "+961", "+965", "+971", "+968", "+974", "+973", "+967"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Cargo el spiner
        Spinner spinner = findViewById(R.id.spinner);
        // Y le paso un array adapter con todos los prefijos
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, PREFIJOS));
        // Por defecto selecionara el numero 14 el cual es +34
        spinner.setSelection(14);

        // Al presoinar el boton registrar
        findViewById(R.id.buttonRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se cargaran todos los datos que sean relevantes
                String nombre =((EditText) findViewById(R.id.editTextNombreRegistrar)).getText().toString().trim();
                String correo =((EditText) findViewById(R.id.editTextCorreoElectronicoRegistrar)).getText().toString().trim().toLowerCase();
                String telefono =((EditText) findViewById(R.id.editTextTelefonoRegistrar)).getText().toString().trim().toLowerCase();
                String password =((EditText) findViewById(R.id.editTextPasswordRegistrar)).getText().toString();
                CheckBox checkBox = findViewById(R.id.checkBoxTerminosYCondiciones);

                if (!nombre.isEmpty() && !correo.isEmpty() && !password.isEmpty() && !telefono.isEmpty()){     // Si ningun campo esta vacio
                    if (isCorreo(correo)){  // Si el correo no es un correo electronico
                        // Se le pasara una mensaje al usuario con el error
                        Snackbar.make(v, "Asegurese de poner su correo real", Snackbar.LENGTH_LONG).show();
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                    } else if (isPasswd(password)) {    // Si la contraseña no es una contraseña
                        // Se le pasara un error y se le especificara como ha de ser la contraseña
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
                    } else if (isNombre(nombre)) {  // Si el nombre no cumple con las especificaciones
                        // Se lanzara un mensaje con el problema y con las especificaciones que este ha de tener
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
                    } else if(!checkBox.isChecked()) {  // Si la checkBox no este pulsada
                        // Avisara al usuario de esto
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Snackbar.make(v, "Asegurese de Aceptar nuestros terminos y condiciones", Snackbar.LENGTH_LONG).show();
                    } else if (isTelefono(telefono)){   // Si el telefono no es un telefono
                        // Se le pondra el mensaje al usuario
                        v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                        Snackbar.make(v, "Asegurese de poner un telefono real", Snackbar.LENGTH_LONG).show();
                    } else {    // Si todos los campos estan bien
                        // Inentara crear al usuario
                        boolean isCreado = new ModeloBBDD().insertarUsuario(getApplicationContext(), new Usuario(correo, nombre, spinner.getSelectedItem().toString() + telefono, password));
                        if (!isCreado) {  // Si no se ha creado
                            // Significara que el usuario ya se creo con anterioridad y se le avisara al usuario
                            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                            Toast.makeText(Registrar.this, "Usuario ya creado", Toast.LENGTH_SHORT).show();
                        } else {         // Si se ha creado
                            // Se le mandara un correo con la informacion personal recolectada
                            sendEmail(correo, "Informacion persoanl", "Su nombre de usuario: " + nombre + "\nSu telefono es: " + (spinner.getSelectedItem().toString() + telefono) + "\nSu contraseña es: " + password);
                            // Se mandara un mensaje de que este usuario se ha registrado
                            Toast.makeText(Registrar.this, "Registro Creado", Toast.LENGTH_SHORT).show();
                            // Se cerrara el apartado de registrar
                            finish();
                        }
                    }
                } else { // Si alguno de los campos estaba vacio
                    // Se lo hara saber al usuario con un mensaje intuitivo
                    Snackbar.make(v, "No se olvide de rellenar todos los campos", Snackbar.LENGTH_SHORT).show();
                    v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.error_btn));
                }
            }
        });

        // Si se persiona en el texto de las politicas y privacidad
        findViewById(R.id.textViewVerPoliticas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se iniciara la ventana de las politicas de privacidad
                startActivity(new Intent(getApplicationContext(), PoliticasDePrivacidad.class));
            }
        });

        // Se creara el menu Contextual en el editor de texto del nombre
        registerForContextMenu(findViewById(R.id.editTextNombreRegistrar));
    }

    /**
     * Metodo que infla el menu contextual
     * @param menu The context menu that is being built
     * @param v The view for which the context menu is being built
     * @param menuInfo Extra information about the item for which the
     *            context menu should be shown. This information will vary
     *            depending on the class of v.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Se infla el menu contextual con su respectivo menu
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * Metodo que detecta cuando es presionado un objeto del menu contextual
     * @param item The context menu item that was selected.
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Se coge el edit text
        TextInputEditText editTextNombre = findViewById(R.id.editTextNombreRegistrar);
        if(item.getItemId() == R.id.set_red){               // Si se presina la opcion rojo
            // Se pondra el texto de color rojo
            editTextNombre.setTextColor(Color.RED);
        } else if (item.getItemId() == R.id.set_upper) {    // Si se elige la opcion mayusculas
            // Pondra el texto contenido en mayusculas
            editTextNombre.setText(editTextNombre.getText().toString().toUpperCase());
        } else if (item.getItemId() == R.id.set_loweer) {   // Si se elige la opcion minusculas
            // Pondra el texto contenido en minusculas
            editTextNombre.setText(editTextNombre.getText().toString().toLowerCase());
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Metodo que calcula si es un correo o no
     * @param correo
     * @return
     */
    boolean isCorreo(String correo){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(correo);
        return !matcher.matches();
    }

    /**
     * Metodo que calcula si es un numero de telefono o no
     * @param telefono
     * @return
     */
    boolean isTelefono(String telefono){
        Pattern pattern = Patterns.PHONE;
        Matcher matcher = pattern.matcher(telefono);
        return !matcher.matches();
    }

    /**
     * Metodo que calcula si es una contraseña o no
     * @param pass
     * @return
     */
    boolean isPasswd(String pass){
        return pass.length()<8;
    }

    /**
     * Metodo que calcula si es un nombre o no
     * @param nombre
     * @return
     */
    boolean isNombre(String nombre){
        return nombre.length()<4;
    }

    /**
     * Metodo que manda un email a un email pasado
     * @param email
     * @param titulo
     * @param texto
     */
    void sendEmail(String email, String titulo, String texto){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
        intent.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(intent);
    }
}