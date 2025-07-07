package com.statix.android.settings.notification;

import android.content.Context;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.utils.ThreadUtils;

public abstract class BaseRingtonePreferenceController extends BasePreferenceController {
    private static final String TAG = "BaseRingtonePreferenceController";

    @Override
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override
    public Class getBackgroundWorkerClass() {
        return super.getBackgroundWorkerClass();
    }

    @Override
    public IntentFilter getIntentFilter() {
        return super.getIntentFilter();
    }

    public abstract int getRingtoneType();

    @Override
    public int getSliceHighlightMenuRes() {
        return super.getSliceHighlightMenuRes();
    }

    @Override
    public boolean hasAsyncUpdate() {
        return super.hasAsyncUpdate();
    }

    @Override
    public boolean isPublicSlice() {
        return super.isPublicSlice();
    }

    @Override
    public boolean isSliceable() {
        return super.isSliceable();
    }

    public abstract boolean isVibrationSupported();

    @Override
    public boolean useDynamicSliceSummary() {
        return super.useDynamicSliceSummary();
    }

    public BaseRingtonePreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override
    public void updateState(final Preference preference) {
        ThreadUtils.postOnBackgroundThread(new Runnable() {
            @Override
            public final void run() {
                BaseRingtonePreferenceController.this.updateSummary(preference);
            }
        });
    }

    private void updateSummary(final Preference preference) {
        final String ringtoneSummary = getRingtoneSummary(this.mContext, getRingtoneType(), isVibrationSupported());
        if (ringtoneSummary != null) {
            ThreadUtils.postOnMainThread(new Runnable() {
                @Override
                public final void run() {
                    preference.setSummary(ringtoneSummary);
                }
            });
        }
    }

    static String getRingtoneSummary(Context context, int i, boolean z) {
        Uri actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, i);
        String title = Ringtone.getTitle(context, actualDefaultRingtoneUri, false, true);
        String str = "";
        if (z) {
            try {
                str = Utils.queryVibrationTitleFromRingtoneUri(context, actualDefaultRingtoneUri, i);
            } catch (IllegalArgumentException e) {
                Log.w(TAG, "Error getting ringtone summary.", e);
            }
        }
        return Utils.formatSoundVibrationSummary(title, str);
    }
}
