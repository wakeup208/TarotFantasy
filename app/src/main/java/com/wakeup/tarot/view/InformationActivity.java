package com.wakeup.tarot.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.MapData;

public class InformationActivity extends AppCompatActivity {

    RelativeLayout rlGioiThieu;
    RelativeLayout rlMean;
    RelativeLayout cunghoangdao;


    FrameLayout mainFragment;
    ImageView imgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thong_tin);
        imgHome = (ImageView) findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mainFragment = (FrameLayout) findViewById(R.id.nav_main_fragment);

        rlGioiThieu =(RelativeLayout) findViewById(R.id.gioithieu);
        rlGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentGioiThieu();
            }
        });

        rlMean = (RelativeLayout) findViewById(R.id.rtl_mean);
        rlMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentMean();
            }
        });

        cunghoangdao = (RelativeLayout) findViewById(R.id.cunghoangdao);
        cunghoangdao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentCungHoangDao();
            }
        });
        //selectBrowserMode(mode);
    }

    private void addFragmentWithNavigationId(int i, Bundle bundle) {
        try {
            NavHostFragment create = NavHostFragment.create(i, bundle);
            if (getSupportFragmentManager() != null) {
                mainFragment.setVisibility(View.VISIBLE);
                FragmentTransaction add = getSupportFragmentManager().beginTransaction().add((int) R.id.nav_main_fragment, (Fragment) create);
                add.addToBackStack(create + "").setPrimaryNavigationFragment(create).commit();
            }
        } catch (IllegalStateException unused) {
        }
    }

    public void addFragmentGioiThieu() {
        addFragmentWithNavigationId(R.layout.gioi_thieu_xml, (Bundle) null);
    }

    public void addFragmentMean() {
        addFragmentWithNavigationId(R.layout.type_card_page_xml, (Bundle) null);
    }

    public void addFragmentCungHoangDao() {
        addFragmentWithNavigationId(R.layout.cung_hoang_dao, (Bundle) null);
    }

    public void selectBrowserMode(int mode) {

        switch (mode) {
            case 0:
                addFragmentMean();
                break;

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainFragment.setVisibility(View.GONE);
    }
}
