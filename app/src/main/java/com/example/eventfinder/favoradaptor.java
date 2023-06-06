package com.example.eventfinder;

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

public class favoradaptor extends RecyclerView.Adapter<cardview> {
    Context mcontext;
    private ArrayList<favoritem> items=new ArrayList<favoritem>();

    public favoradaptor(ArrayList<favoritem> items,Context m){
        this.items=items;
        this.mcontext=m;
    }
    @NonNull

    public cardview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        cardview vh=new cardview(v);
        return vh;
    }


    public void onBindViewHolder(@NonNull cardview holder, int position) {
        int index = holder.getBindingAdapterPosition();
        String d=items.get(position).dateall;
        holder.date.setText(items.get(position).date);

        holder.event.setText(items.get(position).event);
        holder.favor.setVisibility(ImageView.INVISIBLE);
        holder.favored.setVisibility(ImageView.VISIBLE);
        holder.time.setText(items.get(position).time);
        holder.genre.setText(items.get(position).genre);
        holder.venue.setText(items.get(position).venue);
        ImageView imv=holder.image;
        Picasso.get().load(items.get(position).image).into(imv);

        holder.favored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SearchFragment newsg=new SearchFragment();
                holder.favored.setVisibility(ImageView.INVISIBLE);  holder.favor.setVisibility(ImageView.VISIBLE);
                String x=items.get(position).event+" is removed from favorites";
                Toast.makeText(mcontext,x,Toast.LENGTH_LONG).show();
                Log.d(items.get(position).id, "onClick: ");
                ((MainActivity) mcontext).deletefavor(items.get(position).id);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());

                // newsg.showfavortext(x);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ((MainActivity) mcontext).gotodetail(items.get(position).id);

            }
        });

    }



    public int getItemCount() {
        //   int i=items.size();
        //   Log.d("get item count", Integer.toString(i));
        return items.size();
    }

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
