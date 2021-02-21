package com.wakeup.tarot.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wakeup.tarot.BuildConfig;
import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;

import java.util.ArrayList;

public class InformationActivity extends BaseActivity implements View.OnTouchListener {

    RelativeLayout rlGioiThieu;
    RelativeLayout rlThanhTay;

    RelativeLayout rtlRate;
    RelativeLayout rtlVoc;
    RelativeLayout rtlShare;
    LinearLayout lnBrick;
    LinearLayout lnBaby;

    FrameLayout mainFragment;
    ImageView imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin);

        ConfigData.loadSettingData(this);
        mAdView = (AdView) findViewById(R.id.adView);
        getInstance.loadAd(mAdView);

        imgHome = (ImageView) findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(InformationActivity.this,
                        MainActivity.class));
            }
        });

        mainFragment = (FrameLayout) findViewById(R.id.nav_main_fragment);

        rlGioiThieu = (RelativeLayout) findViewById(R.id.gioithieu);
        rlGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentGioiThieu();
            }
        });
        rlGioiThieu.setOnTouchListener(this);

        rlThanhTay = (RelativeLayout) findViewById(R.id.thanhtaybaiRl);
        rlThanhTay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentThanhTay();
            }
        });
        rlThanhTay.setOnTouchListener(this);


        rtlRate = (RelativeLayout) findViewById(R.id.rate_app);
        rtlRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate_this_app();
            }
        });
        rtlRate.setOnTouchListener(this);

        rtlVoc = (RelativeLayout) findViewById(R.id.VOC);
        rtlVoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });
        rtlVoc.setOnTouchListener(this);


        rtlShare = (RelativeLayout) findViewById(R.id.share);
        rtlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareView();
            }
        });
        rtlShare.setOnTouchListener(this);


        lnBrick = (LinearLayout) findViewById(R.id.btnBrickGame);
        lnBrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBrick();
            }
        });
        lnBrick.setOnTouchListener(this);

        lnBaby = (LinearLayout) findViewById(R.id.babySleep);
        lnBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSleep();
            }
        });
        lnBaby.setOnTouchListener(this);
        //selectBrowserMode(mode);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConfigData.saveSettingData();
    }

    private void addFragmentWithNavigationId(int i, Bundle bundle) {
        try {
            NavHostFragment create = NavHostFragment.create(i, bundle);
            if (getSupportFragmentManager() != null) {
                mainFragment.setVisibility(View.VISIBLE);
                FragmentTransaction add = getSupportFragmentManager().beginTransaction();
                add.setCustomAnimations(R.anim.slide_down, R.anim.no_animation);
                add.add((int) R.id.nav_main_fragment, (Fragment) create);
                add.addToBackStack(create + "").setPrimaryNavigationFragment(create).commit();
            }
        } catch (IllegalStateException unused) {
        }
    }

    public void addFragmentGioiThieu() {
        addFragmentWithNavigationId(R.layout.gioi_thieu_xml, (Bundle) null);
    }

    public void addFragmentThanhTay() {
        addFragmentWithNavigationId(R.layout.thanh_tay_xml, (Bundle) null);
    }

    public void addFragmentMean() {
        addFragmentWithNavigationId(R.layout.type_card_page_xml, (Bundle) null);
    }

    public void addFragmentCungHoangDao() {
        addFragmentWithNavigationId(R.layout.cung_hoang_dao, (Bundle) null);
    }

    private void getSleep() {
        String theUrl = "https://play.google.com/store/apps/details?id=com.wakeup.babysleep";
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(theUrl));
        startActivity(browse);
    }

    private void getBrick() {
        String theUrl = "https://play.google.com/store/apps/details?id=com.wakeup.gamexephinh";
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(theUrl));
        startActivity(browse);
    }

    private void rate_this_app() {
        String appName = BuildConfig.APPLICATION_ID;
        openInPlayStore(appName);
    }

    private void openInPlayStore(String appName) {
        String theUrl = "market://details?id=" + appName;
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(theUrl));
        startActivity(browse);
    }

    private void feedback() {
        Intent feedbackEmail = new Intent(Intent.ACTION_SEND);
        feedbackEmail.setType("text/email");
        feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"anhhoang.korean@gmail.com"});
        feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        startActivity(Intent.createChooser(feedbackEmail, "Send Feedback to TAROT VIET:"));

        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{"anhhoang.korean@gmail.com"});
            intent.putExtra("android.intent.extra.SUBJECT", "Feedback");
            intent.putExtra("android.intent.extra.TEXT", "Send Feedback to TAROT VIET:");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Send Feedback to TAROT VIET:"));

        } catch (Exception e) {
        }
    }

    private void openRateApp() {
        if (getApplicationContext() != null) {
            String packageName = getPackageName();
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
            } catch (ActivityNotFoundException unused) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    private void onShareView() {
        String str = "\n - Android : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        if (getApplicationContext() != null) {
            openShareIntent(BuildConfig.APPLICATION_ID, str);
        }
    }

    public void openShareIntent(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.TEXT", str2);
        intent.setType("text/plain");
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, 0)) {
            new ArrayList().add(resolveInfo.activityInfo.packageName);
        }
        startActivity(Intent.createChooser(intent, "Share with:"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainFragment.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.gioithieu:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlGioiThieu.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlGioiThieu.clearAnimation();
                    break;
                }
            case R.id.thanhtaybaiRl:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlThanhTay.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlThanhTay.clearAnimation();
                    break;
                }
            case R.id.rate_app:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rtlRate.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rtlRate.clearAnimation();
                    break;
                }

            case R.id.VOC:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rtlVoc.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rtlVoc.clearAnimation();
                    break;
                }

            case R.id.share:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rtlShare.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rtlShare.clearAnimation();
                    break;
                }

            case R.id.btnBrickGame:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    lnBrick.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    lnBrick.clearAnimation();
                    break;
                }
            case R.id.babySleep:
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    lnBaby.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    lnBaby.clearAnimation();
                    break;
                }

        }
        return false;
    }
}
