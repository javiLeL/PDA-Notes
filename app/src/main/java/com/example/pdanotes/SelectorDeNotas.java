package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectorDeNotas extends AppCompatActivity {
    RecyclerView rv;
    String correo, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_de_notas);
        correo = getIntent().getExtras().getString("correo");
        pass = getIntent().getExtras().getString("pass");

        rv = findViewById(R.id.rv);
        // rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        update();
        findViewById(R.id.floatingActionButtonCrearNota).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreadorDeNotas.class);
                intent.putExtra("correo", correo);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });
    }
    void update(){
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        List<Nota> notas = new ArrayList<>(new ModeloBBDD().selectNotas(getApplicationContext(), correo, pass));
        rv.setAdapter(new Adapter(notas, getApplicationContext(), pass, this));
        rv.setHasFixedSize(true);

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }
}