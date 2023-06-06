package com.example.eventfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.GnssAntennaInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;



import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String urll;
    private String eventname;
    public detaFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static detaFragment newInstance(String param1, String param2) {
        detaFragment fragment = new detaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =inflater.inflate(R.layout.fragment_deta, container, false);
        //    ActionBar actionBar = getSupportActionBar();
//    actionBar.setCustomView(R.layout.actionbar);
//    actionBar.setDisplayHomeAsUpEnabled(true);
      //  View view = inflater.inflate(R.layout.actionbar, null);
        DeActivity act=((DeActivity) getActivity());
        TextView urltext=(TextView) v.findViewById(R.id.buy);
        TextView pricetext=(TextView) v.findViewById(R.id.pricem);

      // String id=((DeActivity) getActivity()).getid();
       // Log.d("id", id);
      String id=getArguments().getString("id");
     // TextView titlebar=view.findViewById(R.id.title);
      String titl=getArguments().getString("name");
        RelativeLayout all=v.findViewById(R.id.all);
        all.setVisibility(RelativeLayout.INVISIBLE);
    //  titlebar.setText(titl);
     //   act.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //act.getSupportActionBar().setDisplayShowCustomEnabled(true);
        Api.detail(getContext(), id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    all.setVisibility(RelativeLayout.VISIBLE);
                   // his.venuename = this.resp._embedded.venues[0].name;
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("ok", "onResponse: ");
                    String name;
                    if(jsonObject.has("name")){
                       name=jsonObject.getString("name");
                    }else{
                        name="";
                    }
                    String date="";
                    String time="";
                    String status="";
                   // res['dates']['start']['localDate']
                    if(jsonObject.has("dates")){
                        JSONObject jsonObject1=jsonObject.getJSONObject("dates");
                        JSONObject jsonObject2=jsonObject1.getJSONObject("start");
                        JSONObject jsonObject4=jsonObject1.getJSONObject("status");
                        status=jsonObject4.getString("code");

                        date=jsonObject2.getString("localDate");
                        if(jsonObject2.has("localTime")){
                            time=jsonObject2.getString("localTime");
                        }


                    }



                    JSONObject jsonObject3=jsonObject.getJSONObject("_embedded");
                    JSONArray jsonarray_arrtact=jsonObject3.getJSONArray("attractions");
                    ArrayList<String> artistslist=new ArrayList<String>();
                    for(int numofart=0;numofart<jsonarray_arrtact.length();numofart++){
                        JSONObject att=jsonarray_arrtact.getJSONObject(numofart);
                        JSONArray tmp=att.getJSONArray("classifications");
                        JSONObject tmp1=tmp.getJSONObject(0);
                        if(tmp1.has("segment")){
                            JSONObject tmp2=tmp1.getJSONObject("segment");
                                if(tmp2.has("name")){
                                    String seg=tmp2.getString("name");
                                    if(seg.equals("Music")){
                                        artistslist.add(att.getString("name"));
                                    }

                                }
                        }
                    }



                    JSONArray jsonArray=jsonObject3.getJSONArray("venues");
                    JSONObject row = jsonArray.getJSONObject(0);
                    String venue=row.getString("name");


                    String url=jsonObject.getString("url");
                    urll=url;
                    ImageView imv=v.findViewById(R.id.imageView);
                    if(jsonObject.has("seatmap")){
                        JSONObject jsonObject5=jsonObject.getJSONObject("seatmap");
                        String imageurl=jsonObject5.getString("staticUrl");
                        Picasso.get().load(imageurl).into(imv);
                    }else{
                        imv.setVisibility(ImageView.INVISIBLE);
                    }

                    JSONArray jsonArray3=jsonObject.getJSONArray("classifications");
                    JSONObject jsonObject6=jsonArray3.getJSONObject(0);
                    ArrayList<String> genres=new ArrayList<String>();
                    if(jsonObject6.has("genre")&&!jsonObject6.getJSONObject("genre").getString("name").equals("Undefined")){
                        genres.add(jsonObject6.getJSONObject("genre").getString("name"));
                    }
                    if(jsonObject6.has("type")&&!jsonObject6.getJSONObject("type").getString("name").equals("Undefined")){
                        genres.add(jsonObject6.getJSONObject("type").getString("name"));
                    }

                    if(jsonObject6.has("segment")&&!jsonObject6.getJSONObject("segment").getString("name").equals("Undefined")){
                        genres.add(jsonObject6.getJSONObject("segment").getString("name"));
                    }
                    String genre=String.join("|",genres);
                    if(jsonObject.has("priceRanges")){
                        JSONArray jsonArray2=jsonObject.getJSONArray("priceRanges");
                        JSONObject jsonObject7=jsonArray2.getJSONObject(0);
                        String min=jsonObject7.getString("min");
                        String max=jsonObject7.getString("max");
                        String price=String.join("-",min,max);

                        pricetext.setText(price);
                        pricetext.setVisibility(TextView.VISIBLE);
                    }

                    TextView art=(TextView) v.findViewById(R.id.art);
                    String xxx=String.join(" ",artistslist);
                    art.setText(xxx);
                    art.setSelected(true);
                   // ((DeActivity) getActivity()).changetitle(name);
                   // act.getSupportActionBar().setTitle(name);

                   eventname=name;
                   // TextView x=view.findViewById(R.id.title);
                    //x.setText(eventname);
                    TextView venuetext=(TextView) v.findViewById(R.id.venuem);
                    venuetext.setText(venue);
                    TextView datetext=(TextView) v.findViewById(R.id.datem);
                    datetext.setText(date);
                    TextView timetext=(TextView) v.findViewById(R.id.timem);
                    TextView genretext=(TextView) v.findViewById(R.id.genrem);

                    TextView statustext=(TextView) v.findViewById(R.id.status);

                    String[] dans=time.split(":");
                    String timefinal="";
                    if(dans.length>=2){
                        String time1=dans[1].substring(0,2);
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
                    statustext.setText(status);
                    if(status.equals("onsale")){

                        statustext.setBackgroundColor(Color.parseColor("#00FF00"));
                    }
                   else{

                        statustext.setBackgroundColor(Color.parseColor("#FF5050"));
                    }
                    urltext.setText(url);
                   urltext.setSelected(true);
                    timetext.setText(timefinal);
                    genretext.setText(genre);







                //    act.getSupportActionBar().setCustomView(view);
//          JSONObject emd= jsonObject.getJSONObject("_embedded");
//          JSONArray att=emd.getJSONArray("attractions");


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //for displaying on the auto complete

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        urltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

               Intent viewintent=new Intent("android.intent.action.VIEW",Uri.parse(urll));
                startActivity(viewintent);

            }
        });



//        ImageView facebook=view.findViewById(R.id.fb);
//        ImageView twitter=view.findViewById(R.id.tw);
//
//        facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
////                Intent viewIntent =
////                        new Intent("android.intent.action.VIEW",
////                                Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" +
////                                        encodeURIComponent(url) +
////                                        "&amp;src=sdkpreparse");
////                startActivity(viewIntent);
//                ShareDialog shareDialog;
//
//                shareDialog = new ShareDialog(getActivity());
//                ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                        .setQuote("Check out "+eventname+" at TicketMaster")
//                        .setContentUrl(Uri.parse(urll)).build();
//                shareDialog.show(linkContent);
//
//            }
//        });
//        twitter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url="https://twitter.com/intent/tweet?text=Check%20out%20"+eventname+"%20on%20Ticketmaster."+"&url="+urll;
//                Intent viewintent=new Intent("android.intent.action.VIEW",Uri.parse(url));
//                startActivity(viewintent);
//            }
//
//        });
//

       return v;
    }
    public interface OnMessage{
        void sendMessage(int fragmentId, ArrayList<String> message);
    }

    public interface OnReceive{
        void onReceive(ArrayList<String> message);
    }
}