package com.wakeup.tarot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CardBackAdapter extends RecyclerView.Adapter<CardBackAdapter.CardBackVH> {

    private Context mContext;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public CardBackAdapter(Context context) {
        this.mContext = context;
    }


    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CardBackVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CardBackVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_setting_items, parent, false));
    }

    @Override
    public void onBindViewHolder(CardBackVH viewHolder, int position) {

        int pos = position % this.mSliderItems.size();;

        Glide.with(viewHolder.itemView)
                .load(Integer.valueOf(Config.ing_back_card[pos]))
                .fitCenter()
                .into(viewHolder.imageGifContainer);

        setFadeAnimation(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getSize() {
        return this.mSliderItems.size();
    }

    public class CardBackVH extends RecyclerView.ViewHolder {

        ImageView imageGifContainer;

        public CardBackVH(View itemView) {
            super(itemView);
            imageGifContainer = itemView.findViewById(R.id.iv_setting_bg);
            imageGifContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("abcd", "getAdapterPosition == " + getAdapterPosition() % getSize());
                    Prefs.setCardBackground(getAdapterPosition() % getSize());
                }
            });

            imageGifContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                        imageGifContainer.startAnimation(ConfigData.animation_zoom_in);
                    } else if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                        imageGifContainer.clearAnimation();
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
