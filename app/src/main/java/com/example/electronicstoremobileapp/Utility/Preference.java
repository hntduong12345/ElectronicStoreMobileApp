package com.example.electronicstoremobileapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    public static void Add(Context context, String name, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public static void Remove(Context context, String name, String key){
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
    public static String Get(Context context, String name, String key){
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }
}
