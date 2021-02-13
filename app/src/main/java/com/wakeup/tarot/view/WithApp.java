package com.wakeup.tarot.view;

import android.app.Application;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.wakeup.tarot.R;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.util.Constant;
import com.wakeup.tarot.util.Utils;

import java.util.ArrayList;

public class WithApp extends Application {
    private AdView adView;
    public static InterstitialAd interstitialAd;

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
        //loadMidAd();

        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id_test));

        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        String bachduong = Utils.ReadFromfile(Constant.PATH_BACHDUONG, this);
        String baobinh = Utils.ReadFromfile(Constant.PATH_BAOBINH, this);
        String bocap = Utils.ReadFromfile(Constant.PATH_BOCAP, this);
        String cugiai = Utils.ReadFromfile(Constant.PATH_CUGIAI, this);
        String kimnguu = Utils.ReadFromfile(Constant.PATH_KIMNGUU, this);
        String maket = Utils.ReadFromfile(Constant.PATH_MAKET, this);
        String nhanma = Utils.ReadFromfile(Constant.PATH_NHANMA, this);
        String songngu = Utils.ReadFromfile(Constant.PATH_SONGNGU, this);
        String songtu = Utils.ReadFromfile(Constant.PATH_SONGTU, this);
        String sutu = Utils.ReadFromfile(Constant.PATH_SUTU, this);
        String thienbinh = Utils.ReadFromfile(Constant.PATH_THIENBINH, this);
        String xunu = Utils.ReadFromfile(Constant.PATH_XUNU, this);
        Config.CunghoangdaoAndDathanhtay.add(bachduong);
        Config.CunghoangdaoAndDathanhtay.add(baobinh);
        Config.CunghoangdaoAndDathanhtay.add(bocap);
        Config.CunghoangdaoAndDathanhtay.add(cugiai);
        Config.CunghoangdaoAndDathanhtay.add(kimnguu);
        Config.CunghoangdaoAndDathanhtay.add(maket);
        Config.CunghoangdaoAndDathanhtay.add(nhanma);
        Config.CunghoangdaoAndDathanhtay.add(songngu);
        Config.CunghoangdaoAndDathanhtay.add(songtu);
        Config.CunghoangdaoAndDathanhtay.add(sutu);
        Config.CunghoangdaoAndDathanhtay.add(thienbinh);
        Config.CunghoangdaoAndDathanhtay.add(xunu);
    }

    public void loadMidAd() {
        AdRequest adInterstitial = new AdRequest.Builder().build();
        interstitialAd.loadAd(adInterstitial);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });
    }

    public void loadAd(AdView mAdView) {

        // Locate the Banner Ad in activity xml
        if (adView.getParent() != null) {
            ViewGroup tempVg = (ViewGroup) adView.getParent();
            tempVg.removeView(adView);
        }

        mAdView.addView(adView);
    }
}
