package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class CreadorDeEventos extends AppCompatActivity {
    Integer id;
    EditText titulo, tipo, fecha, hora;
    String titulor, tipor, fechar, horar, correo, pass;
    LocalTime horaG;
    LocalDate fechaG;
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

        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Datos recolectados
        titulo = findViewById(R.id.editTextTextTitulo);
        tipo = findViewById(R.id.editTextTextTipo);
        fecha = findViewById(R.id.editTextTextFecha);
        hora = findViewById(R.id.editTextTextHora);

        // Poniendo los datos
        if(id!=0) {
            titulo.setText(titulor);
            tipo.setText(tipor);
            fecha.setText(fechar);
            hora.setText(horar);

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
                    boolean isInsertado = new ModeloBBDD().insertarEvento(getApplicationContext(), new Evento(titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, correo));
                    if (isInsertado) {
                        // Toast.makeText(CreadorDeEventos.this, "guardado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    new ModeloBBDD().updateEvento(getApplicationContext(), new Evento(id, titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, correo));
                    finish();
                    /*
                    // Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
                    new ModeloBBDD().updateNota(getApplicationContext(), id, new Nota(titulo.getText().toString(), Nota.encrypt(nota.getText().toString(), pass, pass), correo));
                    finish();
                    */
                }
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Gurdando sus datos");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(0);
        save.start();
        // progressDialog.show();
    }
    void deleteEvento(){
        if (id==0){
            Toast.makeText(this, "Primero debes de crear el evento para borrarlo", Toast.LENGTH_SHORT).show();
        }else {
            new ModeloBBDD().deleteEventos(getApplicationContext(), id);
            finish();
        }
    }
}