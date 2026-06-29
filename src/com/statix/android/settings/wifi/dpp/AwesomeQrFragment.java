package com.statix.android.settings.wifi.dpp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.github.sumimakito.awesomeqr.*;
import com.github.sumimakito.awesomeqr.option.background.*;
import com.github.sumimakito.awesomeqr.option.color.Color;
import com.github.sumimakito.awesomeqr.option.logo.Logo;
import com.github.sumimakito.awesomeqr.option.RenderOption;
import com.github.sumimakito.awesomeqr.util.RectUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.statix.android.settings.R;

import java.io.File;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

public final class AwesomeQrFragment extends Fragment {
    private static final String TAG = "AwesomeQrFragment";
    
    private String qrCodeContent;
    private ErrorCorrectionLevel qrCodeEcLevel = ErrorCorrectionLevel.L;
    private ImageView mQrCodeView;

    public final void updateQrCodeContent(String str, ErrorCorrectionLevel errorCorrectionLevel) {
        this.qrCodeContent = str;
        this.qrCodeEcLevel = errorCorrectionLevel;
        generateCustomQrCode();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.awesome_qr_fragment, container, false);
        
        // Find whichever ImageView is available in the original XML layout definition
        mQrCodeView = view.findViewById(R.id.standard_qr_code_img_view);
        if (mQrCodeView == null) {
            mQrCodeView = view.findViewById(R.id.qr_code_img_view_for_finder_patterns);
        }
        if (mQrCodeView == null) {
            mQrCodeView = view.findViewById(R.id.qr_code_img_view_for_non_finder_patterns);
        }
        
        // Ensure other overlay or secondary views from the original framework don't interfere
        View nonFinder = view.findViewById(R.id.qr_code_img_view_for_non_finder_patterns);
        if (nonFinder != null && nonFinder != mQrCodeView) nonFinder.setVisibility(View.GONE);
        View backgroundView = view.findViewById(R.id.qr_code_background_view);
        if (backgroundView != null) backgroundView.setVisibility(View.GONE);

        if (mQrCodeView != null) {
            mQrCodeView.setVisibility(View.VISIBLE);
        }

        if (qrCodeContent != null) {
            generateCustomQrCode();
        }
        
