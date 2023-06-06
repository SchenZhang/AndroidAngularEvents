package com.example.eventfinder;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 100;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private autosug  autoSuggestAdapter;
    private Handler handler;
    public SearchFragment() {
        // Required empty public constructor
    }
    private ArrayList<String> ans = new ArrayList<String>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private void getsug(String text) {
        Api.auto_sug(getContext(), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<String> stringList = new ArrayList<>();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject emd= jsonObject.getJSONObject("_embedded");
                    JSONArray att=emd.getJSONArray("attractions");
                    for (int i = 0; i < att.length(); ++i) {
                        JSONObject rec = att.getJSONObject(i);

                        String names = rec.getString("name");
                        stringList.add(names);
                        // ...
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //for displaying on the auto complete
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_search,container,false);
        View vblank=inflater.inflate(R.layout.fragment_res,container,false);
        Spinner dropdown = (Spinner) v.findViewById(R.id.spinner);

        String[] items = new String[]{"All", "Music", "Sports","Arts&Threater","Film","Miscellaneous"};
ArrayAdapter ad=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,items);

ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      dropdown.setAdapter(ad);
        dropdown.setSelection(0, true);
        View dropdownview = dropdown.getSelectedView();
        ((TextView) dropdownview).setTextColor(Color.WHITE);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              dropdown.setSelection(position,true);
                View dropdownv = dropdown.getSelectedView();
                ((TextView) dropdownv).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btn=(Button) v.findViewById(R.id.submit);
        Button clearbtn=(Button) v.findViewById(R.id.clear);
        AutoCompleteTextView keyedit=(AutoCompleteTextView) v.findViewById(R.id.key);
        Log.d("success", "changed to autocomplete text view ");
        TextView backk=(TextView) v.findViewById(R.id.backto);
       // SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
        EditText disedit=(EditText) v.findViewById(R.id.dis);
        EditText locedit=(EditText) v.findViewById(R.id.loc);
        RelativeLayout formlayout=(RelativeLayout) v.findViewById(R.id.form);
        RelativeLayout pblayout=(RelativeLayout) v.findViewById(R.id.pblayout);
        RelativeLayout nrlayout=(RelativeLayout) v.findViewById(R.id.nrlayout);
        ScrollView scview=(ScrollView) v.findViewById(R.id.scroll);
        Switch swt=(Switch) v.findViewById(R.id.switch1);
        LinearLayout backlayout=(LinearLayout) v.findViewById(R.id.backlayout);
        RecyclerView reslayout=(RecyclerView) v.findViewById(R.id.reslayout);
       // ImageView favor=(ImageView) v.findViewById(R.id.favor);

        autoSuggestAdapter = new autosug(getContext(),
                android.R.layout.simple_dropdown_item_1line);
        keyedit.setThreshold(1);
        keyedit.setAdapter(autoSuggestAdapter);
        keyedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                ((TextView)view).setTextColor(Color.WHITE);
                keyedit.setText(autoSuggestAdapter.getObject(itemIndex).toString());
            }
        });

       keyedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(keyedit.getText())) {
                        getsug(keyedit.getText().toString());
                    }
                }
                return false;
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key=keyedit.getText().toString();
                String loc=locedit.getText().toString();
                String cata=dropdown.getSelectedItem().toString();
                String dis=disedit.getText().toString();
                Log.d("params", cata);
                Log.d("params", key);
                Log.d("params", dis);
                Log.d("params", loc);
                ans=new ArrayList<String>();
                if(key.isEmpty()){
                    Toast.makeText(getContext(),"Please fill in keyword section.",Toast.LENGTH_LONG).show();
                } else if (!swt.isChecked()&&loc.isEmpty()) {
                    Toast.makeText(getContext(),"Please fill in location section.",Toast.LENGTH_LONG).show();
                }
                else if(swt.isChecked()){
                    formlayout.setVisibility(RelativeLayout.INVISIBLE);
                    pblayout.setVisibility(RelativeLayout.VISIBLE);
                    Log.d("should be", "onCheckedChanged: auto ");
                    Api.get_ip(getContext(), "get", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String ip= jsonObject.getString("ip");
                                Log.d("ip is", ip);

                                Api.getip_loc(getContext(), ip, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response2) {
                                        try {
                                            //Log.d("run", response2);
                                            JSONObject jsonObject2 = new JSONObject(response2);
                                            String locip= jsonObject2.getString("loc");

                                            String[] latlng = locip.split(",");
                                            Log.d("latlng", latlng[0]);
                                            String l1="37.422131";
                                            String l2="-122.084801";

                                            Api.getgeohash(getContext(), latlng[0],latlng[1], new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                       String geo=response.substring(2,9);

                                                        Log.d("geo", geo);
                                                        Api.getevents(getContext(), key,cata,dis,geo, new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response4) {
                                                                try {
                                                                   JSONArray ja=new JSONArray(response4);
                                                                   String[][] matrix=new String[ja.length()][6];
                                                                    for (int i = 0; i < ja.length(); i++) {
                                                                        JSONObject row = ja.getJSONObject(i);
                                                                        ans.add(row.getString("date"));
                                                                        ans.add(row.getString("icon"));
                                                                        ans.add(row.getString("event"));
                                                                        ans.add(row.getString("genre"));
                                                                        ans.add(row.getString("venue"));
                                                                        ans.add(row.getString("id"));

                                                                    }
//                                                                    Intent searchresult=new Intent(getContext(),SearchRes.class);
//                                                                    searchresult.putExtra("tab",0);
//                                                                    searchresult.putExtra("ans",matrix);
//                                                                    startActivity(searchresult);
                                                                  //  passData(ans);
                                                                    //switchToSecondFragment();
                                                                   // ((MainActivity)getActivity()).switchToSecondFragment();


                                                                    pblayout.setVisibility(RelativeLayout.INVISIBLE);
                                                                  scview.setVisibility(ScrollView.VISIBLE);
                                                                    display();

                                                                } catch (Exception e) {
                                                                   displaynone();
                                                                    e.printStackTrace();

                                                                }
                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                            }
                                                        });

                                                    } catch (Exception e) {
                                                       displaynone();
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                   displaynone();
                                                }
                                            });

                                        } catch (Exception e) {
                                           displaynone();
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                       displaynone();
                                    }
                                });

                            } catch (Exception e) {
                               displaynone();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                  displaynone();
                        }
                    });
                }else{
                    formlayout.setVisibility(RelativeLayout.INVISIBLE);
                    pblayout.setVisibility(RelativeLayout.VISIBLE);
                    //using loc
                    Api.loc_hash(getContext(), loc, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response5) {
                            try {
                                String geo2=response5.substring(2,9);
                                Log.d("loc geo", geo2);
                                Api.getevents(getContext(), key,cata,dis,geo2, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response4) {
                                        try {
                                            JSONArray ja=new JSONArray(response4);
                                            String[][] matrix=new String[ja.length()][6];
                                            for (int i = 0; i < ja.length(); i++) {
                                                JSONObject row = ja.getJSONObject(i);
                                                ans.add(row.getString("date"));
                                                ans.add(row.getString("icon"));
                                                ans.add(row.getString("event"));
                                                ans.add(row.getString("genre"));
                                                ans.add(row.getString("venue"));
                                                ans.add(row.getString("id"));

                                            }
//                                            Intent searchresult=new Intent(getContext(),SearchRes.class);
//                                            searchresult.putExtra("tab",0);
//                                            searchresult.putExtra("ans",matrix);
//                                            startActivity(searchresult);
                                           // passData(ans);
                                          //  ((MainActivity)getActivity()).switchToSecondFragment();


                                            pblayout.setVisibility(RelativeLayout.INVISIBLE);
                                            scview.setVisibility(LinearLayout.VISIBLE);
                                            display();


                                        } catch (Exception e) {
                                         displaynone();
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        displaynone();
                                    }
                                });

                            } catch (Exception e) {
                                displaynone();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            displaynone();
                        }
                    });
                }

            }
            public void display(){

                backlayout.setVisibility(View.VISIBLE);
                RecyclerView recyleview=(RecyclerView) v.findViewById(R.id.reslayout);
                ArrayList<showitem> result= new ArrayList<showitem>();
                int j=ans.size();
                int k=j/6;
                Log.d("there are ", Integer.toString(k));
                for(int i=0;i<j;i+=6){

                    result.add(new showitem(ans.get(i),ans.get(i+1),ans.get(i+2),ans.get(i+3),ans.get(i+4),ans.get(i+5)));
                }

                padaptor Adaptor=new padaptor(result,getContext());
                recyleview.setAdapter(Adaptor);
                recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
                Log.d("1", "display: called");



            }
            public void displaynone(){
                backlayout.setVisibility(LinearLayout.VISIBLE);
                pblayout.setVisibility(RelativeLayout.INVISIBLE);
                nrlayout.setVisibility(LinearLayout.VISIBLE);
            }
        });
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyedit.setText("");
                disedit.setText("");
                locedit.setText("");
                disedit.setText("10");
                dropdown.setSelection(0);
                swt.setChecked(false);
            }
        });

        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    locedit.setVisibility(EditText.INVISIBLE);
                }
                else{
                    locedit.setVisibility(EditText.VISIBLE);
                }
            }
        });
        backlayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                backlayout.setVisibility(View.INVISIBLE);
               // pblayout.setVisibility(RelativeLayout.INVISIBLE);

                formlayout.setVisibility(RelativeLayout.VISIBLE);
                pblayout.setVisibility(RelativeLayout.INVISIBLE);
                scview.setVisibility(ScrollView.GONE);
                nrlayout.setVisibility(LinearLayout.INVISIBLE);
            }
        });

        return v;
    }
//    public void hideform(){
//
//    }

    public void switchToSecondFragment(){

        Log.d("changed", "switchToSecondFragment: called");

        FragmentManager fragmentManager = getChildFragmentManager();
             //   getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ResFragment fg=new ResFragment();

        Fragment fragment =getActivity().getSupportFragmentManager().findFragmentById(R.id.form);

        fragmentTransaction.replace(R.id.form,fg);
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();

    }
    public void showfavortext(String x){
        Toast.makeText(getContext(),x,Toast.LENGTH_LONG).show();
    }



//    public interface OnDataPass {
//        public void onDataPass(ArrayList<String> data);
//    }
//    OnDataPass dataPasser;
//
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        dataPasser = (OnDataPass) context;
//    }
//    public void passData(ArrayList<String> data) {
//        Log.d("h", "passData: called");
//        dataPasser.onDataPass(data);
//    }
}