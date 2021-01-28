package com.wakeup.tarot.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.wakeup.tarot.R;

public class SplashScreen extends BaseActivity {
 
    private static int SPLASH_TIME_OUT = 0; // 2 seconds
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar));
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        aniRotate.setRepeatCount(2);
        ((ImageView) findViewById(R.id.img)).startAnimation(aniRotate);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                new Handler().postDelayed(new Runnable() {

                    /*
                     * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company
                     */

                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                        // Load all setting in background thread when splash show
                        //ConfigData.loadSettingData(SplashScreen.this);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                new Handler().postDelayed(new Runnable() {

                    /*
                     * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company
                     */

                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                        // Load all setting in background thread when splash show
                        //ConfigData.loadSettingData(SplashScreen.this);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }
        });

    }
}