package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lab5.fragment.FragmentAdd;
import com.example.lab5.fragment.FragmentDel;
import com.example.lab5.fragment.FragmentShow;
import com.example.lab5.fragment.FragmentUpdate;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentShow();
            case 1: return new FragmentAdd();
            case 2: return new FragmentDel();
            case 3: return new FragmentUpdate();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Show";
            case 1: return "Add";
            case 2: return "Del";
            case 3: return "Update";
            default: return null;
        }
    }
}
