package com.campplanner.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static final String TAG = "SessionManager";
    private static final String PREF_NAME = "CampPlannerSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_NAME = "userName";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(int userId, String email, String fullName) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_NAME, fullName);
        editor.apply();

        Log.d(TAG, "Session créée: userId=" + userId + ", email=" + email);
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = pref.getBoolean(KEY_IS_LOGGED_IN, false);
        int userId = pref.getInt(KEY_USER_ID, -1);

        Log.d(TAG, "isLoggedIn: " + isLoggedIn + ", userId: " + userId);

        return isLoggedIn && userId != -1;
    }

    public int getUserId() {
        int userId = pref.getInt(KEY_USER_ID, -1);
        Log.d(TAG, "getUserId: " + userId);
        return userId;
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    public void logout() {
        editor.clear();
        editor.apply();

        Log.d(TAG, "Session détruite - utilisateur déconnecté");
    }

    public void clearRememberedEmail() {
        editor.remove(KEY_USER_EMAIL);
        editor.apply();
    }
}