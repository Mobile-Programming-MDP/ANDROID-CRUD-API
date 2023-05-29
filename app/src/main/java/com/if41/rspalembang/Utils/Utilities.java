package com.if41.rspalembang.Utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.if41.rspalembang.Activity.LoginActivity;
//silakan ambil dari file Utilities.java di Project Sharead Prefernces (Biodataku/Buku Kita)
public class Utilities {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void setPreferences(Context context, String key, String value) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferences(Context context, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public boolean isLogin(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPreferences.getString(LoginActivity.LOGIN_USERNAME, null);
        return value != null;
    }
}