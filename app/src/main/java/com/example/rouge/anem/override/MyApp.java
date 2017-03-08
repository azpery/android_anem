package com.example.rouge.anem.override;

import android.app.Application;

/**
 * Created by r0b on 22/01/17.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SERIF", "fonts/MuseoSans-300.otf");
        //  This FontsOverride comes from the example I posted above
    }
}
