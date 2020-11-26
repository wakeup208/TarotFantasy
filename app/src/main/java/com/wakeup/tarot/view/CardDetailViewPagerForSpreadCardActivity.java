package com.wakeup.tarot.view;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.CardsDetailJasonHelper;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.fragment.CardDetailForSpreadCardFragment;
import com.wakeup.tarot.util.ImageCache;
import com.wakeup.tarot.util.ImageLoaderAsynch;

public class CardDetailViewPagerForSpreadCardActivity extends FragmentActivity
        implements OnClickListener, ViewPager.OnPageChangeListener {

    private static final String VIEW_PAGER_IMAGE_CACHE_DIR = "extra_image_cache";

    // Visible mode
    // 1 for card info
    // 2 for card interpretation
    // 3 for card association
    private int visibleMode;
    public boolean isShowDetail = false;

    private TextView tvCardName;
    private RelativeLayout top_bar;
    private RelativeLayout bottom_bar;
    private Button btn_home_spread;
    private LinearLayout btn_card_spread;
    private LinearLayout btn_card_interpretation;
    private LinearLayout btn_associations;

    private ImageView card_spread;
    private ImageView card_interpretation;
    private ImageView associations;
    //private ImageView btn_shop;
    private int cardClickedIndex;

    private ImagePagerAdapter mAdapter;
    private ImageLoaderAsynch mImageLoader;
    private ViewPager mPager;

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager_spread_card);

        // Reload screen size and background
        //ConfigData.reloadScreen(this);
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this,
                VIEW_PAGER_IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
        // 25% of current available
        // memory

        // The ImageLoader takes care of loading images into our ImageView
        // children asynchronously
        mImageLoader = new ImageLoaderAsynch(this);
        mImageLoader.setLoadingImage(null);
        mImageLoader.addImageCache(getSupportFragmentManager(), cacheParams);
        mImageLoader.setImageFadeIn(false);

        // Load background
//        ((ImageView) findViewById(R.id.background))
//                .setBackground(ConfigData.rbdBackground);

        // get card show now in ViewPager
        cardClickedIndex = getIntent().getIntExtra("cardClickedIndex", 0);

        // Load component view from xml file to object

        // load top bar
        top_bar = (RelativeLayout) findViewById(R.id.top_bar);

        tvCardName = (TextView) findViewById(R.id.tvCardName);
        tvCardName.setTypeface(ConfigData.UVNCatBien_R);
        tvCardName
                .setText(CardsDetailJasonHelper
                        .getEnglishCardName(ConfigData.randomCardIdArray[cardClickedIndex]));

        btn_home_spread = (Button) findViewById(R.id.btn_home_spread);
        btn_home_spread.setHeight(tvCardName.getHeight());
        btn_home_spread.setWidth(tvCardName.getHeight());
        btn_home_spread.setOnClickListener(this);

        // load bottom bar
        bottom_bar = (RelativeLayout) findViewById(R.id.bottom_bar);

        card_spread = (ImageView) findViewById(R.id.card_spread1);
        card_interpretation = (ImageView) findViewById(R.id.card_interpretation1);
        associations = (ImageView) findViewById(R.id.associations1);

        btn_card_spread = (LinearLayout) findViewById(R.id.btn_card_spread1);
        btn_card_spread.setOnClickListener(this);

        btn_card_interpretation = (LinearLayout) findViewById(R.id.btn_card_interpretation1);
        btn_card_interpretation.setOnClickListener(this);

        btn_associations = (LinearLayout) findViewById(R.id.btn_associations1);
        btn_associations.setOnClickListener(this);

