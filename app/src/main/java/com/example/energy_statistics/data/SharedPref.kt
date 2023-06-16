package com.example.energy_statistics.data

import android.content.Context

class SharedPref(context: Context) {
    private val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setAccessTokenValue(key: String, value: String?) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun getAccessTokenValue(key: String) = sharedPref.getString(key, null)

    fun logout(onLogout: ()-> Unit) {
        sharedPref.edit().clear().apply().apply { onLogout() }
    }

    companion object {
        const val PREF_NAME = "SharedPref"
        const val KEY_USER = "access_token"
    }
}