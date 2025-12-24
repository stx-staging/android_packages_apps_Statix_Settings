package com.google.android.settings.localepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.internal.app.LocaleStore;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;

public class SearchBarPreferenceController extends BasePreferenceController {
    private static final String ACTION_REGION_SEARCH =
            "com.google.android.settings.localepicker.REGION_SEARCH";
    private static final String KEY_SEARCH_BAR_PREFERENCE = "search_bar_button";
    private DashboardFragment mFragment;
    private LocaleStore.LocaleInfo mLocaleInfo;

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    public SearchBarPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    protected void setLocaleInfo(LocaleStore.LocaleInfo localeInfo) {
        mLocaleInfo = localeInfo;
    }

    protected void setFragment(DashboardFragment dashboardFragment) {
        mFragment = dashboardFragment;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_SEARCH_BAR_PREFERENCE;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey()) || mFragment == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        if (mFragment.getActivity().getIntent().getExtras() != null) {
            bundle = mFragment.getActivity().getIntent().getExtras();
        }
        bundle.putSerializable("extra_target_locale", mLocaleInfo);
        bundle.putBoolean("extra_is_numbering_system", mLocaleInfo.hasNumberingSystems());
        Intent intent = new Intent(ACTION_REGION_SEARCH);
        intent.putExtras(bundle);
        mFragment.startActivityForResult(intent, 0);
        return true;
    }
}
