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
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        drawer = findViewById(R.id.drawer_principal);
        navigationView = findViewById(R.id.navigation_principal);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Recogiendo informacion que se le pasa
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        rv = findViewById(R.id.rv);
        // rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        findViewById(R.id.floatingActionButtonCrearEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreadorDeEventos.class);
                intent.putExtra("correo", correo);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });

        update();
    }
    void update(){
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        List<Evento> eventos = new ArrayList<>(new ModeloBBDD().selectEventos(getApplicationContext(), correo, pass));
        rv.setAdapter(new AdapterEventos(eventos, getApplicationContext(), pass, this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.apartado_de_notas){
            Intent intent = new Intent(this, SelectorDeNotas.class);
            intent.putExtra("correo", correo);
            intent.putExtra("pass", pass);
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}