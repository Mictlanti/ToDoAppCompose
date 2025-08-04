package com.horizon.todoappgit.data.repository

import android.content.SharedPreferences
import com.horizon.todoappgit.domain.ToDoState
import com.horizon.todoappgit.ui.constans.KEY_THEME
import com.horizon.todoappgit.ui.constans.KEY_VIEW_CARDS
import javax.inject.Inject

class PreferencesRepo @Inject constructor(
    private val prefs: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    private val userState = ToDoState()

    fun saveSelectedTheme(darkTheme: Boolean) {
        editor.putBoolean(KEY_THEME, darkTheme).apply()
    }

    fun getModeTheme() : Boolean {
        return prefs.getBoolean(KEY_THEME, userState.darkTheme)
    }

    fun saveCardsView(view: Int) {
        editor.putInt(KEY_VIEW_CARDS, view).apply()
    }

    fun getCardsView () : Int {
        return prefs.getInt(KEY_VIEW_CARDS, userState.sorterView)
    }
}