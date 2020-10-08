package com.wakeup.tarot.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import androidx.fragment.app.Fragment;

import com.wakeup.tarot.R;
import com.wakeup.tarot.adapter.AssociationItemViewAdapter;
import com.wakeup.tarot.data.CardsDetailJasonHelper;
import com.wakeup.tarot.data.ConfigData;
import com.wakeup.tarot.data.MapData;
import com.wakeup.tarot.data.SpreadCardJasonHelper;
import com.wakeup.tarot.util.ImageLoaderAsynch;
import com.wakeup.tarot.util.ImageWorker;
import com.wakeup.tarot.view.BrowseGroupCardsActivity;
import com.wakeup.tarot.view.CardDetailViewPagerForSpreadCardActivity;
import com.wakeup.tarot.view.SpreadCardsActivity;

/**
 * This fragment will populate the children of the ViewPager from
 * {@link CardDetailViewPagerForSpreadCardActivity}.
 */
public class CardDetailForSpreadCardFragment extends Fragment implements
		OnClickListener {
	private static final String CARD_INDEX = "card_index";
	private int mCardIndex;
	private Resources mResources;
	private CardDetailViewPagerForSpreadCardActivity mActivity;
	private ImageLoaderAsynch mImageLoader;

	/**
	 * Factory method to generate a new instance of the fragment given an image
	 * number.
	 * 
	 * @param cardIndex
	 *            The card index in array spread card to load
	 * @return A new instance of ImageDetailFragment with imageNum extras
	 */
	public static CardDetailForSpreadCardFragment newInstance(int cardIndex) {
		final CardDetailForSpreadCardFragment f = new CardDetailForSpreadCardFragment();
		final Bundle args = new Bundle();
		args.putInt(CARD_INDEX, cardIndex);
		f.setArguments(args);

		return f;
	}

	/**
	 * Empty constructor as per the Fragment documentation
	 */
	public CardDetailForSpreadCardFragment() {
	}

	/**
	 * Populate image using a cardId from extras, use the convenience factory
	 * method {@link CardDetailForSpreadCardFragment#newInstance(int)} to create
	 * this fragment.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCardIndex = getArguments() != null ? getArguments().getInt(CARD_INDEX)
				: 0;
		mResources = getActivity().getResources();
		mActivity = (CardDetailViewPagerForSpreadCardActivity) getActivity();
		mImageLoader = mActivity.getImageLoader();
	}

	View view;
	ScrollView svCardSpread;
	TextView tvKeyWords;
	TextView tvStepInfo;
	TextView tvKeyWordsDetail;
	Button btn_expand_reverse_keywords;
	TextView tvReverseKeyWordsDetail;
	Button btn_view_card_interpretation;
	TextView tvKeywordForward;
	TextView tvInterpretations;
	TextView tvForward;
	TextView tvKeywordReverse;
	ImageView ivDivider;
	TextView tvReverse;
	Button btn_expand_reverse_interpretations;
	Button btn_view_card_association;
	ScrollView svCardAssociations;
	ImageView mImageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * Inflate and locate the main ImageView
		 */
		final View view = inflater.inflate(
				R.layout.fragment_card_detail_view_pager_spread_card,
				container, false);

		// Load Image Card
		mImageView = (ImageView) view.findViewById(R.id.ivCardImage);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show more detail of card in Scroll View hide below
				mActivity.isShowDetail = true;
				mActivity.updateShowHideDetailForViewPager();
			}
		});
		// Use the parent mActivity to load the image asynchronously into the
		// ImageView (so a single
		// cache can be used over all pages in the ViewPager
		if (CardDetailViewPagerForSpreadCardActivity.class
				.isInstance(getActivity())) {
			// Finally load the image asynchronously into the ImageView, this
			// also
			// takes care of
			// setting a placeholder image while the background thread runs
			int w = ConfigData.SCREEN_WIDTH / 9 * 8;
			int h = w * 1232 / 710;
			if (ConfigData.randomCardDimensionsArray[mCardIndex] == 1) {
				mImageLoader
						.loadImage(
								MapData.arrCardImage_R_Id[ConfigData.randomCardIdArray[mCardIndex]]
										+ "_" + w + "_" + h + "_" + 180,
								mImageView);
			} else {
				mImageLoader
						.loadImage(
								MapData.arrCardImage_R_Id[ConfigData.randomCardIdArray[mCardIndex]]
										+ "_" + w + "_" + h, mImageView);
			}

		}

		/**
		 * Load all info of card right here
		 */
		// 1. Load all components of ScrollView CardSpread
		svCardSpread = (ScrollView) view.findViewById(R.id.svCardSpread);
		svCardSpread.setOnClickListener(this);

		tvStepInfo = (TextView) view.findViewById(R.id.tvStepInfo);
		tvStepInfo.setTextSize(ConfigData.FONT_SIZE);
		tvStepInfo.setText(SpreadCardJasonHelper
				.getStepArray(SpreadCardsActivity.spreadId)[mCardIndex]);

		tvKeyWords = (TextView) view.findViewById(R.id.tvKeyWords);
		tvKeyWords.setTextSize(ConfigData.FONT_SIZE);
		tvKeyWords.setTypeface(ConfigData.UVNCatBien_R);
		tvKeyWords.setOnClickListener(this);

		tvKeyWordsDetail = (TextView) view.findViewById(R.id.tvKeyWordsDetail);
		tvKeyWordsDetail.setTextSize(ConfigData.FONT_SIZE);
		tvKeyWordsDetail.setText(CardsDetailJasonHelper
				.getKeywordForward(ConfigData.randomCardIdArray[mCardIndex]));
		tvKeyWordsDetail.setOnClickListener(this);

		tvReverseKeyWordsDetail = (TextView) view
				.findViewById(R.id.tvReverseKeyWordsDetail);
		tvReverseKeyWordsDetail.setTextSize(ConfigData.FONT_SIZE);
		tvReverseKeyWordsDetail.setText(CardsDetailJasonHelper
				.getKeywordReverse(ConfigData.randomCardIdArray[mCardIndex]));
		tvReverseKeyWordsDetail.setOnClickListener(this);

		btn_expand_reverse_keywords = (Button) view
				.findViewById(R.id.btn_expand_reverse_keywords);
		btn_expand_reverse_keywords.setTextSize(ConfigData.FONT_SIZE);
		btn_expand_reverse_keywords.setTypeface(ConfigData.UVNCatBien_R);
		btn_expand_reverse_keywords.setTag(R.id.tvReverseKeyWordsDetail,
				tvReverseKeyWordsDetail);
		btn_expand_reverse_keywords.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (((TextView) v.getTag(R.id.tvReverseKeyWordsDetail))
						.getVisibility() == View.GONE) {
					((TextView) v.getTag(R.id.tvReverseKeyWordsDetail))
							.setVisibility(View.VISIBLE);
					((Button) v).setCompoundDrawablesWithIntrinsicBounds(
							mActivity.getResources().getDrawable(
									R.drawable.close_item), null, null, null);

				} else {
					((TextView) v.getTag(R.id.tvReverseKeyWordsDetail))
							.setVisibility(View.GONE);
					((Button) v).setCompoundDrawablesWithIntrinsicBounds(
							mActivity.getResources().getDrawable(
									R.drawable.expand_item, mActivity.getTheme()), null, null, null);
				}
			}
		});

		btn_view_card_interpretation = (Button) view
				.findViewById(R.id.btn_view_card_interpretation);
		btn_view_card_interpretation.setOnClickListener(this);
		btn_view_card_interpretation.setTypeface(ConfigData.UVNCatBien_R);
		btn_view_card_interpretation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.btnCardInterpretationClicked();
			}
		});

		// 2. Load all components of ScrollView CardInterpretation

		tvInterpretations = (TextView) view
				.findViewById(R.id.tvInterpretations);
		tvInterpretations.setTextSize(ConfigData.FONT_SIZE);
		tvInterpretations.setTypeface(ConfigData.UVNCatBien_R);
		tvInterpretations.setOnClickListener(this);

		tvKeywordForward = (TextView) view.findViewById(R.id.tvKeywordForward);
		tvKeywordForward.setTextSize(ConfigData.FONT_SIZE);
		tvKeywordForward.setText(CardsDetailJasonHelper
				.getKeywordForward(ConfigData.randomCardIdArray[mCardIndex]));
		tvKeywordForward.setOnClickListener(this);

		tvForward = (TextView) view.findViewById(R.id.tvForward);
		tvForward.setTextSize(ConfigData.FONT_SIZE);
		tvForward.setText(CardsDetailJasonHelper
				.getForward(ConfigData.randomCardIdArray[mCardIndex]));
		tvForward.setOnClickListener(this);

		tvKeywordReverse = (TextView) view.findViewById(R.id.tvKeywordReverse);
		tvKeywordReverse.setTextSize(ConfigData.FONT_SIZE);
		tvKeywordReverse.setText(CardsDetailJasonHelper
				.getKeywordReverse(ConfigData.randomCardIdArray[mCardIndex]));
		tvKeywordReverse.setOnClickListener(this);

		ivDivider = (ImageView) view.findViewById(R.id.ivDivider2);

		tvReverse = (TextView) view.findViewById(R.id.tvReverse);
		tvReverse.setTextSize(ConfigData.FONT_SIZE);
		tvReverse.setText(CardsDetailJasonHelper
				.getReverse(ConfigData.randomCardIdArray[mCardIndex]));
		tvReverse.setOnClickListener(this);

		btn_expand_reverse_interpretations = (Button) view
				.findViewById(R.id.btn_expand_reverse_interpretations);
		btn_expand_reverse_interpretations.setTextSize(ConfigData.FONT_SIZE);
		btn_expand_reverse_interpretations
				.setTypeface(ConfigData.UVNCatBien_R);
		btn_expand_reverse_interpretations.setTag(R.id.tvKeywordReverse,
				tvKeywordReverse);
		btn_expand_reverse_interpretations.setTag(R.id.ivDivider, ivDivider);
		btn_expand_reverse_interpretations.setTag(R.id.tvReverse, tvReverse);
		btn_expand_reverse_interpretations
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (((TextView) v.getTag(R.id.tvKeywordReverse))
								.getVisibility() == View.GONE) {
							((TextView) v.getTag(R.id.tvKeywordReverse))
									.setVisibility(View.VISIBLE);
							((ImageView) v.getTag(R.id.ivDivider))
									.setVisibility(View.VISIBLE);
							((TextView) v.getTag(R.id.tvReverse))
									.setVisibility(View.VISIBLE);
							((Button) v)
									.setCompoundDrawablesWithIntrinsicBounds(
											mActivity
													.getResources()
													.getDrawable(
															R.drawable.close_item),
											null, null, null);
						} else {
							((TextView) v.getTag(R.id.tvKeywordReverse))
									.setVisibility(View.GONE);
							((ImageView) v.getTag(R.id.ivDivider))
									.setVisibility(View.GONE);
							((TextView) v.getTag(R.id.tvReverse))
									.setVisibility(View.GONE);
							((Button) v)
									.setCompoundDrawablesWithIntrinsicBounds(
											mActivity
													.getResources()
													.getDrawable(
															R.drawable.expand_item),
											null, null, null);
						}
					}
				});

		btn_view_card_association = (Button) view
				.findViewById(R.id.btn_view_card_association);
		btn_view_card_association.setTypeface(ConfigData.UVNCatBien_R);
		btn_view_card_association.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.btnCardAssociationClicked();
			}
		});

		ScrollView svCardAssociations = (ScrollView) view
				.findViewById(R.id.svCardAssociations);
		svCardAssociations.setOnClickListener(this);

		/**
		 * get suit group
		 */
		LinearLayout layoutSuit = (LinearLayout) view
				.findViewById(R.id.layoutSuit);
		layoutSuit.setOnClickListener(this);
		Button btnSuit = (Button) view.findViewById(R.id.btnSuit);
		btnSuit.setTypeface(ConfigData.UVNCatBien_R);
		btnSuit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentSuit = new Intent(mActivity,
						BrowseGroupCardsActivity.class);
				intentSuit.putExtra("mode", 0);
				mActivity.startActivity(intentSuit);
			}
		});

		TableLayout suitTable = (TableLayout) view.findViewById(R.id.suitTable);
		suitTable.setOnClickListener(this);
		String[] arrSuitId = CardsDetailJasonHelper
				.getSuitIds(ConfigData.randomCardIdArray[mCardIndex]);

		if (arrSuitId != null) {
			AssociationItemViewAdapter suitAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrSuitId);
			int i = 0;
			while (i < arrSuitId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
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
		 * get star group
		 */
		LinearLayout layoutStar = (LinearLayout) view
				.findViewById(R.id.layoutStar);
		layoutStar.setOnClickListener(this);
		Button btnStar = (Button) view.findViewById(R.id.btnStar);
		btnStar.setTypeface(ConfigData.UVNCatBien_R);
		btnStar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentStar = new Intent(mActivity,
						BrowseGroupCardsActivity.class);
				intentStar.putExtra("mode", 1);
				mActivity.startActivity(intentStar);
			}
		});

		TableLayout starTable = (TableLayout) view.findViewById(R.id.starTable);
		starTable.setOnClickListener(this);
		String[] arrStarId = CardsDetailJasonHelper
				.getStarIds(ConfigData.randomCardIdArray[mCardIndex]);
		if (arrStarId != null) {
			AssociationItemViewAdapter starAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrStarId);
			int i = 0;
			while (i < arrStarId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
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
		 * get number group
		 */
		LinearLayout layoutNumber = (LinearLayout) view
				.findViewById(R.id.layoutNumber);
		layoutNumber.setOnClickListener(this);
		Button btnNumber = (Button) view.findViewById(R.id.btnNumber);
		btnNumber.setTypeface(ConfigData.UVNCatBien_R);
		btnNumber.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentNumber = new Intent(mActivity,
						BrowseGroupCardsActivity.class);
				intentNumber.putExtra("mode", 2);
				mActivity.startActivity(intentNumber);
			}
		});

		TableLayout numberTable = (TableLayout) view
				.findViewById(R.id.numberTable);
		numberTable.setOnClickListener(this);
		String[] arrNumberId = CardsDetailJasonHelper
				.getNumberIds(ConfigData.randomCardIdArray[mCardIndex]);
		if (arrNumberId != null) {
			AssociationItemViewAdapter numberAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrNumberId);
			int i = 0;
			while (i < arrNumberId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
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
		 * get symbol group
		 */
		LinearLayout layoutSymbol = (LinearLayout) view
				.findViewById(R.id.layoutSymbol);
		layoutSymbol.setOnClickListener(this);
		Button btnSymbol = (Button) view.findViewById(R.id.btnSymbol);
		btnSymbol.setTypeface(ConfigData.UVNCatBien_R);
		btnSymbol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity,
						BrowseGroupCardsActivity.class);
				intent.putExtra("mode", 3);
				mActivity.startActivity(intent);
			}
		});

		btnNumber.setTypeface(ConfigData.UVNCatBien_R);
		TableLayout symbolTable = (TableLayout) view
				.findViewById(R.id.symbolTable);
		symbolTable.setOnClickListener(this);
		String[] arrSymbolId = CardsDetailJasonHelper
				.getSymbolIds(ConfigData.randomCardIdArray[mCardIndex]);
		if (arrSymbolId != null) {
			AssociationItemViewAdapter symbolAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrSymbolId);
			int i = 0;
			while (i < arrSymbolId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
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

		/**
		 * reverse keyword move to first position of view
		 */
		if (ConfigData.randomCardDimensionsArray[mCardIndex] == 1) {

			// Reverse for svCardSpread
			CharSequence s1 = tvKeyWords.getText();
			CharSequence s2 = tvKeyWordsDetail.getText();

			tvKeyWords.setText(btn_expand_reverse_keywords.getText());
			tvKeyWordsDetail.setText(tvReverseKeyWordsDetail.getText());

			btn_expand_reverse_keywords.setText(s1);
			tvReverseKeyWordsDetail.setText(s2);

			// Reverse for svCardInterpretation
			CharSequence s0 = tvInterpretations.getText();
			s1 = tvKeywordForward.getText();
			s2 = tvForward.getText();

			tvInterpretations.setText(btn_expand_reverse_interpretations
					.getText());
			tvKeywordForward.setText(tvKeywordReverse.getText());
			tvForward.setText(tvReverse.getText());

			btn_expand_reverse_interpretations.setText(s0);
			tvKeywordReverse.setText(s1);
			tvReverse.setText(s2);
		}

		// Click at Ad1
		((ImageView) view.findViewById(R.id.ivAd1))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
						Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
								uriUrl);
						startActivity(launchBrowser);
					}
				});

		// Click at Ad2
		((ImageView) view.findViewById(R.id.ivAd2))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
						Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
								uriUrl);
						startActivity(launchBrowser);
					}
				});

		// Click at Ad3
		((ImageView) view.findViewById(R.id.ivAd3))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
						Intent launchBrowser = new Intent(Intent.ACTION_VIEW,
								uriUrl);
						startActivity(launchBrowser);
					}
				});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mImageView != null) {
			// Cancel any pending image work
			ImageWorker.cancelWork(mImageView);
			mImageView.setImageDrawable(null);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		mActivity.isShowDetail = false;
		mActivity.updateShowHideDetailForViewPager();
	}
}