//        btn_shop = (Button) findViewById(R.id.btn_shop);
//        btn_shop.setOnClickListener(this);

        // Set up ViewPager and backing adapter
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), ConfigData.randomCardIdArray.length);
        mPager = (ViewPager) findViewById(R.id.Pager);
        mPager.addOnPageChangeListener(this);
        mPager.setAdapter(mAdapter);
        mPager.setPageMargin((int) getResources().getDimension(R.dimen.thin_padding));
        mPager.setOffscreenPageLimit(1);

        // Set the current item based on the extra passed in to this activity
        if (cardClickedIndex != -1) {
            mPager.setCurrentItem(cardClickedIndex);
        }

        // new CardImageDetailPagerApdapter_SpreadCard(this, mImageLoader);

        isShowDetail = false; // don't show detail
        visibleMode = 1; // show card spread info first

    }

    @Override
    public void onResume() {
        super.onResume();
        mImageLoader.setExitTasksEarly(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mImageLoader.setExitTasksEarly(true);
        mImageLoader.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageLoader.closeCache();
    }

    /**
     * Called by the ViewPager child fragments to load images via the one
     * ImageLoader
     */
    public ImageLoaderAsynch getImageLoader() {
        return mImageLoader;
    }

    /**
     * The main adapter that backs the ViewPager. A subclass of
     * FragmentStatePagerAdapter as there could be a large number of items in
     * the ViewPager and we don't want to retain them all in memory at once but
     * create/destroy them on the fly.
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return CardDetailForSpreadCardFragment.newInstance(position);
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = true;
        super.onBackPressed();
    }

    public void showTopAndBottomBar() {
        // TODO Auto-generated method stub
        top_bar.setVisibility(View.VISIBLE);
        bottom_bar.setVisibility(View.VISIBLE);
    }

    public void hideTopAndBottomBar() {
        // TODO Auto-generated method stub
        top_bar.setVisibility(View.INVISIBLE);
        bottom_bar.setVisibility(View.INVISIBLE);
    }

    public void updateShowHideDetailForViewPager() {

        for (int i = 0; i < mAdapter.getCount(); i++) {
            try {

                View view = mPager.getChildAt(i);

                ScrollView svCardSpread = (ScrollView) view
                        .findViewById(R.id.svCardSpread);
                ScrollView svCardInterpretation = (ScrollView) view
                        .findViewById(R.id.svCardInterpretation);
                ScrollView svCardAssociations = (ScrollView) view
                        .findViewById(R.id.svCardAssociations);

                // Show ViewMode
                if (isShowDetail) {
                    showTopAndBottomBar();
                    if (visibleMode == 1) {
                        svCardSpread.setVisibility(View.VISIBLE);
                        svCardInterpretation.setVisibility(View.INVISIBLE);
                        svCardAssociations.setVisibility(View.INVISIBLE);
                    }
                    if (visibleMode == 2) {
                        svCardSpread.setVisibility(View.INVISIBLE);
                        svCardInterpretation.setVisibility(View.VISIBLE);
                        svCardAssociations.setVisibility(View.INVISIBLE);
                    }
                    if (visibleMode == 3) {
                        svCardSpread.setVisibility(View.INVISIBLE);
                        svCardInterpretation.setVisibility(View.INVISIBLE);
                        svCardAssociations.setVisibility(View.VISIBLE);
                    }
                } else {
                    hideTopAndBottomBar();
                    svCardSpread.setVisibility(View.INVISIBLE);
                    svCardInterpretation.setVisibility(View.INVISIBLE);
                    svCardAssociations.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.btn_home_spread:
                ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = true;
                this.finish();
                break;

            case R.id.btn_card_spread1:
                card_spread
                        .setImageResource(R.drawable.ic_grid_press);
                card_interpretation
                        .setImageResource(R.drawable.btn_card_interpretation);
                associations.setImageResource(R.drawable.btn_associations);
                //btn_shop.setBackgroundResource(R.drawable.btn_shop);
                visibleMode = 1;
                updateShowHideDetailForViewPager();
                // other process below here

                break;

            case R.id.btn_card_interpretation1:
                card_spread.setImageResource(R.drawable.btn_card_spread);
                card_interpretation
                        .setImageResource(R.drawable.ic_details_press);
                associations.setImageResource(R.drawable.btn_associations);
                //btn_shop.setBackgroundResource(R.drawable.btn_shop);
                visibleMode = 2;
                updateShowHideDetailForViewPager();
                // other process below here

                break;

            case R.id.btn_associations1:
                card_spread.setImageResource(R.drawable.btn_card_spread);
                card_interpretation
                        .setImageResource(R.drawable.btn_card_interpretation);
                associations
                        .setImageResource(R.drawable.ic_book_press);
                //btn_shop.setBackgroundResource(R.drawable.btn_shop);
                visibleMode = 3;
                updateShowHideDetailForViewPager();
                // other process below here

                break;

//            case R.id.btn_shop:
//                btn_card_spread.setBackgroundResource(R.drawable.btn_card_spread);
//                btn_card_interpretation
//                        .setBackgroundResource(R.drawable.btn_card_interpretation);
//                btn_associations.setBackgroundResource(R.drawable.btn_associations);
//                // other process below here
//
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    public void btnCardAssociationClicked() {
        card_spread.setImageResource(R.drawable.btn_card_spread);
        card_interpretation
                .setImageResource(R.drawable.btn_card_interpretation);
        associations
                .setImageResource(R.drawable.ic_book_press);
        //btn_shop.setBackgroundResource(R.drawable.btn_shop);
        visibleMode = 3;
        updateShowHideDetailForViewPager();
    }

    public void btnCardInterpretationClicked() {
        card_spread.setImageResource(R.drawable.btn_card_spread);
        card_interpretation
                .setImageResource(R.drawable.ic_details_press);
        associations.setImageResource(R.drawable.btn_associations);
        //btn_shop.setBackgroundResource(R.drawable.btn_shop);
        visibleMode = 2;
        updateShowHideDetailForViewPager();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int new_position, float arg1, int arg2) {
        mImageLoader.clearMemCache();
    }

    @Override
    public void onPageSelected(int arg0) {
        mImageLoader.clearMemCache();
        tvCardName.setText(CardsDetailJasonHelper.getEnglishCardName(ConfigData.randomCardIdArray[mPager.getCurrentItem()]));
        updateShowHideDetailForViewPager();
        System.gc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encyclopedia_menu, menu);
        return true;
    }

}
