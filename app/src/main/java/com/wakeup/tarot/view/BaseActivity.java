package com.wakeup.tarot.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.wakeup.tarot.R;
import com.wakeup.tarot.listener.OnChangeBackground;
import com.wakeup.tarot.model.CustomModelClass;
import com.wakeup.tarot.preferences.Prefs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements OnChangeBackground {
    public InterstitialAd interstitialAd, interstitialAd1;
    public WithApp getInstance;
    public AdView mAdView;

    public abstract void refreshAppBg();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getInstance = (WithApp) getApplication();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
        AdRequest adInterstitial = new AdRequest.Builder().build();
        //adRequestBuilder1 = new AdRequest.Builder();
        interstitialAd.loadAd(adInterstitial);

        interstitialAd1 = new InterstitialAd(this);
        interstitialAd1.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
        AdRequest adInterstitial1 = new AdRequest.Builder().build();
        //adRequestBuilder2 = new AdRequest.Builder();
        interstitialAd1.loadAd(adInterstitial1);

        Prefs.init(this);
        CustomModelClass.getInstance().setListener(this);

    }
}
