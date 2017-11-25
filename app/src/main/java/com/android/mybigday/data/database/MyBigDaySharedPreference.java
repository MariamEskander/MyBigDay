package com.android.mybigday.data.database;

import android.content.Context;
import android.content.SharedPreferences;



public class MyBigDaySharedPreference {

    private static final String PREFS_NAME = "com.android.mybigday";
    private static final String DATE = "date";
    private static final String COVER = "cover";

    private SharedPreferences sharedPreferences;

    public MyBigDaySharedPreference(Context context) {
        super();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public void saveCover(String s) {
        sharedPreferences.edit().putString(COVER, s).apply();
    }

    public  String getCover() {
        return sharedPreferences.getString(COVER, "");
    }

    public void saveDate(String s) {
        sharedPreferences.edit().putString(DATE, s).apply();
    }

    public  String getDate() {
        return sharedPreferences.getString(DATE, "");
    }

}