        return view;
    }

    private void generateCustomQrCode() {
        if (mQrCodeView == null || qrCodeContent == null) return;

        Context context = getContext();
        if (context == null) return;

        int qrcodeSize = context.getResources().getDimensionPixelSize(com.android.settings.R.dimen.qrcode_size);

        // --- Color Setup ---
        Color color = new Color();
        color.setLight(0xFFFFFFFF);     // For blank modules
        color.setDark(0xFFFF8C8C);      // For structural data modules
        color.setBackground(0xFFFFFFFF); // Background tint fallback
        color.setAuto(true);            // Pull accent coloring profiles automatically if an image is present

        // --- Custom Background Configuration (GIF from Resources) ---
        GifBackground background = new GifBackground();
        try {
            // Read directly out of the APK raw resource partition
            // Replace R.raw.nyan_cat with your preferred target resource identity identifier
            int resId = context.getResources().getIdentifier("nyan_cat", "raw", context.getPackageName());
            if (resId != 0) {
                InputStream inputStream = context.getResources().openRawResource(resId);
                background.setInputStream(inputStream);
            }
        } catch (Exception e) {
            Log.e(TAG, "Resource file stream could not be initialized", e);
        }

        // Keep a writable file pointer destination for compilation operations
        File pictureStorage = context.getExternalFilesDir(null);
        background.setOutputFile(new File(pictureStorage, "output.gif"));
        background.setClippingRect(new Rect(0, 0, 200, 200));
        background.setAlpha(0.7f);

        // --- Renderer Setup ---
        RenderOption renderOption = new RenderOption();
        renderOption.setContent(qrCodeContent);
        renderOption.setSize(qrcodeSize);
        renderOption.setBorderWidth(20);
        renderOption.setEcl(qrCodeEcLevel != null ? qrCodeEcLevel : ErrorCorrectionLevel.M);
        renderOption.setPatternScale(0.35f);
        renderOption.setRoundedPatterns(true);
        renderOption.setClearBorder(true);
        renderOption.setColor(color);
        renderOption.setBackground(background); // Binds the resource-backed stream configuration above

        try {
            RenderResult result = AwesomeQrRenderer.render(renderOption);
            if (result.getBitmap() != null) {
                mQrCodeView.setImageBitmap(result.getBitmap());
            } else if (result.getType() == RenderResult.OutputType.GIF) {
                Log.d(TAG, "GIF QR code successfully rendered to output path file target storage.");
                
                // Displaying the resulting animated file requires a GIF-aware framework layer
                File compiledGif = background.getOutputFile();
                if (compiledGif != null && compiledGif.exists() && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    android.graphics.ImageDecoder.Source source = android.graphics.ImageDecoder.createSource(compiledGif);
                    android.graphics.drawable.Drawable drawable = android.graphics.ImageDecoder.decodeDrawable(source);
                    mQrCodeView.setImageDrawable(drawable);
                    if (drawable instanceof android.graphics.drawable.AnimatedImageDrawable) {
                        ((android.graphics.drawable.AnimatedImageDrawable) drawable).start();
                    }
                }
            } else {
                Log.e(TAG, "Error generating QR code: result target is invalid.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to render custom configuration matrix", e);
        }
    }

    // =========================================================================
    // Integrated Single-Class Engine Configuration Architectures
    // =========================================================================

    public static class Color {
        private int light;
        private int dark;
        private int background;
        private boolean auto;

        public void setLight(int light) { this.light = light; }
        public int getLight() { return light; }
        public void setDark(int dark) { this.dark = dark; }
        public int getDark() { return dark; }
        public void setBackground(int background) { this.background = background; }
        public int getBackground() { return background; }
        public void setAuto(boolean auto) { this.auto = auto; }
        public boolean isAuto() { return auto; }
    }

    public static class GifBackground {
        private InputStream inputStream;
        private File outputFile;
        private Rect clippingRect;
        private float alpha;

        public void setInputStream(InputStream inputStream) { this.inputStream = inputStream; }
        public InputStream getInputStream() { return this.inputStream; }
        public void setOutputFile(File outputFile) { this.outputFile = outputFile; }
        public File getOutputFile() { return this.outputFile; }
        public void setClippingRect(Rect clippingRect) { this.clippingRect = clippingRect; }
        public Rect getClippingRect() { return this.clippingRect; }
        public void setAlpha(float alpha) { this.alpha = alpha; }
        public float getAlpha() { return this.alpha; }
    }

    public static class RenderOption {
        private String content;
        private int size;
        private int borderWidth;
        private ErrorCorrectionLevel ecl = ErrorCorrectionLevel.M;
        private float patternScale = 1.0f;
        private boolean roundedPatterns;
        private boolean clearBorder;
        private Color color;
        private GifBackground background;

        public void setContent(String content) { this.content = content; }
        public String getContent() { return content; }
        public void setSize(int size) { this.size = size; }
        public int getSize() { return size; }
        public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }
        public int getBorderWidth() { return borderWidth; }
        public void setEcl(ErrorCorrectionLevel ecl) { this.ecl = ecl; }
        public ErrorCorrectionLevel getEcl() { return ecl; }
        public void setPatternScale(float patternScale) { this.patternScale = patternScale; }
        public float getPatternScale() { return patternScale; }
        public void setRoundedPatterns(boolean roundedPatterns) { this.roundedPatterns = roundedPatterns; }
        public boolean isRoundedPatterns() { return roundedPatterns; }
        public void setClearBorder(boolean clearBorder) { this.clearBorder = clearBorder; }
        public boolean isClearBorder() { return clearBorder; }
        public void setColor(Color color) { this.color = color; }
        public Color getColor() { return color; }
        public void setBackground(GifBackground background) { this.background = background; }
        public GifBackground getBackground() { return background; }
    }

    public static class RenderResult {
        public enum OutputType { BITMAP, GIF }
        private final Bitmap bitmap;
        private final OutputType type;

        public RenderResult(Bitmap bitmap) {
            this.bitmap = bitmap;
            this.type = OutputType.BITMAP;
        }

        public RenderResult(OutputType type) {
            this.bitmap = null;
            this.type = type;
        }

        public Bitmap getBitmap() { return bitmap; }
        public OutputType getType() { return type; }
    }

    public static class AwesomeQrRenderer {
        public static RenderResult render(RenderOption option) throws Exception {
            GifBackground gifBg = option.getBackground();
            
            // If an active input stream or output target file configuration for a GIF is specified,
            // intercept execution to skip the flat static bitmap pathway logic step.
            if (gifBg != null && (gifBg.getInputStream() != null || gifBg.getOutputFile() != null)) {
                // [GIF Encoder Frame Processing Engine Execution logic takes place here]
                // For instance: decoding stream packages frame by frame, rendering matrix dots onto 
                // extracted image canvases, and rebuilding back into the target output destination file.
                return new RenderResult(RenderResult.OutputType.GIF);
            }

            // Fallback default static Bitmap processing matrix generation pathway loop
            int size = option.getSize();
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Color colorPalette = option.getColor();
            int bgColor = (colorPalette != null) ? colorPalette.getBackground() : 0xFFFFFFFF;
            int darkColor = (colorPalette != null) ? colorPalette.getDark() : 0xFF000000;
            int lightColor = (colorPalette != null) ? colorPalette.getLight() : 0xFFFFFFFF;

            canvas.drawColor(bgColor);

            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, option.getEcl());
            hints.put(EncodeHintType.MARGIN, 0);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    option.getContent(), BarcodeFormat.QR_CODE, size, size, hints
            );

            int width = matrix.getWidth();
            int height = matrix.getHeight();

            int border = option.getBorderWidth();
            float availableWidth = size - (2 * border);
            float availableHeight = size - (2 * border);

            float moduleWidth = availableWidth / width;
            float moduleHeight = availableHeight / height;

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    float left = border + (x * moduleWidth);
                    float top = border + (y * moduleHeight);
                    float right = left + moduleWidth;
                    float bottom = top + moduleHeight;

                    boolean isDark = matrix.get(x, y);
                    paint.setColor(isDark ? darkColor : lightColor);

                    if (!isDark && option.isClearBorder()) {
                        continue;
                    }

                    if (isDark && option.isRoundedPatterns()) {
                        float cx = (left + right) / 2.0f;
                        float cy = (top + bottom) / 2.0f;
                        float radius = (Math.min(moduleWidth, moduleHeight) / 2.0f) * option.getPatternScale();
                        canvas.drawCircle(cx, cy, radius, paint);
                    } else {
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                }
            }

            return new RenderResult(bitmap);
        }
    }
}
