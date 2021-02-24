package com.wakeup.tarot.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.SpreadCardJasonHelper;
import com.wakeup.tarot.model.CustomModelClass;
import com.wakeup.tarot.preferences.Prefs;
import com.wakeup.tarot.util.Config;
import com.wakeup.tarot.util.Utils;

public class ChooseCardActivity extends BaseActivity implements OnClickListener,
		AnimationListener {

	private int padding;
	private int card_width;
	private int card_height;
	private int marginTop;
	private AbsoluteLayout as;
	private Button btnSkip;
	private int cardSelectedCount;
	private int theNumberCardNeedToSelect;
	private int spreadId;
	private ImageView cardNeedRemove;
	private Context mContext;
	private LayoutAnimationController controller;
	private Bitmap cardBack;

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
		setContentView(R.layout.activity_choose_card);

		// Reload screen size and background
		//ConfigData.reloadScreen(this);


		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackgroundDrawable(ConfigData.rbdBackground);

		mContext = this.getApplicationContext();

		if (ConfigData.IS_SOUND_ON) {
			MediaPlayer mPlayer = MediaPlayer.create(mContext,
					R.raw.cards_layout);
			mPlayer.start();
		}

		// get card need to select
		theNumberCardNeedToSelect = this.getIntent().getExtras()
				.getInt("cardSelectInNeed");

		// get spreadId
		spreadId = this.getIntent().getExtras().getInt("spreadId", -1);
		cardSelectedCount = 0;

		as = (AbsoluteLayout) findViewById(R.id.cardDrawArea);

		padding = 10;
		card_width = (ConfigData.SCREEN_WIDTH - padding * 3) / 2;
		card_height = card_width * 710 / 1232;
		marginTop = (ConfigData.SCREEN_HEIGHT - padding - card_height) / 40;
		//R.drawable.card_back1
		cardBack = Utils.decodeSampledBitmapFromResource(getResources(),
				(Config.ing_back_card[Prefs.getCardBackground(this)]), card_width, card_height, 90);

		cardBack = getRoundedCornerBitmap(cardBack, 50);
		btnSkip = (Button) findViewById(R.id.btnSkip);
		btnSkip.setOnClickListener(this);
		btnSkip.setTypeface(ConfigData.UVNCatBien_R);

		create78Card();

		ConfigData.animation_select_card.setAnimationListener(this);

	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = Color.RED;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	@Override
	protected void onResume() {
		// Load background
//		((ImageView) findViewById(R.id.background))
//				.setBackground(ConfigData.rbdBackground);
		super.onResume();
	}

	private void create78Card() {

		// Create 39 Cards right
		int xRight = card_width + 2 * padding;
		int i ;
		for (i = 0; i < 39; i++) {
			ImageView card_top_right = new ImageView(this);
			card_top_right.setClickable(true);
			card_top_right.setImageBitmap(cardBack);
			card_top_right.setId(i + 1);
			AbsoluteLayout.LayoutParams lpCardRight = new AbsoluteLayout.LayoutParams(
					card_width, card_height, xRight, i * marginTop);
			card_top_right.setLayoutParams(lpCardRight);
			card_top_right.setOnClickListener(this);
			as.addView(card_top_right);
		}

		// Create 39 Cards left
		int xLeft = padding;
		int j;
		for (j = 0; j < 39; j++) {
			ImageView card_top_left = new ImageView(this);
			card_top_left.setClickable(true);
			card_top_left.setImageBitmap(cardBack);
			card_top_left.setId(j + 40);
			AbsoluteLayout.LayoutParams lpCardLeft = new AbsoluteLayout.LayoutParams(
					card_width, card_height, xLeft, j * marginTop);
			card_top_left.setLayoutParams(lpCardLeft);
			card_top_left.setOnClickListener(this);
			as.addView(card_top_left);
		}

	}

	@Override
	protected void onStart() {

		super.onStart();

		if (ConfigData.IS_SOUND_ON) {
			ConfigData.playSound(R.raw.cards_layout);
		}

		controller = new LayoutAnimationController(
				ConfigData.animation_spread_card);

		as.setLayoutAnimation(controller);

		Toast.makeText(this, "Rút " + theNumberCardNeedToSelect + " lá bài",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_menu, menu);
		return true;
	}

	@SuppressLint("ResourceType")
	@Override
	public void onClick(View v) {

		if (v.getId() >= 1 && v.getId() <= 78
				&& cardSelectedCount < theNumberCardNeedToSelect) {
			// move card selected on top
			as.bringChildToFront(v);

			// try to remove previous card still not remove because
			// has new card selected before it end animation
			try {
				as.removeView(cardNeedRemove);
			} catch (Exception e) {

			}

			// run animation
			cardSelectedCount++;
			cardNeedRemove = (ImageView) v;
			cardNeedRemove.startAnimation(ConfigData.animation_select_card);
			ConfigData.playSound(R.raw.single_card);

			return;
		}

		if (v.getId() == R.id.btnSkip) {
			ConfigData.stopSound();
			cardSelectedCount = theNumberCardNeedToSelect;

			if (spreadId == -1) {
				// Random one card to show
				ConfigData.randomOneCard();
				startActivity(new Intent(this,
						SingleCardActivity.class));
			} else {
				// Show Spread card activity of spreadId
				Intent intentSpreadCardsActivity = new Intent(this,
						SpreadCardsActivity.class);
				intentSpreadCardsActivity.putExtra("spreadId", spreadId);
				ConfigData.randomMultipleCards(SpreadCardJasonHelper
						.getStepArray(spreadId).length);
				this.startActivity(intentSpreadCardsActivity);
			}

			this.finish();
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {

		if (animation == ConfigData.animation_select_card) {
			if (cardSelectedCount == theNumberCardNeedToSelect) {

				if (spreadId == -1) {
					// Random one card to show
					ConfigData.randomOneCard();
					startActivity(new Intent(this,
							SingleCardActivity.class));
				} else {
					// Show Spread card activity of spreadId
					Intent intentSpreadCardsActivity = new Intent(this,
							SpreadCardsActivity.class);
					intentSpreadCardsActivity.putExtra("spreadId", spreadId);
					ConfigData.randomMultipleCards(SpreadCardJasonHelper
							.getStepArray(spreadId).length);
					this.startActivity(intentSpreadCardsActivity);
				}
				this.finish();
			}
		}

		try {
			if (cardNeedRemove != null)
				as.removeView(cardNeedRemove);
		} catch (Exception e) {

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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			as.removeAllViews();
			cardBack.recycle();
			System.gc();
		} catch (Exception e) {

		}
	}

}
