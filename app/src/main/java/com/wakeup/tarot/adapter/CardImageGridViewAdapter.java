package com.wakeup.tarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.fragment.RecyclingImageView;
import com.wakeup.tarot.view.BrowseCardsActivity;
import com.wakeup.tarot.view.CardDetailViewPagerForBrowserCardActivity;

public class CardImageGridViewAdapter extends BaseAdapter implements
        OnTouchListener, OnClickListener {

    private int imageEmptyCount;
    private Context mContext;
    private int mNumColumns = 0;
    private int mTopBarHeight = 0;
    private int mBottomBarHeight = 0;

    public int getNumColumns() {
        return mNumColumns;
    }

    public int getTopBarHeight() {
        return mTopBarHeight;
    }

    public void setNumColumns(int mNumColumns) {
        this.mNumColumns = mNumColumns;
    }

    public void setTopBarHeight(int topBarHeight) {
        this.mTopBarHeight = topBarHeight;
    }

    public void setBottomBarHeight(int bottomBarHeight) {
        if (bottomBarHeight > mTopBarHeight) {
            this.mBottomBarHeight = mTopBarHeight + 10;
        } else {
            this.mBottomBarHeight = bottomBarHeight;
        }
    }

    public CardImageGridViewAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        // the first mNumColumns for the start empty row
        // the second mNumColumns for the end empty row
        return mNumColumns + MapData.arrCardImage_R_Id.length + mNumColumns;
    }

    @Override
    public Object getItem(int position) {

        if (position - mNumColumns >= 0 && position - mNumColumns - MapData.arrCardImage_R_Id.length < 0) {
            return MapData.arrCardImage_R_Id[position - mNumColumns];
        }

        return null;
    }

    @Override
    public long getItemId(int position) {

        if (position - mNumColumns >= 0 && position - mNumColumns - MapData.arrCardImage_R_Id.length < 0) {
            return position - mNumColumns;
        }

        return 0;
    }

    @Override
    public int getViewTypeCount() {
        // Two types of views, the normal ImageView and the top row of empty
        // views
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position - mNumColumns < 0) {
            return 0;
        }

        if (position - mNumColumns - MapData.arrCardImage_R_Id.length < 0) {
            return 1;
        }

        imageEmptyCount = mNumColumns - (MapData.arrCardImage_R_Id.length % mNumColumns);
        if (position - mNumColumns - MapData.arrCardImage_R_Id.length - imageEmptyCount < 0) {
            return 2;
        }

        return 3;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        int w = BrowseCardsActivity.GirdCardFragment.imageWidth;
        int h = BrowseCardsActivity.GirdCardFragment.imageHeight;

        // First check if this is the top row
        if (position - mNumColumns < 0) {

            if (convertView == null) {
                convertView = new View(mContext);
            }
            // Set empty view with height of TopBar
            convertView.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, mTopBarHeight));

            convertView.setOnClickListener(null);
            convertView.setOnLongClickListener(null);
            convertView.setLongClickable(false);
            convertView.setOnTouchListener(null);
            return convertView;
        }

        // Second check if this is the bottom row
        if (position - mNumColumns - MapData.arrCardImage_R_Id.length >= 0) {

            if (convertView == null) {
                convertView = new View(mContext);
            }

            imageEmptyCount = mNumColumns - (MapData.arrCardImage_R_Id.length % mNumColumns);


            if (position - mNumColumns - MapData.arrCardImage_R_Id.length - imageEmptyCount < 0 &&
                    imageEmptyCount < mNumColumns) {
                // Set empty image view
                convertView.setLayoutParams(new GridView.LayoutParams(w, h));
            } else {
                // Set empty view with height of BototmBar
                convertView.setLayoutParams(new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, mBottomBarHeight));
            }

            convertView.setOnClickListener(null);
            convertView.setOnLongClickListener(null);
            convertView.setLongClickable(false);
            convertView.setOnTouchListener(null);

            return convertView;
        }

        // Now handle the main ImageView thumbnails
        ImageView imageView;
//        To prevent reuse image has size nolonger suitable need to create new all cell 
//        if (convertView == null) { // if it's not recycled, instantiate and initialize
        imageView = new RecyclingImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(w, h));
        imageView.setOnTouchListener(this);
        imageView.setOnClickListener(this);
//        } else { // Otherwise re-use the converted view
//            imageView = (ImageView) convertView;
//        }

        // Finally load the image asynchronously into the ImageView, this also takes care of
        // setting a placeholder image while the background thread runs
        BrowseCardsActivity.GirdCardFragment.mImageLoader.loadImage(MapData.arrCardImage_R_Id[position - mNumColumns] + "_" + w + "_" + h, imageView);


        imageView.setTag(position - mNumColumns);
        return imageView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.startAnimation(ConfigData.animation_button_press);
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        // Reclaim memory and cancel all background task
        BrowseCardsActivity.GirdCardFragment.mInstance.restartCacheToClaimMemory();

        int realPosition = Integer.parseInt(v.getTag().toString());

        // Show card view pager
        Intent intentCardViewPager = new Intent(mContext,
                CardDetailViewPagerForBrowserCardActivity.class);
        intentCardViewPager.putExtra("position", realPosition);
        mContext.startActivity(intentCardViewPager);
    }
}