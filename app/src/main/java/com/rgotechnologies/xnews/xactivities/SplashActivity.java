package com.rgotechnologies.xnews.xactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rgotechnologies.xnews.MainActivity;
import com.rgotechnologies.xnews.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //checkStatus();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            },1300);



        }
    }

