package com.studclips.app.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String PREFERENCE_NAME = "StudClips";
    SharedPreferences sharedPreferences;

    public static String getStringWithDefaultValue(String key, String defaultValue, Context context) {
        try {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static String getString(String key, Context context) {
        try {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveInt(String key, int value, Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public static int getInt(String key, Context context) {
        try {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getInt(key, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void saveLong(String key, long value, Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putLong(key, value).apply();
    }

    public static long getLong(String key, Context context) {
        try {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getLong(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void saveBoolean(String key, boolean value, Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, Context context) {
        try {
            return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(key, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void removeKey(Context context, String key) {
        try {
            context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().remove(key).apply();
        } catch (Exception ignored) {
        }
    }

    public static void removeAll(Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }

    public static void saveString(String key, String value, Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static boolean isLogin(Context context) {
        return getBoolean(Global.isLogin, context);
    }
}
