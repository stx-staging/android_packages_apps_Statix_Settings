package com.google.android.settings.fuelgauge.batterysaver;

import android.R;
import android.content.Context;
import android.content.IntentFilter;
import android.provider.Settings;
import android.widget.CompoundButton;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.MainSwitchPreference;
import com.android.settingslib.widget.TopIntroPreference;
import com.google.android.settings.fuelgauge.batterysaver.ExpandDividerPreference;

/* loaded from: classes3.dex */
public class AdaptiveBatteryExpandController extends BasePreferenceController implements ExpandDividerPreference.OnExpandListener, CompoundButton.OnCheckedChangeListener {
    static final String ADAPTIVE_BATTERY_INTRO_KEY = "adaptive_battery_top_intro";
    static final String ADAPTIVE_BATTERY_SWITCH_KEY = "adaptive_battery_management_enabled";
    static final int OFF = 0;
    static final int ON = 1;
    private MainSwitchPreference mAdaptiveBatterySwitchPreference;
    private TopIntroPreference mAdaptiveBatteryTopIntroPreference;

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

    public AdaptiveBatteryExpandController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_supportsCamToggle) ? 0 : 3;
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        ExpandDividerPreference expandDividerPreference = (ExpandDividerPreference) preferenceScreen.findPreference(getPreferenceKey());
        expandDividerPreference.setOnExpandListener(this);
        this.mAdaptiveBatteryTopIntroPreference = (TopIntroPreference) expandDividerPreference.findPreference(ADAPTIVE_BATTERY_INTRO_KEY);
        MainSwitchPreference mainSwitchPreference = (MainSwitchPreference) expandDividerPreference.findPreference(ADAPTIVE_BATTERY_SWITCH_KEY);
        this.mAdaptiveBatterySwitchPreference = mainSwitchPreference;
        mainSwitchPreference.addOnSwitchChangeListener(this);
        this.mAdaptiveBatterySwitchPreference.updateStatus(Settings.Global.getInt(this.mContext.getContentResolver(), ADAPTIVE_BATTERY_SWITCH_KEY, 1) == 1);
        onExpand(expandDividerPreference.isExpended());
    }

    @Override // com.google.android.settings.fuelgauge.batterysaver.ExpandDividerPreference.OnExpandListener
    public void onExpand(boolean z) {
        TopIntroPreference topIntroPreference = this.mAdaptiveBatteryTopIntroPreference;
        if (topIntroPreference != null) {
            topIntroPreference.setVisible(z);
        }
        MainSwitchPreference mainSwitchPreference = this.mAdaptiveBatterySwitchPreference;
        if (mainSwitchPreference != null) {
            mainSwitchPreference.setVisible(z);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), ADAPTIVE_BATTERY_SWITCH_KEY, z ? 1 : 0);
    }
}
