package com.example.eventfinder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String eventname;
    private String urll;
    private ArrayList<String> art;

    public ArtistFragment() {
        // Required empty public constructor
    }
    public void setart(ArrayList<String> artlist){
        art=artlist;
    }
    public interface OnMessage{
        void sendMessage(int fragmentId, ArrayList<String> message);
    }

    public void setArt(ArrayList<String> art) {
        this.art = art;
    }


    public interface OnReceive{
        void onReceive(ArrayList<String> message);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistFragment newInstance(String param1, String param2) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("create", "onCreate: ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("art frag", "onCreateView: called");
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_artist, container, false);
        View view = inflater.inflate(R.layout.actionbar, null);
        DeActivity act=((DeActivity) getActivity());
        ArrayList<artistitem> result=new ArrayList<artistitem>();
        ArrayList<String> id=new ArrayList<String>();


        RecyclerView recyleview=v.findViewById(R.id.layout);
        ArrayList<String> ans=getArguments().getStringArrayList("ans");
        if(ans==null||ans.isEmpty()){
           TextView nod= v.findViewById(R.id.nodata);
           nod.setVisibility(TextView.VISIBLE);
            return v;}
        Log.d("ans init down", "onCreateView: ");
//         Log.d(ans.get(0)+ans.get(1), "onCreateView: ");
         for(int i=0;i<ans.size();i++){
            //  Log.d("this is ans", ans.get(i));

            Api.spotify(getContext(),ans.get(i) , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObjectt = new JSONObject(response);
                        JSONObject js1=jsonObjectt.getJSONObject("artists").getJSONArray("items").getJSONObject(0);
                        String idnow=js1.getString("id");
                        final String[] image1 = {new String()};
                        final String[] image2 = {new String()};
                        final String[] image3 = {new String()};
                        String folo=js1.getJSONObject("followers").getString("total");
                        String spotifyurl=js1.getJSONObject("external_urls").getString("spotify");
                        String name=js1.getString("name");
                        String imageurl=js1.getJSONArray("images").getJSONObject(0).getString("url");
                        String pop=js1.getString("popularity");
                        id.add(idnow);
                        Api.album(getContext(),idnow , new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("hi", "onResponse: called album");
                                    JSONObject jsonObjecttt = new JSONObject(response);
                                    JSONObject obj=jsonObjecttt.getJSONArray("items").getJSONObject(0);
                                    JSONObject obj1=jsonObjecttt.getJSONArray("items").getJSONObject(1);
                                    JSONObject obj2=jsonObjecttt.getJSONArray("items").getJSONObject(2);
                                    image1[0] =obj.getJSONArray("images").getJSONObject(0).getString("url");
                                    image2[0] =obj1.getJSONArray("images").getJSONObject(0).getString("url");
                                    image3[0] =obj2.getJSONArray("images").getJSONObject(0).getString("url");
                                    Log.d(image1[0], "one album");
                                    Log.d(image1[0], "onResponse: this is album ");
                                    result.add(new artistitem(name,imageurl,folo,spotifyurl,pop,image1[0],image2[0],image3[0]));
                                    artistadaptor Adaptor=new artistadaptor(result,getContext());
                                    recyleview.setAdapter(Adaptor);
                                    recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            if(i==ans.size()-1){
                Log.d("this is excuted before?s", Integer.toString(result.size()));

            }


        }
//
        return v;




    }

//    TextView txtData;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(
//                R.layout.fragment_two, container, false);
//        return rootView;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        txtData = (TextView)view.findViewById(R.id.txtData);
//    }
//
//    protected void displayReceivedData(String message)
//    {
//        txtData.setText("Data received: "+message);
//    }
}