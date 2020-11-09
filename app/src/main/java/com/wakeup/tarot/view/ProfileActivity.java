package com.wakeup.tarot.view;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.fragment.AboutCustomDialog;
import com.wakeup.tarot.fragment.BuyTarotCustomDialog;
import com.wakeup.tarot.fragment.ChangeFontSizeCustomDialog;

public class ProfileActivity extends AppCompatActivity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView tvProfileTitle;
	private Button btnBuyTarotCards;
	private Button btnFeedbackAndDiscuss;
	private Button btnAuthor;

	private TextView btnReverseCard;
	private CheckBox cbReverseCard;

	private TextView btnSoundOnOff;
	private CheckBox cbSoundOnOff;

	private TextView btnFontSize;
	private TextView tvFontSize;

	private Button btnDefault;
	private Button btnSave;
	private Button btnCancel;

	private boolean isSettingChange;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		// Reload screen size and background
		//ConfigData.reloadScreen(this);

		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackgroundDrawable(ConfigData.rbdBackground);

		// Look up the AdView as a resource and load a request.


//		tvProfileTitle = (TextView) findViewById(R.id.tvProfileTitle);
//		tvProfileTitle.setTypeface(ConfigData.UVNCatBien_R);

//		btnBuyTarotCards = (Button) findViewById(R.id.btnBuyTarotCards);
//		btnBuyTarotCards.setOnClickListener(this);
//
//		btnFeedbackAndDiscuss = (Button) findViewById(R.id.btnFeedbackAndDiscuss);
//		btnFeedbackAndDiscuss.setOnClickListener(this);
//
//		btnAuthor = (Button) findViewById(R.id.btnAuthor);
//		btnAuthor.setOnClickListener(this);

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
//		case R.id.btnBuyTarotCards:
//			(new BuyTarotCustomDialog(this)).showDialog();
//			break;
//
//		case R.id.btnFeedbackAndDiscuss:
//			Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
//			Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//			startActivity(launchBrowser);
//			break;
//
//		case R.id.btnAuthor:
//			(new AboutCustomDialog(this)).showDialog();
//			break;

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
			(new AlertDialog.Builder(this))
					.setIcon(R.drawable.icon_question)
					.setTitle("Xác nhận")
					.setMessage(
							"Bạn có chắc chắn muốn cài lại mặc định hay không ?")
					.setNegativeButton("Không", null)
					.setPositiveButton("Có",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
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
								}
							}).create().show();

			break;

		case R.id.btnSave:
			(new AlertDialog.Builder(this))
					.setIcon(R.drawable.icon_question)
					.setTitle("Xác nhận !")
					.setMessage("Bạn có chắc chắn muốn lưu lại thay đổi.")
					.setNegativeButton("Không", null)
					.setPositiveButton("Có",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
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
								}
							}).create().show();

			break;

		case R.id.btnCancel:
			// Update this UI
			tvFontSize.setText("" + ConfigData.FONT_SIZE);
			cbReverseCard.setChecked(ConfigData.IS_REVERSE_CARD);
			cbSoundOnOff.setChecked(ConfigData.IS_SOUND_ON);

			(new AlertDialog.Builder(this)).setTitle("Thông báo !")
					.setMessage("Đã hủy thay đổi của bạn.")
					.setPositiveButton("OK", null).create().show();

			isSettingChange = false;

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
			(new AlertDialog.Builder(this))
					.setIcon(R.drawable.icon_question)
					.setTitle("Chưa lưu thay đổi !")
					.setMessage("Bạn có muốn lưu lại thay đổi cài đặt không ?")
					.setPositiveButton("Có",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
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
							})
					.setNegativeButton("Không",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Update this UI
									tvFontSize.setText(""
											+ ConfigData.FONT_SIZE);
									cbReverseCard
											.setChecked(ConfigData.IS_REVERSE_CARD);
									cbSoundOnOff
											.setChecked(ConfigData.IS_SOUND_ON);
									ProfileActivity.this.finish();
								}
							}).create().show();

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
