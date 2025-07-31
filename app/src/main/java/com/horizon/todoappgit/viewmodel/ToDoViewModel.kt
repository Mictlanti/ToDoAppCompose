package com.horizon.todoappgit.viewmodel

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.todoappgit.data.HomeworkState
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.data.datastore.DataAppStore
import com.horizon.todoappgit.entitys.HomeworkEntity
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.repository.HomeworkRepo
import com.horizon.todoappgit.ui.theme.primaryDarkOther1
import com.horizon.todoappgit.ui.theme.primaryDarkOther2
import com.horizon.todoappgit.ui.theme.primaryLightOther1
import com.horizon.todoappgit.ui.theme.primaryLightOther2
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther1
import com.horizon.todoappgit.ui.theme.tertiaryDarkOther2
import com.horizon.todoappgit.ui.theme.tertiaryLightOther1
import com.horizon.todoappgit.ui.theme.tertiaryLightOther2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repo: HomeworkRepo,
    private val dataPref: DataAppStore
) : ViewModel() {

    private val _state = MutableStateFlow(ToDoState())
    val state: StateFlow<ToDoState> = _state.asStateFlow()

    private val emptyState = ToDoState()

    init {
        loadThemeColor()
        viewModelScope.launch {
            repo.getAllNotes().collect { list ->
                _state.update {
                    it.copy(
                        listHomeWork = list.map { hw ->
                            HomeworkState(
                                hw.idDoc,
                                hw.title,
                                hw.body,
                                color = hw.color
                            )
                        }
                    )
                }
            }
        }
    }

    fun onEvent(event: ToDoEvents) {
        when (event) {
            is ToDoEvents.TitleTextField -> {
                _state.value = _state.value.copy(title = event.value)
            }

            is ToDoEvents.BodyTextField -> {
                _state.value = _state.value.copy(body = event.value)
            }

            is ToDoEvents.SelectedColor -> {
                _state.value = _state.value.copy(colorCard = event.value)
            }
        }
    }

    fun addNote() {
        val title = _state.value.title
        val body = _state.value.body
        val color = _state.value.colorCard

        viewModelScope.launch {
            val idDoc = repo.insertNote(   //This repo.insert(....) returns de idDoc
                HomeworkEntity(
                    title = title,
                    body = body,
                    color = color
                )
            )

            _state.update { it.copy(id = idDoc.toInt()) }
        }

        _state.value = emptyState
    }

    fun getNoteById(idDoc: Int) {
        viewModelScope.launch {
            repo.getNoteById(idDoc).collect { note ->
                if (note != null) {
                    _state.update {
                        it.copy(
                            id = idDoc,
                            title = note.title,
                            body = note.body
                        )
                    }
                }
            }
        }
    }

    fun editNote() {
        val id = _state.value.id ?: return
        val title = _state.value.title
        val body = _state.value.body
        val color = _state.value.colorCard

        val update = HomeworkEntity(
            idDoc = id,
            title = title,
            body = body,
            color = color
        )

        viewModelScope.launch {
            repo.insertNote(update) //This updates the latest note
        }

    }

    fun deleteHomework() {

        val id = _state.value.id ?: return

        viewModelScope.launch {
            repo.deleteNote(id)
            _state.value = emptyState
        }
    }

    private fun loadThemeColor() {

        val value = dataPref.getModeTheme()

        changeThemeMode(value)

    }

    fun changeThemeMode(themeValue: Boolean) {

        _state.update { state ->
            state.copy(
                darkTheme = themeValue
            )
        }

        dataPref.saveSelectedTheme(themeValue)

    }

}