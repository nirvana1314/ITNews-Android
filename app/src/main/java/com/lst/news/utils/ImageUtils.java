package com.lst.news.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by lisongtao on 2017/10/30.
 */

public class ImageUtils {
    public static boolean checkImageExits(Context context, String imageName) {
        String name = context.getCacheDir()+"/.lstCache/"+imageName+".jpg";
        File file = new File(name);
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }

    public static Bitmap getBitMapWithUrl(Context context, String imageName) {
        if (ImageUtils.checkImageExits(context, imageName)) {
            String path = context.getCacheDir()+"/.lstCache/"+imageName+".jpg";
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }

}
