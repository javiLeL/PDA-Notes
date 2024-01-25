package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectorDeNotas extends AppCompatActivity {
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_de_notas);
        String correo = getIntent().getExtras().getString("correo");
        String pass = getIntent().getExtras().getString("pass");

        rv = findViewById(R.id.rv);
        // rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));

        ArrayList<Nota> notas = new ArrayList<>();
        notas.add(new Nota(1, "Titulo 1", "hola soy una nota", "correo"));
        notas.add(new Nota(2, "Titulo 2", "no soy una nota", "correo"));
        notas.add(new Nota(3, "Titulo 3", "awa man es que me muero", "correo"));
        notas.add(new Nota(4, "Aiuda :,)", "aiuda auxilio me desmaandiaudapuidapidhaida9duha\nidahwiduhadiuahdawuhdaduha\ndaiudhaiduhawdauhdaiwudhawiduh", "correo"));

        rv.setAdapter(new Adapter(notas, getApplicationContext()));
        rv.setHasFixedSize(true);
    }
}