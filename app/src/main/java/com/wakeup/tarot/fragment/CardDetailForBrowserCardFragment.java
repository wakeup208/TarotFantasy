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
import com.wakeup.tarot.util.ImageLoaderAsynch;
import com.wakeup.tarot.util.ImageWorker;
import com.wakeup.tarot.view.BrowseGroupCardsActivity;
import com.wakeup.tarot.view.CardDetailViewPagerForBrowserCardActivity;

/**
 * This fragment will populate the children of the ViewPager from
 * {@link CardDetailViewPagerForBrowserCardActivity}.
 */
public class CardDetailForBrowserCardFragment extends Fragment implements
		OnClickListener {
	private static final String CARD_ID = "card_id";
	private int mCardId;
	private ImageView mImageView;
	private Resources mResources;
	private CardDetailViewPagerForBrowserCardActivity mActivity;
	private ImageLoaderAsynch mImageLoader;

	/**
	 * Factory method to generate a new instance of the fragment given an image
	 * number.
	 * 
	 * @param cardId
	 *            The image id to load
	 * @return A new instance of ImageDetailFragment with imageNum extras
	 */
	public static CardDetailForBrowserCardFragment newInstance(int cardId) {
		final CardDetailForBrowserCardFragment f = new CardDetailForBrowserCardFragment();
		final Bundle args = new Bundle();
		args.putInt(CARD_ID, cardId);
		f.setArguments(args);

		return f;
	}

	/**
	 * Empty constructor as per the Fragment documentation
	 */
	public CardDetailForBrowserCardFragment() {
	}

	/**
	 * Populate image using a cardId from extras, use the convenience factory
	 * method {@link CardDetailForBrowserCardFragment#newInstance(int)} to
	 * create this fragment.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCardId = getArguments() != null ? getArguments().getInt(CARD_ID) : -1;
		mResources = getActivity().getResources();
		mActivity = (CardDetailViewPagerForBrowserCardActivity) getActivity();
		mImageLoader = mActivity.getImageLoader();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/**
		 * Inflate and locate the main ImageView
		 */
		final View view = inflater.inflate(
				R.layout.fragment_card_detail_view_pager_browser_card,
				container, false);

		// Load Image Card
		mImageView = (ImageView) view.findViewById(R.id.ivCardImage);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.isShowDetail = true;
				mActivity.updateShowHideDetailForViewPager();
			}
		});
		// Use the parent activity to load the image asynchronously into the
		// ImageView (so a single
		// cache can be used over all pages in the ViewPager
		if (CardDetailViewPagerForBrowserCardActivity.class
				.isInstance(getActivity())) {
			// Finally load the image asynchronously into the ImageView, this
			// also
			// takes care of
			// setting a placeholder image while the background thread runs
			int w = ConfigData.SCREEN_WIDTH / 9 * 8;
			int h = w * 1232 / 710;
			mImageLoader.loadImage(MapData.arrCardImage_R_Id[mCardId] + "_" + w
					+ "_" + h, mImageView);
		}

		
		/**
		 * Load all info of card right here
		 */
		// tvKeywordForward
		final TextView tvKeywordForward = (TextView) view
				.findViewById(R.id.tvKeywordForward);
		tvKeywordForward.setTextSize(ConfigData.FONT_SIZE);
		tvKeywordForward.setText(CardsDetailJasonHelper
				.getKeywordForward(mCardId));
		tvKeywordForward.setOnClickListener(this);

		// tvForward
		final TextView tvForward = (TextView) view.findViewById(R.id.tvForward);
		tvForward.setTextSize(ConfigData.FONT_SIZE);
		tvForward.setText(CardsDetailJasonHelper.getForward(mCardId));
		tvForward.setOnClickListener(this);

		// tvKeywordReverse
		final TextView tvKeywordReverse = (TextView) view
				.findViewById(R.id.tvKeywordReverse);
		tvKeywordReverse.setTextSize(ConfigData.FONT_SIZE);
		tvKeywordReverse.setText(CardsDetailJasonHelper
				.getKeywordReverse(mCardId));
		tvKeywordReverse.setOnClickListener(this);

		// ivDivider
		final ImageView ivDivider = (ImageView) view
				.findViewById(R.id.ivDivider);
		TextView tvReverse = (TextView) view.findViewById(R.id.tvReverse);
		tvReverse.setTextSize(ConfigData.FONT_SIZE);
		tvReverse.setText(CardsDetailJasonHelper.getReverse(mCardId));
		tvReverse.setOnClickListener(this);

		// btn_expand_reverse
		final Button btn_expand_reverse = (Button) view
				.findViewById(R.id.btn_expand_reverse);
		btn_expand_reverse.setTypeface(ConfigData.UVNCatBien_R);
		btn_expand_reverse.setTextSize(ConfigData.FONT_SIZE);
		btn_expand_reverse.setTag(R.id.tvKeywordReverse, tvKeywordReverse);
		btn_expand_reverse.setTag(R.id.ivDivider, ivDivider);
		btn_expand_reverse.setTag(R.id.tvReverse, tvReverse);
		btn_expand_reverse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((TextView) v.getTag(R.id.tvKeywordReverse))
						.getVisibility() == View.GONE) {
					((TextView) v.getTag(R.id.tvKeywordReverse))
							.setVisibility(View.VISIBLE);
					((ImageView) v.getTag(R.id.ivDivider))
							.setVisibility(View.VISIBLE);
					((TextView) v.getTag(R.id.tvReverse))
							.setVisibility(View.VISIBLE);
					((Button) v).setCompoundDrawablesWithIntrinsicBounds(
							mResources.getDrawable(R.drawable.close_item),
							null, null, null);
				} else {
					((TextView) v.getTag(R.id.tvKeywordReverse))
							.setVisibility(View.GONE);
					((ImageView) v.getTag(R.id.ivDivider))
							.setVisibility(View.GONE);
					((TextView) v.getTag(R.id.tvReverse))
							.setVisibility(View.GONE);
					((Button) v).setCompoundDrawablesWithIntrinsicBounds(
							mResources.getDrawable(R.drawable.expand_item),
							null, null, null);
				}

			}
		});

		// btn_view_card_association
		final Button btn_view_card_association = (Button) view
				.findViewById(R.id.btn_view_card_association);
		btn_view_card_association.setTypeface(ConfigData.UVNCatBien_R);
		btn_view_card_association.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivity.btnCardAssociationClicked();
			}
		});

		/**
		 * GET SUIT GROUP
		 */

		// layoutSuit
		final LinearLayout layoutSuit = (LinearLayout) view
				.findViewById(R.id.layoutSuit);
		layoutSuit.setOnClickListener(this);

		// btnSuit
		final Button btnSuit = (Button) view.findViewById(R.id.btnSuit);
		btnSuit.setTypeface(ConfigData.UVNCatBien_R);
		btnSuit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentStar = new Intent(mActivity,
						BrowseGroupCardsActivity.class);
				intentStar.putExtra("mode", 0);
				mActivity.startActivity(intentStar);
			}
		});

		// suitTable
		final TableLayout suitTable = (TableLayout) view
				.findViewById(R.id.suitTable);
		suitTable.setOnClickListener(this);
		final String[] arrSuitId = CardsDetailJasonHelper.getSuitIds(mCardId);
		if (arrSuitId != null) {
			AssociationItemViewAdapter suitAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrSuitId);
			int i = 0;
			while (i < arrSuitId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				// add 3 association item to this row if has
				for (int c = 0; c < 3; c++) {
					// create association
					tr.addView(suitAdapter.getView(i, null, null));
					tr.setOnClickListener(this);
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
		 * GET STAR GROUP
		 */

		// layoutStar
		final LinearLayout layoutStar = (LinearLayout) view
				.findViewById(R.id.layoutStar);
		layoutStar.setOnClickListener(this);

		// btnStar
		final Button btnStar = (Button) view.findViewById(R.id.btnStar);
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

		// starTable
		final TableLayout starTable = (TableLayout) view
				.findViewById(R.id.starTable);
		starTable.setOnClickListener(this);
		final String[] arrStarId = CardsDetailJasonHelper.getStarIds(mCardId);
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
		 * GET NUMBER GROUP
		 */

		// layoutNumber
		final LinearLayout layoutNumber = (LinearLayout) view
				.findViewById(R.id.layoutNumber);
		layoutNumber.setOnClickListener(this);

		// btnNumber
		final Button btnNumber = (Button) view.findViewById(R.id.btnNumber);
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

		// numberTable
		final TableLayout numberTable = (TableLayout) view
				.findViewById(R.id.numberTable);
		numberTable.setOnClickListener(this);
		final String[] arrNumberId = CardsDetailJasonHelper
				.getNumberIds(mCardId);
		if (arrNumberId != null) {
			AssociationItemViewAdapter numberAdapter = new AssociationItemViewAdapter(
					mActivity, mImageLoader, arrNumberId);
			int i = 0;
			while (i < arrNumberId.length) {
				// Create table layout of suit array
				TableRow tr = new TableRow(mActivity);
				tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
				tr.setOnClickListener(this);
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
		 * GET SYMBOY GROUP
		 */

		// layoutSymbol
		final LinearLayout layoutSymbol = (LinearLayout) view
				.findViewById(R.id.layoutSymbol);
		layoutSymbol.setOnClickListener(this);

		// btnSymbol
		final Button btnSymbol = (Button) view.findViewById(R.id.btnSymbol);
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

		// symbolTable
		final TableLayout symbolTable = (TableLayout) view
				.findViewById(R.id.symbolTable);
		symbolTable.setOnClickListener(this);
		String[] arrSymbolId = CardsDetailJasonHelper.getSymbolIds(mCardId);
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
		
		// Click at Ad1
		((ImageView)view.findViewById(R.id.ivAd1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
				startActivity(launchBrowser);
			}
		});
		
		// Click at Ad2
		((ImageView)view.findViewById(R.id.ivAd2)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uriUrl = Uri.parse(ConfigData.FEED_BACK_LINK);
				Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
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
