package com.example.eventfinder;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import android.app.job.JobInfo;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import io.reactivex.rxjava3.core.Single;


public class MainActivity extends AppCompatActivity implements LocationListener {
    private ArrayList<String> favor=new ArrayList<>();
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    public Double lat;
    public Double lng;
public TextView txt;
    private SharedPreferences.Editor myEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setTheme(R.style.Theme_EventFinder);
     //   setTheme(R.style.Theme_EventFinder_Launcher);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTheme(R.style.Theme_EventFinder);
        TabLayout tabLayout=findViewById(R.id.tabLayout);
        TabItem searchtab=findViewById(R.id.tabSearch);
        TabItem favortab=findViewById(R.id.tabFavor);
        ViewPager viewpage=findViewById(R.id.viewpager);
txt=findViewById(R.id.textView11);
        File file = new File(MainActivity.this.getFilesDir(), "text");
        int REQUEST_LOCATION = 99;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    

        pageradaptor pgadaptor=new pageradaptor(getSupportFragmentManager(),tabLayout.getTabCount(),favor);
        viewpage.setAdapter(pgadaptor);
        tabLayout.setupWithViewPager(viewpage);
        File fileEvents = new File(MainActivity.this.getFilesDir()+"/text/sample");
        if (fileEvents.exists()) {
            Log.d("there is file", "onCreate: ");
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line="";

                while ((line = br.readLine())!= null) {
                    text.append(line);
                }
            } catch (IOException e) { }
            if(!text.toString().isEmpty()){
                            String result = text.toString();
            String[] aa=result.split("#");
            for(int i=0;i<aa.length;i++){
                favor.add(aa[i]);
                Log.d(aa[i], "onCreate: this creates favor form before");
            }
            }

        }

    }
    public String gettext(){
        return "";
       //
    }
    public void updatex(){
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "sample");
            FileWriter writer = new FileWriter(gpxfile);
            if(favor.isEmpty()){

            }else{
                String tmp=favor.get(0);
                for(int i=1;i<favor.size();i++){
                    tmp+="#"+favor.get(i);
                }
                writer.append(tmp);
                writer.flush();
                writer.close();
            //    Toast.makeText(MainActivity.this, tmp, Toast.LENGTH_LONG).show();
                //output.setText(readFile());
            }

        } catch (Exception e) { }

    }
    @Override
    protected void onResume() {
        super.onResume();
        File fileEvents = new File(MainActivity.this.getFilesDir()+"/text/sample");
        if (fileEvents.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                String line= br.readLine();
                br.close();
            } catch (IOException e) { }
            Log.d("there is file", "onResume: ");
            if(!text.toString().isEmpty()){
                String result = text.toString();
                String[] aa=result.split("#");
                for(int i=0;i<aa.length;i++){
                    favor.add(aa[i]);
                    Log.d(aa[i], "onCreate: this creates favor form before");
                }
            }

        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        updatex();

    }

    public ArrayList<String> readf(){
       return favor;
    }

    public void deletefavor(String id){
        if(!favor.isEmpty()){
            for(int i=0;i<favor.size();i++){
                Log.d(favor.get(i), "deletefavor: ");
            }
            for(int i=0;i<favor.size();i+=7){
                if(favor.get(i)==id){
                    favor.remove(i);
                    favor.remove(i);
                    favor.remove(i);
                    favor.remove(i);
                    favor.remove(i);
                    favor.remove(i);
                    favor.remove(i);

                    break;
                }
            }
            updatex();
        }
       // update();
    }


    // The update is completed once updateResult is completed.
    public void addfavor(String a,String b,String c,String d,String e,String f,String id){

        favor.add(id);
        favor.add(a);
        favor.add(b);
        favor.add(c);
        favor.add(d);
        favor.add(e);
        favor.add(f);
updatex();
//        TabLayout tabLayout=findViewById(R.id.tabLayout);
//        TabItem searchtab=findViewById(R.id.tabSearch);
//        TabItem favortab=findViewById(R.id.tabFavor);
//        ViewPager viewpage=findViewById(R.id.viewpager);
//
//        pageradaptor pgadaptor=new pageradaptor(getSupportFragmentManager(),tabLayout.getTabCount(),favor);
//        viewpage.setAdapter(pgadaptor);
//        tabLayout.setupWithViewPager(viewpage);

    }
    public boolean checkif(String id){
        if(!favor.isEmpty()){
            for(int i=0;i<favor.size();i+=7){
                if(favor.get(i)==id){
                    return true;
                }
            }
        }
        return false;
    }
    public void gotodetail(String id){

        Intent myIntent = new Intent(this, DeActivity.class);
        Bundle b = new Bundle();
        b.putString("id",id);
        Boolean y=checkif(id);
        b.putBoolean("if",y);
        myIntent.putExtras(b); //Optional parameters
        this.startActivity(myIntent);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        return true;
    }
    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
       // Log.d("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), "onLocationChanged: ");

    }
    public double getlng(){
        return lng;
    }
    public double getlat(){
        return lat;
    }


    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}