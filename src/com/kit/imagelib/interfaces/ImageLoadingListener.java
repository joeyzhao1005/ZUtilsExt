package com.kit.imagelib.interfaces;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Zhao on 16/7/3.
 */
public interface ImageLoadingListener {

    public void onLoadingStarted(String s, View view);

    public void onLoadingFailed(String s, View view, String failReason);

    public void onLoadingComplete(String s, View view, Bitmap bitmap);

    public void onLoadingCancelled(String s, View view);

}
