package com.wakeup.tarot.view;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.receiver.MyNotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SplashScreen extends AppCompatActivity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000; // 2 seconds
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
                //ConfigData.loadSettingData(SplashScreen.this);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        updateLabel ();
    }

    private void updateLabel () {
        scheduleNotification(getNotification( "Nguyen Mau Thuc") , 5000) ;
    }

    private void scheduleNotification (Notification notification , long delay) {
        long timeAtButtonClick = System.currentTimeMillis();

        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;


        //alarmManager.set(AlarmManager. RTC_WAKEUP , timeAtButtonClick + 1000*10, pendingIntent) ;
        //alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , (delay - timeAtButtonClick) , pendingIntent) ;
        //alarmManager.setExact(AlarmManager. RTC_WAKEUP , delay, pendingIntent);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);


    }
    private Notification getNotification (String content) {
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notice_collapse);
        RemoteViews contentViewExpand = new RemoteViews(getPackageName(), R.layout.notice_expand);


        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Tarot" ).setOngoing(true).setColor(getColor(R.color.white));
        builder.setSmallIcon(R.drawable. icon_call ) ;
        builder.setAutoCancel( true ).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(Notification.CATEGORY_MESSAGE) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle()).setCustomContentView(contentView).setDefaults(Notification.DEFAULT_ALL);
        builder.setCustomBigContentView(contentViewExpand);
        builder.setContentIntent(activity);
        return builder.build() ;
    }

}