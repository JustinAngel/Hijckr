package com.justinangel.hijckrsampleapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class SmileyEditText extends EditText {
    public SmileyEditText(Context context) {
        super(context);
    }

    public SmileyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmileyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (text.toString().contains(":)")) {
            setText(text.toString().replace(":)", "\uD83D\uDE0A"));
        }
    }
}
