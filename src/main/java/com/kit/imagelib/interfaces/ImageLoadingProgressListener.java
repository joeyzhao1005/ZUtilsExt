package com.kit.imagelib.interfaces;

import android.view.View;

/**
 * Created by Zhao on 16/7/3.
 */
public interface ImageLoadingProgressListener {
    public void onProgressUpdate(String imageUri, View view, int current, int total) ;
}
