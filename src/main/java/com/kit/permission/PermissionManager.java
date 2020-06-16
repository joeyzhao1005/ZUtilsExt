/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;

import android.util.Log;

import com.kit.app.application.AppMaster;
import com.kit.extend.R;
import com.kit.utils.PermissionCallback;
import com.kit.utils.PermissionUtils;
import com.kit.utils.ResWrapper;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author joeyzhao
 */
public class PermissionManager {
    public static boolean check(Context context, @NonNull String permission) {
        return PermissionUtils.check(context, permission);
    }

    public static void check(Context context, @NonNull String permission, PermissionCallback callback) {
        PermissionUtils.check(context, permission, callback);

    }

    public static boolean check(@NonNull String permission) {
        return check(AppMaster.getInstance().getAppContext(), permission);
    }

    public static void check(@NonNull String permission, PermissionCallback callback) {
        check(AppMaster.getInstance().getAppContext(), permission, callback);
    }

    /**
     * 申请授权，当用户拒绝时，会显示默认一个默认的Dialog提示用户
     *
     * @param context
     * @param listener
     * @param permissions 要申请的权限
     */
    public static void requestPermission(@NonNull Context context, String tip, @NonNull PermissionListener listener, String... permissions) {
        if (permissions == null || permissions.length <= 0) {
            Log.e(TAG, "permission cannot be null or empty");
            return;
        }
        requestPermission(context, listener, permissions, true, new TipInfo(tip));
    }


    /**
     * 申请授权，当用户拒绝时，会显示默认一个默认的Dialog提示用户
     *
     * @param context
     * @param listener
     * @param permissions 要申请的权限
     */
    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener listener, String... permissions) {
        if (permissions == null || permissions.length <= 0) {
            Log.e(TAG, "permission cannot be null or empty");
            return;
        }
        requestPermission(context, listener, permissions, true, null);
    }

    /**
     * 申请授权，当用户拒绝时，可以设置是否显示Dialog提示用户，也可以设置提示用户的文本内容
     *
     * @param context
     * @param listener
     * @param permissions 需要申请授权的权限
     * @param showTip     当用户拒绝授权时，是否显示提示
     * @param tip         当用户拒绝时要显示Dialog设置
     */
    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener listener
            , @NonNull String[] permissions, boolean showTip, @Nullable TipInfo tip) {

        if (hasPermission(context, permissions)) {
            listener.onPermission(permissions, true);
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                listener.onPermission(permissions, false);
            } else {
                String key = String.valueOf(System.currentTimeMillis());
                if (listenerMap == null) {
                    listenerMap = new ConcurrentHashMap<String, PermissionListener>(10);
                }
                listenerMap.put(key, listener);
                Intent intent = new Intent(context, PermissionActivity.class);
                intent.putExtra("permission", permissions);
                intent.putExtra("key", key);
                intent.putExtra("showTip", showTip);
                if (tip != null) {
                    intent.putExtra("tipInfo", tip);
                }
                if (context instanceof Activity) {
                    //从activity中发起 直接启起来
                } else {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                context.startActivity(intent);
            }
        }
    }


    /**
     * 判断权限是否授权
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {

        if (permissions.length == 0) {
            return false;
        }

        for (String per : permissions) {
            int result = androidx.core.content.PermissionChecker.checkSelfPermission(context, per);
            if (result != androidx.core.content.PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断一组授权结果是否为授权通过
     *
     * @param grantResult
     * @return
     */
    public static boolean isGranted(@NonNull int... grantResult) {

        if (grantResult.length == 0) {
            return false;
        }

        for (int result : grantResult) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跳转到当前应用对应的设置页面
     *
     * @param context
     */
    public static void gotoSetting(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * @param key
     * @return
     */
    @Nullable
    static PermissionListener fetchListener(String key) {
        if (key == null) {
            return null;
        }
        if (listenerMap == null) {
            return null;
        }
        return listenerMap.remove(key);
    }


    public static final String TAG = "PermissionGrantor";
    private static ConcurrentHashMap<String, PermissionListener> listenerMap = new ConcurrentHashMap<String, PermissionListener>(10);


    /**
     * Created by dfqin on 2017/1/20.
     */

    public interface PermissionListener {

        /**
         * isPassed 是否通过授权
         *
         * @param permissions
         */
        void onPermission(@NonNull String[] permissions, boolean isPassed);


    }
}
