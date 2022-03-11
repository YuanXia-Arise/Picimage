package com.yuanxia.yuanxia.imaging;

import android.content.Context;

public class IMG {

    private static Context mApplicationContext;

    public static void initialize(Context context) {
        mApplicationContext = context.getApplicationContext();

    }

    public static class Config {

        private boolean isSave;

    }
}
