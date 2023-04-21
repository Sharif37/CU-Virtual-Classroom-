package com.example.cuvc;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPrefUtils {
    private static final String PREF_KEY = "class_key";
    private SharedPreferences preferences;

    public SharedPrefUtils(Context context) {
        preferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }

    public String getClassKey() {
        String classkey = preferences.getString("classCode", "");
        return classkey;
    }
}

