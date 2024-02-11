package com.example.pdanotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

/**
 * @author JaviLeL
 * @version 1.0.1
 */
public class AcercaDe extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Uso el adaptado personalizado de los Fragmentos para pasarle los Fragmentos, us titulo, y orden
        AdaptadorFragmentosAcercaDe adapter = new AdaptadorFragmentosAcercaDe(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // Le añado los fragmentos al adaptados junto con su titulo
        adapter.add(new SobreLaAppFragment(), "Sobre la app");      // 1º
        adapter.add(new TutorialsFragment(), "Guia de Usuario");    // 2º

        // Le establezco el adaptador para que pueda recoger sus datos
        viewPager.setAdapter(adapter);
    }
}