package com.google.android.settings.localepicker;

import android.R;
import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.google.android.setupdesign.util.ItemStyler;
import com.google.android.setupdesign.util.ThemeHelper;

public class ExpressivePreferenceCategory extends PreferenceCategory {
    private boolean mShouldApplyGlifExpressiveStyle;

    public ExpressivePreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShouldApplyGlifExpressiveStyle = ThemeHelper.shouldApplyGlifExpressiveStyle(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        if (mShouldApplyGlifExpressiveStyle) {
            ItemStyler.applyPartnerCustomizationLayoutMarginStyle(holder.findViewById(R.id.title));
        }
    }
}
