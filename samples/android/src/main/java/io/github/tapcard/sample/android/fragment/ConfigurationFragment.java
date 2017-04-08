package io.github.tapcard.sample.android.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.support.v4.preference.PreferenceFragment;
import android.view.View;

import io.github.tapcard.sample.android.R;
import io.github.tapcard.sample.android.activity.HomeActivity;
import io.github.tapcard.sample.android.utils.CroutonUtils;
import io.github.tapcard.sample.android.utils.CroutonUtils.CoutonColor;

/**
 * Configuration fragment
 *
 * @author Millau Julien
 *
 */
public class ConfigurationFragment extends PreferenceFragment {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		// click on clear button
		Preference clear = findPreference("clear_card");
		clear.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(final Preference preference) {
				HomeActivity activity = (HomeActivity) getActivity();
				if (activity != null) {
					activity.clear();
					CroutonUtils.display(activity, getString(R.string.card_deleted), CoutonColor.GREEN);
					activity.backToHomeScreen();
				}
				return true;
			}
		});

	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		view.setBackgroundColor(getResources().getColor(android.R.color.white));
		super.onViewCreated(view, savedInstanceState);
	}
}
