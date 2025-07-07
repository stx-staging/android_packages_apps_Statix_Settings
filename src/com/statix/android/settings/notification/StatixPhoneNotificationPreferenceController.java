package com.statix.android.settings.notification;

import android.content.Context;
import android.content.IntentFilter;

import com.android.server.notification.Flags;

public class StatixPhoneNotificationPreferenceController extends BaseRingtonePreferenceController {
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
        return 2;
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

    public StatixPhoneNotificationPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (isVibrationSupported() && this.mContext.getResources().getBoolean(com.android.settings.R.bool.config_show_notification_ringtone)) ? 0 : 3;
    }

    @Override // com.google.android.settings.notification.BaseRingtonePreferenceController
    public boolean isVibrationSupported() {
        return Flags.notificationVibrationInSoundUri() && this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_startDreamImmediatelyOnDock);
    }
}
