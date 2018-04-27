/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

public class PermissionManager {


//    /**
//     * 检查用户是否登录 是否有权限做事情
//     */
//
//    public void checkPremission(Activity context, Consumer<Boolean> consumer, @NonNull String... permissions) {
//        try {
//            boolean isPassed = true;
//            if (permissions.length > 0) {
//                for (String permission : permissions) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (context == null) {
//                            isPassed = false;
//                        } else {
//                            isPassed = isPassed & (android.support.v4.content.PermissionManager.PERMISSION_GRANTED == android.support.v4.content.PermissionManager.checkSelfPermission(context, permission));
//                        }
//                    }
//                }
//            }
//            if (isPassed) {
//                if (consumer != null) {
//                    consumer.accept(true);
//                }
//            } else {
//                if (context == null) {
//                    if (consumer != null) {
//                        consumer.accept(false);
//                    }
//                    return;
//                }
//                ActivityCompat.requestPermissions(context, permissions, 0);
//            }
//        } catch (Exception e) {
//            LogUtils.e("checkPremission Exception: " + e);
//        }
//    }

    /**
     * 申请授权，当用户拒绝时，会显示默认一个默认的Dialog提示用户
     *
     * @param context
     * @param listener
     * @param permissions 要申请的权限
     */
    public static void requestPermission(Context context, PermissionListener listener, String... permissions) {
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
     * @param showTip    当用户拒绝授权时，是否显示提示
     * @param tip        当用户拒绝时要显示Dialog设置
     */
    public static void requestPermission(@NonNull Context context, @NonNull PermissionListener listener
            , @NonNull String[] permissions, boolean showTip, @Nullable TipInfo tip) {


        if (listener == null) {
            Log.e(TAG, "listener is null");
            return;
        }

        if (hasPermission(context, permissions)) {
            listener.onPermission(permissions, true);
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                listener.onPermission(permissions, false);
            } else {
                String key = String.valueOf(System.currentTimeMillis());
                listenerMap.put(key, listener);
                Intent intent = new Intent(context, PermissionActivity.class);
                intent.putExtra("permission", permissions);
                intent.putExtra("key", key);
                intent.putExtra("showTip", showTip);
                intent.putExtra("tip", tip);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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
            int result = android.support.v4.content.PermissionChecker.checkSelfPermission(context, per);
            if (result != android.support.v4.content.PermissionChecker.PERMISSION_GRANTED) {
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
    static PermissionListener fetchListener(String key) {
        return listenerMap.remove(key);
    }


    public static class TipInfo implements Parcelable {


        String title;
        String content;
        String cancel;  //取消按钮文本
        String ensure;  //确定按钮文本

        public TipInfo(@Nullable String title, @Nullable String content, @Nullable String cancel, @Nullable String ensure) {
            this.title = title;
            this.content = content;
            this.cancel = cancel;
            this.ensure = ensure;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.cancel);
            dest.writeString(this.ensure);
        }

        protected TipInfo(Parcel in) {
            this.title = in.readString();
            this.content = in.readString();
            this.cancel = in.readString();
            this.ensure = in.readString();
        }

        public static final Creator<TipInfo> CREATOR = new Creator<TipInfo>() {
            @Override
            public TipInfo createFromParcel(Parcel source) {
                return new TipInfo(source);
            }

            @Override
            public TipInfo[] newArray(int size) {
                return new TipInfo[size];
            }
        };
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
