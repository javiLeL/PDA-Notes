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

import com.example.pdanotes.bbdd.ModeloBBDD;
import com.example.pdanotes.dto.Evento;
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

        // Pongo que la orientacion de movil es obligatoriamente en vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Se recoge todos los datos que se le pasa cuando se evento en la edicion de un evento.
        // Si no se recogen datos significa que la evento es nuevo
        id = getIntent().getExtras().getInt("id");
        titulor = getIntent().getExtras().getString("titulo");
        tipor = getIntent().getExtras().getString("tipo");
        fechar = getIntent().getExtras().getString("fecha");
        horar = getIntent().getExtras().getString("hora");
        descripcionr = getIntent().getExtras().getString("descripcion");
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        // Creando el menu que aparece a la derecha
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_eventos);
        toolbar = findViewById(R.id.toolbarCreadorDeEventos);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Datos recolectados

        // Los botones flotantes
        botonGuardar = findViewById(R.id.floatingActionButtonGuardarEvento);
        botonBorrar = findViewById(R.id.floatingActionButtonBorrarEvento);

        // Las animaciones
        abrir = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abrir);
        cerrar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cerrar);
        izquierda = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_izquierda);
        derecha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotacion_derecha);

        // Los diversos campos de la creacion de eventos
        titulo = findViewById(R.id.editTextTextTitulo);
        tipo = findViewById(R.id.editTextTextTipo);
        fecha = findViewById(R.id.editTextTextFecha);
        hora = findViewById(R.id.editTextTextHora);
        descripcion = findViewById(R.id.editTextTextMultiLineDescripcion);

        // Se "esconden los botones y se desactivan para que aparezca el menu por defecto cerrado y se desactivan
        botonGuardar.startAnimation(cerrar);
        botonBorrar.startAnimation(cerrar);
        botonGuardar.setEnabled(false);
        botonBorrar.setEnabled(false);
        botonGuardar.setClickable(false);
        botonBorrar.setClickable(false);

        // Pasando los datos al autocomplete view
        tipo.setAdapter(ArrayAdapter.createFromResource(this, R.array.tipos_de_evento, android.R.layout.simple_spinner_dropdown_item));

        // Cuando la descripcion obtiene el foco
        descripcion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Se lee el tipo de evento que es
                // Esto es facilmente ampliable solo habria que crear mas listas para los diferentes eventos
                String isThisTipo = tipo.getText().toString().trim();
                if (isThisTipo.equals("Compra")) {      // Si este coincide con la palabra compra
                    // Se cargan un adaptador
                    descripcion.setAdapter(ArrayAdapter.createFromResource(CreadorDeEventos.this, R.array.compras, android.R.layout.simple_list_item_1));
                }else {
                    // Si no es ninguno de los anteriores se pone un adaptador vacio para evitar errores
                    descripcion.setAdapter(new ArrayAdapter<String>(CreadorDeEventos.this,  android.R.layout.simple_list_item_1, new String[]{}));
                }
            }
        });
        // descripcion.setAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.compras, android.R.layout.simple_dropdown_item_1line));
        // Se pone un tokenicer para que cada vez que se auto complete al final ponga una coma
        descripcion.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Poniendo los datos
        if (id != 0) {      // Si la id es 0 significara que no se esta cargando de la base de datos esto es porque el autoincrement funciona de 0 a "infinito" y si no se carga de la base de datos por defecto el getIntent pondra un 0 en el int para evitar errores
            titulo.setText(titulor);
            tipo.setText(tipor);
            fecha.setText(fechar);
            hora.setText(horar);
            descripcion.setText(descripcionr);

            // Ya que el la fecha y hora se paso en forma de string hay que convertirlo de nuevo a su clase correspondiente por suerte sigue un patron reconocible
            // Por lo que es faci extraer todos los digitios que lo compone
            calendar.set(Integer.parseInt(fechar.split("-")[0]), Integer.parseInt(fechar.split("-")[1]), Integer.parseInt(fechar.split("-")[2]), Integer.parseInt(horar.split(":")[0]), Integer.parseInt(horar.split(":")[1]));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Se aplica la misma filosofia para la fecha y para la hora
                fechaG = LocalDate.of(Integer.parseInt(fechar.split("-")[0]), Integer.parseInt(fechar.split("-")[1]), Integer.parseInt(fechar.split("-")[2]));
                horaG = LocalTime.of(Integer.parseInt(horar.split(":")[0]), Integer.parseInt(horar.split(":")[1]));
            }
        }
        // Al persiona el boton para elegir la fecha
        findViewById(R.id.imageButtonSelecionarFecha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se lanza un dialogo que contendra un calendario para poder elegir mas facilmente la hora
                // En mi caso en el apartado del mes no me es necesario sumarle un 1 para el correcto duncionamiento del mismo pero esto varia con la version de android
                DatePickerDialog alertDatePickerDialog = new DatePickerDialog(CreadorDeEventos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // Se pone la nueva fecha
                            fechaG = LocalDate.of(year, month+1, dayOfMonth);
                            // Se pone en el editor de texto que hace referencia a la hora
                            fecha.setText(fechaG.toString());
                        }
                    }
                    // A partir del calendar (de la instancia o del recolectado por el recolectado en la base de datos) se le pasan los datos
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                // Si este no es nulo
                if (alertDatePickerDialog != null) {
                    // Se mostrara el dialogo correspondiente a la seleccion de horas
                    alertDatePickerDialog.show();
                }
            }
        });
        // Si se presiona el boton de la selecion de horas
        findViewById(R.id.imageButtonSelecionarHora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se lanzara un dialogo con un reloj para poder elegir la hora de forma mas facil
                TimePickerDialog alert = new TimePickerDialog(CreadorDeEventos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // Se guarda la hora selecionada por el usuario
                            horaG = LocalTime.of(hourOfDay, minute);
                            // Se escribe en el edit text asociado a la hora lo selecionado por el usuario
                            hora.setText(horaG.toString());
                        }
                    }
                    // Se le pasa la informacion obtenida del dispositovo o de la base de datos y se le pasa un formato de 24h
                }, (calendar.get(Calendar.HOUR)+12)%24, calendar.get(Calendar.MINUTE), true);
                // Se lanza el dialogo
                alert.show();
            }
        });
        // Si se presiona el boton del menu
        findViewById(R.id.floatingActionButtonMenuNotas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se activan o desactivan los botones enfuncion si ya lo estaban o no
                botonGuardar.setEnabled(!isAbierto);
                botonBorrar.setEnabled(!isAbierto);
                botonGuardar.setClickable(!isAbierto);
                botonBorrar.setClickable(!isAbierto);
                if(isAbierto) {         // Si estaba abierto se cierra
                    v.startAnimation(izquierda);
                    botonGuardar.startAnimation(cerrar);
                    botonBorrar.startAnimation(cerrar);
                    isAbierto=false;
                }else {                 // Si estaba cerrado se abre
                    v.startAnimation(derecha);
                    botonGuardar.startAnimation(abrir);
                    botonBorrar.startAnimation(abrir);
                    isAbierto=true;
                }
            }
        });
        // Al presionar el boton guardar
        findViewById(R.id.floatingActionButtonGuardarEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se comprobara que todos los campos (menos la descripcion) poseean informacion
                if (fechaG != null && horaG != null && titulo != null && tipo != null) {
                    // Si es asi se guardara el evento
                    saveEvento();
                } else {    // de no ser asi se mostrara un mensaje con el problema asociado
                    Toast.makeText(CreadorDeEventos.this, "No se olvide de rellenar todos los campos", Toast.LENGTH_SHORT).show();
                    v.startAnimation(AnimationUtils.loadAnimation(CreadorDeEventos.this, R.anim.error_btn));
                }
            }
        });
        // Al presionar el boton borrar
        findViewById(R.id.floatingActionButtonBorrarEvento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se borrara el evento correspondiente
                deleteEvento();
            }
        });
    }

    /**
     * Metodo que se encarga de guardar un evento en la base de datos
     */
    void saveEvento() {
        // Se lanzara un hilo el cual se encargara de guardar la informacion en la base de datos
        Thread save = new Thread(new Runnable() {
            @Override
            public void run() {
                if (id == 0) {  // Si la id es 0 significara que la nota es nueva por lo que
                    // Se tratara de insertar el evento
                    Evento evento = new Evento(titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo);
                    boolean isInsertado = new ModeloBBDD().insertarEvento(getApplicationContext(), evento);

                    if (isInsertado) {  // Si este es insertado con normalidad
                        // Se creara un PendingIntent con informacion de la fecha y hora del evento creado
                        clickNotificacion(evento);
                        // Se creara una canal de notificaciones
                        crearCanal();
                        // Se lanzara una notificacion especifica por este canal
                        lanzarNotificacion(evento);
                        // Toast.makeText(CreadorDeEventos.this, "guardado", Toast.LENGTH_SHORT).show();
                        // Se cerrara le creador de eventos
                        finish();
                    }
                } else {    // Si su id no es 0 significara que se esta editando por lo que
                    // Se actualizara le evento
                    Evento evento = new Evento(id, titulo.getText().toString(), tipo.getText().toString(), fechaG, horaG, descripcion.getText().toString(), correo);
                    new ModeloBBDD().updateEvento(getApplicationContext(), evento);
                    // Se creara un PendingIntent con informacion de la fecha y hora del evento creado
                    clickNotificacion(evento);
                    // Se creara una canal de notificaciones
                    crearCanal();
                    // Se lanzara una notificacion especifica por este canal
                    lanzarNotificacion(evento);
                    // Se cerrara el creador de eventos
                    finish();
                }
            }
        });

        // Se creara un dialogo que posee una barra de carga circular ya que no sabemos cuanto tardara en guardar la informacion en la base de datos
        ProgressDialog progressDialog = new ProgressDialog(this);
        // Se le aploca un titulo que pone lo que esta realizando
        progressDialog.setTitle("Gurdando sus datos");
        // Se hace que no sea cancelable al pulsar en cualquier lado de la pantalla
        progressDialog.setCancelable(false);
        // Se le especifica que sera una progres bar circular
        progressDialog.setProgressStyle(0);
        // Se lanzara el hilo que guarda la informacion
        save.start();
        // Se lanzara le dialogo en cuestion
        progressDialog.show();
    }

    /**
     * Metodo que elimina un evento
     */
    void deleteEvento() {
        if (id == 0) {  // Si la id es 0 significa que no es esta editando
            // Se le avisara al usuario con un mensaje de error
            Toast.makeText(getApplicationContext(), "Primero debes de crear el evento para borrarlo", Toast.LENGTH_SHORT).show();
        } else {        // Si no es 0
            // Se borrara el evento apartir de la id
            new ModeloBBDD().deleteEventos(getApplicationContext(), id);
            // Se cerrara el editor de eventos
            finish();
        }
    }

    /**
     * Metodo que obtiene la informacion del boton que se pulsa en el menu
     * @param item The selected item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Dependiendo del boton que se toque se hara una accion u otra
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
        // Simpre se cerrara el menu
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

    /**
     * Metodo que crea el pending intent para ser lanzado al presionar la notifiacion
     * @param evento
     */
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