package com.example.pdanotes;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.AnimeViewHolder> {
    List<Nota> notas;
    Context context;
    public Adapter(List<Nota> notas, Context context){
        this.notas = notas;
        this.context = context;
    }
    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_nota, parent, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        holder.bind(notas.get(position));
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
            nota = itemView.findViewById(R.id.textViewNota);
        }
        void bind(Nota note){
            titulo.setText(note.getTitulo());
            nota.setText(note.getNota());
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