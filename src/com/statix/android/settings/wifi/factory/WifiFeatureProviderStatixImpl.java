/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.statix.android.settings.wifi.factory;

import android.content.Context;
import android.net.TetheringManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.settings.wifi.details.WifiNetworkDetailsViewModel;
import com.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragment;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settings.wifi.tether.WifiHotspotSecurityViewModel;
import com.android.settings.wifi.tether.WifiHotspotSpeedViewModel;
import com.android.settings.wifi.tether.WifiTetherViewModel;

import com.statix.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragmentStatixImpl;

/**
 * Wi-Fi Feature Provider
 */
public class WifiFeatureProviderStatixImpl extends WifiFeatureProvider {
    private static final String TAG = "WifiFeatureProvider";

    private final Context mAppContext;

    public WifiFeatureProviderStatixImpl(@NonNull Context appContext) {
        super(appContext);
        mAppContext = appContext;
    }

    /**
     * Gets an instance of WifiDppQrCodeGeneratorFragment
     */
    @Override
    public WifiDppQrCodeGeneratorFragment getWifiDppQrCodeGeneratorFragment() {
        WifiDppQrCodeGeneratorFragment fragment = new WifiDppQrCodeGeneratorFragmentStatixImpl();
        verboseLog(TAG, "getWifiDppQrCodeGeneratorFragment():" + fragment);
        return fragment;
    }

}
