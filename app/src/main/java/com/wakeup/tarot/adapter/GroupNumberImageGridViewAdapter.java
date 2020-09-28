package com.wakeup.tarot.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.wakeup.tarot.view.BrowseGroupCardsActivity;
import com.wakeup.tarot.view.ItemGroupDetailActivity;

public class GroupNumberImageGridViewAdapter extends BaseAdapter implements
		OnTouchListener, OnClickListener {

	private Context mContext;
	public static int cell_width;

	public GroupNumberImageGridViewAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return MapData.arrGroupNumberCardImage_R_Id.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(R.layout.cell_grid_view_image_and_text,
					parent, false);
		}
		TextView tvCellGridName = (TextView) view
				.findViewById(R.id.tvCellGridName);
		//tvCellGridName.setTypeface(ConfigData.UVNCatBien_R);

		tvCellGridName.setText(NumberJasonHelper.getTitle(position));

		int w = BrowseGroupCardsActivity.GroupNumberCardFragment.mImageLoader.getImageWidth();
		int h = BrowseGroupCardsActivity.GroupNumberCardFragment.mImageLoader.getImageHeight();
		ImageView ivCellGridImage = (ImageView) view.findViewById(R.id.ivCellGridImage);
		ivCellGridImage.setLayoutParams(new LinearLayout.LayoutParams(w, h));

		// Load bitmap asynchronously
		BrowseGroupCardsActivity.GroupNumberCardFragment.mImageLoader.loadImage(MapData.arrGroupNumberCardImage_R_Id[position] + "_" + w + "_" + h , ivCellGridImage);

		view.setTag(position);
		view.setOnTouchListener(this);
		view.setOnClickListener(this);

		return view;

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
		// Reclaim memory and cancel all background task
		BrowseGroupCardsActivity.GroupNumberCardFragment.mInstance.restartCacheToClaimMemory();
		
		// TODO Auto-generated method stub
		int position = Integer.parseInt(v.getTag().toString());
		Intent intent = new Intent(mContext, ItemGroupDetailActivity.class);
		intent.putExtra("position", position);
		intent.putExtra("group_name", "Number");
		mContext.startActivity(intent);
	}
}