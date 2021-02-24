package com.wakeup.tarot.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;

final class SharedPrefs {
    private static final String PREFERENCES_NAME = "com.wakeup.tarot.SHARED_PREF";
    private final SharedPreferences sharedPrefs;

    SharedPrefs(@NonNull Context context) {
        this.sharedPrefs = context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private Editor getEditor() {
        return this.sharedPrefs.edit();
    }

    public void put(String str, int value) {
        getEditor().putInt(str, value).commit();
    }

    public int get(String str, int value) {
        return this.sharedPrefs.getInt(str, value);
    }

    public void put(String str, boolean z) {
        getEditor().putBoolean(str, z).commit();
    }

    public boolean get(String str, boolean z) {
        return this.sharedPrefs.getBoolean(str, z);
    }
}
