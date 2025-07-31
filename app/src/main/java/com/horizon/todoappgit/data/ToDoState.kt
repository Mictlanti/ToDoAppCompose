package com.horizon.todoappgit.data

import androidx.compose.ui.graphics.Color

data class ToDoState(
    val id: Int? = null,
    val title: String = "",
    val body: String = "",
    val colorCard: Int = 0,
    val listHomeWork: List<HomeworkState> = emptyList(),
    val darkTheme : Boolean = false
)