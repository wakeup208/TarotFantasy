package com.wakeup.tarot.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.SliderAdapter;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;

import java.util.ArrayList;
import java.util.List;

public class DaThanhTayInfor extends BaseActivity {
    private SliderView sliderView;
    private SliderAdapter adapter;
    private String des;
    private RelativeLayout background;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public void refreshCardBack() {

    }

    @Override
    public void refreshAppBg() {
        background.setBackground(getDrawable(Config.ing_app_bg[Prefs.getAppBackground(this)]));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //refreshAppBg();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.da_thanh_tay);

        sliderView = findViewById(R.id.imageSlider);
        background = (RelativeLayout) findViewById(R.id.rlDaThanhTay);

        adapter = new SliderAdapter(this, this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
//        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
            }
        });

        List<SliderItem> sliderItemList = new ArrayList<>();
        for (int i = 0; i < Config.img_cung_hoang_dao.length; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            sliderItem.setInteger(Integer.valueOf(Config.img_cung_hoang_dao[i]));
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }
}
