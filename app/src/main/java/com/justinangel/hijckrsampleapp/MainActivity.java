package com.justinangel.hijckrsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.justinangel.hijckr.HijckrClassLoader;

/*
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public ClassLoader getClassLoader() {
        return new HijckrClassLoader(super.getClassLoader())
                .withClassRouting(EditText.class, SmileyEditText.class);
    }
}
*/


import com.justinangel.hijckr.HijckrActivity;
public class MainActivity extends HijckrActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
