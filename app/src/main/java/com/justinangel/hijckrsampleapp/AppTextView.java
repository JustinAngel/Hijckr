package com.justinangel.hijckrsampleapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppTextView extends TextView {
    public AppTextView(Context context) {
        super(context);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setTextSize(28);
        this.setTextColor(Color.RED);
    }
}
