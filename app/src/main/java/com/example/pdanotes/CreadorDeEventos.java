package com.example.pdanotes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CreadorDeEventos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Integer id;
    EditText titulo, fecha, hora;
    String titulor, tipor, fechar, horar, descripcionr, correo, pass;
    LocalTime horaG;
    LocalDate fechaG;
    AutoCompleteTextView tipo;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    MultiAutoCompleteTextView descripcion;
    PendingIntent pendingIntent;
    Animation abrir, cerrar, izquierda, derecha;
    FloatingActionButton botonGuardar, botonBorrar;
    boolean isAbierto = false;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_de_eventos);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        botonGuardar = findViewById(R.id.floatingActionButtonGuardarEvento);
        botonBorrar = findViewById(R.id.floatingActionButtonBorrarEvento);

        abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abrir);
        cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cerrar);
        izquierda = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_izquierda);
        derecha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_derecha);

        titulo = findViewById(R.id.editTextTextTitulo);
        tipo = findViewById(R.id.editTextTextTipo);
        fecha = findViewById(R.id.editTextTextFecha);
        hora = findViewById(R.id.editTextTextHora);
        descripcion = findViewById(R.id.editTextTextMultiLineDescripcion);

        botonGuardar.startAnimation(cerrar);
        botonBorrar.startAnimation(cerrar);
        botonGuardar.setEnabled(false);
        botonBorrar.setEnabled(false);

        // Pasando los datos al autocomplete view
        tipo.setAdapter(ArrayAdapter.createFromResource(this, R.array.tipos_de_evento, android.R.layout.simple_spinner_dropdown_item));
        descripcion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String isThisTipo = tipo.getText().toString().trim();
                if (isThisTipo.equals("Compra")) {
                    descripcion.setAdapter(ArrayAdapter.createFromResource(CreadorDeEventos.this, R.array.compras, android.R.layout.simple_list_item_1));
                }else {
                    descripcion.setAdapter(new ArrayAdapter<String>(CreadorDeEventos.this,  android.R.layout.simple_list_item_1, new String[]{}));
                }
            }
        });
        // descripcion.setAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.compras, android.R.layout.simple_dropdown_item_1line));
        descripcion.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        // Poniendo los datos
        if (id != 0) {
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
                            fechaG = LocalDate.of(year, month+1, dayOfMonth);
                            fecha.setText(fechaG.toString());
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                if (alertDatePickerDialog != null) {
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
                }, (calendar.get(Calendar.HOUR)+12)%24, calendar.get(Calendar.MINUTE), true);
                alert.show();
            }
        });

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
            }
        });

        findViewById(R.id.floatingActionButtonGuardarEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fechaG != null && horaG != null && titulo != null && tipo != null) {
                    saveEvento();
                } else {
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
        botonGuardar.setClickable(false);
        botonBorrar.setClickable(false);
    }

    void saveEvento() {
        Thread save = new Thread(new Runnable() {
            @Override
            public void run() {
                if (id == 0) {
                    Evento evento = new Evento(titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo);
                    boolean isInsertado = new ModeloBBDD().insertarEvento(getApplicationContext(), evento);
                    if (isInsertado) {
                        clickNotificacion(evento);
                        crearCanal();
                        lanzarNotificacion(evento);
                        // Toast.makeText(CreadorDeEventos.this, "guardado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Evento evento = new Evento(id, titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo);
                    new ModeloBBDD().updateEvento(getApplicationContext(), evento);
                    clickNotificacion(evento);
                    crearCanal();
                    lanzarNotificacion(evento);
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

    void deleteEvento() {
        if (id == 0) {
            Toast.makeText(getApplicationContext(), "Primero debes de crear el evento para borrarlo", Toast.LENGTH_SHORT).show();
        } else {
            new ModeloBBDD().deleteEventos(getApplicationContext(), id);
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guardar_evento) {
            if (fechaG != null && horaG != null && titulo != null && tipo != null) {
                saveEvento();
            } else {
                Toast.makeText(CreadorDeEventos.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.borrar_evento) {
            deleteEvento();
        } else if (item.getItemId() == R.id.cerrar_evento) {
            Thread save = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.currentThread().sleep(400);
                    } catch (Exception e) {
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

    /**
     * Metodo que crea el canal donde se lanzara la notificacion
     */
    void crearCanal() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0", "canal fecha y hora", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager constructor = getSystemService(NotificationManager.class);
            constructor.createNotificationChannel(channel);
        }
    }

    /**
     * metodo usado para lanzar la notificacion en el canal creado con anterioridad
     */
    void lanzarNotificacion(Evento evento) {

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(getApplicationContext(), "0");
        constructor.setSmallIcon(R.drawable.calendar_month);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            constructor.setContentText(evento.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
        };
        constructor.setSubText(evento.getTitulo());
        constructor.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        constructor.setContentIntent(pendingIntent);
        NotificationManagerCompat noti = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        noti.notify(0, constructor.build());
    }
    void clickNotificacion(Evento evento){
        Intent intent = new Intent(this, NotificacionActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.putExtra("fecha", evento.getFechaYHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            intent.putExtra("hora", evento.getFechaYHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        };
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }
}