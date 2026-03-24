package com.fcbox.locker.ai.project

import android.content.Context
import android.content.SharedPreferences

object UserStorage {
    private const val PREFS_NAME = "ai_project_prefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_PHONE = "user_phone"
    private const val KEY_NAME = "user_name"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
        if (!isLoggedIn) {
            clearUserInfo(context)
        }
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveUserInfo(context: Context, phone: String, name: String) {
        getPrefs(context).edit()
            .putString(KEY_PHONE, phone)
            .putString(KEY_NAME, name)
            .apply()
    }

    private fun clearUserInfo(context: Context) {
        getPrefs(context).edit()
            .remove(KEY_PHONE)
            .remove(KEY_NAME)
            .apply()
    }
}
