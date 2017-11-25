package com.android.mybigday;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;


import java.util.Locale;


public class MyBigDayApplication extends MultiDexApplication {

    private static final String TAG = MyBigDayApplication.class.getSimpleName();
    private static Context context;
    private static MyBigDayApplication mInstance;
    private Configuration config;




    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MyBigDayApplication.context = getApplicationContext();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static synchronized MyBigDayApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return MyBigDayApplication.context;
    }


}
