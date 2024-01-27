package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreadorDeNotas extends AppCompatActivity {
    EditText titulo, nota;
    String titulor, notar, correo, pass;
    Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_de_notas);

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
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarCreadorDeNotas));
        ProgressDialog a = new ProgressDialog(this);
        a.setTitle("Gurdando sus datos");
        a.setCancelable(false);
        a.setProgressStyle(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.guardar_nota){
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


        }else if (item.getItemId()==R.id.borrar_nota){
            if (id==0){
                Toast.makeText(this, "Primero debes de crear la nota para borrarla", Toast.LENGTH_SHORT).show();
            }else {
                new ModeloBBDD().delete(getApplicationContext(), id);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}