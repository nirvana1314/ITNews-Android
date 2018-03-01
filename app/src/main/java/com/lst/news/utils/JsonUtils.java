package com.lst.news.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by lisongtao on 2017/10/27.
 */

public class JsonUtils {
    private static Gson gson;
    public static <T>T parse (String json, Class<T> t) {
        if (gson == null) {
            gson = new Gson();
        }

        if (TextUtils.isEmpty(json) || t == null) {
            return null;
        }

        T back = gson.fromJson(json, t);
        return back;
    }
}
