package com.wakeup.tarot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.wakeup.tarot.R;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.view.BrowseCardsActivity;
import com.wakeup.tarot.view.BrowseGroupCardsActivity;
import com.wakeup.tarot.view.InformationActivity;
import com.wakeup.tarot.view.KienThuc;

public class TypeCardPageMeanFragment extends Fragment {

    Button btnClick;
    ImageView ivBackTb;
    ImageView background;
    BrowseGroupCardsActivity.GroupStarCardFragment mGroupStarCardFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_card_page_mean, container, false);
        btnClick = (Button) view.findViewById(R.id.click_button);
        ivBackTb = (ImageView) view.findViewById(R.id.ivBackTb);
        background = (ImageView) view.findViewById(R.id.background);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.blink);


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BrowseCardsActivity.class);
                Bundle b = new Bundle();
                b.putInt("selected", 1);
                intent.putExtras(b);

                startActivity(intent);
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
