package com.kit.imagelib.interfaces;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    void displayFile(ImageView iv, String filePath,ImageLoadingListener loadingListener);

    /**
     * 避免列表错位的加载
     * @param iv
     * @param placeHolderResId
     * @param filePath
     */
    void displayFileFixed(ImageView iv, @DrawableRes int placeHolderResId, String filePath);

    void displayFile(ImageView iv, String filePath, ImageView.ScaleType scaleType);

    Bitmap loadImageSync(String url);

    File getCacheImage(String url);

    void cancelDisplayTask(ImageView iv);

    void loadImage(String url, @Nullable ImageLoadingListener imageLoadingListener
            , @Nullable ImageLoadingProgressListener imageLoadingProgressListener);


    void clearFileCache(String path);
    void clearFileMemoryCache(String path);

    void clearCaches();

    void resume(Activity activity);
    void resume(Fragment fragment);


    void pause();
    void pause(Activity activity);
    void pause(Fragment fragment);
}
