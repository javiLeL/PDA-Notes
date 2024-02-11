package com.example.pdanotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdanotes.dto.Nota;

import java.util.List;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class AdapterNotas extends RecyclerView.Adapter<AdapterNotas.AnimeViewHolder> {
    List<Nota> notas;
    String pass;
    Context context;
    Activity act;

    /**
     * Se crea el construcotor de las notas y se le pasan ciertos atributos importantes para su correcto duncionamento
     * @param notas
     * @param context
     * @param pass
     * @param act
     */
    public AdapterNotas(List<Nota> notas, Context context, String pass, Activity act){
        this.notas = notas;
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
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se le hace devolver la vista inflada
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_nota, parent, false);
        return new AnimeViewHolder(v);
    }

    /**
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        // Se le hace cargar una nota segun su posicion en la lista de las mismas
        holder.bind(notas.get(position), context, pass, act);
    }

    /**
     * Metodo que se le hace devolver el numer maximo de notas
     * @return
     */
    @Override
    public int getItemCount() {
        // Se le pasa el tamaño de la lista
        return notas.size();
    }

    /**
     * Clase creada para cargar la informacion de una nota al formato visual (layout estilo nota)
     */
    public  static  class  AnimeViewHolder extends  RecyclerView.ViewHolder{
        TextView titulo, nota;
        View v;

        /**
         * Metodo que extrae informacion del layout de estilo nota
         * @param itemView
         */
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            titulo = itemView.findViewById(R.id.textViewTitulo);
            nota = itemView.findViewById(R.id.textViewTipoEvento);
        }

        /**
         * Metodo que al pasarle una nota es y crea su elemento visual
         * @param note
         * @param context
         * @param pass
         * @param act
         */
        void bind(Nota note, Context context, String pass, Activity act){
            // Se le asigna un numero maximo de caracteres
            final int MAXNUM = 50;
            // Se le pasa toda la informacion necesaria al estilo
            titulo.setText(note.getTitulo());
            // Si se pasa del numero de caractes este le quitara los 3 ultimos para poner tres puntos
            if(note.getNota().length()>MAXNUM){
                nota.setText(note.getNota().substring(0, MAXNUM-3)+"...");
            }else{
                nota.setText(note.getNota());
            }
            // Si se peresiona la vista de la nota esta lazara el modo editor de notas con la informacion de la misma para su posible modificacion o lectura
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, ""+note.getId(), Toast.LENGTH_SHORT).show();
                    // Se crea un intent con el contexto que se le ha pasado con anterioridad
                    Intent intent = new Intent(context, CreadorDeNotas.class);

                    // Se le pasan todos los valores de ls nota
                    intent.putExtra("id", note.getId());
                    intent.putExtra("titulo", note.getTitulo());
                    intent.putExtra("nota", note.getNota());
                    intent.putExtra("correo", note.getCorreo());
                    intent.putExtra("pass", pass);

                    // El objeto de actividad que se le pasa en el constructor es usado para iniciar dicho intent
                    act.startActivity(intent);
                }
            });
            // Por ultimo se le aplica un efecto visual
            buttonEffect(v);
        }
    }

    /**
     * Metodo que poner una capa de color negro con transparencia para que de el efecto de que cada vista de las notas es un boton
     * @param view
     */
    private static void buttonEffect(View view){
        // Si se presiona la view que se le pasa
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Dependiendo del tipo de evento que se le pasa actuara de una forma u otra
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {     // Si se presiona
                        // Este lo teñira de un negro con transparencia
                        v.getBackground().setColorFilter(0xA0000000, PorterDuff.Mode.SRC_ATOP);
                        // Se le aplcara a la view
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:         // Tanto si se levanta el dedo
                    case MotionEvent.ACTION_CANCEL: {   // Como si se cancela (hace scrol el dedo se sale etc...)
                        // Se le quitaran todos los efectos puetos con anterioridad
                        v.getBackground().clearColorFilter();
                        // Se le aplcara a la view
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}