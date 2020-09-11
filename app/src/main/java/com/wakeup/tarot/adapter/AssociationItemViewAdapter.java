package com.wakeup.tarot.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.data.NumberJasonHelper;
import com.wakeup.tarot.data.StarJasonHelper;
import com.wakeup.tarot.data.SuitJasonHelper;
import com.wakeup.tarot.data.SymbolJasonHelper;
import com.wakeup.tarot.util.ImageLoaderAsynch;
import com.wakeup.tarot.view.ItemGroupDetailActivity;

public class AssociationItemViewAdapter extends BaseAdapter implements
		OnTouchListener, OnClickListener {
	private Activity mActivity;
	public static int cell_width;
	private String[] associationAdapter;
	private String association_name;
	private int association_image_id;
	private String association_group_name;
	private int associationId;
	ImageLoaderAsynch mImageLoader;
	
	public AssociationItemViewAdapter(Activity a, ImageLoaderAsynch imageLoader, String[] arrId) {
		mActivity = a;
		mImageLoader = imageLoader;
		associationAdapter = arrId;
	}

	public int getCount() {
		return associationAdapter.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(R.layout.association_item, parent, false);
		}

		// get assoiciation info: image id and name to create new view
		GetAssociationInfo(position);

		TextView tvAssociationName = (TextView) view.findViewById(R.id.tvAssociationName);
		tvAssociationName.setText(association_name);

		int w = (ConfigData.SCREEN_WIDTH - 75) / 3;
		int h = w;		
		ImageView imageView = (ImageView) view.findViewById(R.id.ivAssociationImage);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(w, h));
		mImageLoader.loadImage(association_image_id + "_" + w + "_" + h, imageView);
		
		// put info for handle click event
		Bundle associationInfo = new Bundle();
		associationInfo.putInt("associationId", associationId);
		associationInfo.putString("association_group_name", association_group_name);
		imageView.setTag(associationInfo);
		
		imageView.setOnTouchListener(this);
		imageView.setOnClickListener(this);

		return view;

	}

	private void GetAssociationInfo(int position) {
		// TODO Auto-generated method stub
		String id = associationAdapter[position];

		// If type of id is S(suit id)
		if (id.split("S")[0].equals("")) {
			try {
				// try if id.split("S")[1] can be a number
				int suitId = Integer.parseInt(id.split("S")[1]) - 1;
				association_name = SuitJasonHelper.getTitle(suitId);
				association_image_id = MapData.arrGroupSuitCardImage_R_Id[suitId];
				association_group_name = "Suit";
				associationId = suitId;
				return;
			} catch (Exception e) {

			}
		}

		// If type of id is St(star id)
		if (id.split("St")[0].equals("")) {
			try {
				int starId = Integer.parseInt(id.split("St")[1]) - 1;
				association_name = StarJasonHelper.getTitle(starId);
				association_image_id = MapData.arrGroupStarCardImage_R_Id[starId];
				association_group_name = "Star";
				associationId = starId;
				return;
			} catch (Exception e) {

			}
		}

		// If type of id is N(number id)
		if (id.split("N")[0].equals("")) {
			try {
				int numberId = Integer.parseInt(id.split("N")[1]);
				association_name = NumberJasonHelper.getTitle(numberId);
				association_image_id = MapData.arrGroupNumberCardImage_R_Id[numberId];
				association_group_name = "Number";
				associationId = numberId;
				return;
			} catch (Exception e) {

			}
		}

		// If type of id is Sy(symbol id)
		if (id.split("Sy")[0].equals("")) {
			try {
				int symbolId = Integer.parseInt(id.split("Sy")[1]) - 1;
				association_name = SymbolJasonHelper.getTitle(symbolId);
				association_image_id = MapData.arrGroupSymbolCardImage_R_Id[symbolId];
				association_group_name = "Symbol";
				associationId = symbolId;
				return;
			} catch (Exception e) {

			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			v.startAnimation(ConfigData.animation_button_press);
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		// Show ItemGroupDetail of association item
		Bundle info = (Bundle) v.getTag();
		Intent intent = new Intent(mActivity, ItemGroupDetailActivity.class);
		intent.putExtra("position", info.getInt("associationId"));
		intent.putExtra("group_name", info.getString("association_group_name"));
		mActivity.startActivity(intent);
	}
}