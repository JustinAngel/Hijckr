package com.justinangel.hijckrsampleapp;

import android.app.Application;
import android.widget.EditText;
import android.widget.TextView;

import com.justinangel.hijckr.HijckrClassLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HijckrClassLoader.addGlobalClassRouting(TextView.class, AppTextView.class);
    }
}
