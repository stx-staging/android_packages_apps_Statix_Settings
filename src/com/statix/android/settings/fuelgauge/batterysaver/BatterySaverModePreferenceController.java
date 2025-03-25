package com.google.android.settings.fuelgauge.batterysaver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* loaded from: classes3.dex */
public class BatterySaverModePreferenceController extends BasePreferenceController implements SelectorWithWidgetPreference.OnClickListener, LifecycleObserver, OnResume, OnPause {
    private static final String TAG = "BatterySaverModePreferenceController";
    SelectorWithWidgetPreference mBasicPreference;
    private final ContentObserver mContentObserver;
    boolean mCurrentBatterySaverMode;
    SelectorWithWidgetPreference mExtremePreference;
    private HandlerThread mHandlerThread;
    boolean mIsFlipendoAggressiveMode;
    boolean mIsFlipendoEnabled;

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

    public BatterySaverModePreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.google.android.settings.fuelgauge.batterysaver.BatterySaverModePreferenceController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                BatterySaverModePreferenceController.this.refreshFlipendoStates();
                BatterySaverModePreferenceController batterySaverModePreferenceController = BatterySaverModePreferenceController.this;
                if (batterySaverModePreferenceController.mIsFlipendoAggressiveMode) {
                    return;
                }
                batterySaverModePreferenceController.updateSaverModeSelection(!batterySaverModePreferenceController.mIsFlipendoEnabled);
            }
        };
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory = (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        if (preferenceCategory != null) {
            refreshFlipendoStates();
            initRadioButton(preferenceCategory);
        }
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public void onRadioButtonClicked(SelectorWithWidgetPreference selectorWithWidgetPreference) {
        char c;
        String key = selectorWithWidgetPreference.getKey();
        int hashCode = key.hashCode();
        if (hashCode != -1469013245) {
            if (hashCode == 25408901 && key.equals("basic_battery_saver_entry")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (key.equals("extreme_battery_saver_entry")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            updateSaverModeSelection(true);
        } else if (c == 1) {
            updateSaverModeSelection(false);
        }
        if (this.mIsFlipendoEnabled) {
            this.mCurrentBatterySaverMode = this.mExtremePreference.isChecked();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        try {
            boolean z = false;
            this.mContext.getContentResolver().registerContentObserver(FlipendoUtils.FLIPENDO_ENABLED_OBSERVABLE_URI, false, this.mContentObserver);
            SelectorWithWidgetPreference selectorWithWidgetPreference = this.mBasicPreference;
            if (selectorWithWidgetPreference != null) {
                this.mCurrentBatterySaverMode = selectorWithWidgetPreference.isChecked();
            }
            refreshFlipendoStates();
            if (!this.mIsFlipendoEnabled && !this.mIsFlipendoAggressiveMode) {
                z = true;
            }
            updateSaverModeSelection(z);
        } catch (Exception e) {
            Log.e(TAG, "onResume() failed", e);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        if (this.mCurrentBatterySaverMode == this.mBasicPreference.isChecked()) {
            return;
        }
        if (!this.mIsFlipendoAggressiveMode && this.mIsFlipendoEnabled && this.mExtremePreference.isChecked()) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread(TAG);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        new Handler(this.mHandlerThread.getLooper()).post(new Runnable() { // from class: com.google.android.settings.fuelgauge.batterysaver.BatterySaverModePreferenceController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BatterySaverModePreferenceController.this.lambda$onPause$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPause$0() {
        updateBatterySaverMode(this.mContext, !this.mBasicPreference.isChecked() ? 1 : 0);
    }

    private void initRadioButton(PreferenceCategory preferenceCategory) {
        SelectorWithWidgetPreference selectorWithWidgetPreference = (SelectorWithWidgetPreference) preferenceCategory.findPreference("basic_battery_saver_entry");
        this.mBasicPreference = selectorWithWidgetPreference;
        if (selectorWithWidgetPreference != null) {
            selectorWithWidgetPreference.setExtraWidgetOnClickListener(null);
            this.mBasicPreference.setOnClickListener(this);
            this.mBasicPreference.setChecked(!this.mIsFlipendoAggressiveMode);
        }
        SelectorWithWidgetPreference selectorWithWidgetPreference2 = (SelectorWithWidgetPreference) preferenceCategory.findPreference("extreme_battery_saver_entry");
        this.mExtremePreference = selectorWithWidgetPreference2;
        if (selectorWithWidgetPreference2 != null) {
            selectorWithWidgetPreference2.setExtraWidgetOnClickListener(new View.OnClickListener() { // from class: com.google.android.settings.fuelgauge.batterysaver.BatterySaverModePreferenceController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BatterySaverModePreferenceController.this.lambda$initRadioButton$1(view);
                }
            });
            this.mExtremePreference.setOnClickListener(this);
            this.mExtremePreference.setChecked(this.mIsFlipendoAggressiveMode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRadioButton$1(View view) {
        launchFlipendo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSaverModeSelection(boolean z) {
        SelectorWithWidgetPreference selectorWithWidgetPreference = this.mBasicPreference;
        if (selectorWithWidgetPreference == null || this.mExtremePreference == null) {
            return;
        }
        selectorWithWidgetPreference.setChecked(z);
        this.mExtremePreference.setChecked(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshFlipendoStates() {
        Pair flipendoState = FlipendoUtils.getFlipendoState(this.mContext);
        this.mIsFlipendoAggressiveMode = ((Boolean) flipendoState.first).booleanValue();
        this.mIsFlipendoEnabled = ((Boolean) flipendoState.second).booleanValue();
    }

    private void updateBatterySaverMode(Context context, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("update_flipendo_mode", i);
        try {
            context.getContentResolver().call(FlipendoUtils.FLIPENDO_STATE_AUTHORITY, "update_flipendo_mode_method", (String) null, bundle);
        } catch (Exception e) {
            Log.e(TAG, "updateBatterySaverMode() failed", e);
        }
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mHandlerThread = null;
        }
    }

    private void launchFlipendo() {
        try {
            this.mContext.startActivity(new Intent("android.settings.batterysaver.flipendo").setPackage("com.google.android.flipendo"));
        } catch (Exception e) {
            Log.e(TAG, "launchFlipendo() failed", e);
        }
    }
}
