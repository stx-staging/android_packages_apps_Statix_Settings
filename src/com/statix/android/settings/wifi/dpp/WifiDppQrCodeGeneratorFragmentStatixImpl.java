/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.statix.android.settings.wifi.dpp;

import android.app.settings.SettingsEnums;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.settings.flags.Flags;
import com.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragment;
import com.android.settings.wifi.dpp.WifiNetworkConfig;
import com.android.settingslib.qrcode.QrCodeGenerator;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.statix.android.settings.R;

import java.io.File;

/**
 * After sharing a saved Wi-Fi network, {@code WifiDppConfiguratorActivity} start with this fragment
 * to generate a Wi-Fi DPP QR code for other device to initiate as an enrollee.
 */
public class WifiDppQrCodeGeneratorFragmentStatixImpl extends WifiDppQrCodeGeneratorFragment {
    private static final String TAG = "WifiDppQrCodeGeneratorFragment";

    private ImageView mQrCodeView;

    private static final String CHIP_LABEL_METADATA_KEY = "android.service.chooser.chip_label";
    private static final String CHIP_ICON_METADATA_KEY = "android.service.chooser.chip_icon";
    private static final String EXTRA_WIFI_CREDENTIALS_BUNDLE =
            "android.intent.extra.WIFI_CREDENTIALS_BUNDLE";
    private static final String EXTRA_SSID = "android.intent.extra.SSID";
    private static final String EXTRA_PASSWORD = "android.intent.extra.PASSWORD";
    private static final String EXTRA_SECURITY_TYPE = "android.intent.extra.SECURITY_TYPE";
    private static final String EXTRA_HIDDEN_SSID = "android.intent.extra.HIDDEN_SSID";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView qrCodeView = view.findViewById(R.id.qrcode_view);
        if (qrCodeView != null) {
            qrCodeView.setVisibility(View.GONE);
        }
        FragmentContainerView fragmentContainerView = view.findViewById(R.id.qr_code_fragment_container_view);
        if (fragmentContainerView != null) {
            fragmentContainerView.setVisibility(View.VISIBLE);
        }
    }

    // Magic Start.
    @Override
    protected void setQrCode() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (childFragmentManager.findFragmentById(R.id.qr_code_fragment_container_view) == null) {
            childFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.qr_code_fragment_container_view, new AwesomeQrFragment()).commitNow();
        }
        Fragment qrCodeFragment = childFragmentManager.findFragmentById(R.id.qr_code_fragment_container_view);
        ((AwesomeQrFragment) qrCodeFragment).updateQrCodeContent(mQrCode, ErrorCorrectionLevel.L);
    }
    // Magic End.
}
