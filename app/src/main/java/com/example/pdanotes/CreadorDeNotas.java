package com.example.pdanotes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class CreadorDeNotas extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    FloatingActionButton botonGuardar, botonBorrar;
    Toolbar toolbar;
    EditText titulo, nota;
    String titulor, notar, correo, pass;
    Integer id;
    Animation abrir, cerrar, izquierda, derecha;
    boolean isAbierto = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_de_notas);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Se recoge todos los datos que se le pasa cuando se evento en la edicion de un nota.
        // Si no se recogen datos significa que la nota es nuevo
        id = getIntent().getExtras().getInt("id");
        titulor = getIntent().getExtras().getString("titulo");
        notar = getIntent().getExtras().getString("nota");
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Creando el menu que aparecera arriba a la izquierda
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbarCreadorDeNotas);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Recogiendo informacion que se le pasa
        // Botones flotantes
        botonGuardar = findViewById(R.id.floatingActionButtonGuardarNota);
        botonBorrar = findViewById(R.id.floatingActionButtonBorrarNota);

        // Animaciones
        abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abrir);
        cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cerrar);
        izquierda = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_izquierda);
        derecha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_derecha);

        // Los diferentes campos al crear la nota
        titulo =  findViewById(R.id.editTextTextTitulo);
        nota = findViewById(R.id.editTextTextMultiLineNota);

        // Se cierran los botones y se desactivan los botones y se hacen no clickables
        botonGuardar.startAnimation(cerrar);
        botonBorrar.startAnimation(cerrar);
        botonGuardar.setEnabled(false);
        botonBorrar.setEnabled(false);
        botonGuardar.setClickable(false);
        botonBorrar.setClickable(false);
        // findViewById(R.id.floatingActionButtonMenuNotas).startAnimation(derecha);

        if (id!=0){  // Si la id no es 0 se pondra el titulo recolectado por la nota pasada
            titulo.setText(titulor);
            nota.setText(notar);
        }

        // Se crea un dialogo de progreso
        ProgressDialog progressDialog = new ProgressDialog(this);
        // Se le pasara un titulo intuitivo
        progressDialog.setTitle("Gurdando sus datos");
        // Se hara que no sea cancelable al pulsar fuera de este
        progressDialog.setCancelable(false);
        // Se le especifica que su progres bar sera circular
        progressDialog.setProgressStyle(0);
        findViewById(R.id.floatingActionButtonMenuNotas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se quitara la funcionalidad del boton dependiendo si se habia abierto o no con anterioridad
                botonGuardar.setEnabled(!isAbierto);
                botonBorrar.setEnabled(!isAbierto);
                botonGuardar.setClickable(!isAbierto);
                botonBorrar.setClickable(!isAbierto);
                if(isAbierto) {
                    v.startAnimation(izquierda);
                    botonGuardar.startAnimation(cerrar);
                    botonBorrar.startAnimation(cerrar);
                    isAbierto=false;
                }else {
                    v.startAnimation(derecha);
                    botonGuardar.startAnimation(abrir);
                    botonBorrar.startAnimation(abrir);
                    isAbierto=true;
                }
            }
        });
        // Si se presiona le boton de guardar nota
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se guardara la nota
                saveNote();
            }
        });
        // Si se presiona el boton borrar
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se borrara la nota
                deleteNote();
            }
        });
        // Se leera la configuracion de la aplicacion (Fuentes fondos...)
        leerConfiguracion();
    }

    /**
     * Metodo capaz de guardar la nota que contienen los campos
     */
    void saveNote(){
        // Se crea un hilo
        Thread save = new Thread(new Runnable() {
            @Override
            public void run() {
                // El cual
                if (id==0) { // Si la id es 0
                    // Se insertara la nota en la base de datos
                    boolean isInsertado = new ModeloBBDD().insertarNotas(getApplicationContext(), new Nota(titulo.getText().toString(), Nota.encrypt(nota.getText().toString(), pass, pass), correo));
                    if (isInsertado) {// Si se inseroto correctamente se cerrara el apartado de edicion
                        finish();
                    }
                }else {     // Si la id es distinto a 0
                    // Se actualizara la nota
                    new ModeloBBDD().updateNota(getApplicationContext(), id, new Nota(titulo.getText().toString(), Nota.encrypt(nota.getText().toString(), pass, pass), correo));
                    // Y se cerrara el editor
                    finish();
                }
            }
        });
        // Se creara un dialogo de progreso
        ProgressDialog progressDialog = new ProgressDialog(this);
        // Se le pondra un titulo intuitivo
        progressDialog.setTitle("Gurdando sus datos");
        // Se le especifica que no se puede cancelar si se presiona fuera del mismo
        progressDialog.setCancelable(false);
        // Se le especifica que su progres bar sera de tipo circular
        progressDialog.setProgressStyle(0);
        // Se lanzara el hilo que se dedica a guardar la informacion en la base de datos
        save.start();
        // Se lanza el dialogo para que se muestre en pantalla
        progressDialog.show();
    }

    /**
     * Metodo que borrar una nota
     */
    void deleteNote(){
        if (id==0){ // Si la id es 0
            // Se le especificara que la nora no ha sido creado
            Toast.makeText(this, "Primero debes de crear la nota para borrarla", Toast.LENGTH_SHORT).show();
        }else {     // Se posee otra id
            // Se borrar la nota con dicha id
            new ModeloBBDD().deleteNotas(getApplicationContext(), id);
            // Se cerrar el modo editor de notas
            finish();
        }
    }

    /**
     * Metodo que lee el boton que ha sido presionado en el menu
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Deependiendo de lo que se presione se hara una cosa u otra
        if(item.getItemId()==R.id.guardar_nota){
            saveNote();
        }else if (item.getItemId()==R.id.borrar_nota){
            deleteNote();
        } else if (item.getItemId() == R.id.cerrar_nota) {
            Thread save = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(400);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    finish();
                }
            });
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saliendo del modo editor");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(0);
            save.start();
            progressDialog.show();
        }
        // Simpre se cerrara el menu
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Siempre que se habra el editor de notas se cargaran los datos pasados por el menu
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        leerConfiguracion();
    }

    /**
     * Recogera la informacion selecionada en los ajustes globales y hara unas cosas u otras con respecto a esto
     */
    private void leerConfiguracion(){
        // Se recogera la infromacion relebante a las notas
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPersonalizado = preferences.getBoolean("check_box_preference_fondo_personalizado", false);
        String tituloDefault = preferences.getString("titulo_por_defecto", "");
        String fuente = preferences.getString("list_preference_fuente", "Del sistema");
        String fondo = preferences.getString("list_preference_fondo", "Por Defecto");

        if (isPersonalizado){   // Si el fondo es presonalizaod
            // Dependiendo de lo elegido por el usuario se pondra un color u otro
            if(fondo.equals("Rojo")){
                // La metodologia es siempre la misma
                // El color del fondo se teñira del color selecionado
                drawer.setBackgroundColor(getResources().getColor(R.color.rojo));
            } else if (fondo.equals("Naranja")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.naranja));
            } else if (fondo.equals("Amarillo")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.amarillo));
            } else if (fondo.equals("Verde")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.verde));
            } else if (fondo.equals("Cyan")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.cyan));
            } else if (fondo.equals("Azul")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.azul));
            } else if (fondo.equals("Magenta")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.magenta));
            } else if (fondo.equals("Morado")) {
                drawer.setBackgroundColor(getResources().getColor(R.color.morado));
            }
        }
        // Dependiendo de la funte elegida se cargara un archivo de fuetes u otro
        if (fuente.equals("Minecraft")){
            // La metodologia siempre es la misma
            // Se recolecta la informacion del archivo de fuentes que se llama x
            Typeface typeface = ResourcesCompat.getFont(this, R.font.minecraft);
            // Y se le aplicara tanto al titulo
            titulo.setTypeface(typeface);
            // Como a la nota
            nota.setTypeface(typeface);
        } else if (fuente.equals("dDenut")) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.d_denyut);
            titulo.setTypeface(typeface);
            nota.setTypeface(typeface);
        } else if (fuente.equals("Love Craft")) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.love_craft);
            titulo.setTypeface(typeface);
            nota.setTypeface(typeface);
        } else if (fuente.equals("Old London")) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.olondon);
            titulo.setTypeface(typeface);
            nota.setTypeface(typeface);
        }
        if (id==0) {    // Si su id es 0 es decir la nota no se ha creado con anterioridad
            // El titulo poseera el valor pordefecto escrito por el usuario
            titulo.setText(tituloDefault);
        }
    }
}