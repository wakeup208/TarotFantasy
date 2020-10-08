package com.wakeup.tarot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.data.SpreadCardJasonHelper;

public class SpreadCardNameListViewAdapter extends BaseAdapter {

	private final Context mContext;

	public SpreadCardNameListViewAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		if (convertView == null) {
			rowView = inflater.inflate(R.layout.row_profile_image_with_text, parent, false);
		}
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.ivListViewItemIcon);
		imageView.setBackgroundResource(MapData.arrSpreadCardIcon_R_Id[position]);
		
		TextView textView = (TextView) rowView.findViewById(R.id.tvListViewItemText);
		textView.setText(SpreadCardJasonHelper.getSpreadName(position));
		textView.setTypeface(ConfigData.UVNCatBien_R);

		return rowView;
	}

	@Override
	public int getCount() {				
		return MapData.arrSpreadCardIcon_R_Id.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
