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
        adView.setAdSize(AdSize.BANNER);
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

        Config.CunghoangdaoAndDathanhtay.add(bachduong);Config.CunghoangdaoAndDathanhtay.add(baobinh);Config.CunghoangdaoAndDathanhtay.add(bocap);
        Config.CunghoangdaoAndDathanhtay.add(cugiai);Config.CunghoangdaoAndDathanhtay.add(kimnguu);Config.CunghoangdaoAndDathanhtay.add(maket);
        Config.CunghoangdaoAndDathanhtay.add(nhanma);Config.CunghoangdaoAndDathanhtay.add(songngu);Config.CunghoangdaoAndDathanhtay.add(songtu);
        Config.CunghoangdaoAndDathanhtay.add(sutu);Config.CunghoangdaoAndDathanhtay.add(thienbinh);Config.CunghoangdaoAndDathanhtay.add(xunu);

        String a1 = Utils.ReadFromfile(Constant.PATH_AMETHYST, this);
        String a2 = Utils.ReadFromfile(Constant.PATH_AVENTU, this);
        String a3 = Utils.ReadFromfile(Constant.PATH_CLEARQ, this);
        String a4 = Utils.ReadFromfile(Constant.PATH_HENMATITE, this);
        String a5 = Utils.ReadFromfile(Constant.PATH_LABRADO, this);
        String a6 = Utils.ReadFromfile(Constant.PATH_MOON, this);
        String a7 = Utils.ReadFromfile(Constant.PATH_MOSS, this);
        String a8 = Utils.ReadFromfile(Constant.PATH_TIGER, this);
        String a9 = Utils.ReadFromfile(Constant.PATH_BLACK, this);
        String a10 = Utils.ReadFromfile(Constant.PATH_CARNELIAN, this);
        String a11 = Utils.ReadFromfile(Constant.PATH_CITRINE, this);
        String a12 = Utils.ReadFromfile(Constant.PATH_GARNET, this);
        String a13 = Utils.ReadFromfile(Constant.PATH_LAPIS, this);
        String a14 = Utils.ReadFromfile(Constant.PATH_RED, this);
        String a15 = Utils.ReadFromfile(Constant.PATH_ROSE, this);
        String a16 = Utils.ReadFromfile(Constant.PATH_SMOKER, this);
        String a17 = Utils.ReadFromfile(Constant.PATH_SOLADO, this);
        String a18 = Utils.ReadFromfile(Constant.PATH_HOWLITE, this);

        Config.StoneDes.add(a1);Config.StoneDes.add(a2);Config.StoneDes.add(a3);
        Config.StoneDes.add(a4);Config.StoneDes.add(a5);Config.StoneDes.add(a6);
        Config.StoneDes.add(a7);Config.StoneDes.add(a8);Config.StoneDes.add(a9);
        Config.StoneDes.add(a10);Config.StoneDes.add(a11);Config.StoneDes.add(a12);
        Config.StoneDes.add(a13);Config.StoneDes.add(a14);Config.StoneDes.add(a15);
        Config.StoneDes.add(a16);Config.StoneDes.add(a17);Config.StoneDes.add(a18);
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
