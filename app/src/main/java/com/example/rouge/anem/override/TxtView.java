package com.example.rouge.anem.override;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by r0b on 22/01/17.
 */

public class TxtView extends TextView {
    public TxtView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TxtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TxtView(Context context) {
        super(context);
        init();
    }

    private void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne & Limousines.ttf");
        setTypeface(tf);

    }
}
