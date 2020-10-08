package com.wakeup.tarot.view;

import android.net.Uri;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

import androidx.fragment.app.FragmentActivity;

import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.AssociationItemViewAdapter;
import com.wakeup.tarot.animation.AnimationFactory;
import com.wakeup.tarot.data.CardsDetailJasonHelper;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.util.ImageCache;
import com.wakeup.tarot.util.ImageLoaderAsynch;

public class SingleCardActivity extends FragmentActivity implements OnClickListener {

	private ViewFlipper vfCardImage;
	private ImageView ivFontCard;
	private TextView tvCardName;
	private TextView tvBlueText;
	
	private ScrollView svCardSpread;
	private TextView tvKeyWords;
	private TextView tvKeyWordsDetail;
	private Button btn_expand_reverse_keywords;
	private TextView tvReverseKeyWordsDetail;
	private Button btn_view_card_interpretation;

	private ScrollView svCardInterpretation;
	private TextView tvInterpretation;
	private TextView tvInterpretationDetail;
	private Button btn_expand_reverse_interpretation;
	private TextView tvReverseInterpretationDetail;
	private Button btn_view_card_associations;

	private ScrollView svCardAssociations;

	private RelativeLayout bottom_bar;
	private Button btn_card_spread;
	private Button btn_card_interpretation;
	private Button btn_associations;

	private ImageLoaderAsynch mImageLoader;
	private static String EXTRA_IMAGE_CACHE_DIR = "extra_image_cache";

