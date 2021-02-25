package com.wakeup.tarot.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.view.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppBackgroundAdater extends RecyclerView.Adapter<AppBackgroundAdater.AppBackgroundVH> {

    private Context mContext;
    private ProfileActivity mActivity;

    private List<SliderItem> mAppBackgroundList = new ArrayList<>();
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public AppBackgroundAdater(ProfileActivity context) {
        this.mActivity =  context;
    }

    @NonNull
    @Override
    public AppBackgroundVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new AppBackgroundAdater.AppBackgroundVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_setting_items, parent, false));    }

    @Override
    public void onBindViewHolder(@NonNull AppBackgroundVH viewHolder, int position) {
        int pos = position % this.mAppBackgroundList.size();;

        Glide.with(viewHolder.itemView)
                .load(Integer.valueOf(Config.ing_app_bg[pos]))
                .fitCenter()
                .into(viewHolder.imgAppBg);

        setFadeAnimation(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getSize() {
        return this.mAppBackgroundList.size();    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mAppBackgroundList = sliderItems;
        notifyDataSetChanged();
    }

    public class AppBackgroundVH extends RecyclerView.ViewHolder {

        ImageView imgAppBg;

        public AppBackgroundVH(View itemView) {
            super(itemView);
            imgAppBg = itemView.findViewById(R.id.iv_setting_bg);
            imgAppBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Prefs.setAppBackground(getAdapterPosition() % getSize());
                    mActivity.refreshAppBg();
                }
            });

            imgAppBg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                        imgAppBg.startAnimation(ConfigData.animation_zoom_in);
                    } else if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                        imgAppBg.clearAnimation();
                    }
                    return false;
                }
            });
        }
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
