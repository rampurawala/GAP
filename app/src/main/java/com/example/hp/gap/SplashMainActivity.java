package com.example.hp.gap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.hp.gap.SelectOption.SelectOptionActivity;
import com.example.hp.gap.SharedPreferences.PreferenceManager;


public class SplashMainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//[Start] Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_main);
        preferenceManager = new PreferenceManager(SplashMainActivity.this);
        //[End] Hide title bar

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent mainIntent = new Intent(SplashMainActivity.this, SplashMainActivity.class);
//                startActivity(mainIntent);
//                finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3 * 1000);

                    // After 5 seconds redirect to another intent
                    if (preferenceManager.getCheck()==0){
                        Intent i = new Intent(getBaseContext(), SelectOptionActivity.class);
                        startActivity(i);

                    }
                    else {
                        Intent i = new Intent(getBaseContext(), UserLoginActivity.class);
                        startActivity(i);

                    }
                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
