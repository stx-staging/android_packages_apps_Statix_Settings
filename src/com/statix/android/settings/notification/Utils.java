package com.statix.android.settings.notification;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Slog;

public abstract class Utils {
    public static String queryVibrationTitleFromRingtoneUri(Context context, Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("extra_requested_type", i);
        bundle.putBoolean("extra_has_haptic_channel", RingtoneManager.hasHapticChannels(context, uri));
        Bundle call = context.getContentResolver().call("com.google.android.apps.haptics.provider", "get_vibration_title", uri.toString(), bundle);
        if (call != null) {
            String string = call.getString("extra_vibration_title");
            if (string == null) {
                Slog.w("Utils", "receive null title in bundle");
            }
            return string;
        }
        return "";
    }

    public static String formatSoundVibrationSummary(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        return str + " / " + str2;
    }
}
