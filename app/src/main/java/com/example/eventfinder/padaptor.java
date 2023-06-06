package com.example.eventfinder;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class padaptor extends RecyclerView.Adapter<cardview> {
    Context mcontext;
    private ArrayList<showitem> items=new ArrayList<showitem>();

public padaptor(ArrayList<showitem> items,Context m){
    this.items=items;
    this.mcontext=m;
}
    @NonNull
    @Override
    public cardview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
       cardview vh=new cardview(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull cardview holder, int position) {
       int index = holder.getBindingAdapterPosition();
       String d=items.get(position).dateall;
       String[] dans=d.split("\\s+");
       String timefinal;
if(dans.length==2){
    String time=dans[1].substring(0,5);
    int ti=Integer.parseInt(time.substring(0,2));

    if(ti>12){
        ti-=12;
        timefinal=Integer.toString(ti)+time.substring(2,5)+"PM";
    }else{
        timefinal=Integer.toString(ti)+time.substring(2,5)+"AM";
    }
}else{
    timefinal=" ";
}

        holder.date.setText(dans[0]);

        holder.event.setText(items.get(position).event);
        holder.event.setSelected(true);
        holder.time.setText(timefinal);
        holder.genre.setText(items.get(position).genre);
        holder.venue.setText(items.get(position).venue);
        ImageView imv=holder.image;
        Boolean iffavor=((MainActivity) mcontext).checkif(items.get(position).id);
        if(iffavor){
            holder.favored.setVisibility(ImageView.VISIBLE);
            holder.favor.setVisibility(ImageView.INVISIBLE);
        }else{
            holder.favored.setVisibility(ImageView.INVISIBLE);
            holder.favor.setVisibility(ImageView.VISIBLE);
        }
        Picasso.get().load(items.get(position).image).into(imv);
        holder.favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SearchFragment newsg=new SearchFragment();
                holder.favor.setVisibility(ImageView.INVISIBLE);  holder.favored.setVisibility(ImageView.VISIBLE);
               String x=items.get(position).event+" is added to favorites";

                Toast.makeText(mcontext,x,Toast.LENGTH_LONG).show();
                ((MainActivity) mcontext).addfavor(items.get(position).event,timefinal,dans[0],items.get(position).venue,items.get(position).genre,items.get(position).image,items.get(position).id);

               // newsg.showfavortext(x);
               // Toast.makeText(getContext(),x,Toast.LENGTH_LONG).show();
            }
        });
        holder.favored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SearchFragment newsg=new SearchFragment();
                holder.favored.setVisibility(ImageView.INVISIBLE);  holder.favor.setVisibility(ImageView.VISIBLE);
                String x=items.get(position).event+" is removed from favorites";
                Toast.makeText(mcontext,x,Toast.LENGTH_LONG).show();
                ((MainActivity) mcontext).deletefavor(items.get(position).id);
               // newsg.showfavortext(x);

            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ((MainActivity) mcontext).gotodetail(items.get(index).id);
                Log.d("a", Integer.toString(index));
            }
        });
    }


    @Override
    public int getItemCount() {
 //   int i=items.size();
     //   Log.d("get item count", Integer.toString(i));
        return items.size();
    }
    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class projectviewholder extends RecyclerView.ViewHolder{

      public projectviewholder(@NonNull View viewitem){
          super(viewitem);
      }
  }
}
