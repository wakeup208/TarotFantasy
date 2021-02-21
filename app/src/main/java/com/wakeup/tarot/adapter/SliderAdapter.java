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
import com.wakeup.tarot.view.DaThanhTayInfor;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private DaThanhTayInfor mainActivity;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context, DaThanhTayInfor mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);
        viewHolder.textViewDescription.setText(Config.cunghoangdao[position]);
        viewHolder.textViewDetails.setText(Config.CunghoangdaoAndDathanhtay.get(position));
        
        viewHolder.textViewDetails.setTextSize(16);
        viewHolder.textViewDetails.setTextColor(Color.WHITE);
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
//        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImageUrl())
//                .load(Integer.valueOf(sliderItem.getInteger()))
//                .fitCenter()
//                .into(viewHolder.imageViewBackground);

        Glide.with(viewHolder.itemView)
//                .load(sliderItem.getImageUrl())
                .load(Integer.valueOf(Config.img_cung_hoang_dao[position]))
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
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;
        TextView textViewDetails;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            textViewDetails = itemView.findViewById(R.id.txtDes);

            this.itemView = itemView;
        }
    }

}
