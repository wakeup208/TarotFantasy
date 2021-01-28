package com.wakeup.tarot.view;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.GroupNumberImageGridViewAdapter;
import com.wakeup.tarot.adapter.GroupStarImageGridViewAdapter;
import com.wakeup.tarot.adapter.GroupSuitImageGridViewAdapter;
import com.wakeup.tarot.adapter.GroupSymbolImageGridViewAdapter;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.util.ImageCache;
import com.wakeup.tarot.util.ImageCache.ImageCacheParams;
import com.wakeup.tarot.util.ImageLoaderAsynch;

import static com.wakeup.tarot.adapter.GroupNumberImageGridViewAdapter.realPositionGroupNumber;
import static com.wakeup.tarot.adapter.GroupSuitImageGridViewAdapter.realPositionGroupSuitI;
import static com.wakeup.tarot.adapter.GroupSymbolImageGridViewAdapter.realPositionGroupSymbol;

public class BrowseGroupCardsActivity extends FragmentActivity implements
        OnClickListener {

    public static Context mContext;
    public static final String BROWSE_GROUP_IMAGE_CACHE_DIR = "browse_group_image_cache";

    // Has 4 levels (0 --> 3) default with level 1 and 5 columns
    public static int zoom_level = 0;
    private TextView tvTitle;
    private ImageButton btn_home;

    // Fragment to show Browse mode
    GroupSuitCardFragment mGroupSuitCardFragment;
    GroupNumberCardFragment mGroupNumberCardFragment;
    GroupSymbolCardFragment mGroupSymbolCardFragment;
    GroupStarCardFragment mGroupStarCardFragment;
    private int select = 0;
    private int mode = 0;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_group_cards);

        // Reload screen size and background
        //ConfigData.reloadScreen(this);

        mContext = this.getApplicationContext();

        try{
            Bundle b = getIntent().getExtras();
            select = b.getInt("select");
            mode =  b.getInt("mode");
        } catch(Exception ex){
            select = 0;
            mode = 0;
        }

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(ConfigData.UVNCatBien_R);

        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(this);

        // Replace container by mGridCardFragment
        selectBrowserMode(mode);

    }

    @Override
    protected void onResume() {
        // Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (select == 0) {
            this.finish();
        } else {
            select = 0;
            if (mode == 1) {
                this.finish();
                startActivity(new Intent(this, InformationActivity.class));
            } else {
                this.finish();
            }
        }
        ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = true;
    }

    /**
     * select browser view mode
     *
     * @param mode
     */
    public void selectBrowserMode(int mode) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        tvTitle.setText(MapData.arrGroupCardName[mode]);

        switch (mode) {
            case 0: // Grid Suit card mode
                if (mGroupSuitCardFragment == null) {
                    mGroupSuitCardFragment = new GroupSuitCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGroupSuitCardFragment)
                        .commit();
                break;

            case 1: // Grid Star card mode
                if (mGroupStarCardFragment == null) {
                    mGroupStarCardFragment = new GroupStarCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGroupStarCardFragment)
                        .addToBackStack("mGroupStarCardFragment")
                        .commit();
                break;

            case 2: // Grid Number card mode
                if (mGroupNumberCardFragment == null) {
                    mGroupNumberCardFragment = new GroupNumberCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGroupNumberCardFragment)
                        .commit();
                break;

            case 3: // Grid Symbol card mode
                if (mGroupSymbolCardFragment == null) {
                    mGroupSymbolCardFragment = new GroupSymbolCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGroupSymbolCardFragment)
                        .commit();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btn_home) {
            if (select == 0) {
                this.finish();
                this.startActivity(new Intent(this.getApplicationContext(),
                        MainActivity.class));
            } else {
                select = 0;
                this.finish();
                this.startActivity(new Intent(this.getApplicationContext(),
                        InformationActivity.class));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encyclopedia_menu, menu);
        return true;
    }

    public static class GroupSuitCardFragment extends Fragment {

        public static int cell_width = 0;
        private GridView gridview;
        private GroupSuitImageGridViewAdapter mGroupSuitImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GroupSuitCardFragment mInstance;
        public static InterstitialAd interstitialAdGroupSuitCardFragment;

        public GroupSuitCardFragment() {
            mInstance = this;
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
                    BROWSE_GROUP_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.setImageSize((ConfigData.SCREEN_WIDTH - 300) / 3);
            mImageLoader.setLoadingImage(null);
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);
        }

        /**
         * Reclaim memory and cancel all background task
         */
        public void restartCacheToClaimMemory() {
            // TODO Auto-generated method stub
            mImageLoader.clearMemCache();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
            mImageLoader.closeCache();
            createImageLoader();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            createImageLoader();
            View view = inflater.inflate(R.layout.fragment_gridcard, container, false);
            mGroupSuitImageGridViewAdapter = new GroupSuitImageGridViewAdapter(view.getContext());

            gridview = (GridView) view.findViewById(R.id.gridview);
            gridview.setAdapter(mGroupSuitImageGridViewAdapter);
            cell_width = (ConfigData.SCREEN_WIDTH - 40) / 2;
            gridview.setColumnWidth(cell_width);

            interstitialAdGroupSuitCardFragment = new InterstitialAd(getContext());
            interstitialAdGroupSuitCardFragment.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
            AdRequest adInterstitial = new AdRequest.Builder().build();
            interstitialAdGroupSuitCardFragment.loadAd(adInterstitial);

            interstitialAdGroupSuitCardFragment.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mGroupSuitImageGridViewAdapter.startGroupSuit(realPositionGroupSuitI);
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mGroupSuitImageGridViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPause() {
            super.onPause();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mImageLoader.clearMemCache();
            if (ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON) {
                mImageLoader.clearDiskCache();
                ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = false;
            }
            mImageLoader.closeCache();

        }

    }

    public static class GroupNumberCardFragment extends Fragment {

        public static int cell_width = 0;
        private GridView gridview;
        private GroupNumberImageGridViewAdapter mGroupNumberImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GroupNumberCardFragment mInstance;
        public static InterstitialAd interstitialAdGroupNumberCardFragment;


        public GroupNumberCardFragment() {
            mInstance = this;
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
                    BROWSE_GROUP_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.setImageSize((ConfigData.SCREEN_WIDTH - 300) / 3);
            mImageLoader.setLoadingImage(null);
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);
        }

        /**
         * Reclaim memory and cancel all background task
         */
        public void restartCacheToClaimMemory() {
            // TODO Auto-generated method stub
            mImageLoader.clearMemCache();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
            mImageLoader.closeCache();
            createImageLoader();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            createImageLoader();

            View view = inflater.inflate(R.layout.fragment_gridcard, container,
                    false);

            mGroupNumberImageGridViewAdapter = new GroupNumberImageGridViewAdapter(view.getContext());

            gridview = (GridView) view.findViewById(R.id.gridview);
            gridview.setAdapter(mGroupNumberImageGridViewAdapter);
            cell_width = (ConfigData.SCREEN_WIDTH - 40) / 3;

            gridview.setColumnWidth(cell_width);


            interstitialAdGroupNumberCardFragment = new InterstitialAd(getContext());
            interstitialAdGroupNumberCardFragment.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
            AdRequest adInterstitial = new AdRequest.Builder().build();
            interstitialAdGroupNumberCardFragment.loadAd(adInterstitial);

            interstitialAdGroupNumberCardFragment.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mGroupNumberImageGridViewAdapter.startGroupNumber(realPositionGroupNumber);
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mGroupNumberImageGridViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPause() {
            super.onPause();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mImageLoader.clearMemCache();
            if (ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON) {
                mImageLoader.clearDiskCache();
                ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = false;
            }
            mImageLoader.closeCache();

        }

    }

    public static class GroupSymbolCardFragment extends
            Fragment {

        public static int cell_width = 0;
        private GridView gridview;
        private GroupSymbolImageGridViewAdapter mGroupSymbolImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GroupSymbolCardFragment mInstance;
        public static InterstitialAd interstitialAdGroupSymbolCardFragment;


        public GroupSymbolCardFragment() {
            mInstance = this;
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
                    BROWSE_GROUP_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.setImageSize((ConfigData.SCREEN_WIDTH - 300) / 3);
            mImageLoader.setLoadingImage(null);
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);
        }

        /**
         * Reclaim memory and cancel all background task
         */
        public void restartCacheToClaimMemory() {
            // TODO Auto-generated method stub
            mImageLoader.clearMemCache();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
            mImageLoader.closeCache();
            createImageLoader();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            createImageLoader();


            View view = inflater.inflate(R.layout.fragment_gridcard, container,
                    false);

            mGroupSymbolImageGridViewAdapter = new GroupSymbolImageGridViewAdapter(view.getContext());

            gridview = (GridView) view.findViewById(R.id.gridview);
            gridview.setAdapter(mGroupSymbolImageGridViewAdapter);
            cell_width = (ConfigData.SCREEN_WIDTH - 40) / 3;
            gridview.setColumnWidth(cell_width);

            interstitialAdGroupSymbolCardFragment = new InterstitialAd(getContext());
            interstitialAdGroupSymbolCardFragment.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
            AdRequest adInterstitial = new AdRequest.Builder().build();
            interstitialAdGroupSymbolCardFragment.loadAd(adInterstitial);

            interstitialAdGroupSymbolCardFragment.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mGroupSymbolImageGridViewAdapter.startGroupSymbol(realPositionGroupSymbol);
                }
            });
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mGroupSymbolImageGridViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPause() {
            super.onPause();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mImageLoader.clearMemCache();
            if (ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON) {
                mImageLoader.clearDiskCache();
                ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = false;
            }
            mImageLoader.closeCache();

        }

    }

    public static class GroupStarCardFragment extends
            Fragment {

        public static int cell_width = 0;
        private GridView gridview;
        private GroupStarImageGridViewAdapter mGroupStarImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GroupStarCardFragment mInstance;
        public static InterstitialAd interstitialAdGroupStarCardFragment;



        public GroupStarCardFragment() {
            mInstance = this;
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCacheParams cacheParams = new ImageCacheParams(getActivity(),
                    BROWSE_GROUP_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.setImageSize((ConfigData.SCREEN_WIDTH - 300) / 3);
            mImageLoader.setLoadingImage(null);
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);
        }

        /**
         * Reclaim memory and cancel all background task
         */
        public void restartCacheToClaimMemory() {
            // TODO Auto-generated method stub
            mImageLoader.clearMemCache();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
            mImageLoader.closeCache();
            createImageLoader();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            createImageLoader();

            View view = inflater.inflate(R.layout.fragment_gridcard, container,
                    false);

            mGroupStarImageGridViewAdapter = new GroupStarImageGridViewAdapter(view.getContext());

            gridview = (GridView) view.findViewById(R.id.gridview);
            gridview.setAdapter(mGroupStarImageGridViewAdapter);
            cell_width = (ConfigData.SCREEN_WIDTH - 40) / 3;
            gridview.setColumnWidth(cell_width);


            interstitialAdGroupStarCardFragment = new InterstitialAd(getContext());
            interstitialAdGroupStarCardFragment.setAdUnitId(getString(R.string.interstitial_ad_unit_test));
            AdRequest adInterstitial = new AdRequest.Builder().build();
            interstitialAdGroupStarCardFragment.loadAd(adInterstitial);

            interstitialAdGroupStarCardFragment.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    mGroupStarImageGridViewAdapter.startGroupStar(realPositionGroupSymbol);
                }
            });

            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mGroupStarImageGridViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPause() {
            super.onPause();
            mImageLoader.setPauseWork(false);
            mImageLoader.setExitTasksEarly(true);
            mImageLoader.flushCache();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mImageLoader.clearMemCache();
            if (ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON) {
                mImageLoader.clearDiskCache();
                ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = false;
            }
            mImageLoader.closeCache();

        }

    }


}
