package com.example.eventfinder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(i);
//                // close this activity
//                finish();
//            }
//        }, 3000);
    }




}