package com.spirit.kitchn.core.auth.repo

import android.content.Context

class TokensManager(
    context: Context,
) {
    private val sharedPref = context.getSharedPreferences("mne_poh", Context.MODE_PRIVATE)

    fun getAccess(): String {
        return sharedPref.getString("access_token", "") ?: ""
    }

    fun saveAccess(value: String) {
        with (sharedPref.edit()) {
            putString("access_token", value)
            apply()
        }
    }
}