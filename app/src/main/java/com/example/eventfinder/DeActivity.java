package com.example.eventfinder;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class DeActivity extends AppCompatActivity {
    private String id;
    private String vname;
    private String eventname;
    private String urll;
    private Boolean iffavor;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                Log.d("home", "onOptionsItemSelected: hello");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!=null){

        }
        Log.d("hiiiiiii", "onCreate: called");
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar);


        View view =getSupportActionBar().getCustomView();



        Bundle b = getIntent().getExtras();
        ArrayList<String> result= new ArrayList<String>();


        if(b != null)
            id = b.getString("id");
            iffavor=b.getBoolean("if");

        ImageView favored=view.findViewById(R.id.favored);
        ImageView favor=view.findViewById(R.id.favor);

        if(iffavor){
            favored.setVisibility(ImageView.VISIBLE);
            favor.setVisibility(ImageView.INVISIBLE);
        }else{
            favored.setVisibility(ImageView.INVISIBLE);
            favor.setVisibility(ImageView.VISIBLE);
        }
        Api.detail(this, id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("hi", "onResponse: called api detail");
                    JSONObject jsonObject = new JSONObject(response);
                    String name;
                    if(jsonObject.has("name")){
                        name=jsonObject.getString("name");
                    }else{
                        name="Defa";
                    }
                    eventname=name;
                    TextView x=view.findViewById(R.id.title);
                    x.setText(name);
                    x.setSelected(true);
                    if(jsonObject.has("url")){
                        urll=jsonObject.getString("url");
                    }else{
                        urll="";
                    }


                    JSONObject jsonObject3=jsonObject.getJSONObject("_embedded");
                    String vname=jsonObject3.getJSONArray("venues").getJSONObject(0).getString("name");
                    vname=vname;
                    JSONArray jsonarray_arrtact=jsonObject3.getJSONArray("attractions");
                    ArrayList<String> artistslist=new ArrayList<String>();
                    Log.d("length shall be", Integer.toString(jsonarray_arrtact.length()));
                    for(int numofart=0;numofart<jsonarray_arrtact.length();numofart++){
                       // Log.d("length shall be", Integer.toString(jsonarray_arrtact.length()));
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

                    setContentView(R.layout.activity_de);
                    TabLayout tabLayout=findViewById(R.id.tab);
                    TabItem detailtab=findViewById(R.id.tabdetail);
                    TabItem venuetab=findViewById(R.id.tabvenue);
                    TabItem artisttab=findViewById(R.id.tabartist);
                    ViewPager viewpage=findViewById(R.id.viewpager2);
                  //  Log.d(Integer.toString(artistslist.size()), "onResponse: ");
                    pageadap pgadaptor=new pageadap(getSupportFragmentManager(),tabLayout.getTabCount(),id,artistslist,vname,eventname);
                    viewpage.setAdapter(pgadaptor);
                    tabLayout.setupWithViewPager(viewpage);
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

        ImageView facebook=view.findViewById(R.id.fb);
        ImageView twitter=view.findViewById(R.id.tw);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                ShareDialog shareDialog;

                shareDialog = new ShareDialog(DeActivity.this);
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Check out "+eventname+" at TicketMaster")
                        .setContentUrl(Uri.parse(urll)).build();
                shareDialog.show(linkContent);

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://twitter.com/intent/tweet?text=Check%20out%20"+eventname+"%20on%20Ticketmaster."+"&url="+urll;
                Intent viewintent=new Intent("android.intent.action.VIEW",Uri.parse(url));
                startActivity(viewintent);
            }

        });
       //  super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

    }

}