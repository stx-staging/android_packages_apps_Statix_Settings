package com.google.android.settings.fuelgauge.batterysaver;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.widget.FooterPreference;
import com.google.android.settings.R$string;

/* loaded from: classes3.dex */
public class BatterySaverFooterPreferenceController extends BasePreferenceController {
    private FooterPreference mPreference;

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

    public BatterySaverFooterPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (FooterPreference) preferenceScreen.findPreference(getPreferenceKey());
        setupFooter();
    }

    void setupFooter() {
        if (TextUtils.isEmpty(this.mContext.getString(R$string.help_url_battery_saver_settings))) {
            return;
        }
        addHelpLink();
    }

    void addHelpLink() {
        FooterPreference footerPreference = this.mPreference;
        if (footerPreference != null) {
            footerPreference.setLearnMoreAction(new View.OnClickListener() { // from class: com.google.android.settings.fuelgauge.batterysaver.BatterySaverFooterPreferenceController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BatterySaverFooterPreferenceController.this.lambda$addHelpLink$0(view);
                }
            });
            this.mPreference.setLearnMoreText(this.mContext.getString(com.android.settings.R$string.battery_saver_link_a11y));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHelpLink$0(View view) {
        Context context = this.mContext;
        context.startActivity(HelpUtils.getHelpIntent(context, context.getString(R$string.help_url_battery_saver_settings), ""));
    }
}
