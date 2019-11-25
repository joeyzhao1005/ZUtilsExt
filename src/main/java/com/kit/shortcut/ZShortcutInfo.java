package com.kit.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kit.app.application.AppMaster;
import com.kit.utils.ColorUtils;
import com.kit.utils.DensityUtils;
import com.kit.utils.DrawableUtils;
import com.kit.utils.StringUtils;
import com.kit.utils.VectorDrawableUtils;
import com.kit.utils.log.Zog;
import com.kit.vector.PathDrawable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zhao on 2017/5/31.
 */

public class ZShortcutInfo implements Cloneable {


    public ComponentName getComponentName() {
        if (componentName == null) {
            try {
                componentName = new ComponentName(targetPackage, targetClass);
            } catch (Exception e) {
            }
        }
        return componentName;
    }

    public void setComponentName(ComponentName componentName) {
        this.componentName = componentName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getShortcutId() {
        return shortcutId;
    }

    public void setShortcutId(String shortcutId) {
        this.shortcutId = shortcutId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getShortcutShortLabel() {
        return shortcutShortLabel;
    }

    public void setShortcutShortLabel(int shortcutShortLabel) {
        this.shortcutShortLabel = shortcutShortLabel;
    }


    public int getShortcutLongLabel() {
        return shortcutLongLabel;
    }

    public void setShortcutLongLabel(int shortcutLongLabel) {
        this.shortcutLongLabel = shortcutLongLabel;
    }

    public int getShortcutDisabledMessage() {
        return shortcutDisabledMessage;
    }

    public void setShortcutDisabledMessage(int shortcutDisabledMessage) {
        this.shortcutDisabledMessage = shortcutDisabledMessage;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public Set<Extra> getExtras() {
        return extras;
    }


    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }

    public void putExtra(Extra extra) {
        if (extra == null) {
            return;
        }

        if (this.extras == null) {
            this.extras = new HashSet<>();
        }
        this.extras.add(extra);
    }

    public String getDisabledMessage() {
        return disabledMessage;
    }

    public void setDisabledMessage(String disabledMessage) {
        this.disabledMessage = disabledMessage;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public String getLongLabel() {
        return longLabel;
    }

    public void setLongLabel(String longLabel) {
        this.longLabel = longLabel;
    }

    public boolean isRootLaunch() {
        return isRootLaunch;
    }

    public void setRootLaunch(boolean rootLaunch) {
        isRootLaunch = rootLaunch;
    }


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getIntentStr() {
        return intentStr;
    }

    public void setIntentStr(String intentStr) {
        this.intentStr = intentStr;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public long getSerialNumberForUser() {
        return serialNumberForUser;
    }

    public void setSerialNumberForUser(long serialNumberForUser) {
        this.serialNumberForUser = serialNumberForUser;
    }

    public String getIconDrawablePathData() {
        return iconDrawablePathData;
    }

    public void setIconDrawablePathData(String iconDrawablePathData) {
        this.iconDrawablePathData = iconDrawablePathData;
    }

    public String getIconDrawableFillColor() {
        return iconDrawableFillColor;
    }

    public void setIconDrawableFillColor(String iconDrawableFillColor) {
        this.iconDrawableFillColor = iconDrawableFillColor;
    }

    public int getIconDrawableSize() {
        return iconDrawableSize;
    }

    public void setIconDrawableSize(int iconDrawableSize) {
        this.iconDrawableSize = iconDrawableSize;
    }

    long serialNumberForUser;


    @Expose(serialize = false)
    Intent intent;

    @SerializedName("intentStr")
    String intentStr;

    @SerializedName("isPinned")
    boolean isPinned;

    @SerializedName("isDynamic")
    boolean isDynamic;

    @SerializedName("rank")
    int rank;

    @SerializedName("componentName")
    ComponentName componentName;

    @SerializedName("shortcutId")
    String shortcutId;

    @SerializedName("enabled")
    boolean enabled;

    @SerializedName("iconDrawable")
    Drawable iconDrawable;

    @SerializedName("shortLabel")
    String shortLabel;

    @SerializedName("longLabel")
    String longLabel;

    @SerializedName("disabledMessage")
    String disabledMessage;

    @SerializedName("icon")
    int icon;

    @SerializedName("shortcutShortLabel")
    int shortcutShortLabel;

    @SerializedName("shortcutLongLabel")
    int shortcutLongLabel;

    @SerializedName("shortcutDisabledMessage")
    int shortcutDisabledMessage;


    @SerializedName("action")
    String action;

    @SerializedName("targetPackage")
    String targetPackage;

    @SerializedName("targetClass")
    String targetClass;

    @SerializedName("categories")
    Set<String> categories;

    @SerializedName("extras")
    Set<Extra> extras;

    @SerializedName("data")
    String data;

    @SerializedName("iconDrawablePathData")
    String iconDrawablePathData;

    @SerializedName("iconDrawableFillColor")
    String iconDrawableFillColor;

    @SerializedName("isRootLaunch")
    boolean isRootLaunch;

    @SerializedName("iconDrawableSize")
    int iconDrawableSize = 32;

    /**
     * 耗时操作 外部使用请在线程中使用
     */
    public void deal() {
        if (StringUtils.isEmptyOrNullStr(iconDrawablePathData)) {
            return;
        }
        int color = Color.parseColor(iconDrawableFillColor);
        if (color == Color.TRANSPARENT) {
            return;
        }
        int size = iconDrawableSize == 0 ? 32 : iconDrawableSize;
        PathDrawable pathDrawable = new PathDrawable(iconDrawablePathData, color, size, size);
        Bitmap bitmap = DrawableUtils.drawableToBitmap(pathDrawable, DensityUtils.dip2px(20), DensityUtils.dip2px(20));
        iconDrawable = new BitmapDrawable(bitmap);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ZShortcutInfo) && !StringUtils.isEmptyOrNullStr(shortcutId)) {
            return shortcutId.equals(((ZShortcutInfo) obj).shortcutId);
        }
        return false;
    }

    @Override
    public ZShortcutInfo clone() {
        ZShortcutInfo zShortcutInfo = new ZShortcutInfo();
        zShortcutInfo.setComponentName(this.componentName);
        zShortcutInfo.setShortcutId(this.shortcutId);
        zShortcutInfo.setEnabled(this.enabled);
        zShortcutInfo.setIconDrawable(this.iconDrawable);
        zShortcutInfo.setShortLabel(this.shortLabel);
        zShortcutInfo.setLongLabel(this.longLabel);
        zShortcutInfo.setDisabledMessage(this.disabledMessage);
        zShortcutInfo.setIcon(this.icon);
        zShortcutInfo.setShortcutShortLabel(this.shortcutShortLabel);
        zShortcutInfo.setShortcutLongLabel(this.shortcutLongLabel);
        zShortcutInfo.setShortcutDisabledMessage(this.shortcutDisabledMessage);
        zShortcutInfo.setAction(this.action);
        zShortcutInfo.setTargetPackage(this.targetPackage);
        zShortcutInfo.setTargetClass(this.targetClass);
        zShortcutInfo.setCategories(this.categories);
        zShortcutInfo.setExtras(this.extras);
        zShortcutInfo.setData(this.data);
        zShortcutInfo.setRootLaunch(this.isRootLaunch);
        zShortcutInfo.setIntentStr(this.intentStr);
        zShortcutInfo.setIntent(this.intent);
        zShortcutInfo.setDynamic(this.isDynamic);
        zShortcutInfo.setPinned(this.isPinned);
        zShortcutInfo.setRank(this.rank);
        zShortcutInfo.setSerialNumberForUser(this.serialNumberForUser);
        zShortcutInfo.setIconDrawablePathData(this.iconDrawablePathData);
        zShortcutInfo.setIconDrawableFillColor(this.iconDrawableFillColor);
        zShortcutInfo.setIconDrawableSize(this.iconDrawableSize);
        return zShortcutInfo;
    }
}
