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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.pdanotes.bbdd.ModeloBBDD;
import com.example.pdanotes.dto.Nota;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class SelectorDeNotas extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView rv;
    String correo, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_de_notas);

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

        // Recogiendo informacion que se le pasa por el intent
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Cargo el RecyclerView por la id
        rv = findViewById(R.id.rv);

        // Si se pulsa el boton flotante
        findViewById(R.id.floatingActionButtonCrearEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se creara y lanzara un intent con el correo y contraseña real del usuario
                Intent intent = new Intent(getApplicationContext(), CreadorDeNotas.class);
                intent.putExtra("correo", correo);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });
        // Actualizara el RecicleView
        update();
    }

    /**
     * Metodo que carga todas las notas de un usuario
     */
    void update(){
        // Obtiene la lista de notas
        List<Nota> notas = new ArrayList<>(new ModeloBBDD().selectNotas(getApplicationContext(), correo, pass));

        if(notas.size()<=1){    // Si esta es igual o inferior a una nota
            // Las notas en horizontal seran de una
            rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        }else{                  // Si es de mas de una
            // Las notas en horizontal seran de dos
            rv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        }
        // Se le pasara el adaptador con todas las notas recolectadas
        rv.setAdapter(new AdapterNotas(notas, getApplicationContext(), pass, this));
        //rv.setHasFixedSize(fa);
    }

    /**
     * Al reiniciar el layout
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        // Volvera a cargar todas las notas que se encuentre
        update();
    }

    /**
     * Metodo que detecta que se ha pulsado un boton en el menu
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Dependiendo de lo que se presione hara una accion u otra
        if (item.getItemId()==R.id.apartado_de_eventos){
            Intent intent = new Intent(this, SelectorDeEventos.class);
            intent.putExtra("correo", correo);
            intent.putExtra("pass", pass);
            startActivity(intent);
            finish();
        } else if (item.getItemId()==R.id.apartado_de_ajustes) {
            startActivity(new Intent(this, Ajustes.class));
        } else if (item.getItemId()==R.id.acerca_de){
            startActivity(new Intent(this, AcercaDe.class));
        }
        // Simpre se cerrar el menu
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}