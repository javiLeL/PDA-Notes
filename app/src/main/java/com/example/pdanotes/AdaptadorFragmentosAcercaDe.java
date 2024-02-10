package com.example.pdanotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdaptadorFragmentosAcercaDe extends FragmentPagerAdapter {
    private final ArrayList <Fragment> fragments = new ArrayList<>();
    private final ArrayList<String> nombre = new ArrayList<>();

    public AdaptadorFragmentosAcercaDe(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return nombre.get(position);
    }
    public void add(Fragment f, String n){
        fragments.add(f);
        nombre.add(n);
    }
}
