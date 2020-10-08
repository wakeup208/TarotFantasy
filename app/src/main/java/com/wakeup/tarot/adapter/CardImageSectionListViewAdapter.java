package com.wakeup.tarot.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.CardsDetailJasonHelper;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.view.BrowseCardsActivity;
import com.wakeup.tarot.view.CardDetailViewPagerForBrowserCardActivity;

public class CardImageSectionListViewAdapter extends BaseAdapter {
	private Context mContext;
	private int mTopBarHeight = 0;
	private int mBottomBarHeight = 0;

	public int getTopBarHeight() {
		return mTopBarHeight;
	}
	
	public void setTopBarHeight(int topBarHeight) {
		this.mTopBarHeight = topBarHeight;
	}

	public int getBottomBarHeight() {
		return mBottomBarHeight;
	}
	
	public void setBottomBarHeight(int bottomBarHeight) {
		this.mBottomBarHeight = bottomBarHeight + 10;
	}

	public CardImageSectionListViewAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		// 2 for Header and Footer of List view
		// 5 for 5 separator in List view
		return 2 + MapData.arrCardImage_R_Id.length + 5;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Convert position in ListView to index of Array image R.id resource in
	 * MapData
	 * 
	 * @param position
	 * @return index in need 	-1 : is Header
	 * 							-2 : is section of Major Arcana
	 * 							-3 : is section of Suit of Wands
	 * 							-4 : is section of Suit of Cups
	 * 							-5 : is section of Suit of Swords
	 * 							-6 : is section of Suit of Pentacles
	 * 							-7 : is Footer
	 */
	public static int convertPosition2ArrayImageResourceIndex(int position) {
		if (position == 0) {
			return -1; // Header
		}
		if (position == 1) {
			return -2; // section of Major Arcana
		}
		if (position <= 23) {
			return position - 2;
		}

		if (position == 24) {
			return -3; // // section of Suit of Wands
		}

		if (position <= 38) {
			return position - 3;
		}

		if (position == 39) {
			return -4; // // section of Suit of Cups
		}

		if (position <= 53) {
			return position - 4;
		}

		if (position == 54) {
			return -5; // section of Suit of Swords
		}

		if (position <= 68) {
			return position - 5;
		}

		if (position == 69) {
			return -6; // section of Suit of Pentacles
		}
		if (position <= 83) {
			return position - 6;
		}
		
		return -7; // Footer
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		
		int w = BrowseCardsActivity.ListViewCardFragment.mImageLoader.getImageWidth();
		int h = BrowseCardsActivity.ListViewCardFragment.mImageLoader.getImageHeight();
		
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int realPosition = convertPosition2ArrayImageResourceIndex(position);

		if (realPosition < 0) {
			// return view in section
			View rowViewSection = inflater.inflate(R.layout.row_section, parent, false);
			TextView tvSection = (TextView) rowViewSection.findViewById(R.id.tvListViewSection);
			tvSection.setTypeface(ConfigData.UVNCatBien_R);

			switch (realPosition) {

			case -1:	// Header separator
				tvSection.setText("");
				tvSection.setBackgroundColor(0x00000000);
				tvSection.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mTopBarHeight));
				break;
			case -2:	// Major_Arcana separator
				tvSection.setText(mContext
						.getString(R.string.section_of_Major_Arcana));
				break;
			case -3:	// Suit_of_Wands separator
				tvSection.setText(mContext
						.getString(R.string.section_of_Suit_of_Wands));
				break;	
			case -4:	// Suit_of_Cups separator
				tvSection.setText(mContext
						.getString(R.string.section_of_Suit_of_Cups));
				break;
			case -5:	// Suit_of_Swords separator
				tvSection.setText(mContext
						.getString(R.string.section_of_Suit_of_Swords));
				break;
			case -6:	// Suit_of_Pentacles separator
				tvSection.setText(mContext
						.getString(R.string.section_of_Suit_of_Pentacles));
				break;
			case -7:	// Footer separator
				tvSection.setText("");
				tvSection.setBackgroundColor(0x00000000);
				tvSection.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBottomBarHeight));
				break;
					
			}

			rowViewSection.setOnClickListener(null);
			rowViewSection.setOnLongClickListener(null);
			rowViewSection.setLongClickable(false);
			rowViewSection.setOnTouchListener(null);

			return rowViewSection;
		} else {
			// return view as image list view item has Image and Name card
			View rowView = inflater.inflate(
					R.layout.row_tarot_spread_image_with_text, parent, false);
			TextView tvCardName = (TextView) rowView
					.findViewById(R.id.tvListViewItemText);
			tvCardName.setTypeface(ConfigData.UVNCatBien_R);

			tvCardName.setText(CardsDetailJasonHelper
					.getEnglishCardName(realPosition));

			// Now handle the main ImageView thumbnails
			ImageView ivListViewItemIcon = (ImageView) rowView.findViewById(R.id.ivListViewItemIcon);
			ivListViewItemIcon.setLayoutParams(new LinearLayout.LayoutParams(w, h));

			// Finally load the image asynchronously into the ImageView, this also takes care of
	        // setting a placeholder image while the background thread runs
			BrowseCardsActivity.ListViewCardFragment.mImageLoader.loadImage(MapData.arrCardImage_R_Id[convertPosition2ArrayImageResourceIndex(position)] + "_" + w + "_" + h, ivListViewItemIcon);
			
			rowView.setTag(realPosition);

			return rowView;
		}
	}

	public void showDetailViewPager(int position) {
		// Reclaim memory and cancel all background task
		BrowseCardsActivity.ListViewCardFragment.mInstance.restartCacheToClaimMemory();
		
		// Show card view pager
		int realPosition = convertPosition2ArrayImageResourceIndex(position);
		Intent intentCardViewPager = new Intent(mContext,
				CardDetailViewPagerForBrowserCardActivity.class);
		intentCardViewPager.putExtra("position", realPosition);

		mContext.startActivity(intentCardViewPager);
	}

}