	// Visible mode
	// 1 for CardDetail
	// 2 for card interpretation
	// 3 for card association
	private int visibleMode = 1;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_card);

		// Reload screen size and background
		ConfigData.reloadScreen(this);
		
		// TODO SETTING FOR IMAGE CACHE
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(getApplicationContext(), EXTRA_IMAGE_CACHE_DIR);

		cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to
													// 25% of app memory

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageLoader = new ImageLoaderAsynch(getApplicationContext());
		mImageLoader.setLoadingImage(null);
		mImageLoader.addImageCache(getSupportFragmentManager(), cacheParams);
		mImageLoader.setImageFadeIn(false);
		
		// Load background
		((ImageView) findViewById(R.id.background)).setBackgroundDrawable(ConfigData.rbdBackground);

		// Init for View Fliper card component
		vfCardImage = (ViewFlipper) findViewById(R.id.vfCardImage);

		/**
		 * Bind a click listener to initiate the flip transitions
		 */

		// Flip card when user Click at back card
		this.findViewById(R.id.ivBackCard).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// This is all you need to do to 3D flip
						AnimationFactory.flipTransition(vfCardImage,
								AnimationFactory.FlipDirection.RIGHT_LEFT);

						if (ConfigData.IS_SOUND_ON) {
							MediaPlayer mPlayer = MediaPlayer.create(
									SingleCardActivity.this, R.raw.card_deal);
							mPlayer.start();
						}
					}

				});

		// Initial for all component otherwise
		ivFontCard = (ImageView) findViewById(R.id.ivFontCard);
		int w = ConfigData.SCREEN_WIDTH / 9 * 8;
		int h = w * 1232 / 710;
		if (ConfigData.randOneCardDimension == 1) {
			mImageLoader.loadImage(MapData.arrCardImage_R_Id[ConfigData.randOneCardId] + "_" + w + "_" + h + "_" + 180, ivFontCard);
		} else {
			mImageLoader.loadImage(MapData.arrCardImage_R_Id[ConfigData.randOneCardId] + "_" + w + "_" + h, ivFontCard);
		}
		ivFontCard.setOnClickListener(this);
		
		tvCardName = (TextView) findViewById(R.id.tvCardName);
		tvCardName.setTypeface(ConfigData.UVNCatBien_R);
		tvCardName.setText(CardsDetailJasonHelper
				.getEnglishCardName(ConfigData.randOneCardId));

		tvBlueText = (TextView) findViewById(R.id.tvBlueText);
		tvBlueText.setTextSize(ConfigData.FONT_SIZE);
		tvBlueText.setOnClickListener(this);

		// Init for Card Detail ScrollView
		svCardSpread = (ScrollView) findViewById(R.id.svCardSpread);
		svCardSpread.setOnClickListener(this);
		tvKeyWords = (TextView) findViewById(R.id.tvKeyWords);
		tvKeyWords.setTextSize(ConfigData.FONT_SIZE);
		tvKeyWords.setTypeface(ConfigData.UVNCatBien_R);
		tvKeyWords.setOnClickListener(this);

		tvKeyWordsDetail = (TextView) findViewById(R.id.tvKeyWordsDetail);
		tvKeyWordsDetail.setTextSize(ConfigData.FONT_SIZE);
		tvKeyWordsDetail.setText(CardsDetailJasonHelper
				.getKeywordForward(ConfigData.randOneCardId));
		tvKeyWordsDetail.setOnClickListener(this);

		btn_expand_reverse_keywords = (Button) findViewById(R.id.btn_expand_reverse_keywords);
		btn_expand_reverse_keywords.setTextSize(ConfigData.FONT_SIZE);
		btn_expand_reverse_keywords.setTypeface(ConfigData.UVNCatBien_R);
		btn_expand_reverse_keywords.setOnClickListener(this);

		tvReverseKeyWordsDetail = (TextView) findViewById(R.id.tvReverseKeyWordsDetail);
		tvReverseKeyWordsDetail.setTextSize(ConfigData.FONT_SIZE);
		tvReverseKeyWordsDetail.setText(CardsDetailJasonHelper
				.getKeywordReverse(ConfigData.randOneCardId));
		tvReverseKeyWordsDetail.setOnClickListener(this);

		btn_view_card_interpretation = (Button) findViewById(R.id.btn_view_card_interpretation);
		btn_view_card_interpretation.setOnClickListener(this);
		btn_view_card_interpretation.setTypeface(ConfigData.UVNCatBien_R);

		svCardInterpretation = (ScrollView) findViewById(R.id.svCardInterpretation);
		svCardInterpretation.setOnClickListener(this);
		tvInterpretation = (TextView) findViewById(R.id.tvInterpretation);
		tvInterpretation.setTextSize(ConfigData.FONT_SIZE);
		tvInterpretation.setTypeface(ConfigData.UVNCatBien_R);
		tvInterpretation.setOnClickListener(this);

		tvInterpretationDetail = (TextView) findViewById(R.id.tvInterpretationDetail);
		tvInterpretationDetail.setTextSize(ConfigData.FONT_SIZE);
		tvInterpretationDetail.setText(CardsDetailJasonHelper
				.getForward(ConfigData.randOneCardId));
		tvInterpretationDetail.setOnClickListener(this);

		btn_expand_reverse_interpretation = (Button) findViewById(R.id.btn_expand_reverse_interpretation);
		btn_expand_reverse_interpretation.setTextSize(ConfigData.FONT_SIZE);
		btn_expand_reverse_interpretation.setTypeface(ConfigData.UVNCatBien_R);
		btn_expand_reverse_interpretation.setOnClickListener(this);
		btn_view_card_associations = (Button) findViewById(R.id.btn_view_card_associations);
		btn_view_card_associations.setOnClickListener(this);
		tvReverseInterpretationDetail = (TextView) findViewById(R.id.tvReverseInterpretationDetail);
		tvReverseInterpretationDetail.setTextSize(ConfigData.FONT_SIZE);
		tvReverseInterpretationDetail.setText(CardsDetailJasonHelper
				.getReverse(ConfigData.randOneCardId));
		tvReverseInterpretationDetail.setOnClickListener(this);

		btn_view_card_associations.setTypeface(ConfigData.UVNCatBien_R);

		// Load Scroll view for card associations info
		svCardAssociations = (ScrollView) findViewById(R.id.svCardAssociations);

		/**
		 * Get SUIT GROUP
		 */
		LinearLayout layoutSuit = (LinearLayout) findViewById(R.id.layoutSuit);
		layoutSuit.setOnClickListener(this);
		Button btnSuit = (Button) findViewById(R.id.btnSuit);
		btnSuit.setOnClickListener(this);
		btnSuit.setTypeface(ConfigData.UVNCatBien_R);
		TableLayout suitTable = (TableLayout) findViewById(R.id.suitTable);
		suitTable.setOnClickListener(this);
		String[] arrSuitId = CardsDetailJasonHelper.getSuitIds(ConfigData.randOneCardId);

		if (arrSuitId != null) {
			AssociationItemViewAdapter suitAdapter = new AssociationItemViewAdapter(
					this, mImageLoader, arrSuitId);
			int i = 0;
			while (i < arrSuitId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(this);
				tr.setOnClickListener(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				// add 3 association item to this row if has
				for (int c = 0; c < 3; c++) {
					// create association
					tr.addView(suitAdapter.getView(i, null, null));
					i++;
					if (i == arrSuitId.length)
						break;
				}
				suitTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			}

		} else {
			layoutSuit.setVisibility(View.GONE);
		}

		/**
		 * Get STAR GROUP
		 */
		LinearLayout layoutStar = (LinearLayout) findViewById(R.id.layoutStar);
		layoutStar.setOnClickListener(this);
		Button btnStar = (Button) findViewById(R.id.btnStar);
		btnStar.setOnClickListener(this);
		btnStar.setTypeface(ConfigData.UVNCatBien_R);
		TableLayout starTable = (TableLayout) findViewById(R.id.starTable);
		starTable.setOnClickListener(this);
		String[] arrStarId = CardsDetailJasonHelper.getStarIds(ConfigData.randOneCardId);
		if (arrStarId != null) {
			AssociationItemViewAdapter starAdapter = new AssociationItemViewAdapter(
					this, mImageLoader, arrStarId);
			int i = 0;
			while (i < arrStarId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(this);
				tr.setOnClickListener(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				// add 3 association item to this row if has
				for (int c = 0; c < 3; c++) {
					// create association
					tr.addView(starAdapter.getView(i, null, null));
					i++;
					if (i == arrStarId.length)
						break;
				}
				starTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			}

		} else {
			layoutStar.setVisibility(View.GONE);
		}

		/**
		 * Get NUMBER GROUP
		 */
		LinearLayout layoutNumber = (LinearLayout) findViewById(R.id.layoutNumber);
		layoutNumber.setOnClickListener(this);
		Button btnNumber = (Button) findViewById(R.id.btnNumber);
		btnNumber.setOnClickListener(this);
		btnNumber.setTypeface(ConfigData.UVNCatBien_R);
		TableLayout numberTable = (TableLayout) findViewById(R.id.numberTable);
		numberTable.setOnClickListener(this);
		String[] arrNumberId = CardsDetailJasonHelper.getNumberIds(ConfigData.randOneCardId);
		if (arrNumberId != null) {
			AssociationItemViewAdapter numberAdapter = new AssociationItemViewAdapter(
					this, mImageLoader, arrNumberId);
			int i = 0;
			while (i < arrNumberId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(this);
				tr.setOnClickListener(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				// add 3 association item to this row if has
				for (int c = 0; c < 3; c++) {
					// create association
					tr.addView(numberAdapter.getView(i, null, null));
					i++;
					if (i == arrNumberId.length)
						break;
				}
				numberTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			}

		} else {
			layoutNumber.setVisibility(View.GONE);
		}

		/**
		 * Get SYMBOL GROUP
		 */
		LinearLayout layoutSymbol = (LinearLayout) findViewById(R.id.layoutSymbol);
		layoutSymbol.setOnClickListener(this);
		Button btnSymbol = (Button) findViewById(R.id.btnSymbol);
		btnSymbol.setOnClickListener(this);
		btnSymbol.setTypeface(ConfigData.UVNCatBien_R);
		TableLayout symbolTable = (TableLayout) findViewById(R.id.symbolTable);
		symbolTable.setOnClickListener(this);
		String[] arrSymbolId = CardsDetailJasonHelper.getSymbolIds(ConfigData.randOneCardId);
		if (arrSymbolId != null) {
			AssociationItemViewAdapter symbolAdapter = new AssociationItemViewAdapter(
					this, mImageLoader, arrSymbolId);
			int i = 0;
			while (i < arrSymbolId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(this);
				tr.setOnClickListener(this);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				// add 3 association item to this row if has
				for (int c = 0; c < 3; c++) {
					// create association
					tr.addView(symbolAdapter.getView(i, null, null));
					i++;
					if (i == arrSymbolId.length)
						break;
				}
				symbolTable.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			}

		} else {
			layoutSymbol.setVisibility(View.GONE);
		}

		// Init for Bottom button bar
		bottom_bar = (RelativeLayout) findViewById(R.id.bottom_bar);

		btn_card_spread = (Button) findViewById(R.id.btn_card_spread);
		btn_card_spread.setOnClickListener(this);

		btn_card_interpretation = (Button) findViewById(R.id.btn_card_interpretation);
		btn_card_interpretation.setOnClickListener(this);

		btn_associations = (Button) findViewById(R.id.btn_associations);
		btn_associations.setOnClickListener(this);

		/**
		 * reverse keyword move to first position of view
		 */
		handleReverseCard();

		visibleMode = 1;
		updateVisibleMode();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = true;
		super.onBackPressed();
	}
	
	private void handleReverseCard() {
		if (ConfigData.randOneCardDimension == 1) {

			// Reverse for svCardSpread
			CharSequence s1 = tvKeyWords.getText();
			CharSequence s2 = tvKeyWordsDetail.getText();

			tvKeyWords.setText(btn_expand_reverse_keywords.getText());
			tvKeyWordsDetail.setText(tvReverseKeyWordsDetail.getText());

			btn_expand_reverse_keywords.setText(s1);
			tvReverseKeyWordsDetail.setText(s2);

			// Reverse for svCardInterpretation
			s1 = tvInterpretation.getText();
			s2 = tvInterpretationDetail.getText();

			tvInterpretation.setText(btn_expand_reverse_interpretation
					.getText());
			tvInterpretationDetail.setText(tvReverseInterpretationDetail
					.getText());

			btn_expand_reverse_interpretation.setText(s1);
			tvReverseInterpretationDetail.setText(s2);

		}
	}

	private void updateVisibleMode() {
		if (tvCardName.getVisibility() == View.VISIBLE) {
			tvCardName.setVisibility(View.VISIBLE);
			bottom_bar.setVisibility(View.VISIBLE);

			switch (visibleMode) {
			case 1:
				svCardSpread.setVisibility(View.VISIBLE);
				svCardInterpretation.setVisibility(View.INVISIBLE);
				svCardAssociations.setVisibility(View.INVISIBLE);
				break;
			case 2:
				svCardSpread.setVisibility(View.INVISIBLE);
				svCardInterpretation.setVisibility(View.VISIBLE);
				svCardAssociations.setVisibility(View.INVISIBLE);
				break;
			case 3:
				svCardSpread.setVisibility(View.INVISIBLE);
				svCardInterpretation.setVisibility(View.INVISIBLE);
				svCardAssociations.setVisibility(View.VISIBLE);
				break;
			}
		} else {
			tvCardName.setVisibility(View.INVISIBLE);
			bottom_bar.setVisibility(View.INVISIBLE);
			svCardSpread.setVisibility(View.INVISIBLE);
			svCardInterpretation.setVisibility(View.INVISIBLE);
			svCardAssociations.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_card_spread:
			btn_card_spread
					.setBackgroundResource(R.drawable.btn_card_spread_selected);
			btn_card_interpretation
					.setBackgroundResource(R.drawable.btn_card_interpretation);
			btn_associations.setBackgroundResource(R.drawable.btn_associations);

			// other process below here
			visibleMode = 1;
			updateVisibleMode();
			break;

		case R.id.btn_view_card_interpretation:
		case R.id.btn_card_interpretation:
			btn_card_spread.setBackgroundResource(R.drawable.btn_card_spread);
			btn_card_interpretation
					.setBackgroundResource(R.drawable.btn_card_interpretation_selected);
			btn_associations.setBackgroundResource(R.drawable.btn_associations);
			// other process below here
			visibleMode = 2;
			updateVisibleMode();
			break;

		case R.id.btn_view_card_associations:
		case R.id.btn_associations:
			btn_card_spread.setBackgroundResource(R.drawable.btn_card_spread);
			btn_card_interpretation
					.setBackgroundResource(R.drawable.btn_card_interpretation);
			btn_associations
					.setBackgroundResource(R.drawable.btn_associations_selected);
			// other process below here
			visibleMode = 3;
			updateVisibleMode();
			break;

		case R.id.btn_expand_reverse_keywords:
			if (tvReverseKeyWordsDetail.getVisibility() == View.GONE) {
				tvReverseKeyWordsDetail.setVisibility(View.VISIBLE);
				btn_expand_reverse_keywords
						.setCompoundDrawablesWithIntrinsicBounds(
								this.getResources().getDrawable(
										R.drawable.close_item), null, null,
								null);
			} else {
				tvReverseKeyWordsDetail.setVisibility(View.GONE);
				btn_expand_reverse_keywords
						.setCompoundDrawablesWithIntrinsicBounds(
								this.getResources().getDrawable(
										R.drawable.expand_item), null, null,
								null);
			}

			break;
		case R.id.btn_expand_reverse_interpretation:
			if (tvReverseInterpretationDetail.getVisibility() == View.GONE) {
				tvReverseInterpretationDetail.setVisibility(View.VISIBLE);
				btn_expand_reverse_interpretation
						.setCompoundDrawablesWithIntrinsicBounds(
								this.getResources().getDrawable(
										R.drawable.close_item), null, null,
								null);
			} else {
				tvReverseInterpretationDetail.setVisibility(View.GONE);
				btn_expand_reverse_interpretation
						.setCompoundDrawablesWithIntrinsicBounds(
								this.getResources().getDrawable(
										R.drawable.expand_item), null, null,
								null);
			}

			break;

		case R.id.svCardAssociations:
		case R.id.svCardInterpretation:
		case R.id.svCardDetail:
			tvCardName.setVisibility(View.INVISIBLE);
			bottom_bar.setVisibility(View.INVISIBLE);
			updateVisibleMode();
			break;

		case R.id.ivFontCard:
			tvCardName.setVisibility(View.VISIBLE);
			bottom_bar.setVisibility(View.VISIBLE);
			updateVisibleMode();
			break;

		case R.id.btnSuit:
			Intent intentSuit = new Intent(this, BrowseGroupCardsActivity.class);
			intentSuit.putExtra("mode", 0);
			this.startActivity(intentSuit);
			break;
		case R.id.btnStar:
			Intent intentStar = new Intent(this, BrowseGroupCardsActivity.class);
			intentStar.putExtra("mode", 1);
			this.startActivity(intentStar);
			break;
		case R.id.btnNumber:
			Intent intentNumber = new Intent(this,
					BrowseGroupCardsActivity.class);
			intentNumber.putExtra("mode", 2);
			this.startActivity(intentNumber);
			break;
		case R.id.btnSymbol:
			Intent intent = new Intent(this, BrowseGroupCardsActivity.class);
			intent.putExtra("mode", 3);
			this.startActivity(intent);
			break;

		default:
			tvCardName.setVisibility(View.INVISIBLE);
			updateVisibleMode();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.encyclopedia_menu, menu);
		return true;
	}
	

	@Override
	public void onResume() {
		super.onResume();
		mImageLoader.setExitTasksEarly(false);
		// Load background
		((ImageView) findViewById(R.id.background))
		.setBackground(ConfigData.rbdBackground);
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.setPauseWork(false);
		mImageLoader.setExitTasksEarly(true);
		mImageLoader.flushCache();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageLoader.clearMemCache();
		if (ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON) {
			mImageLoader.clearDiskCache();
			ConfigData.IS_USER_DESTROY_BY_BACK_BUTTON = false;
		}
		mImageLoader.closeCache();
	}
	
	public void onAdvertisementClick(View v) {
		Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}
}
