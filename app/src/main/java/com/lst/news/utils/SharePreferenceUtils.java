package com.lst.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lisongtao on 2017/10/30.
 */

public class SharePreferenceUtils {

    static final String File_Name = "Preference";
    public static final String Next_Req_Time = "Next_Req_Time";
    public static final String AD_RESPONSE_STRING = "AD_RESPONSE_STRING";

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(File_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(File_Name, Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }


}
