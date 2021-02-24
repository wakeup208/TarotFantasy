package com.wakeup.tarot.view;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.SpreadCardNameListViewAdapter;
import com.wakeup.tarot.data.ConfigData;

public class TarotSpreadActivity extends BaseActivity implements
		OnItemClickListener {

	private TextView tvTarotSpreadTitle;
	private ListView lvTarotSpread;
	private int pos;

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
		setContentView(R.layout.activity_tarot_spread);

		// Reload screen size and background
		//ConfigData.reloadScreen(this);
				
		
		// Load background
		//((ImageView) findViewById(R.id.background)).setBackgroundDrawable(ConfigData.rbdBackground);

		tvTarotSpreadTitle = (TextView) findViewById(R.id.tvTarotSpreadTitle);
		tvTarotSpreadTitle.setTypeface(ConfigData.UVNCatBien_R);

		SpreadCardNameListViewAdapter adapter = new SpreadCardNameListViewAdapter(
				this.getApplicationContext());
		lvTarotSpread = (ListView) findViewById(R.id.lvTarotSpread);
		lvTarotSpread.setAdapter(adapter);
		lvTarotSpread.setOnItemClickListener(this);

		//interstitialAd.loadAd(adRequestBuilder1.build());
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				super.onAdClosed();
				itemClickCardList(pos);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> listAdapter, View arg1,
			int position, long arg3) {
		pos = position;
		if(interstitialAd.isLoaded()) {
			interstitialAd.show();
		}
		else {
			itemClickCardList(position);
		}
	}

	private void itemClickCardList(int position) {
		Intent intent = new Intent(TarotSpreadActivity.this,
				TarotSpreadGuideActivity.class);
		intent.putExtra("spreadId", position);
		this.startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);
		super.onResume();
	}

}
