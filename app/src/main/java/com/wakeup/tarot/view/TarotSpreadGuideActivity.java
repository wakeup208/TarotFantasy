package com.wakeup.tarot.view;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.data.SpreadCardJasonHelper;

public class TarotSpreadGuideActivity extends AppCompatActivity implements
		OnClickListener {

	private int spreadId;
	private Button btn_shuffle_cards;
	private Button btn_go_directly_to_spread;
	private TextView tvTarotSpreadName;
	private ImageView ivTarotSpread;
	private TextView tvTarotSpreadGuide;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarot_spread_guide);

		// Reload screen size and background
		//ConfigData.reloadScreen(this);
				
		
		// Load background
		//((ImageView) findViewById(R.id.background)).setBackground(ConfigData.rbdBackground);
		
		btn_shuffle_cards = (Button) findViewById(R.id.btn_shuffle_cards);
		btn_shuffle_cards.setTypeface(ConfigData.UVNCatBien_R);
		btn_shuffle_cards.setOnClickListener(this);

		btn_go_directly_to_spread = (Button) findViewById(R.id.btn_go_directly_to_spread);
		btn_go_directly_to_spread.setTypeface(ConfigData.UVNCatBien_R);
		btn_go_directly_to_spread.setOnClickListener(this);

		tvTarotSpreadName = (TextView) findViewById(R.id.tvTarotSpreadName);
		tvTarotSpreadName.setTypeface(ConfigData.UVNCatBien_R);

		ivTarotSpread = (ImageView) findViewById(R.id.ivTarotSpread);
		ivTarotSpread.setOnClickListener(this);

		tvTarotSpreadGuide = (TextView) findViewById(R.id.tvTarotSpreadGuide);
		tvTarotSpreadGuide.setTextSize(ConfigData.FONT_SIZE);
		tvTarotSpreadGuide.setTypeface(ConfigData.UVNCatBien_R);

		spreadId = getIntent().getExtras().getInt("spreadId", 0);

		tvTarotSpreadName
				.setText(SpreadCardJasonHelper.getSpreadName(spreadId));
		ivTarotSpread
				.setBackgroundResource(MapData.arrSpreadCardIcon_R_Id[spreadId]);
		tvTarotSpreadGuide.setText(SpreadCardJasonHelper
				.getSpreadInfo(spreadId));

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivTarotSpread:
		case R.id.btn_shuffle_cards:
			Intent intentDrawCardActivity = new Intent(this,
					ChooseCardActivity.class);
			intentDrawCardActivity.putExtra("spreadId", spreadId);
			intentDrawCardActivity.putExtra("cardSelectInNeed",
					SpreadCardJasonHelper.getStepArray(spreadId).length);
			this.startActivity(intentDrawCardActivity);
			break;

		case R.id.btn_go_directly_to_spread:
			Intent intentSpreadCardsActivity = new Intent(this,
					SpreadCardsActivity.class);
			intentSpreadCardsActivity.putExtra("spreadId", spreadId);
			ConfigData.randomMultipleCards(SpreadCardJasonHelper.getStepArray(spreadId).length);
			this.startActivity(intentSpreadCardsActivity);
			break;
		}
	}

	@Override
	protected void onResume() {
		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);
		super.onResume();
	}
}
