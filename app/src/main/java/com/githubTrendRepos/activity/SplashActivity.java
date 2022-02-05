package com.githubTrendRepos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.githubTrendRepos.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent main = new Intent(SplashActivity.this, MainListActivity.class);
                startActivity(main);
                finish();

            }
        }, SPLASH_TIME_OUT);


    }
}