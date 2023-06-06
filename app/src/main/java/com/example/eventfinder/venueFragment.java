package com.example.eventfinder;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link venueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class venueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private float k=0;
    private float kk=0;
    private String mParam2;
    MapView mMapView;
    private GoogleMap googleMap;

    public venueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment venueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static venueFragment newInstance(String param1, String param2) {
        venueFragment fragment = new venueFragment();
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
        String venue=getArguments().getString("v");
        Log.d(venue, "onCreateView: venue frag");

        View v=inflater.inflate(R.layout.fragment_venue, container, false);
        if(venue==null){return v;}
        TextView name=v.findViewById(R.id.name);
        TextView address=v.findViewById(R.id.address);
        TextView contact=v.findViewById(R.id.contact);
        TextView city=v.findViewById(R.id.city);
        TextView open=v.findViewById(R.id.open);
        TextView general=v.findViewById(R.id.general);
        TextView child=v.findViewById(R.id.child);
        mMapView = (MapView) v.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        Context context = getActivity().getApplicationContext();

        Api.venue(getContext(), venue, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // his.venuename = this.resp._embedded.venues[0].name;
                    JSONObject jsonObject = new JSONObject(response);
                  //  this.v = res['_embedded']['venues'][0];
                    JSONObject obj=jsonObject.optJSONObject("_embedded").getJSONArray("venues").getJSONObject(0);
                   // {{ v.address.line1 }}, {{ v.city.name }}, {{ v.state.name }}
                    name.setText(obj.getString("name"));
                    name.setSelected(true);
                    address.setText(obj.getJSONObject("address").getString("line1"));
                    address.setSelected(true);
                    city.setText(obj.getJSONObject("city").getString("name")+","+obj.getJSONObject("state").getString("name"));
                    city.setSelected(true);
                   // {{ v.boxOfficeInfo.phoneNumberDetail }}

                    if(obj.has("boxOfficeInfo")){
                       if(obj.getJSONObject("boxOfficeInfo").has("phoneNumberDetail")){
                           contact.setText(obj.getJSONObject("boxOfficeInfo").getString("phoneNumberDetail"));
                           contact.setSelected(true);
                       }else{
                           contact.setVisibility(TextView.INVISIBLE);
                       }
                        if(obj.getJSONObject("boxOfficeInfo").has("openHoursDetail")){
                            open.setText(obj.getJSONObject("boxOfficeInfo").getString("openHoursDetail"));
                        }else{
                            open.setVisibility(TextView.INVISIBLE);
                        }

                    }
                    if(obj.getJSONObject("generalInfo").has("generalRule")){
                        Log.d("rule gen", "onResponse: general");
                        general.setText(obj.getJSONObject("generalInfo").getString("generalRule"));
                        general.setSelected(true);
                    }else{
                        general.setVisibility(TextView.INVISIBLE);
                    }
                    if(obj.getJSONObject("generalInfo").has("childRule")){
                        child.setText(obj.getJSONObject("generalInfo").getString("childRule"));
                        child.setSelected(true);
                    }else{
                        child.setVisibility(TextView.INVISIBLE);
                    }
//                    googleMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(0, 0))
//                            .title("Marker"));
//
                    JSONObject loc=obj.getJSONObject("location");
                   k=Float.parseFloat(loc.getString("latitude"));
                    kk=Float.parseFloat(loc.getString("longitude"));








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
      //  this.marker.position!.lat = parseFloat(this.v.location.latitude);
        //this.marker.position!.lng = parseFloat(this.v.location.longitude);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    public void onMapClick(LatLng point) {
                        // Drawing marker on the map
                        if(k!=0){
                        LatLng sydney = new LatLng(k, kk);
                        googleMap.addMarker(new MarkerOptions()
                                .position(sydney)
                                .title("Marker"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
                       }
                    }
                });
            }
        });
        return v;
    }



//    public void onMapReady(GoogleMap googleMap,float i,float j) {
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}