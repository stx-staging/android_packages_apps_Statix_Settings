package com.google.android.settings.fuelgauge.batterysaver;

import android.content.Context;
import android.provider.Settings;
import android.widget.SeekBar;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import com.android.settings.R$layout;
import com.android.settings.R$string;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.Utils;

/* loaded from: classes3.dex */
class BatterySaverSeekBarController implements Preference.OnPreferenceChangeListener, SeekBar.OnSeekBarChangeListener {
    private final Context mContext;
    int mPercentage;
    SeekBarPreference mSeekBarPreference;

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    BatterySaverSeekBarController(Context context) {
        this.mContext = context;
        SeekBarPreference seekBarPreference = new SeekBarPreference(context);
        this.mSeekBarPreference = seekBarPreference;
        seekBarPreference.setLayoutResource(R$layout.preference_widget_seekbar_settings);
        this.mSeekBarPreference.setIconSpaceReserved(false);
        this.mSeekBarPreference.setOnPreferenceChangeListener(this);
        this.mSeekBarPreference.setOnSeekBarChangeListener(this);
        this.mSeekBarPreference.setContinuousUpdates(true);
        this.mSeekBarPreference.setOrder(50);
        this.mSeekBarPreference.setMax(15);
        this.mSeekBarPreference.setMin(4);
        this.mSeekBarPreference.setKey("battery_saver_seek_bar");
        this.mSeekBarPreference.setHapticFeedbackMode(1);
    }

    void updateSeekBar(PreferenceCategory preferenceCategory, String str, int i) {
        if ("key_battery_saver_percentage".equals(str)) {
            int max = Math.max(i / 5, 4);
            this.mSeekBarPreference.setProgress(max);
            CharSequence formatStateDescription = formatStateDescription(max * 5);
            this.mSeekBarPreference.setTitle(formatStateDescription);
            this.mSeekBarPreference.overrideSeekBarStateDescription(formatStateDescription);
            preferenceCategory.addPreference(this.mSeekBarPreference);
            return;
        }
        preferenceCategory.removePreference(this.mSeekBarPreference);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.mPercentage > 0) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "low_power_trigger_level", this.mPercentage);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int intValue = ((Integer) obj).intValue() * 5;
        this.mPercentage = intValue;
        CharSequence formatStateDescription = formatStateDescription(intValue);
        preference.setTitle(formatStateDescription);
        this.mSeekBarPreference.overrideSeekBarStateDescription(formatStateDescription);
        return true;
    }

    private CharSequence formatStateDescription(int i) {
        return this.mContext.getString(R$string.battery_saver_seekbar_title, Utils.formatPercentage(i));
    }
}
