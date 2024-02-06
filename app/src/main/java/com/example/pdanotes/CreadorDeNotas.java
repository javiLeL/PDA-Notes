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

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Creando el menu
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbarCreadorDeNotas);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Recogiendo informacion que se le pasa
        botonGuardar = findViewById(R.id.floatingActionButtonGuardarNota);
        botonBorrar = findViewById(R.id.floatingActionButtonBorrarNota);

        abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abrir);
        cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cerrar);
        izquierda = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_izquierda);
        derecha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_derecha);

        botonGuardar.startAnimation(cerrar);
        botonBorrar.startAnimation(cerrar);
        botonGuardar.setEnabled(false);
        botonBorrar.setEnabled(false);
        // findViewById(R.id.floatingActionButtonMenuNotas).startAnimation(derecha);

        id = getIntent().getExtras().getInt("id");
        titulor = getIntent().getExtras().getString("titulo");
        notar = getIntent().getExtras().getString("nota");

        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        titulo =  findViewById(R.id.editTextTextTitulo);
        nota = findViewById(R.id.editTextTextMultiLineNota);

        if (id!=null){
            titulo.setText(titulor);
            nota.setText(notar);
        }
        
        ProgressDialog a = new ProgressDialog(this);
        a.setTitle("Gurdando sus datos");
        a.setCancelable(false);
        a.setProgressStyle(0);
        findViewById(R.id.floatingActionButtonMenuNotas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                // saveNote();
            }
        });
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });
        botonGuardar.setClickable(false);
        botonBorrar.setClickable(false);
        leerConfiguracion();
    }
    void saveNote(){
        Thread save = new Thread(new Runnable() {
            @Override
            public void run() {
                if (id==0) {
                    boolean isInsertado = new ModeloBBDD().insertarNotas(getApplicationContext(), new Nota(titulo.getText().toString(), Nota.encrypt(nota.getText().toString(), pass, pass), correo));
                    if (isInsertado) {
                        // Toast.makeText(this, "Nota Guardada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
                    new ModeloBBDD().updateNota(getApplicationContext(), id, new Nota(titulo.getText().toString(), Nota.encrypt(nota.getText().toString(), pass, pass), correo));
                    finish();
                }
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Gurdando sus datos");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(0);
        save.start();
        progressDialog.show();
    }
    void deleteNote(){
        if (id==0){
            Toast.makeText(this, "Primero debes de crear la nota para borrarla", Toast.LENGTH_SHORT).show();
        }else {
            new ModeloBBDD().deleteNotas(getApplicationContext(), id);
            finish();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        leerConfiguracion();
    }

    private void leerConfiguracion(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isPersonalizado = preferences.getBoolean("check_box_preference_fondo_personalizado", false);
        String tituloDefault = preferences.getString("titulo_por_defecto", "");
        String fuente = preferences.getString("list_preference_fuente", "Del sistema");
        String fondo = preferences.getString("list_preference_fondo", "Por Defecto");

        if (isPersonalizado){
            if(fondo.equals("Rojo")){
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
        if (fuente.equals("Minecraft")){
            Typeface typeface = ResourcesCompat.getFont(this, R.font.minecraft);
            titulo.setTypeface(typeface);
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
        if (id==0) {
            titulo.setText(tituloDefault);
        }
    }
}