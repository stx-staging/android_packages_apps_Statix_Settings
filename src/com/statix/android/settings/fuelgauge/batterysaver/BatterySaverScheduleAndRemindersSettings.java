package com.google.android.settings.fuelgauge.batterysaver;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.google.android.settings.R$xml;

/* loaded from: classes3.dex */
public class BatterySaverScheduleAndRemindersSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new BaseSearchIndexProvider(R$xml.battery_saver_schedule_and_reminders);

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return 1977;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    protected int getPreferenceScreenResId() {
        return R$xml.battery_saver_schedule_and_reminders;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.settings.dashboard.DashboardFragment
    public String getLogTag() {
        return "BatterySaverScheduleAndRemindersSettings";
    }
}
