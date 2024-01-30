package com.example.pdanotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.AnimeViewHolder> {
    List<Evento> eventos;
    String pass;
    Context context;
    Activity act;

    public AdapterEventos(List<Evento> eventos, Context context, String pass, Activity act){
        this.eventos = eventos;
        this.context = context;
        this.pass = pass;
        this.act = act;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_evento, parent, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        holder.bind(eventos.get(position), context, pass, act);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public  static  class  AnimeViewHolder extends  RecyclerView.ViewHolder{
        TextView titulo, tipo, fechaYHora;
        View v;
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            titulo = itemView.findViewById(R.id.textViewTitulo);
            tipo = itemView.findViewById(R.id.textViewTipoEvento);
            fechaYHora = itemView.findViewById(R.id.textViewFechaYHora);
        }
        void bind(Evento event, Context context, String pass, Activity act) {
            titulo.setText(event.getTitulo());
            tipo.setText(event.getTipo());
            fechaYHora.setText(event.getFecha().toString()+" "+event.getHora().toString());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, ""+note.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CreadorDeEventos.class);

                    intent.putExtra("id", event.getId());
                    intent.putExtra("titulo", event.getTitulo());
                    intent.putExtra("tipo", event.getTipo());
                    intent.putExtra("fecha", event.getFecha().toString());
                    intent.putExtra("hora", event.getHora().toString());
                    intent.putExtra("correo", event.getCorreo());
                    intent.putExtra("pass", pass);

                    act.startActivity(intent);
                }
            });
        }
    }
}