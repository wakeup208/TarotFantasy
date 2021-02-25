package com.wakeup.tarot.view;

import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.AppBackgroundAdater;
import com.wakeup.tarot.adapter.CardBackAdapter;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.fragment.ChangeFontSizeCustomDialog;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {
	private CheckBox cbReverseCard;
	private CheckBox cbSoundOnOff;
	private RelativeLayout btnFontSize;
	private TextView tvFontSize;
	private boolean isSettingChange;
	private RecyclerView cardSlider;
	private RecyclerView backgroundSlider;
	private CardBackAdapter cardBackAdapter;
	private AppBackgroundAdater appBackgroundAdater;
	private ImageView background;
	private Button btnHome;
	private RelativeLayout rtlReserve, rtlSoundOff;

	@Override
	public void refreshCardBack() {

	}

	@Override
	public void refreshAppBg() {
		background.setBackground(getDrawable(Config.ing_app_bg[Prefs.getAppBackground(this)]));
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		background = (ImageView) findViewById(R.id.background);

		cardBackAdapter = new CardBackAdapter(this);
		appBackgroundAdater = new AppBackgroundAdater(ProfileActivity.this);

		btnFontSize = (RelativeLayout) findViewById(R.id.btnFontSize);
		btnFontSize.setOnClickListener(this);

		cbReverseCard = (CheckBox) findViewById(R.id.cbReverseCard);
		//cbReverseCard.setOnCheckedChangeListener(this);

		cbSoundOnOff = (CheckBox) findViewById(R.id.cbSoundOnOff);
		//cbSoundOnOff.setOnCheckedChangeListener(this);

		rtlReserve = (RelativeLayout) findViewById(R.id.rtlReverse);
		rtlReserve.setOnClickListener(this);
		rtlSoundOff = (RelativeLayout) findViewById(R.id.rtlSoundoff);
		rtlSoundOff.setOnClickListener(this);

		tvFontSize = (TextView) findViewById(R.id.tvFontSize);
		tvFontSize.setText(ConfigData.FONT_SIZE + "");

		btnHome = (Button) findViewById(R.id.btn_home);
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		// Update this UI
		tvFontSize.setText("" + ConfigData.FONT_SIZE);
		cbReverseCard.setChecked(ConfigData.IS_REVERSE_CARD);
		cbSoundOnOff.setChecked(ConfigData.IS_SOUND_ON);

		isSettingChange = false;
		//startLevelDialog = new AlertDialog.Builder(this, R.style.DialogStyle);

		int resId = R.anim.layout_animation;
		LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);

		cardSlider = (RecyclerView) findViewById(R.id.sliderCardBack);
		backgroundSlider = (RecyclerView) findViewById(R.id.sliderBackground);

		List<SliderItem> backcardItems = new ArrayList<>();
		for (int i = 0; i < Config.ing_back_card.length; i++) {
			SliderItem sliderItem = new SliderItem();
			sliderItem.setDescription("Slider Item " + i);
			sliderItem.setInteger(Integer.valueOf(Config.ing_back_card[i]));
			backcardItems.add(sliderItem);
		}
		cardBackAdapter.renewItems(backcardItems);
		cardSlider.setAdapter(cardBackAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		linearLayoutManager.scrollToPosition(cardBackAdapter.getItemCount() % cardBackAdapter.getSize());
		cardSlider.setLayoutManager(linearLayoutManager);
		cardSlider.setLayoutAnimation(animation);

		List<SliderItem> appBackItems = new ArrayList<>();
		for (int i = 0; i < Config.ing_app_bg.length; i++) {
			SliderItem sliderItem = new SliderItem();
			sliderItem.setDescription("Slider Item " + i);
			sliderItem.setInteger(Integer.valueOf(Config.ing_app_bg[i]));
			appBackItems.add(sliderItem);
		}
		appBackgroundAdater.renewItems(appBackItems);
		backgroundSlider.setAdapter(appBackgroundAdater);
		LinearLayoutManager linearLayoutManagerApp = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		linearLayoutManager.scrollToPosition(appBackgroundAdater.getItemCount() % appBackgroundAdater.getSize());
		backgroundSlider.setLayoutManager(linearLayoutManagerApp);
		backgroundSlider.setLayoutAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rtlReverse:
			cbReverseCard.setChecked(!cbReverseCard.isChecked());
			ConfigData.IS_REVERSE_CARD = cbReverseCard
					.isChecked();
			ConfigData.saveSettingData();
			// Update this UI
			cbReverseCard
					.setChecked(ConfigData.IS_REVERSE_CARD);

			isSettingChange = true;
			break;

			case R.id.rtlSoundoff:
			cbSoundOnOff.setChecked(!cbSoundOnOff.isChecked());
			ConfigData.IS_SOUND_ON = cbSoundOnOff
					.isChecked();
			ConfigData.saveSettingData();
			// Update this UI
			cbSoundOnOff
					.setChecked(ConfigData.IS_SOUND_ON);
			isSettingChange = true;
			break;

		case R.id.btnFontSize:
			(new ChangeFontSizeCustomDialog(this, Float.parseFloat(tvFontSize
					.getText().toString()))).showDialog();

			ConfigData.FONT_SIZE = Float
					.parseFloat(tvFontSize.getText()
							.toString());
			ConfigData.saveSettingData();
			// Update this UI
			tvFontSize.setText(""
					+ ConfigData.FONT_SIZE);
			break;

//		case R.id.btnDefault:
//
//			final AlertDialog alertDialog = startLevelDialog.create();
//			View view = getLayoutInflater().inflate(R.layout.custom_profile_dialog, null);
//			Button btnYes = (Button) view.findViewById(R.id.btnYes);
//			Button btnNo = (Button) view.findViewById(R.id.btnNo);
//
//			btnYes.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					ConfigData.resetDefault();
//					ConfigData.saveSettingData();
//					// Update this UI
//					tvFontSize.setText(""
//							+ ConfigData.FONT_SIZE);
//					cbReverseCard
//							.setChecked(ConfigData.IS_REVERSE_CARD);
//					cbSoundOnOff
//							.setChecked(ConfigData.IS_SOUND_ON);
//
//					isSettingChange = false;
//					alertDialog.dismiss();
//				}
//			});
//
//			btnNo.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					alertDialog.dismiss();
//				}
//			});
//
//			alertDialog.setCancelable(false);
//			alertDialog.setView(view);
//			alertDialog.show();
//			break;
//		case R.id.btnSave:
//
//			final AlertDialog alertDialog1 = startLevelDialog.create();
//			View view1 = getLayoutInflater().inflate(R.layout.custom_profile_dialog, null);
//			Button btnYes1 = (Button) view1.findViewById(R.id.btnYes);
//			Button btnNo1 = (Button) view1.findViewById(R.id.btnNo);
//			TextView txt = (TextView) view1.findViewById(R.id.txt_title_dialog);
//			txt.setText(R.string.huy_thay_doi);
//
//			btnYes1.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					ConfigData.FONT_SIZE = Float
//							.parseFloat(tvFontSize.getText()
//									.toString());
//					ConfigData.IS_SOUND_ON = cbSoundOnOff
//							.isChecked();
//					ConfigData.IS_REVERSE_CARD = cbReverseCard
//							.isChecked();
//					ConfigData.saveSettingData();
//					// Update this UI
//					tvFontSize.setText(""
//							+ ConfigData.FONT_SIZE);
//					cbReverseCard
//							.setChecked(ConfigData.IS_REVERSE_CARD);
//					cbSoundOnOff
//							.setChecked(ConfigData.IS_SOUND_ON);
//
//					isSettingChange = false;
//					alertDialog1.dismiss();
//				}
//			});
//
//			btnNo1.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					alertDialog1.dismiss();
//				}
//			});
//
//
//			alertDialog1.setCancelable(false);
//			alertDialog1.setView(view1);
//			alertDialog1.show();
//
//			break;
//
//		case R.id.btnCancel:
//			// Update this UI
//			finish();
//			break;
		}
	}

	public void setFontSizeFromDialog(float size) {
		tvFontSize.setText("" + size);
		ConfigData.FONT_SIZE = Float
				.parseFloat(tvFontSize.getText()
						.toString());
		ConfigData.saveSettingData();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshAppBg();
	}
}
