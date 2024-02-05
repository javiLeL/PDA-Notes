package com.example.pdanotes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class CreadorDeEventos extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    Integer id;
    EditText titulo,  fecha, hora;
    String titulor, tipor, fechar, horar, descripcionr, correo, pass;
    LocalTime horaG;
    LocalDate fechaG;
    AutoCompleteTextView tipo;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MultiAutoCompleteTextView descripcion;
    final Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_de_eventos);

        id = getIntent().getExtras().getInt("id");
        titulor = getIntent().getExtras().getString("titulo");
        tipor = getIntent().getExtras().getString("tipo");
        fechar = getIntent().getExtras().getString("fecha");
        horar = getIntent().getExtras().getString("hora");
        descripcionr = getIntent().getExtras().getString("descripcion");

        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Creando el menu

        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_eventos);
        toolbar = findViewById(R.id.toolbarCreadorDeEventos);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Datos recolectados
        titulo = findViewById(R.id.editTextTextTitulo);
        tipo = findViewById(R.id.editTextTextTipo);
        fecha = findViewById(R.id.editTextTextFecha);
        hora = findViewById(R.id.editTextTextHora);
        descripcion = findViewById(R.id.editTextTextMultiLineDescripcion);

        // Pasando los datos al autocomplete view
        tipo.setAdapter(ArrayAdapter.createFromResource(this, R.array.tipos_de_evento, android.R.layout.simple_spinner_item));
        // Poniendo los datos
        if(id!=0) {
            titulo.setText(titulor);
            tipo.setText(tipor);
            fecha.setText(fechar);
            hora.setText(horar);
            descripcion.setText(descripcionr);

            calendar.set(Integer.parseInt(fechar.split("-")[0]), Integer.parseInt(fechar.split("-")[1]), Integer.parseInt(fechar.split("-")[2]), Integer.parseInt(horar.split(":")[0]), Integer.parseInt(horar.split(":")[1]));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fechaG = LocalDate.of(Integer.parseInt(fechar.split("-")[0]), Integer.parseInt(fechar.split("-")[1]), Integer.parseInt(fechar.split("-")[2]));
                horaG = LocalTime.of(Integer.parseInt(horar.split(":")[0]), Integer.parseInt(horar.split(":")[1]));
            }
        }

        findViewById(R.id.imageButtonSelecionarFecha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog alertDatePickerDialog = new DatePickerDialog(CreadorDeEventos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            fechaG = LocalDate.of(year, month, dayOfMonth);
                            fecha.setText(fechaG.toString());
                        }
                    }
                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));

                if (alertDatePickerDialog!=null) {
                    alertDatePickerDialog.show();
                }
            }
        });
        findViewById(R.id.imageButtonSelecionarHora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog alert = new TimePickerDialog(CreadorDeEventos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            horaG = LocalTime.of(hourOfDay, minute);
                            hora.setText(horaG.toString());
                        }
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
                alert.show();
            }
        });
        findViewById(R.id.floatingActionButtonGuardarEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaG!=null && horaG!=null && titulo!=null && tipo!=null) {
                    saveEvento();
                }else {
                    Toast.makeText(CreadorDeEventos.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
                    v.startAnimation(AnimationUtils.loadAnimation(CreadorDeEventos.this, R.anim.error_btn));
                }
            }
        });
        findViewById(R.id.floatingActionButtonBorrarEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvento();
            }
        });
    }

    void saveEvento(){
        Thread save = new Thread(new Runnable() {
            @Override
            public void run() {
                if (id==0) {
                    boolean isInsertado = new ModeloBBDD().insertarEvento(getApplicationContext(), new Evento(titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo));
                    if (isInsertado) {
                        // Toast.makeText(CreadorDeEventos.this, "guardado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    new ModeloBBDD().updateEvento(getApplicationContext(), new Evento(id, titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo));
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
    void deleteEvento(){
        if (id==0){
            Toast.makeText(getApplicationContext(), "Primero debes de crear el evento para borrarlo", Toast.LENGTH_SHORT).show();
        }else {
            new ModeloBBDD().deleteEventos(getApplicationContext(), id);
            finish();
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.guardar_evento){
            if (fechaG!=null && horaG!=null && titulo!=null && tipo!=null) {
                saveEvento();
            }else {
                Toast.makeText(CreadorDeEventos.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        }else if (item.getItemId()==R.id.borrar_evento){
            deleteEvento();
        } else if (item.getItemId()==R.id.cerrar_evento) {
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