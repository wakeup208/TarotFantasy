package com.wakeup.tarot.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wakeup.tarot.BuildConfig;
import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.receiver.MyNotificationPublisher;
import com.wakeup.tarot.util.ImageCache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private TextView tvAppName;
    private LinearLayout btn_drawcard;
    private LinearLayout btn_spreadcard;
    private LinearLayout btn_encyclopedia;
    private LinearLayout btn_profile_animation;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.new_main_activity);

        // Load all setting in background thread when splash show
        ConfigData.loadSettingData(this);

        // Load background
        ((ImageView) findViewById(R.id.background))
                .setBackground(ConfigData.rbdBackground);

        tvAppName = (TextView) findViewById(R.id.tvAppName);
        tvAppName.setTypeface(ConfigData.UVNCatBien_Sub);

        btn_drawcard = (LinearLayout) findViewById(R.id.chonbai);
        //btn_drawcard.setTypeface(ConfigData.UVNCatBien_R);
        btn_drawcard.setOnClickListener(this);
        btn_drawcard.setOnTouchListener(this);

        btn_spreadcard = (LinearLayout) findViewById(R.id.trabai);
        //btn_spreadcard.setTypeface(ConfigData.UVNCatBien_R);
        btn_spreadcard.setOnClickListener(this);
        btn_spreadcard.setOnTouchListener(this);

        btn_encyclopedia = (LinearLayout) findViewById(R.id.sotay);
        //btn_encyclopedia.setTypeface(ConfigData.UVNCatBien_R);
        btn_encyclopedia.setOnClickListener(this);
        btn_encyclopedia.setOnTouchListener(this);

        btn_profile_animation = (LinearLayout) findViewById(R.id.hotro);
        //btn_profile_animation.setTypeface(ConfigData.UVNCatBien_R);
        btn_profile_animation.setOnClickListener(this);
        btn_profile_animation.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        // Load background
        ((ImageView) findViewById(R.id.background))
                .setBackground(ConfigData.rbdBackground);

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        try {
            // Delete Disk cache directory
            ImageCache.deleteDiskCacheDir();
        } catch (Exception e) {
        }
        super.onDestroy();
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
        alarmManager.setExact(AlarmManager. RTC_WAKEUP , delay, pendingIntent); ;


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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
            myCalendar .set(Calendar. YEAR , year) ;
            myCalendar .set(Calendar. MONTH , monthOfYear) ;
            myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
            updateLabel() ;
        }
    } ;

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            myCalendar.set(Calendar.SECOND, 0);
            myCalendar.set(Calendar.MILLISECOND, 0);
            updateLabel1();
        }
    };

    public void setDate (View view) {
        new DatePickerDialog(
                MainActivity. this, date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;
    }

    public void setTime (View view) {
        new TimePickerDialog(MainActivity.this, timeSetListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                true).show();
    }

    private void updateLabel () {
        String myFormat = "dd/MM/yy" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
       scheduleNotification(getNotification( "Nguyen Mau Thuc") , date.getTime()) ;
    }

    private void updateLabel1 () {
        Log.d("abcd","time = " + myCalendar.getTimeInMillis());
        scheduleNotification(getNotification( "") , myCalendar.getTimeInMillis()) ;
    }

    public static void printAvailableMemory() {
        if (BuildConfig.DEBUG) {
            Log.w("Total memory now: ", ""
                    + (Runtime.getRuntime().totalMemory() / 1024) + "KB");
            Log.w("Max memory now: ", ""
                    + (Runtime.getRuntime().maxMemory() / 1024) + "KB");
            Log.w("Free memory now: ", ""
                    + (Runtime.getRuntime().freeMemory() / 1024) + "KB");

            Log.w("WIDTH x HEIGHT", ConfigData.SCREEN_WIDTH + " x "
                    + ConfigData.SCREEN_HEIGHT);
        }
    }

    @Override
    protected void onPause() {
        ConfigData.saveSettingData();
        super.onPause();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chonbai:
                Intent intentDrawCardActivity = new Intent(this,
                        ChooseCardActivity.class);
                intentDrawCardActivity.putExtra("cardSelectInNeed", 1);
                this.startActivity(intentDrawCardActivity);
                break;

            case R.id.trabai:
                Intent intentTarotSpreadActivity = new Intent(this,
                        TarotSpreadActivity.class);
                this.startActivity(intentTarotSpreadActivity);
                break;

            case R.id.sotay:
                Intent intentBrowseCards = new Intent(this,
                        BrowseCardsActivity.class);
                this.startActivity(intentBrowseCards);
                break;

            case R.id.hotro:
                Intent intentProfileActivity = new Intent(this,
                        ProfileActivity.class);
                this.startActivity(intentProfileActivity);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.chonbai:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    btn_drawcard.startAnimation(ConfigData.animation_button_press);
                }
                break;

            case R.id.trabai:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    btn_spreadcard
                            .startAnimation(ConfigData.animation_button_press);
                }
                break;

            case R.id.sotay:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    btn_encyclopedia
                            .startAnimation(ConfigData.animation_button_press);
                }
                break;

            case R.id.hotro:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    btn_profile_animation
                            .startAnimation(ConfigData.animation_button_press);
                }
                break;
        }
        return false;    }
}
