package io.github.tapcard.sample.android.fragment.viewPager.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import io.github.tapcard.sample.android.EmvApplication;
import io.github.tapcard.sample.android.R;
import io.github.tapcard.sample.android.fragment.viewPager.AbstractFragment;
import io.github.tapcard.sample.android.fragment.viewPager.IFragment;

import com.github.devnied.emvnfccard.model.Application;
import com.github.devnied.emvnfccard.model.EmvCard;
import io.github.tapcard.sample.android.utils.CardUtils;
import io.github.tapcard.sample.android.utils.AndroidCommonsUtils;
import io.github.tapcard.sample.android.utils.ViewUtils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * View pager fragment used to display card detail
 *
 * @author Millau Julien
 *
 */
public class CardDetailFragment extends AbstractFragment {

	/**
	 * Card to display
	 */
	private EmvCard mCard;

	/**
	 * Empty view
	 */
	private LinearLayout mEmptyView;

	/**
	 * Card view
	 */
	private ScrollView mScrollView;

	/**
	 * Card number
	 */
	private TextView mCardNumber;

	/**
	 * Card validity
	 */
	private TextView mCardValidity;

	/**
	 * Image view
	 */
	private ImageView mImageView;

	/**
	 * Extended layout
	 */
	private TableLayout mExtendedLayout;

	/**
	 * Linear layout (banner)
	 */
	private LinearLayout mBanner;

	/**
	 * Method used to create a new instance of the fragment
	 *
	 * @param pCard
	 *            EmvCard
	 * @param pTitle
	 *            fragment title
	 * @return fragment
	 */
	public static IFragment newInstance(final EmvCard pCard, final String pTitle) {
		CardDetailFragment ret = new CardDetailFragment();
		ret.setEnable(true);
		ret.setTitle(pTitle);
		ret.setCard(pCard);
		return ret;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		return inflater.inflate(R.layout.card_detail, container, false);
	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		// Get views
		mEmptyView = (LinearLayout) view.findViewById(R.id.emptyView);
		mScrollView = (ScrollView) view.findViewById(R.id.card_detail);
		mCardNumber = (TextView) view.findViewById(R.id.cardNumber);
		mCardValidity = (TextView) view.findViewById(R.id.cardValidity);
		mImageView = (ImageView) view.findViewById(R.id.type);
		mExtendedLayout = (TableLayout) view.findViewById(R.id.extended_content);
		mBanner = (LinearLayout) getActivity().findViewById(R.id.banner);
		// Set OCR-A typeface
		ViewUtils.setTypeFace(EmvApplication.sTypeface, mCardNumber, mCardValidity);
		// Update content
		updateContent();
	}

	/**
	 * Method used to update card detail content
	 */
	private void updateContent() {
		if (getActivity() != null) {
			if (mCard != null) {
				mBanner.setVisibility(View.VISIBLE);
				mEmptyView.setVisibility(View.GONE);
				mScrollView.setVisibility(View.VISIBLE);
				// Update content
				mCardNumber.setText(CardUtils.formatCardNumber(mCard.getCardNumber(), mCard.getType()));
				if (mCard.getExpireDate() != null) {
					SimpleDateFormat format = new SimpleDateFormat("MM/yy", Locale.getDefault());
					mCardValidity.setText(format.format(mCard.getExpireDate()));
				}
				mImageView.setImageResource(CardUtils.getResourceIdCardType(mCard.getType()));

				// Extended card data

				// Remove all existing view
				mExtendedLayout.removeAllViews();

				// Card holder name
				if (AndroidCommonsUtils.isNotBlank(mCard.getHolderFirstname()) && AndroidCommonsUtils.isNotBlank(mCard.getHolderLastname())) {
					createRaw(getString(R.string.extended_card_holder_name),
							StringUtils.join(mCard.getHolderFirstname(), " ", mCard.getHolderLastname()));
				}

				// card AID
				String aid = null;
				if (mCard.getType().getAid().length>0){
					aid = mCard.getType().getAid()[0];
				}
				if (AndroidCommonsUtils.isNotEmpty(aid)) {
					createRaw(getString(R.string.extended_title_AID), CardUtils.formatAid(aid));
				}

				Application application = null;
				if (mCard.getApplications().size()>0) {
					application = mCard.getApplications().get(0);
				}

				// Card Application label
				String applicationLabel = null;
				if (application!=null) {
					applicationLabel = application.getApplicationLabel();
				}
				if (AndroidCommonsUtils.isNotEmpty(applicationLabel)) {
					createRaw(getString(R.string.extended_title_application_label), applicationLabel);
				}

				// Card type
				if (mCard.getType() != null) {
					createRaw(getString(R.string.extended_title_card_type), mCard.getType().getName());
				}

				Integer leftPinTry = null;
				if (application!=null) {
					leftPinTry = application.getLeftPinTry();
				}
				if(leftPinTry!=null) {
					// Pin try left
					createRaw(getString(R.string.extended_title_pin_try), leftPinTry + " "
							+ getString(R.string.extended_title_times));
				}

				// Atr desc
				if (mCard.getAtrDescription() != null && !mCard.getAtrDescription().isEmpty()) {
					createRaw(getString(R.string.extended_title_possible_bank), StringUtils.join(mCard.getAtrDescription(), "\n"));
				}

			} else {
				mBanner.setVisibility(View.GONE);
				mEmptyView.setVisibility(View.VISIBLE);
				mScrollView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * Method used to create a row in the section "Extended card detail"
	 *
	 * @param pKeyName
	 *            key title
	 * @param pValue
	 *            key value
	 */
	private void createRaw(final String pKeyName, final String pValue) {
		View v = View.inflate(getActivity(), R.layout.tablelayout_raw, null);
		TextView title = (TextView) v.findViewById(R.id.extended_raw_title);
		title.setText(pKeyName);
		TextView content = (TextView) v.findViewById(R.id.extended_raw_content);
		content.setText(pValue);
		mExtendedLayout.addView(v);
	}

	public void update(final EmvCard pCard) {
		mCard = pCard;
		updateContent();
	}

	/**
	 * Setter for the field mCard
	 *
	 * @param mCard
	 *            the mCard to set
	 */
	public void setCard(final EmvCard mCard) {
		this.mCard = mCard;
	}

}
