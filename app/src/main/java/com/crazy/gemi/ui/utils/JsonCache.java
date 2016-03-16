package com.crazy.gemi.ui.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class JsonCache {

    private static JsonCache jsonCache = null;

    public static final String NAME = "com.crazy.gemi.ui.utils.JsonCache";

    private SharedPreferences preferences;

    private static Context context;

    private JsonCache(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME, 0);
    }

    public static JsonCache getInstance(Context context){

        if (jsonCache == null) {
            jsonCache = new JsonCache(context);
        }
        return jsonCache;
    }

    public void setJsonCache(String key, String value){
        preferences.edit().putString(key, value).commit();
    }

    public String getJsonCache(String key){
        return preferences.getString(key, null);
    }

    public void clearCache() {
        preferences.edit().clear().commit();
    }

    public void removeCache(String key){
        preferences.edit().remove(key).commit();
    }
}
