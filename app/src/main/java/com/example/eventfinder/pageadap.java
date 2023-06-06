package com.example.eventfinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class pageadap extends FragmentPagerAdapter {
    private int numoftabs;
    private String id;
    private ArrayList<String> xx;
    private String venue;
    private String name;
    public pageadap(@NonNull FragmentManager fm, int numoftabs, String id, ArrayList<String> x,String venue,String eventname){
        super(fm);
        this.numoftabs=numoftabs;
        this.id=id;
        this.xx=x;
        this.venue=venue;
        this.name=eventname;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Fragment fg=new detaFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("name",name);
                fg.setArguments(bundle);
                return fg;

            case 1:
                Fragment artfg=new ArtistFragment();
                Bundle bun=new Bundle();
                bun.putStringArrayList("ans",xx);
                artfg.setArguments(bun);
                return artfg;
            case 2:
                Fragment vfg=new venueFragment();
                Bundle bunn=new Bundle();
                bunn.putString("v",venue);
                vfg.setArguments(bunn);
                return vfg;
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
            title = "Detail";
        else if (position == 1)
            title = "Artist";
        else if(position==2)
            title ="Venue";

        return title;
    }
}

