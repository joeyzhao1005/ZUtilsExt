package com.kit.imagelib.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Zhao on 16/7/3.
 */
public interface IImageLoader {


    void display(ImageView iv, Integer drawableId);


    void display(ImageView iv, String url);

    void display(ImageView iv, String url, ImageView.ScaleType scaleType);

    void displayFile(ImageView iv, String filePath);

    void displayFile(ImageView iv, String filePath, ImageView.ScaleType scaleType);

    Bitmap loadImageSync(String url);

    File getCacheImage(String url);


    void cancelDisplayTask(ImageView iv);

    void loadImage(String url, @Nullable ImageLoadingListener imageLoadingListener
            , @Nullable ImageLoadingProgressListener imageLoadingProgressListener);


    void clearCache(Uri uri);

    void clearFileCache(String path);

    void resume();

    void pause();
}
