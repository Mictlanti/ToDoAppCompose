package com.horizon.todoappgit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.todoappgit.data.HomeworkState
import com.horizon.todoappgit.data.ToDoState
import com.horizon.todoappgit.data.datastore.DataAppStore
import com.horizon.todoappgit.entitys.HomeworkEntity
import com.horizon.todoappgit.events.ToDoEvents
import com.horizon.todoappgit.repository.HomeworkRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
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
                                hw.color
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

        _state.value = _state.value.copy(
            title = "", body = "", colorCard = 0
        )
    }

    suspend fun getNoteById(idDoc: Int) : Boolean {
        val note = repo.getNoteById(idDoc).firstOrNull()

        return if(note != null) {
            _state.update {
                it.copy(
                    id= idDoc,
                    title = note.title,
                    body = note.body,
                    colorCard = note.color
                )
            }
            true
        } else {
            false
        }
//        viewModelScope.launch {
//            repo.getNoteById(idDoc).collect { note ->
//                if (note != null) {
//                    _state.update {
//                        it.copy(
//                            id = idDoc,
//                            title = note.title,
//                            body = note.body,
//                            colorCard = note.color
//                        )
//                    }
//                }
//            }
//        }
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
            _state.value = _state.value.copy(
                title = "", body = "", colorCard = 0
            )
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