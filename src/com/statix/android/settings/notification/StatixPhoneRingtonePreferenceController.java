package com.statix.android.settings.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;

import com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags;
import com.android.settings.DefaultRingtonePreference;
import com.android.settingslib.core.AbstractPreferenceController;

public class StatixPhoneRingtonePreferenceController extends BaseRingtonePreferenceController implements DefaultLifecycleObserver {
    private DefaultRingtonePreference mPreference;
    private final RingerModeReceiver mReceiver;

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return super.getBackgroundWorkerClass();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return super.getIntentFilter();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController
    public int getRingtoneType() {
        return 1;
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return super.getSliceHighlightMenuRes();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return super.hasAsyncUpdate();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return super.isPublicSlice();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return super.isSliceable();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return super.useDynamicSliceSummary();
    }

    public StatixPhoneRingtonePreferenceController(Context context, String str) {
        super(context, str);
        this.mReceiver = new RingerModeReceiver();
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (isVibrationSupported() && com.android.settings.Utils.isVoiceCapable(this.mContext)) ? 0 : 3;
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (DefaultRingtonePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        this.mReceiver.setListening(true);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        this.mReceiver.setListening(false);
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController
    public boolean isVibrationSupported() {
        return Flags.enableRingtoneHapticsCustomization() && this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_startDreamImmediatelyOnDock);
    }

    final class RingerModeReceiver extends BroadcastReceiver {
        private boolean mListening;

        private RingerModeReceiver() {
        }

        public void setListening(boolean z) {
            if (this.mListening == z) {
                return;
            }
            this.mListening = z;
            if (!z) {
                ((AbstractPreferenceController) StatixPhoneRingtonePreferenceController.this).mContext.unregisterReceiver(this);
            } else {
                ((AbstractPreferenceController) StatixPhoneRingtonePreferenceController.this).mContext.registerReceiver(this, new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"));
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null || !intent.getAction().equals("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION")) {
                return;
            }
            StatixPhoneRingtonePreferenceController statixPhoneRingtonePreferenceController = StatixPhoneRingtonePreferenceController.this;
            statixPhoneRingtonePreferenceController.updateState(statixPhoneRingtonePreferenceController.mPreference);
        }
    }
}
