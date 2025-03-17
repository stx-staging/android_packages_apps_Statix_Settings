package com.statix.android.settings.overlay

import android.content.Context

import com.android.settings.accounts.AccountFeatureProvider
import com.android.settings.fuelgauge.BatteryStatusFeatureProvider
import com.android.settings.overlay.FeatureFactoryImpl
import com.android.settings.wifi.factory.WifiFeatureProvider

import com.google.android.settings.accounts.AccountFeatureProviderGoogleImpl

import com.statix.android.settings.wifi.factory.WifiFeatureProviderStatixImpl

class FeatureFactoryImplStatix : FeatureFactoryImpl() {
    override val accountFeatureProvider: AccountFeatureProvider by lazy {
        AccountFeatureProviderGoogleImpl()
    }

    override val wifiFeatureProvider by lazy { WifiFeatureProviderStatixImpl(appContext) }
}
