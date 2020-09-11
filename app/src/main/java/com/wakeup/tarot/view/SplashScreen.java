package com.wakeup.tarot.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;


public class SplashScreen extends AppCompatActivity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000; // 2 seconds
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
        	
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // Load all setting in background thread when splash show
                ConfigData.loadSettingData(SplashScreen.this);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
 
}