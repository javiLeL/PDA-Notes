package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class AdaptadorFragmentosAcercaDe extends FragmentPagerAdapter {
    // Creo un array de Fragmentos
    private final ArrayList <Fragment> fragments = new ArrayList<>();
    // Creo un array de nombres/titulos
    private final ArrayList<String> nombre = new ArrayList<>();

    /**
     * Constructor necesario para que el adaptador funcione correctamente
     * @param fm
     * @param behavior
     */
    public AdaptadorFragmentosAcercaDe(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    /**
     * Metodo que ha de devolver un fragmento determinado segun un numero que ira de 0 a el resultado de getCount()
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Para ello al usar una lista simplemente le hacemos devolver el elemento que se encuentra en esta posicion determinada
        return fragments.get(position);
    }

    /**
     * Metodo que ha de devolver el numero maximo de fragmentos
     * @return
     */
    @Override
    public int getCount() {
        // Simplemente se le hace devolver el tamaño de la lista
        return fragments.size();
    }

    /**
     * Meodo que ha de devolver el titulo a partir de una posicion que ira de 0 a el resultado de getCount()
     * @param position The position of the title requested
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Al estar en una lista solo se le hace devolver el objeto encontrado en la determinada posicion
        return nombre.get(position);
    }

    /**
     * Metodo que servira para pasarle informacion de su fragmento junto con el titulo que este tendra
     * @param f
     * @param n
     */
    public void add(Fragment f, String n){
        // Se añaden a sus respectivas listas los elementos introducidos
        fragments.add(f);
        nombre.add(n);
    }
}
