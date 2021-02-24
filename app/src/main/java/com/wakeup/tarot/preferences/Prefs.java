package com.wakeup.tarot.preferences;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

public class Prefs {
    private static SharedPrefs sharedPrefs;

    public static void init(@NonNull Context context) {
        sharedPrefs = new SharedPrefs(context);
    }

    @NonNull
    private static SharedPrefs getPrefs() {
        if (sharedPrefs == null) {
            Log.v("Prefs", "Prefs has not been instantiated. Call init() with context");
        }
        return sharedPrefs;
    }

    public static int getCardBackground(Context context) {
        return getPrefs().get(Keys.CARD_BACKGROUND, 0);
    }

    public static int getAppBackground(Context context) {
        return getPrefs().get(Keys.APP_BACKGROUND, 0);
    }

    public static void setCardBackground(@NonNull int value) {
        getPrefs().put(Keys.CARD_BACKGROUND, value);
    }

    public static void setAppBackground(@NonNull int value) {
        getPrefs().put(Keys.APP_BACKGROUND, value);
    }
}
