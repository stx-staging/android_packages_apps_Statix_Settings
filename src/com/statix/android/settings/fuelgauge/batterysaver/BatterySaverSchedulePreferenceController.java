package com.google.android.settings.fuelgauge.batterysaver;

import android.content.Context;
import android.content.IntentFilter;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.fuelgauge.BatterySaverUtils;

/* loaded from: classes3.dex */
public class BatterySaverSchedulePreferenceController extends BasePreferenceController implements Preference.OnPreferenceChangeListener {
    static final int DEFAULT_MIN_SCHEDULE_THRESHOLD = 20;
    static final int DEFAULT_THRESHOLD = 0;
    public static final String KEY_BATTERY_SAVER_SCHEDULE = "battery_saver_base_on_percentage";
    private PreferenceCategory mPreferenceCategory;
    BatterySaverSeekBarController mSeekBarController;
    private final int mThreshold;

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return super.getBackgroundWorkerClass();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return super.getIntentFilter();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return super.getSliceHighlightMenuRes();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return super.hasAsyncUpdate();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return super.isPublicSlice();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return super.isSliceable();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return super.useDynamicSliceSummary();
    }

    public BatterySaverSchedulePreferenceController(Context context, String str) {
        super(context, str);
        this.mSeekBarController = new BatterySaverSeekBarController(context);
        this.mThreshold = Settings.Global.getInt(context.getContentResolver(), "low_power_trigger_level", 0);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory = (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreferenceCategory = preferenceCategory;
        if (preferenceCategory != null) {
            initPreferences();
        }
    }

    private void initPreferences() {
        TwoStatePreference twoStatePreference = (TwoStatePreference) this.mPreferenceCategory.findPreference(KEY_BATTERY_SAVER_SCHEDULE);
        if (twoStatePreference != null) {
            twoStatePreference.setOnPreferenceChangeListener(this);
            twoStatePreference.setChecked(BatterySaverUtils.getBatterySaverScheduleKey(this.mContext).equals("key_battery_saver_percentage"));
            this.mSeekBarController.updateSeekBar(this.mPreferenceCategory, BatterySaverUtils.getBatterySaverScheduleKey(this.mContext), this.mThreshold);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!preference.getKey().equals(KEY_BATTERY_SAVER_SCHEDULE)) {
            return true;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        BatterySaverUtils.setBatterySaverScheduleMode(this.mContext, "key_battery_saver_percentage", booleanValue ? 20 : 0);
        this.mSeekBarController.updateSeekBar(this.mPreferenceCategory, booleanValue ? "key_battery_saver_percentage" : "key_battery_saver_no_schedule", booleanValue ? 20 : 0);
        return true;
    }
}
