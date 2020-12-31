package com.wakeup.tarot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wakeup.tarot.R;
import com.wakeup.tarot.view.BrowseCardsActivity;
import com.wakeup.tarot.view.BrowseGroupCardsActivity;

import org.jetbrains.annotations.NotNull;

public class CungHoangDaoFragment extends Fragment {

    Button btnClick;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cung_hoang_dao, container, false);
        btnClick = (Button) view.findViewById(R.id.click_button);

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
                intentStar.putExtra("mode", 1);
                getActivity().startActivity(intentStar);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        btnClick.startAnimation(animation);
    }
}
