package com.example.eventfinder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class cardview extends RecyclerView.ViewHolder {

    public BreakIterator name;
    TextView event;
    TextView venue;
    TextView genre;
    TextView date;
    TextView time;
 ImageView favor;
 ImageView favored;
    View view;
ImageView image;
    cardview(View itemView)
    {
        super(itemView);
       event
                = (TextView)itemView
                .findViewById(R.id.event);
        venue
                = (TextView)itemView
                .findViewById(R.id.venue);
        genre
                = (TextView)itemView
                .findViewById(R.id.genre);
      date
                = (TextView)itemView
                .findViewById(R.id.date);
        time
                = (TextView)itemView
                .findViewById(R.id.time);
        image= (ImageView)itemView.findViewById(R.id.image);
        favor=(ImageView)itemView.findViewById(R.id.favor);
        favored=(ImageView)itemView.findViewById(R.id.favored);

        view  = itemView;
    }
}