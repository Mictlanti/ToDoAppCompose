package com.horizon.todoappgit.data.datastore

import android.content.Context
import android.content.SharedPreferences
import com.horizon.todoappgit.data.ToDoState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataAppStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val userState = ToDoState()

    companion object {
        private val PREFS_NAME = "user_prefs"
        private val KEY_THEME = "dark_theme"
    }

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSelectedTheme(darkTheme: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_THEME, darkTheme).apply()
    }

    fun getModeTheme() : Boolean {
        return sharedPreferences.getBoolean(KEY_THEME, userState.darkTheme)
    }

}