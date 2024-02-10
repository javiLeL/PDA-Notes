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

import java.util.List;

public class AdapterNotas extends RecyclerView.Adapter<AdapterNotas.AnimeViewHolder> {
    List<Nota> notas;
    String pass;
    Context context;
    Activity act;
    public AdapterNotas(List<Nota> notas, Context context, String pass, Activity act){
        this.notas = notas;
        this.context = context;
        this.pass = pass;
        this.act = act;
    }
    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_nota, parent, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        holder.bind(notas.get(position), context, pass, act);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public  static  class  AnimeViewHolder extends  RecyclerView.ViewHolder{
        TextView titulo, nota;
        View v;
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            titulo = itemView.findViewById(R.id.textViewTitulo);
            nota = itemView.findViewById(R.id.textViewTipoEvento);
        }
        void bind(Nota note, Context context, String pass, Activity act){
            final int MAXNUM = 50;
            titulo.setText(note.getTitulo());
            if(note.getNota().length()>MAXNUM){
                nota.setText(note.getNota().substring(0, MAXNUM-3)+"...");
            }else{
                nota.setText(note.getNota());
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, ""+note.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CreadorDeNotas.class);

                    intent.putExtra("id", note.getId());
                    intent.putExtra("titulo", note.getTitulo());
                    intent.putExtra("nota", note.getNota());
                    intent.putExtra("correo", note.getCorreo());
                    intent.putExtra("pass", pass);

                    act.startActivity(intent);
                }
            });
            buttonEffect(v);
        }
    }
    private static void buttonEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xA0000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}