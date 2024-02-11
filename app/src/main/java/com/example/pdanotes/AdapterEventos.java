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

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.AnimeViewHolder> {
    List<Evento> eventos;
    String pass;
    Context context;
    Activity act;

    /**
     * Constructor al cual se le pasara cierta informacion para que este funcione sin problemas
     * @param eventos
     * @param context
     * @param pass
     * @param act
     */
    public AdapterEventos(List<Evento> eventos, Context context, String pass, Activity act){
        this.eventos = eventos;
        this.context = context;
        this.pass = pass;
        this.act = act;
    }

    /**
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override//
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se infla la view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_evento, parent, false);
        return new AnimeViewHolder(v);
    }

    /**
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override//
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        // Se le pasa la informacion respectiva a la clase que hara cada uno de los eventos
        holder.bind(eventos.get(position), context, pass, act);
    }

    /**
     * Se le hara devolver la maxima cantidad de evntos
     * @return
     */
    @Override
    public int getItemCount() {
        return eventos.size();
    }

    /**
     * Clase que leera evento a evento y se le pasara al estilo del layout para que este se vea mejor
     */
    public  static  class  AnimeViewHolder extends  RecyclerView.ViewHolder{
        TextView titulo, tipo, fechaYHora;
        View v;

        /**
         * Metodo que extrae informacion del layout de estilo evento
         * @param itemView
         */
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            titulo = itemView.findViewById(R.id.textViewTitulo);
            tipo = itemView.findViewById(R.id.textViewTipoEvento);
            fechaYHora = itemView.findViewById(R.id.textViewFechaYHora);
        }

        /**
         * Metodo que se le pasa la informacion de un evento y este lo pone en su formato visual correspondiente
         * Tambien se le prepara para que si se presion su boton se le pasaran los datos al Creador de Eventos para su
         * posible modificacion
         * @param event
         * @param context
         * @param pass
         * @param act
         */
        void bind(Evento event, Context context, String pass, Activity act) {
            titulo.setText(event.getTitulo());
            tipo.setText(event.getTipo());
            fechaYHora.setText(event.getFecha().toString()+" "+event.getHora().toString());
            // Al presionar sobre cualquier parte de su vista este le pasara toda la informacion del evento para que esta sea
            // leida por el  creador de eventos para su posible modificacion
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, ""+note.getId(), Toast.LENGTH_SHORT).show();
                    // Se le pasa le contecto que se le ha pasado poseteriormente a la classe
                    Intent intent = new Intent(context, CreadorDeEventos.class);

                    // Se le pasa toda la informacion del evento
                    intent.putExtra("id", event.getId());
                    intent.putExtra("titulo", event.getTitulo());
                    intent.putExtra("tipo", event.getTipo());
                    intent.putExtra("fecha", event.getFecha().toString());
                    intent.putExtra("hora", event.getHora().toString());
                    intent.putExtra("descripcion", event.getDescripcion());
                    intent.putExtra("correo", event.getCorreo());
                    intent.putExtra("pass", pass);

                    // Se inicia la nueva actividad con la actividad pasada en el constructor de la clase
                    act.startActivity(intent);
                }
            });
        }
    }
}