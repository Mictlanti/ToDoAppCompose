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
        private val KEY_VIEW_CARDS = "cards_view"
    }

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSelectedTheme(darkTheme: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_THEME, darkTheme).apply()
    }

    fun getModeTheme() : Boolean {
        return sharedPreferences.getBoolean(KEY_THEME, userState.darkTheme)
    }

    fun saveCardsView(view: Int) {
        sharedPreferences.edit().putInt(KEY_VIEW_CARDS, view).apply()
    }

    fun getCardsView () : Int {
        return sharedPreferences.getInt(KEY_VIEW_CARDS, userState.sorterView)
    }
}