package com.statix.android.settings.notification;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import com.android.settings.notification.app.NotificationSoundPreference;

import com.statix.android.settings.R;

public class StatixNotificationChannelSoundPreference extends NotificationSoundPreference {
    private Context mContext;

    private boolean isVibrationSupported() {
        return false;
    }

    public StatixNotificationChannelSoundPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        if (isVibrationSupported()) {
            setTitle(R.string.phone_notification_channel_sound_title);
        }
    }

    @Override
    public void onPrepareRingtonePickerIntent(Intent intent) {
        super.onPrepareRingtonePickerIntent(intent);
        if (isVibrationSupported()) {
            return;
        }
        intent.putExtra("extra_force_disable_vibration_page", true);
    }

    @Override
    protected String generateRingtoneTitle(Uri uri) {
        String str;
        String generateRingtoneTitle = super.generateRingtoneTitle(uri);
        if (!isVibrationSupported() || uri == null) {
            return generateRingtoneTitle;
        }
        try {
            str = Utils.queryVibrationTitleFromRingtoneUri(this.mContext, uri, 0);
        } catch (IllegalArgumentException e) {
            Log.w("StatixNotificationChannelSoundPreference", "Error getting notification summary.", e);
            str = "";
        }
        return Utils.formatSoundVibrationSummary(generateRingtoneTitle, str);
    }
}
