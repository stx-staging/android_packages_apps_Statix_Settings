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

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import com.android.settings.flags.Flags;
import com.android.settings.wifi.dpp.WifiDppQrCodeGeneratorFragment;
import com.android.settings.wifi.dpp.WifiNetworkConfig;
import com.android.settingslib.qrcode.QrCodeGenerator;

import com.github.sumimakito.awesomeqr.*;
import com.github.sumimakito.awesomeqr.option.background.*;
import com.github.sumimakito.awesomeqr.option.color.Color;
import com.github.sumimakito.awesomeqr.option.logo.Logo;
import com.github.sumimakito.awesomeqr.option.RenderOption;
import com.github.sumimakito.awesomeqr.util.RectUtils;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.WriterException;

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

    // Magic Start.
    @Override
    protected void setQrCode() {
        try {
            final int qrcodeSize = getContext().getResources().getDimensionPixelSize(
                    com.android.settings.R.dimen.qrcode_size);

                    // A gif background (animated)
                    // GifBackground background = new GifBackground();
                    // background.setInputFile(R.raw.nyan_cat); // assign a file object of a gif image to this field
                    // background.setOutputFile(new File(pictureStorage, "output.gif")); // IMPORTANT: the output image will be saved to this file object
                    // background.setClippingRect(new Rect(0, 0, 200, 200));
                    // background.setAlpha(0.7f);

                    Color color = new Color(); 
                    color.setLight(0xFFFFFFFF); // for blank spaces
                    color.setDark(0xFFFF8C8C); // for non-blank spaces
                    color.setBackground(0xFFFFFFFF); // for the background (will be overriden by background images, if set)
                    color.setAuto(true); // set to true to automatically pick out colors from the background image (will only work if background image is present)

                    RenderOption renderOption = new RenderOption();
                    renderOption.setContent(mQrCode); // content to encode
                    renderOption.setSize(qrcodeSize); // size of the final QR code image
                    renderOption.setBorderWidth(20); // width of the empty space around the QR code
                    renderOption.setEcl(ErrorCorrectionLevel.M); // (optional) specify an error correction level
                    renderOption.setPatternScale(0.35f); // (optional) specify a scale for patterns
                    renderOption.setRoundedPatterns(true); // (optional) if true, blocks will be drawn as dots instead
                    renderOption.setClearBorder(true); // if set to true, the background will NOT be drawn on the border area
                    renderOption.setColor(color); // set a color palette for the QR code
                    // renderOption.setBackground(background); // set a background, keep reading to find more about it
                    // renderOption.setLogo(logo); // set a logo, keep reading to find more about it

                    try {
                        RenderResult result = AwesomeQrRenderer.render(renderOption);
                        if (result.getBitmap() != null) {
                            // play with the bitmap
                            mQrCodeView.setImageBitmap(result.getBitmap());
                        } else if (result.getType() == RenderResult.OutputType.GIF) {
                            // If your Background is a GifBackground, the image 
                            // will be saved to the output file set in GifBackground
                            // instead of being returned here. As a result, the 
                            // result.getBitmap() will be null.
                            Log.d(TAG, "GIF QR code generated, bitmap is null, check GifBackground output file.");
                        } else {
                            Log.e(TAG, "Error generating QR code: result.getBitmap() is null and type is not GIF.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            // final Bitmap bmp = QrCodeGenerator.encodeQrCode(mQrCode, qrcodeSize);
            // mQrCodeView.setImageBitmap(bmp);
        } catch (Exception e) {
            Log.e(TAG, "Error generating QR code bitmap " + e);
        }
    }
    // Magic End.

}
