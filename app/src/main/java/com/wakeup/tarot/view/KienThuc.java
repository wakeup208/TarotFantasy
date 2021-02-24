package com.wakeup.tarot.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wakeup.tarot.BuildConfig;
import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.util.Config;

import java.util.ArrayList;

public class KienThuc extends BaseActivity implements View.OnTouchListener {

    private RelativeLayout rlMean;
    private RelativeLayout cunghoangdao;
    private RelativeLayout rlThanhTay;
    private RelativeLayout rlChakra;
    private RelativeLayout rlStone;


    private TextView dathanhtay1, txtCungHoangDao, txtMean1, txtStone;

    private FrameLayout mainFragment;
    private ImageView imgHome;
    private int mode = 0;

    @Override
    public void refreshCardBack() {

    }

    @Override
    public void refreshAppBg() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kien_thuc);

        ConfigData.loadSettingData(this);

        mAdView = (AdView) findViewById(R.id.adView);
        getInstance.loadAd(mAdView);

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                switch (mode) {
                    case 1:
                        addFragmentMean();
                        break;
                    case 2:
                        addFragmentCungHoangDao();
                        break;
                    case 3:
                        startThanhTay();
                        break;
                    case 4:
                        startChakra();
                        break;
                    case 5:
                        startThanhTayAndCung();
                        break;
                    case 0:
                        break;
                }
            }
        });

        imgHome = (ImageView) findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(KienThuc.this,
                        MainActivity.class));
            }
        });

        mainFragment = (FrameLayout) findViewById(R.id.nav_main_fragment_kienthuc);

        rlMean = (RelativeLayout) findViewById(R.id.rtl_mean);
        rlMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                else {
                    addFragmentMean();
                }
            }
        });
        rlMean.setOnTouchListener(this);

        cunghoangdao = (RelativeLayout) findViewById(R.id.cunghoangdao);
        cunghoangdao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                else {
                    addFragmentCungHoangDao();
                }
            }
        });
        cunghoangdao.setOnTouchListener(this);

        rlThanhTay = (RelativeLayout) findViewById(R.id.dathanhtayandcunghoangdao);
        rlThanhTay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 3;
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                else {
                    startThanhTay();
                }
            }
        });
        rlThanhTay.setOnTouchListener(this);

        rlStone = (RelativeLayout) findViewById(R.id.allStone);
        rlStone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 5;
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                else {
                    startThanhTayAndCung();
                }
            }
        });
        rlStone.setOnTouchListener(this);

        rlChakra = (RelativeLayout) findViewById(R.id.rlCharka);
        rlChakra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode = 4;
                if(interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                else {
                    startChakra();
                }
            }
        });
        rlChakra.setOnTouchListener(this);

        dathanhtay1 = (TextView) findViewById(R.id.dathanhtay);
        dathanhtay1.setSelected(true);

        txtMean1 = (TextView) findViewById(R.id.txtMean);
        txtMean1.setSelected(true);

        txtCungHoangDao = (TextView) findViewById(R.id.txtCungHoangDao);
        txtCungHoangDao.setSelected(true);

        txtStone = (TextView) findViewById(R.id.txtallStone);
        txtStone.setSelected(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
//        AdRequest adInterstitial = new AdRequest.Builder().build();
//        //adRequestBuilder1 = new AdRequest.Builder();
//        interstitialAd.loadAd(adInterstitial);
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
                add.add((int) R.id.nav_main_fragment_kienthuc, (Fragment) create);
                add.addToBackStack(create + "").setPrimaryNavigationFragment(create).commit();
            }
        } catch (IllegalStateException unused) {
        }
    }

    public void startThanhTay() {
        Intent intent = new Intent(KienThuc.this, DaThanhTayInfor.class);
        startActivity(intent);
    }

    public void startThanhTayAndCung() {
        Intent intent = new Intent(KienThuc.this, AllStoneInfo.class);
        startActivity(intent);
    }

    public void addFragmentMean() {
        addFragmentWithNavigationId(R.layout.type_card_page_xml, (Bundle) null);
    }

    public void startChakra() {
        addFragmentWithNavigationId(R.layout.chakar_bg, (Bundle) null);
    }

    public void addFragmentCungHoangDao() {
        addFragmentWithNavigationId(R.layout.cung_hoang_dao, (Bundle) null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainFragment.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.rtl_mean:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlMean.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlMean.clearAnimation();
                    break;
                }
            case R.id.cunghoangdao:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    cunghoangdao.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    cunghoangdao.clearAnimation();
                    break;
                }

            case R.id.dathanhtayandcunghoangdao:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlThanhTay.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlThanhTay.clearAnimation();
                    break;
                }

            case R.id.allStone:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlStone.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlStone.clearAnimation();
                    break;
                }

            case R.id.rlCharka:

                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    rlChakra.startAnimation(ConfigData.animation_zoom_in);
                    break;
                } else if (MotionEvent.ACTION_UP == event.getAction()) {
                    rlChakra.clearAnimation();
                    break;
                }
        }
        return false;
    }
}
