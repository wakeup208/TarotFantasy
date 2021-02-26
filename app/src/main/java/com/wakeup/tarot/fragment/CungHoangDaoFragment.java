package com.wakeup.tarot.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wakeup.tarot.R;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.view.BrowseCardsActivity;
import com.wakeup.tarot.view.BrowseGroupCardsActivity;
import com.wakeup.tarot.view.InformationActivity;
import com.wakeup.tarot.view.KienThuc;

import org.jetbrains.annotations.NotNull;

public class CungHoangDaoFragment extends Fragment {

    Button btnClick;
    ImageView ivBackTb;
    ImageView background;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cung_hoang_dao, container, false);
        btnClick = (Button) view.findViewById(R.id.click_button);
        ivBackTb = (ImageView) view.findViewById(R.id.ivBackTb);
        background = (ImageView) view.findViewById(R.id.background);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.blink);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStar = new Intent(getActivity(),
                        BrowseGroupCardsActivity.class);

                Bundle b = new Bundle();
                b.putInt("select", 1);
                b.putInt("mode", 1);

                intentStar.putExtras(b);

                //intentStar.putExtra("mode", 1);
                //intentStar.putExtra("select", 1);

                getActivity().startActivity(intentStar);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_animation);

            }
        });
        btnClick.startAnimation(animation);

        if (ivBackTb != null) {
            ivBackTb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackClicked();
                }
            });
        }

        background.setBackground(getContext().getDrawable(Config.ing_app_bg[Prefs.getAppBackground(getContext())]));
    }

    public void onBackClicked() {
        if (getActivity() instanceof KienThuc) {
            getActivity().onBackPressed();
        }
    }
}
