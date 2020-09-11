package com.wakeup.tarot.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.wakeup.tarot.R;
import com.wakeup.tarot.animation.AnimationFactory;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.view.CardDetailViewPagerForSpreadCardActivity;
import com.wakeup.tarot.view.SpreadCardsActivity;

public class CardViewFlipper extends ViewFlipper implements OnClickListener,
		AnimationListener {

	private int cardWidth;
	private int cardHeight;
	private boolean isCardBack = true;
	private ImageView ivBackCard;
	private ImageView ivFontCard;
	private int mCardIndex = 0;
	private SpreadCardsActivity mSpreadCardsActivity;

	public CardViewFlipper(SpreadCardsActivity spreadCardsActivity,
			int cardIndex, int width, int height, boolean isLandscape) {
		super(spreadCardsActivity.getApplicationContext());

		mSpreadCardsActivity = spreadCardsActivity;		
		cardWidth = width;
		cardHeight = height;
		mCardIndex = cardIndex;

		/**
		 * Create card
		 */
		// Inflate xml file to this view for Portrait orientation
		mSpreadCardsActivity.getLayoutInflater().inflate(R.layout.card_view_flipper, this, true);

		ivBackCard = (ImageView) findViewById(R.id.ivBackCard);
		ivBackCard.setOnClickListener(this);

		ivFontCard = (ImageView) findViewById(R.id.ivFontCard);
		ivFontCard.setTag(this);
		ivFontCard.setOnClickListener(this);

		// get Degree to rotate card
		int rotateDegree = 0;
		if (isLandscape) {
			rotateDegree = 90;
		} else if (ConfigData.randomCardDimensionsArray[mCardIndex] == 1) {
			rotateDegree = 180;
		}
		
		mSpreadCardsActivity.getImageLoader().loadImage(R.drawable.card_back + "_" + cardWidth + "_" + cardHeight + "_" + rotateDegree, ivBackCard);
		mSpreadCardsActivity.getImageLoader().loadImage(getCardId() + "_" + cardWidth + "_" + cardHeight + "_" + rotateDegree, ivFontCard);
	}

	// determine card is back or no
	public boolean isCardBack() {
		return isCardBack;
	}

	/**
	 * Get card id in resource array
	 * 
	 * @return
	 */
	public int getCardId() {
		return MapData.arrCardImage_R_Id[ConfigData.randomCardIdArray[mCardIndex]];
	}

	public int getCardIndex() {
		return mCardIndex;
	}

	/**
	 * Flip card using 3D flip animation
	 */
	public void flipCardToBack(boolean needPlaySound) {
		// Flip back when card is font
		if (isCardBack == false) {
			AnimationFactory.flipTransition(
					(ViewAnimator) ivFontCard.getParent(),
					AnimationFactory.FlipDirection.LEFT_RIGHT);
			isCardBack = true;
		}

		if (needPlaySound) {
			ConfigData.playSound(R.raw.card_deal);
		}

	}

	public void flipCardToFont(boolean needPlaySound) {
		// Flip font when card is back
		if (isCardBack == true) {
			AnimationFactory.flipTransition(
					(ViewAnimator) ivBackCard.getParent(),
					AnimationFactory.FlipDirection.LEFT_RIGHT);
			isCardBack = false;
		}

		if (needPlaySound) {
			ConfigData.playSound(R.raw.card_deal);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.ivBackCard:
			flipCardToFont(true);
			break;

		case R.id.ivFontCard:
			this.bringToFront();
			ConfigData.animation_rotate_zoom_in.setAnimationListener(this);
			startAnimation(ConfigData.animation_rotate_zoom_in);
			isCardBack = false;
			break;
		}

	}

	@Override
	public void onAnimationEnd(Animation animation) {

		/**
		 * Start rotate CardViewFlipper 180 degrees after
		 * animation_rotate_zoom_in end.
		 */
		if (animation == ConfigData.animation_rotate_zoom_in) {
			ConfigData.animation_rotate_zoom_in.setAnimationListener(null);
			ConfigData.animation_rotation_180.setAnimationListener(this);
			this.startAnimation(ConfigData.animation_rotation_180);
		}

		/**
		 * Start activity CardViewPager_SpreadCardActivity to show card info
		 */
		if (animation == ConfigData.animation_rotation_180) {
			ConfigData.animation_rotation_180.setAnimationListener(null);
			// Show Activity guide of card
			Intent intentCardViewPager_SpreadCardActivity = new Intent(
					mSpreadCardsActivity,
					CardDetailViewPagerForSpreadCardActivity.class);
			intentCardViewPager_SpreadCardActivity.putExtra("cardClickedIndex",
					mCardIndex);
			mSpreadCardsActivity
					.startActivity(intentCardViewPager_SpreadCardActivity);
			mSpreadCardsActivity.overridePendingTransition(0, 0);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}
}
