package com.google.android.settings.localepicker;

import android.content.Context;
import android.util.AttributeSet;

import com.android.internal.app.LocaleStore;

import com.google.android.setupdesign.items.Item;

public class LocaleItem extends Item {
    private boolean mIsSuggested;
    private LocaleStore.LocaleInfo mLocaleInfo;

    public LocaleItem() {
        mIsSuggested = false;
    }

    public LocaleItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mIsSuggested = false;
    }

    public void setLocaleInfo(LocaleStore.LocaleInfo localeInfo) {
        mLocaleInfo = localeInfo;
    }

    public LocaleStore.LocaleInfo getLocaleInfo() {
        return mLocaleInfo;
    }

    public void setSuggestedState(boolean isSuggested) {
        mIsSuggested = isSuggested;
    }

    public boolean isSuggested() {
        return mIsSuggested;
    }
}
