package com.google.android.settings.localepicker;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;

import com.android.settingslib.widget.GroupSectionDividerMixin;

import com.statix.android.settings.R;

public class SearchBarPreference extends Preference implements GroupSectionDividerMixin {
    public SearchBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.search_bar_preference);
    }
}
