package com.example.eventfinder;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class pageradaptor extends FragmentStatePagerAdapter{
    private int numoftabs;
    private ArrayList<String> favors=new ArrayList<>();
    public pageradaptor(@NonNull FragmentManager fm, int numoftabs, ArrayList<String> f){
        super(fm);
        this.numoftabs=numoftabs;
        this.favors=f;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch(position){
           case 0:return new SearchFragment();
           case 1:
               Fragment fg=new FavorFragment();
               Bundle bun=new Bundle();
               Log.d("pager adator", "getItem: called");
               bun.putStringArrayList("f",favors);
               fg.setArguments(bun);
               return fg;
           default:return null;
       }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Search";
        else if (position == 1)
            title = "Favorites";

        return title;
    }
}

