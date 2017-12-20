package com.kit.imagelib;

import com.kit.imagelib.interfaces.IImageLoader;
import com.kit.utils.log.ZogUtils;

/**
 * Created by Zhao on 16/7/3.
 */
public class ImageLoader {

    private static IImageLoader iImageLoader;

    public static IImageLoader getInstance() {
        if(iImageLoader ==null ){
            ZogUtils.e("You must called setImageLoader(IImageLoader iImageLoader) before.");
        }
        return iImageLoader;
    }

    public static void setImageLoader(IImageLoader yourImageLoader) {
        iImageLoader = yourImageLoader;
    }




}
