package com.horizon.todoappgit.events

sealed class ToDoEvents {
    data class TitleTextField(val value : String) : ToDoEvents()
    data class BodyTextField(val value : String) : ToDoEvents()
    data class SelectedColor(val value : Int) : ToDoEvents()
}