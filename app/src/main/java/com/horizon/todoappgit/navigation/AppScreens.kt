package com.horizon.todoappgit.navigation

sealed class AppScreens(val route : String) {
    data object HomeView : AppScreens("HomeRoute")

    data object AddNoteView : AppScreens("AddNoteRoute")

    data object EditNote : AppScreens("EditNoteView") {
        fun widthId(id: Int) = "$route/$id"
    }
}