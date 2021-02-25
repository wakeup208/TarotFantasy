package com.wakeup.tarot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    private List<SliderItem> mCardBackItems = new ArrayList<>();
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public CardBackAdapter(Context context) {
        this.mContext = context;
    }


    public void renewItems(List<SliderItem> sliderItems) {
        this.mCardBackItems = sliderItems;
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

        int pos = position % this.mCardBackItems.size();;

        Glide.with(viewHolder.itemView)
                .load(Integer.valueOf(Config.ing_back_card[pos]))
                .fitCenter()
                .into(viewHolder.imgCardBack);

        setFadeAnimation(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getSize() {
        return this.mCardBackItems.size();
    }

    public class CardBackVH extends RecyclerView.ViewHolder {

        ImageView imgCardBack;

        public CardBackVH(View itemView) {
            super(itemView);
            imgCardBack = itemView.findViewById(R.id.iv_setting_bg);
            imgCardBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Prefs.setCardBackground(getAdapterPosition() % getSize());
                    Toast.makeText(view.getContext(), R.string.str_cardback, Toast.LENGTH_SHORT).show();
                }
            });

            imgCardBack.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                        imgCardBack.startAnimation(ConfigData.animation_zoom_in);
                    } else if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
                        imgCardBack.clearAnimation();
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
