package com.horizon.todoappgit.domain

data class ToDoState(
    val id: Int? = null,
    val title: String = "",
    val body: String = "",
    val colorCard: Int = 0,
    val listHomeWork: List<HomeworkState> = emptyList(),
    val darkTheme : Boolean = false,
    val orderBy: Int = 0,
    val sorterView : Int = 0
)