package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JaviLeL
 * @version 1.o.1
 */
public class SelectorDeEventos extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rv;
    String correo, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_de_eventos);

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Creo el menu de arriva a la izquierda
        drawer = findViewById(R.id.drawer_principal);
        navigationView = findViewById(R.id.navigation_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Recogiendo informacion que se le pasa del intent
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Cargo el RecyclerView por la id
        rv = findViewById(R.id.rv);

        // Si se presiona el boton flotante
        findViewById(R.id.floatingActionButtonCrearEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creara y lanzara un intent con la contraseña sin MD5 y el correo del usuaio registrado
                Intent intent = new Intent(getApplicationContext(), CreadorDeEventos.class);
                intent.putExtra("correo", correo);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });
        // Se actualizara las notas existentes (solo aplicable la primera vez)
        update();
    }

    /**
     * Metodo que carga todos las eventos en el RecicleView
     */
    void update(){
        // Pondra la estetica del RecicleView
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        // Recogera todos los eventos que este usuario tubiese
        List<Evento> eventos = new ArrayList<>(new ModeloBBDD().selectEventos(getApplicationContext(), correo, pass));
        // Se los pone en el adaptador del RecicleView
        rv.setAdapter(new AdapterEventos(eventos, getApplicationContext(), pass, this));
    }

    /**
     * Al reiniciar el layout
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        // Volvera a alctualaizara los eventos
        update();
    }

    /**
     * Metodo que detecta si se ha pulsado un boton del menu
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Dependiedo de lo que se presione hara una cosa u otra
        if (item.getItemId() == R.id.apartado_de_notas){
            Intent intent = new Intent(this, SelectorDeNotas.class);
            intent.putExtra("correo", correo);
            intent.putExtra("pass", pass);
            startActivity(intent);
            finish();
        }else if (item.getItemId()==R.id.apartado_de_ajustes) {
            startActivity(new Intent(this, Ajustes.class));
        } else if (item.getItemId()==R.id.acerca_de){
            startActivity(new Intent(this, AcercaDe.class));
        }
        // Se cerrara el menu
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}