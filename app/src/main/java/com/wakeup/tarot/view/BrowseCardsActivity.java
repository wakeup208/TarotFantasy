package com.wakeup.tarot.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.CardImageSectionListViewAdapter;
import com.wakeup.tarot.adapter.GroupCardImageGridViewAdapter;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.util.ImageCache;
import com.wakeup.tarot.util.ImageLoaderAsynch;
import com.wakeup.tarot.adapter.CardImageGridViewAdapter;

import org.jetbrains.annotations.NotNull;


public class BrowseCardsActivity extends FragmentActivity implements
        OnClickListener {

    public Context mContext;
    private static final String BROWSE_CARD_IMAGE_CACHE_DIR = "browse_card_image_cache";

    public static BrowseCardsActivity instance;
    private TextView tvTitle;
    private ImageView btn_grid;
    private ImageView btn_list;
    private ImageView btn_associations;
    private ImageView btn_minus;
    private ImageView btn_plus;
    private ImageButton btnHome;


    private LinearLayout ln_btn_grid;
    private LinearLayout ln_btn_list;
    private LinearLayout ln_btn_associations;
    private LinearLayout ln_btn_minus;
    private LinearLayout ln_btn_plus;
    private int selected = 0;

    // Fragment to show Browse mode
    GirdCardFragment mGridCardFragment;
    ListViewCardFragment mListViewCardFragment;
    GoupCardFragment mGoupCardFragment;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cards);
        instance = this;

        // Reload screen size and background
        //ConfigData.reloadScreen(this);

        // Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackgroundDrawable(ConfigData.rbdBackground);

        mContext = this.getApplicationContext();

        try{
            Bundle b = getIntent().getExtras();
            selected = b.getInt("selected");
        } catch(Exception ex){
            selected = 0;
        }

        //selected = this.getIntent().getExtras().getInt("selected", 0);

        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvTitle.setTypeface(ConfigData.UVNCatBien_R);

        this.ln_btn_grid = (LinearLayout) findViewById(R.id.ln_btn_grid);
        ln_btn_grid.setOnClickListener(this);
        btn_grid = (ImageView) findViewById(R.id.btn_grid);
        //btn_grid.setOnClickListener(this);

        ln_btn_list = (LinearLayout) findViewById(R.id.ln_btn_list);
        ln_btn_list.setOnClickListener(this);
        btn_list = (ImageView) findViewById(R.id.btn_list);
        //btn_list.setOnClickListener(this);

        ln_btn_associations = (LinearLayout) findViewById(R.id.ln_btn_associations);
        ln_btn_associations.setOnClickListener(this);
        btn_associations = (ImageView) findViewById(R.id.btn_associations);
        //btn_associations.setOnClickListener(this);

        ln_btn_minus = (LinearLayout) findViewById(R.id.ln_btn_minus);
        ln_btn_minus.setOnClickListener(this);
        btn_minus = (ImageView) findViewById(R.id.btn_minus);
        //btn_minus.setOnClickListener(this);

        ln_btn_plus = (LinearLayout) findViewById(R.id.ln_btn_plus);
        ln_btn_plus.setOnClickListener(this);
        btn_plus = (ImageView) findViewById(R.id.btn_plus);

        btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setOnClickListener(this);
        //btn_plus.setOnClickListener(this);

        // Replace container by mGridCardFragment
        selectBrowserMode(ConfigData.BROWSER_MODE);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = true;
        //startActivity(new Intent(this, InformationActivity.class));

        Intent intentStar = new Intent(this,
                InformationActivity.class);
        //intentStar.putExtra("mode_tools", 0);
        this.startActivity(intentStar);
        //moveTaskToBack(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
        Log.d("abcd", "onDestroy");
    }

    @Override
    protected void onResume() {
        // Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);
        super.onResume();
    }

    /**
     * select browser view mode
     *
     * @param mode
     */
    public void selectBrowserMode(int mode) {
        ConfigData.BROWSER_MODE = mode;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (ConfigData.BROWSER_MODE) {
            case 0: // Grid view mode
                btn_grid.setImageResource(R.drawable.ic_grid_press);
                btn_list.setImageResource(R.drawable.ic_grid_list);
                btn_associations.setImageResource(R.drawable.ic_grid_book);
                btn_plus.setImageResource(R.drawable.ic_grid_plus);
                btn_plus.setEnabled(true);
                ln_btn_plus.setEnabled(true);
                btn_minus.setImageResource(R.drawable.ic_grid_minus);
                btn_minus.setEnabled(true);
                ln_btn_minus.setEnabled(true);
                if (ConfigData.ZOOM_LEVEL == 3) {
                    btn_plus.setImageResource(R.drawable.ic_grid_plus_dim);
                    btn_plus.setEnabled(false);
                    btn_minus.setImageResource(R.drawable.ic_grid_minus);
                    btn_minus.setEnabled(true);
                    ln_btn_minus.setEnabled(true);
                }

                if (ConfigData.ZOOM_LEVEL == 0) {
                    btn_minus.setImageResource(R.drawable.ic_grid_miuns_dim);
                    btn_minus.setEnabled(false);
                    btn_plus.setImageResource(R.drawable.ic_grid_plus);
                    btn_plus.setEnabled(true);
                    ln_btn_plus.setEnabled(true);
                }

                if (mGridCardFragment == null) {
                    mGridCardFragment = new GirdCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGridCardFragment).commit();
                break;
            case 1: // List view mode
                btn_grid.setImageResource(R.drawable.ic_grid_selected);
                btn_list.setImageResource(R.drawable.ic_list_press);
                btn_associations.setImageResource(R.drawable.ic_grid_book);
                btn_plus.setImageResource(R.drawable.ic_grid_plus_dim);
                btn_plus.setEnabled(false);
                ln_btn_plus.setEnabled(false);
                btn_minus.setImageResource(R.drawable.ic_grid_miuns_dim);
                btn_minus.setEnabled(false);
                ln_btn_minus.setEnabled(false);
//			btn_plus.setVisibility(View.INVISIBLE);
//			btn_minus.setVisibility(View.INVISIBLE);
                if (mListViewCardFragment == null) {
                    mListViewCardFragment = new ListViewCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mListViewCardFragment)
                        .commit();
                break;
            case 2: // Group card mode
                btn_grid.setImageResource(R.drawable.ic_grid_selected);
                btn_list.setImageResource(R.drawable.ic_grid_list);
                btn_associations
                        .setImageResource(R.drawable.ic_book_press);
//			btn_plus.setVisibility(View.INVISIBLE);
//			btn_minus.setVisibility(View.INVISIBLE);

                btn_plus.setImageResource(R.drawable.ic_grid_plus_dim);
                btn_plus.setEnabled(false);
                ln_btn_plus.setEnabled(false);

                btn_minus.setImageResource(R.drawable.ic_grid_miuns_dim);
                btn_minus.setEnabled(false);
                ln_btn_minus.setEnabled(false);

                if (mGoupCardFragment == null) {
                    mGoupCardFragment = new GoupCardFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.browse_container, mGoupCardFragment).commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encyclopedia_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_home:
                if (selected == 0) {
                    this.finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    selected = 0;
                    this.finish();
                    startActivity(new Intent(this, InformationActivity.class));
                }

                break;

            case R.id.ln_btn_grid:
                // other process below here
                selectBrowserMode(0);
                break;

            case R.id.ln_btn_list:
                // other process below here
                selectBrowserMode(1);
                break;

            case R.id.ln_btn_associations:
                // other process below here
                selectBrowserMode(2);
                break;

            case R.id.ln_btn_minus:
                if (mGridCardFragment.zoomGridView('-') == false) {
                    btn_minus.setImageResource(R.drawable.ic_grid_miuns_dim);
                    btn_minus.setEnabled(false);
                    btn_plus.setImageResource(R.drawable.ic_grid_plus);
                    btn_plus.setEnabled(true);
                } else {
                    btn_plus.setImageResource(R.drawable.ic_grid_plus);
                    btn_plus.setEnabled(true);
                    btn_minus.setImageResource(R.drawable.ic_grid_minus);
                    btn_minus.setEnabled(true);
                }
                break;

            case R.id.ln_btn_plus:
                if (mGridCardFragment.zoomGridView('+') == false) {
                    btn_plus.setImageResource(R.drawable.ic_grid_plus_dim);
                    btn_plus.setEnabled(false);
                    btn_minus.setImageResource(R.drawable.ic_grid_minus);
                    btn_minus.setEnabled(true);
                } else {

                    btn_plus.setImageResource(R.drawable.ic_grid_plus);
                    btn_plus.setEnabled(true);
                    btn_minus.setImageResource(R.drawable.ic_grid_minus);
                    btn_minus.setEnabled(true);
                }
                break;
        }
    }

    public static class GirdCardFragment extends
            Fragment {

        private int cell_width = 0;
        private int mNumColumns;
        private GridView mGridView;
        public CardImageGridViewAdapter mCardImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GirdCardFragment mInstance;
        public static int imageWidth;
        public static int imageHeight;


        /**
         * Empty constructor as per the Fragment documentation
         */
        public GirdCardFragment() {
            mInstance = this;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            createImageLoader();

            mCardImageGridViewAdapter = new CardImageGridViewAdapter(
                    getActivity());

            super.onCreate(savedInstanceState);
        }

        private void createImageLoader() {
            //
            // TODO INIT FOR IMAGE CACHE
            ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(getActivity(),
                    BROWSE_CARD_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);

            // Set Image size
            mInstance.calculateCellWidth();
            imageWidth = cell_width - 5;
            imageHeight = imageWidth * 1232 / 710;
        }

        /**
         * Reclaim memory and cancel all background task
         */
        public void restartCacheToClaimMemory() {
            // TODO Auto-generated method stub
            try {
                mImageLoader.setExitTasksEarly(true);
                mImageLoader.clearMemCache();
                mImageLoader.flushCache();
                mImageLoader.closeCache();
            } catch (Exception e) {
            }

            createImageLoader();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View view = inflater.inflate(R.layout.fragment_gridcard,
                    container, false);

            mGridView = (GridView) view.findViewById(R.id.gridview);
//            mGridView.setAdapter(mCardImageGridViewAdapter);
//            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView absListView,
//                                                 int scrollState) {
//                    // Pause fetcher to ensure smoother scrolling when flinging
//                    // if (scrollState ==
//                    // AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                    // mImageLoader.setPauseWork(true);
//                    // } else {
//                    // mImageLoader.setPauseWork(false);
//                    // }
//                }
//
//                @Override
//                public void onScroll(AbsListView absListView,
//                                     int firstVisibleItem, int visibleItemCount,
//                                     int totalItemCount) {
//                }
//            });
//
//            // This listener is used to get the final width of the GridView and
//            // then calculate the
//            // number of columns and the width of each column. The width of each
//            // column is variable
//            // as the GridView has stretchMode=columnWidth. The column width is
//            // used to set the height
//            // of each view so we get nice card.
//            mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
//                    new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                        @Override
//                        public void onGlobalLayout() {
//                            // TODO Auto-generated method stub
//                            if (mCardImageGridViewAdapter.getNumColumns() == 0) {
//                                // compute cell_width base on zoom_level
//                                calculateCellWidth();
//
//                                mNumColumns = (int) Math.floor(mGridView
//                                        .getWidth() / (cell_width + 5));
//
//                                if (mNumColumns > 0) {
//                                    mCardImageGridViewAdapter
//                                            .setNumColumns(mNumColumns);
//
//                                    mCardImageGridViewAdapter
//                                            .setTopBarHeight(BrowseCardsActivity.instance
//                                                    .findViewById(R.id.tvTitle)
//                                                    .getHeight());
//
//                                    mCardImageGridViewAdapter
//                                            .setBottomBarHeight(BrowseCardsActivity.instance
//                                                    .findViewById(
//                                                            R.id.bottom_bar)
//                                                    .getHeight());
//
//                                    mGridView.setNumColumns(mNumColumns);
//                                }
//                            }
//                        }
//                    });

            return view;
        }

        @Override
        public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mGridView.setAdapter(mCardImageGridViewAdapter);
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView,
                                                 int scrollState) {
                    // Pause fetcher to ensure smoother scrolling when flinging
                    // if (scrollState ==
                    // AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // mImageLoader.setPauseWork(true);
                    // } else {
                    // mImageLoader.setPauseWork(false);
                    // }
                }

                @Override
                public void onScroll(AbsListView absListView,
                                     int firstVisibleItem, int visibleItemCount,
                                     int totalItemCount) {
                }
            });

            // This listener is used to get the final width of the GridView and
            // then calculate the
            // number of columns and the width of each column. The width of each
            // column is variable
            // as the GridView has stretchMode=columnWidth. The column width is
            // used to set the height
            // of each view so we get nice card.
            mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            // TODO Auto-generated method stub
                            if (mCardImageGridViewAdapter.getNumColumns() == 0) {
                                // compute cell_width base on zoom_level
                                calculateCellWidth();

                                if (mGridView
                                        .getWidth() != 0) {

                                    mNumColumns = (int) Math.floor(mGridView
                                            .getWidth() / (cell_width + 5));
                                }


                                if (mNumColumns > 0) {
                                    mCardImageGridViewAdapter
                                            .setNumColumns(mNumColumns);

                                    mCardImageGridViewAdapter
                                            .setTopBarHeight(BrowseCardsActivity.instance
                                                    .findViewById(R.id.tvTitle)
                                                    .getHeight());

                                    mCardImageGridViewAdapter
                                            .setBottomBarHeight(BrowseCardsActivity.instance
                                                    .findViewById(
                                                            R.id.bottom_bar)
                                                    .getHeight());

                                    mGridView.setNumColumns(mNumColumns);
                                }
                            }
                        }
                    });
        }

        /**
         * Get width of grid view
         *
         * @return the integer number
         */
        public int calculateCellWidth() {
            switch (ConfigData.ZOOM_LEVEL) {
                case 0: // 8 Columns portrait
                    cell_width = (ConfigData.SCREEN_WIDTH - 9 * 5) / 8;
                    break;
                case 1: // 5 Columns portrait
                    cell_width = (ConfigData.SCREEN_WIDTH - 6 * 5) / 5;
                    break;
                case 2: // 3 Columns portrait
                    cell_width = (ConfigData.SCREEN_WIDTH - 4 * 5) / 3;
                    break;
                case 3: // 2 Columns portrait
                    cell_width = (ConfigData.SCREEN_WIDTH - 3 * 5) / 2;
                    break;
            }

            return cell_width;
        }

        /**
         * Zoom gridView by ZoomIn: mode == '-' ZoomOut mode == '+'
         *
         * @return false: if need disable button fire zoom event true: otherwise
         */
        public boolean zoomGridView(char mode) {

            // Clear cache to reclaim memory
            restartCacheToClaimMemory();

            // Change zoom level after apply new Zoom event
            if (mode == '+') {
                if (ConfigData.ZOOM_LEVEL < 3) {
                    ConfigData.ZOOM_LEVEL++;
                }
            } else { // mode == '-'
                if (ConfigData.ZOOM_LEVEL > 0) {
                    ConfigData.ZOOM_LEVEL--;
                }
            }

            // Calculate and reset Image size
            cell_width = calculateCellWidth();
            Log.d("abcd", "cell_width ==== " + cell_width);

            imageWidth = cell_width - 5;
            imageHeight = imageWidth * 1232 / 710;

            // Calculate the number of columns in new zoom mode
            mNumColumns = (int) Math.floor(mGridView.getWidth()
                    / (cell_width + 5));

            // Update the number of Columns in a line of gridView
            mCardImageGridViewAdapter.setNumColumns(mNumColumns);
            mCardImageGridViewAdapter
                    .setTopBarHeight(BrowseCardsActivity.instance.findViewById(
                            R.id.tvTitle).getHeight());

            // refresh gridView
            mGridView.setNumColumns(mNumColumns);
            mGridView.setColumnWidth(cell_width);
            mGridView.refreshDrawableState();

            if (ConfigData.ZOOM_LEVEL <= 0) {
                return false;
            } else if (ConfigData.ZOOM_LEVEL > 0 && ConfigData.ZOOM_LEVEL < 3) {
                return true;
            } else {
                return false;
            }

        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mCardImageGridViewAdapter.notifyDataSetChanged();
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

    public static class ListViewCardFragment extends
            Fragment implements OnItemClickListener {

        private ListView mListView;
        private CardImageSectionListViewAdapter mCardImageSectionListViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static ListViewCardFragment mInstance;

        /**
         * Empty constructor as per the Fragment documentation
         */
        public ListViewCardFragment() {
            mInstance = this;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            createImageLoader();

            mCardImageSectionListViewAdapter = new CardImageSectionListViewAdapter(
                    getActivity());

            super.onCreate(savedInstanceState);
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(getActivity(),
                    BROWSE_CARD_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);

            // Set Image size
            mImageLoader.setImageSize(ConfigData.SCREEN_WIDTH / 6,
                    ConfigData.SCREEN_WIDTH / 6 * 1232 / 710);

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

            View view = inflater.inflate(R.layout.fragment_listcard, container,
                    false);

            mListView = (ListView) view.findViewById(R.id.lvTarotSpread);
            mListView.setOnItemClickListener(this);
            mListView.setAdapter(mCardImageSectionListViewAdapter);
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView,
                                                 int scrollState) {
                    // // Pause fetcher to ensure smoother scrolling when
                    // flinging
                    // if (scrollState ==
                    // AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // mImageLoader.setPauseWork(true);
                    // } else {
                    // mImageLoader.setPauseWork(false);
                    // }
                }

                @Override
                public void onScroll(AbsListView absListView,
                                     int firstVisibleItem, int visibleItemCount,
                                     int totalItemCount) {
                }
            });

            // This listener is used to get the final width of the GridView and
            // then calculate the
            // number of columns and the width of each column. The width of each
            // column is variable
            // as the GridView has stretchMode=columnWidth. The column width is
            // used to set the height
            // of each view so we get nice card.
            mListView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            // TODO Auto-generated method stub
                            mCardImageSectionListViewAdapter
                                    .setTopBarHeight(BrowseCardsActivity.instance
                                            .findViewById(R.id.tvTitle)
                                            .getHeight());

                            mCardImageSectionListViewAdapter
                                    .setBottomBarHeight(BrowseCardsActivity.instance
                                            .findViewById(R.id.bottom_bar)
                                            .getHeight());

                        }
                    });

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int position,
                                long arg3) {
            mCardImageSectionListViewAdapter.showDetailViewPager(position);
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mCardImageSectionListViewAdapter.notifyDataSetChanged();
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

    public static class GoupCardFragment extends
            Fragment {

        private int cell_width = 0;
        private GridView mGridView;
        private GroupCardImageGridViewAdapter mGroupCardImageGridViewAdapter;
        public static ImageLoaderAsynch mImageLoader;
        public static GoupCardFragment mInstance;

        /**
         * Empty constructor as per the Fragment documentation
         */
        public GoupCardFragment() {
            mInstance = this;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            createImageLoader();

            mGroupCardImageGridViewAdapter = new GroupCardImageGridViewAdapter(
                    getActivity());

            super.onCreate(savedInstanceState);
        }

        private void createImageLoader() {
            // TODO INIT FOR IMAGE CACHE
            ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(getActivity(),
                    BROWSE_CARD_IMAGE_CACHE_DIR);

            cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
            // 25% of app memory

            // The ImageFetcher takes care of loading images into our ImageView
            // children asynchronously
            mImageLoader = new ImageLoaderAsynch(getActivity());
            mImageLoader.addImageCache(getActivity()
                    .getSupportFragmentManager(), cacheParams);
            mImageLoader.setImageFadeIn(false);
            // Set Image size
            mImageLoader.setImageSize(getCellWidth() - 5);

            mImageLoader.setLoadingImage(null);

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

            final View view = inflater.inflate(R.layout.fragment_gridcard,
                    container, false);

            mGridView = (GridView) view.findViewById(R.id.gridview);
            mGridView.setAdapter(mGroupCardImageGridViewAdapter);
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView,
                                                 int scrollState) {
                    // // Pause fetcher to ensure smoother scrolling when
                    // flinging
                    // if (scrollState ==
                    // AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // mImageLoader.setPauseWork(true);
                    // } else {
                    // mImageLoader.setPauseWork(false);
                    // }
                }

                @Override
                public void onScroll(AbsListView absListView,
                                     int firstVisibleItem, int visibleItemCount,
                                     int totalItemCount) {
                }
            });

            // This listener is used to get the final width of the GridView and
            // then calculate the
            // number of columns and the width of each column. The width of each
            // column is variable
            // as the GridView has stretchMode=columnWidth. The column width is
            // used to set the height
            // of each view so we get nice card.
            mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            // TODO Auto-generated method stub
                            int mNumColumns = 2;
                            mGroupCardImageGridViewAdapter
                                    .setNumColumns(mNumColumns);

                            mGroupCardImageGridViewAdapter
                                    .setTopBarHeight(BrowseCardsActivity.instance
                                            .findViewById(R.id.tvTitle)
                                            .getHeight());

                            mGroupCardImageGridViewAdapter
                                    .setBottomBarHeight(BrowseCardsActivity.instance
                                            .findViewById(R.id.bottom_bar)
                                            .getHeight());

                            mGridView.setNumColumns(mNumColumns);
                        }
                    });

            return view;
        }

        /**
         * Get width of grid view
         *
         * @return the integer number
         */
        public int getCellWidth() {
            cell_width = (ConfigData.SCREEN_WIDTH - 40) / 3;
            return cell_width;
        }

        @Override
        public void onResume() {
            super.onResume();
            mImageLoader.setExitTasksEarly(false);
            mGroupCardImageGridViewAdapter.notifyDataSetChanged();
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
