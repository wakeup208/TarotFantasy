package com.wakeup.tarot.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.StoneAdapter;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.util.Config;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

public class AllStoneInfo extends BaseActivity{
    SliderView sliderView;
    private StoneAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.da_thanh_tay);

        sliderView = findViewById(R.id.imageSlider);

        adapter = new StoneAdapter(this, this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
//        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.d("abcd", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        List<SliderItem> sliderItemList = new ArrayList<>();
        for (int i = 0; i < Config.img_stone.length; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            sliderItem.setInteger(Integer.valueOf(Config.img_stone[i]));
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }
}
