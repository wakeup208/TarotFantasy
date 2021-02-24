package com.wakeup.tarot.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.CardBackAdapter;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.fragment.ChangeFontSizeCustomDialog;
import com.wakeup.tarot.model.SliderItem;
import com.wakeup.tarot.util.Config;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView btnReverseCard;
	private CheckBox cbReverseCard;

	private TextView btnSoundOnOff;
	private CheckBox cbSoundOnOff;

	private TextView btnFontSize;
	private TextView tvFontSize;

	private Button btnDefault;
	private Button btnSave;
	private Button btnCancel;

	private AlertDialog.Builder startLevelDialog;

	private boolean isSettingChange;
	private RecyclerView cardSlider;
	private RecyclerView backgroundSlider;
	private CardBackAdapter cardBackAdapter;

	@Override
	public void refreshCardBack() {

	}

	@Override
	public void refreshAppBg() {

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		cardBackAdapter = new CardBackAdapter(this);
		btnReverseCard = (TextView) findViewById(R.id.btnReverseCard);
		btnReverseCard.setOnClickListener(this);

		btnSoundOnOff = (TextView) findViewById(R.id.btnSoundOnOff);
		btnSoundOnOff.setOnClickListener(this);

		btnFontSize = (TextView) findViewById(R.id.btnFontSize);
		btnFontSize.setOnClickListener(this);

		cbReverseCard = (CheckBox) findViewById(R.id.cbReverseCard);
		cbReverseCard.setOnCheckedChangeListener(this);

		cbSoundOnOff = (CheckBox) findViewById(R.id.cbSoundOnOff);
		cbSoundOnOff.setOnCheckedChangeListener(this);

		tvFontSize = (TextView) findViewById(R.id.tvFontSize);
		tvFontSize.setText(ConfigData.FONT_SIZE + "");

		btnDefault = (Button) findViewById(R.id.btnDefault);
		btnDefault.setOnClickListener(this);

		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);

		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

		// Update this UI
		tvFontSize.setText("" + ConfigData.FONT_SIZE);
		cbReverseCard.setChecked(ConfigData.IS_REVERSE_CARD);
		cbSoundOnOff.setChecked(ConfigData.IS_SOUND_ON);

		isSettingChange = false;
		startLevelDialog = new AlertDialog.Builder(this, R.style.DialogStyle);

		cardSlider = (RecyclerView) findViewById(R.id.sliderCardBack);
		backgroundSlider = (RecyclerView) findViewById(R.id.sliderBackground);

		List<SliderItem> sliderItemList = new ArrayList<>();
		for (int i = 0; i < Config.ing_back_card.length; i++) {
			SliderItem sliderItem = new SliderItem();
			sliderItem.setDescription("Slider Item " + i);
			sliderItem.setInteger(Integer.valueOf(Config.img_stone[i]));
			sliderItemList.add(sliderItem);
		}

		cardBackAdapter.renewItems(sliderItemList);
		cardSlider.setAdapter(cardBackAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		linearLayoutManager.scrollToPosition(cardBackAdapter.getItemCount() % cardBackAdapter.getSize());
		cardSlider.setLayoutManager(linearLayoutManager);

		int resId = R.anim.layout_animation;
		LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
		cardSlider.setLayoutAnimation(animation);

//		cardSlider.getAdapter().notifyDataSetChanged();
//		cardSlider.scheduleLayoutAnimation();
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
		case R.id.btnReverseCard:
			cbReverseCard.setChecked(!cbReverseCard.isChecked());
			isSettingChange = true;
			break;

		case R.id.btnSoundOnOff:
			cbSoundOnOff.setChecked(!cbSoundOnOff.isChecked());
			isSettingChange = true;
			break;

		case R.id.btnFontSize: // Show dialog change font size
			(new ChangeFontSizeCustomDialog(this, Float.parseFloat(tvFontSize
					.getText().toString()))).showDialog();
			break;

		case R.id.btnDefault:

			final AlertDialog alertDialog = startLevelDialog.create();
			View view = getLayoutInflater().inflate(R.layout.custom_profile_dialog, null);
			Button btnYes = (Button) view.findViewById(R.id.btnYes);
			Button btnNo = (Button) view.findViewById(R.id.btnNo);

			btnYes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConfigData.resetDefault();
					ConfigData.saveSettingData();
					// Update this UI
					tvFontSize.setText(""
							+ ConfigData.FONT_SIZE);
					cbReverseCard
							.setChecked(ConfigData.IS_REVERSE_CARD);
					cbSoundOnOff
							.setChecked(ConfigData.IS_SOUND_ON);

					isSettingChange = false;
					alertDialog.dismiss();
				}
			});

			btnNo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
				}
			});

			alertDialog.setCancelable(false);
			alertDialog.setView(view);
			alertDialog.show();
			break;
		case R.id.btnSave:

			final AlertDialog alertDialog1 = startLevelDialog.create();
			View view1 = getLayoutInflater().inflate(R.layout.custom_profile_dialog, null);
			Button btnYes1 = (Button) view1.findViewById(R.id.btnYes);
			Button btnNo1 = (Button) view1.findViewById(R.id.btnNo);
			TextView txt = (TextView) view1.findViewById(R.id.txt_title_dialog);
			txt.setText(R.string.huy_thay_doi);

			btnYes1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConfigData.FONT_SIZE = Float
							.parseFloat(tvFontSize.getText()
									.toString());
					ConfigData.IS_SOUND_ON = cbSoundOnOff
							.isChecked();
					ConfigData.IS_REVERSE_CARD = cbReverseCard
							.isChecked();
					ConfigData.saveSettingData();
					// Update this UI
					tvFontSize.setText(""
							+ ConfigData.FONT_SIZE);
					cbReverseCard
							.setChecked(ConfigData.IS_REVERSE_CARD);
					cbSoundOnOff
							.setChecked(ConfigData.IS_SOUND_ON);

					isSettingChange = false;
					alertDialog1.dismiss();
				}
			});

			btnNo1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog1.dismiss();
				}
			});


			alertDialog1.setCancelable(false);
			alertDialog1.setView(view1);
			alertDialog1.show();

			break;

		case R.id.btnCancel:
			// Update this UI
			finish();
			break;
		}
	}

	public void setFontSizeFromDialog(float size) {
		tvFontSize.setText("" + size);
		isSettingChange = true;
	}

	@Override
	public void onBackPressed() {

		if (isSettingChange) {
			final AlertDialog backPressDialog = startLevelDialog.create();
			View view1 = getLayoutInflater().inflate(R.layout.custom_profile_dialog, null);
			Button btnYes1 = (Button) view1.findViewById(R.id.btnYes);
			Button btnNo1 = (Button) view1.findViewById(R.id.btnNo);
			TextView txt = (TextView) view1.findViewById(R.id.txt_title_dialog);
			txt.setText(R.string.you_mean_1);

			btnYes1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConfigData.FONT_SIZE = Float
							.parseFloat(tvFontSize.getText()
									.toString());
					ConfigData.IS_SOUND_ON = cbSoundOnOff
							.isChecked();
					ConfigData.IS_REVERSE_CARD = cbReverseCard
							.isChecked();
					ConfigData.saveSettingData();
					// Update this UI
					tvFontSize.setText(""
							+ ConfigData.FONT_SIZE);
					cbReverseCard
							.setChecked(ConfigData.IS_REVERSE_CARD);
					cbSoundOnOff
							.setChecked(ConfigData.IS_SOUND_ON);
					ProfileActivity.this.finish();
				}
			});

			btnNo1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tvFontSize.setText(""
							+ ConfigData.FONT_SIZE);
					cbReverseCard
							.setChecked(ConfigData.IS_REVERSE_CARD);
					cbSoundOnOff
							.setChecked(ConfigData.IS_SOUND_ON);
					ProfileActivity.this.finish();
				}
			});

			backPressDialog.setCancelable(false);
			backPressDialog.setView(view1);
			backPressDialog.show();
		} else {
			this.finish();
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		isSettingChange = true;
	}

	@Override
	protected void onResume() {
		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);

		super.onResume();
	}
}
