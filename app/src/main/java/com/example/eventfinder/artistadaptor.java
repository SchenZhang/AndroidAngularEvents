package com.example.eventfinder;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class artistadaptor extends RecyclerView.Adapter<artcard> {
    Context mcontext;
    private ArrayList<artistitem> items=new ArrayList<artistitem>();

    public artistadaptor(ArrayList<artistitem> items,Context m){
        this.items=items;
        this.mcontext=m;
    }
    @NonNull
    @Override
    public artcard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.artitem,parent,false);
        artcard vh=new artcard(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull artcard holder, int position) {
        int index = holder.getBindingAdapterPosition();
        String d=items.get(position).artname1;

        holder.follow.setText(items.get(position).follower1+" followers");
        ImageView imv=holder.image;
        holder.pop.setText(items.get(position).pop1);
        holder.artname.setText(d);
        holder.pg.setProgress(Integer.parseInt(items.get(position).pop1));
        ImageView imv2=holder.album1;
        Log.d(items.get(position).album1, "onBindViewHolder: ");
        Picasso.get().load(items.get(position).imageurl1).into(imv);
        Picasso.get().load(items.get(position).album1).into(imv2);

        Picasso.get().load(items.get(position).album2).into(holder.album2);
        Picasso.get().load(items.get(position).album3).into(holder.album3);
        holder.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewintent=new Intent("android.intent.action.VIEW", Uri.parse(items.get(position).url1));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(items.get(position).url1));
                mcontext.startActivity(intent);

            }
        });

    }




    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        //   int i=items.size();
        //   Log.d("get item count", Integer.toString(i));
        return items.size();
    }


    static class projectviewholder extends RecyclerView.ViewHolder{

        public projectviewholder(@NonNull View viewitem){
            super(viewitem);
        }
    }
}
