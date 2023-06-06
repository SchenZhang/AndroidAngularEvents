package com.example.eventfinder;


import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class artcard extends RecyclerView.ViewHolder {

    public BreakIterator name;
    TextView artname;
    TextView follow;
    TextView checkout;
    TextView pop;

    ImageView image;
    ImageView album1;
    ImageView album2;
    ImageView album3;
    View view;
    ProgressBar pg;

    artcard(View itemView)
    {
        super(itemView);
       artname
                = (TextView)itemView
                .findViewById(R.id.name);
       follow
                = (TextView)itemView
                .findViewById(R.id.follow);
        checkout
                = (TextView)itemView
                .findViewById(R.id.check);
        pop=itemView.findViewById(R.id.pop);

        image= (ImageView)itemView.findViewById(R.id.image);
        album1=(ImageView)itemView.findViewById(R.id.imageView2);
        album2=(ImageView)itemView.findViewById(R.id.imageView3);
        album3=(ImageView)itemView.findViewById(R.id.imageView4);
        pg=(ProgressBar) itemView.findViewById(R.id.ProgressBar);
        view  = itemView;
    }
}