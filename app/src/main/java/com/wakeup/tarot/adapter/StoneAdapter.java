package com.wakeup.tarot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.wakeup.tarot.R;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.view.AllStoneInfo;

import java.util.ArrayList;
import java.util.List;

public class StoneAdapter extends SliderViewAdapter<StoneAdapter.StoneAdapterVH>{

    private Context context;
    private AllStoneInfo mainActivity;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public StoneAdapter(Context context, AllStoneInfo mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    @Override
    public StoneAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new StoneAdapter.StoneAdapterVH(inflate);
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StoneAdapterVH viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);
        viewHolder.textViewDescription.setText(Config.stoneText[position]);
        viewHolder.textViewDetails.setText(Config.StoneDes.get(position));

        viewHolder.textViewDetails.setTextSize(16);
        viewHolder.textViewDetails.setTextColor(Color.WHITE);
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(Integer.valueOf(Config.img_stone[position]))
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public class StoneAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;
        TextView textViewDetails;

        public StoneAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            textViewDetails = itemView.findViewById(R.id.txtDes);
            this.itemView = itemView;
        }
    }
}
