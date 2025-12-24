package com.google.android.settings.localepicker;

import android.content.Context;
import android.os.LocaleList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.internal.app.LocaleCollectorBase;
import com.android.internal.app.LocaleStore;
import com.android.internal.app.SystemLocaleCollector;

public class SystemLocaleAllListPreferenceController
        extends LocalePickerBaseListPreferenceController {
    private static final String KEY_PREFERENCE_CATEGORY_ADD_LANGUAGE_ALL_SUPPORTED =
            "system_language_all_supported_category";
    private static final String KEY_PREFERENCE_SYSTEM_LOCALE_LIST = "system_locale_list";
    private LocaleList mExplicitLocales;
    private boolean mIsNumberingSystemMode;
    private LocaleStore.LocaleInfo mLocaleInfo;

    public SystemLocaleAllListPreferenceController(
            @NonNull Context context, @NonNull String preferenceKey) {
        super(context, preferenceKey);
    }

    public SystemLocaleAllListPreferenceController(
            @NonNull Context context,
            @NonNull String preferenceKey,
            @NonNull LocaleStore.LocaleInfo parentLocale,
            boolean isNumberingSystemMode) {
        super(context, preferenceKey);
        mLocaleInfo = parentLocale;
        mIsNumberingSystemMode = isNumberingSystemMode;
    }

    public SystemLocaleAllListPreferenceController(
            @NonNull Context context,
            @NonNull String preferenceKey,
            @Nullable LocaleList explicitLocales) {
        super(context, preferenceKey);
        mExplicitLocales = explicitLocales;
    }

    @Override
    protected String getPreferenceCategoryKey() {
        return KEY_PREFERENCE_CATEGORY_ADD_LANGUAGE_ALL_SUPPORTED;
    }

    @Override
    public @NonNull String getPreferenceKey() {
        return KEY_PREFERENCE_SYSTEM_LOCALE_LIST;
    }

    @Override
    protected LocaleCollectorBase getLocaleCollectorController(Context context) {
        return new SystemLocaleCollector(context, getExplicitLocaleList());
    }

    @Override
    protected @Nullable LocaleStore.LocaleInfo getParentLocale() {
        return mLocaleInfo;
    }

    @Override
    protected boolean isNumberingMode() {
        return mIsNumberingSystemMode;
    }

    @Override
    protected @Nullable LocaleList getExplicitLocaleList() {
        return mExplicitLocales;
    }
}
