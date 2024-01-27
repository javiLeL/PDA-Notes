package com.example.pdanotes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
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

        botonGuardar = findViewById(R.id.floatingActionButtonGuardarNotas);
        botonBorrar = findViewById(R.id.floatingActionButtonBorrarNotas);

        abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abrir);
        cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cerrar);
        izquierda = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_izquierda);
        derecha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_derecha);

        botonGuardar.startAnimation(cerrar);
        botonBorrar.startAnimation(cerrar);
        botonGuardar.setEnabled(!isAbierto);
        botonBorrar.setEnabled(!isAbierto);
        botonGuardar.setClickable(!isAbierto);
        botonBorrar.setClickable(!isAbierto);
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
            new ModeloBBDD().delete(getApplicationContext(), id);
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
}