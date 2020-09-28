package com.wakeup.tarot.view;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.data.NumberJasonHelper;
import com.wakeup.tarot.data.StarJasonHelper;
import com.wakeup.tarot.data.SuitJasonHelper;
import com.wakeup.tarot.data.SymbolJasonHelper;

public class ItemGroupDetailActivity extends FragmentActivity implements
		OnClickListener {

	public static Context mContext;
	private ImageButton btn_home;

	private TextView tvItemGroupName;
	private TextView tvGroupName;
	private TextView tvItemGroupDetail;
	private ImageView ivItemGroupImage;
	
	private String group_name;
	private int position;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_item_detail);
		mContext = this.getApplicationContext();

		// Reload screen size and background
		ConfigData.reloadScreen(this);
				
		
		// Load background
		((ImageView) findViewById(R.id.background)).setBackgroundDrawable(ConfigData.rbdBackground);

		position = this.getIntent().getExtras().getInt("position");
		group_name = this.getIntent().getExtras().getString("group_name");

		btn_home = (ImageButton) findViewById(R.id.btn_home);
		btn_home.setOnClickListener(this);

		tvGroupName = (TextView) findViewById(R.id.tvGroupName);
		//tvGroupName.setTypeface(ConfigData.UVNCatBien_R);
		tvGroupName.setText(group_name);
		tvGroupName.setTextSize(ConfigData.FONT_SIZE - 2);

		tvItemGroupName = (TextView) findViewById(R.id.tvItemGroupName);
		tvItemGroupName.setTextSize(ConfigData.FONT_SIZE);
		//tvItemGroupName.setTypeface(ConfigData.UVNCatBien_R);

		ivItemGroupImage = (ImageView) findViewById(R.id.ivItemGroupImage);

		tvItemGroupDetail = (TextView) findViewById(R.id.tvItemGroupDetail);
		tvItemGroupDetail.setTextSize(ConfigData.FONT_SIZE);

		if ("Suit".equals(group_name)) {
			tvItemGroupName.setText(SuitJasonHelper.getTitle(position));
			ivItemGroupImage
					.setBackgroundResource(MapData.arrGroupSuitCardImage_R_Id[position]);
			tvItemGroupDetail.setText(SuitJasonHelper.getInfo(position));
			return;
		}

		if ("Number".equals(group_name)) {
			tvItemGroupName.setText(NumberJasonHelper.getTitle(position));
			ivItemGroupImage
					.setBackgroundResource(MapData.arrGroupNumberCardImage_R_Id[position]);
			tvItemGroupDetail.setText(NumberJasonHelper.getInfo(position));
			return;
		}

		if ("Symbol".equals(group_name)) {
			tvItemGroupName.setText(SymbolJasonHelper.getTitle(position));
			ivItemGroupImage
					.setBackgroundResource(MapData.arrGroupSymbolCardImage_R_Id[position]);
			tvItemGroupDetail.setText(SymbolJasonHelper.getInfo(position));
			return;
		}

		if ("Star".equals(group_name)) {
			tvItemGroupName.setText(StarJasonHelper.getTitle(position));
			ivItemGroupImage
					.setBackgroundResource(MapData.arrGroupStarCardImage_R_Id[position]);
			String detail = StarJasonHelper.getFirstInfo(position);
			tvItemGroupDetail.setText(detail);

			ImageView ivSeparateBar = (ImageView) findViewById(R.id.ivSeparateBar);
			ivSeparateBar.setVisibility(View.VISIBLE);
			TextView tvItemGroupDetail2 = (TextView) findViewById(R.id.tvItemGroupDetail2);
			tvItemGroupDetail2.setTextSize(ConfigData.FONT_SIZE);
			tvItemGroupDetail2.setVisibility(View.VISIBLE);
			tvItemGroupDetail2.setText(StarJasonHelper.getSecondInfo(position));
			return;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_home) {
			this.finish();
			this.startActivity(new Intent(this.getApplicationContext(),
					MainActivity.class));
		}
	}
	
	@Override
	protected void onResume() {
		// Load background
		((ImageView) findViewById(R.id.background))
				.setBackgroundDrawable(ConfigData.rbdBackground);
		super.onResume();
	}

}
