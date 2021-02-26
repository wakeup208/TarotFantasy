package com.wakeup.tarot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.wakeup.tarot.R;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.util.Constant;
import com.wakeup.tarot.util.Utils;
import com.wakeup.tarot.view.InformationActivity;
import com.wakeup.tarot.view.KienThuc;

import org.jetbrains.annotations.NotNull;

public class FragmentChakra extends Fragment {

    ConstraintLayout clContent;
    ConstraintLayout clParent;
    ImageView ivBackTb;
    ImageView ivThumb;
    NestedScrollView nvDetail;
    Toolbar tbDetail;
    TextView tvDescription;
    TextView tvTitleTb;
    Button btnAbout;
    ImageView background;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (tvDescription != null) {
            tvDescription.setText(Utils.ReadFromfile(Constant.PATH_FULL_ITACHI, getContext()));
        }

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

    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
        clContent = (ConstraintLayout) view.findViewById(R.id.clContent);
        nvDetail = (NestedScrollView) view.findViewById(R.id.nvDetail);
        tbDetail = (Toolbar) view.findViewById(R.id.tbDetail);
        clParent = (ConstraintLayout) view.findViewById(R.id.clParent);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
        ivThumb.setBackground(getActivity().getResources().getDrawable(R.drawable.chakar_bg));
        tvTitleTb = (TextView) view.findViewById(R.id.tvTitleTb);
        tvTitleTb.setText(R.string.str_itachi);
        ivBackTb = (ImageView) view.findViewById(R.id.ivBackTb);
        btnAbout = (Button) view.findViewById(R.id.btnAbout);
        btnAbout.setText(getText(R.string.proud));
        background = (ImageView) view.findViewById(R.id.background);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
    }

    public void onBackClicked() {
        if (getActivity() instanceof KienThuc) {
            getActivity().onBackPressed();
        }
    }
}
