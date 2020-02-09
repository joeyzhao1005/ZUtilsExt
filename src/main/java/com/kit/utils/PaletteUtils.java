package com.kit.utils;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.kit.utils.log.Zog;

/**
 * @author joeyzhao
 */
public class PaletteUtils {

    public static int getColor(@Nullable Bitmap bitmap, int defaultColor) {
        if (bitmap == null || bitmap.isRecycled()) {
            return defaultColor;
        }
        try {
            Palette palette = Palette.from(bitmap).generate();
            Palette.Swatch swatch = palette.getVibrantSwatch();
            Palette.Swatch swatchDark = palette.getDarkVibrantSwatch();
            Palette.Swatch swatchLight = palette.getLightVibrantSwatch();

            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
            Palette.Swatch mutedSwatchDark = palette.getDarkMutedSwatch();
            Palette.Swatch mutedSwatchLight = palette.getLightMutedSwatch();


            Integer themeColor = null;
            if (swatch != null) {
                themeColor = swatch.getRgb();
            }

            if (themeColor != null) {
                return themeColor;
            }

            if (mutedSwatch != null) {
                themeColor = mutedSwatch.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }

            if (swatchDark != null) {
                themeColor = swatchDark.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (mutedSwatchDark != null) {
                themeColor = mutedSwatchDark.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (swatchLight != null) {
                themeColor = swatchLight.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            }
            if (mutedSwatchLight != null) {
                themeColor = mutedSwatchLight.getRgb();
            }
            if (themeColor != null) {
                return themeColor;
            } else {
                themeColor = defaultColor;
            }
            return themeColor;
        } catch (StackOverflowError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultColor;
    }
}
