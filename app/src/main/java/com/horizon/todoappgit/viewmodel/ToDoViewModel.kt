package com.horizon.todoappgit.viewmodel

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

    companion object {
        const val ORDER_BY_DEFAULT = 0

        //        const val ORDER_BY_DATE = 1
        const val ORDER_BY_COLOR = 1
        const val ORDER_BY_LETTER = 2
        const val ORDER_BY_RECENTLY = 3
    }

    init {
        loadThemeColor()
        loadCardsView()
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

            is ToDoEvents.SortedView ->  {
                _state.value = _state.value.copy(sorterView = event.value)
                dataPref.saveCardsView(event.value)
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

    suspend fun getNoteById(idDoc: Int): Boolean {
        val note = repo.getNoteById(idDoc).firstOrNull()

        return if (note != null) {
            _state.update {
                it.copy(
                    id = idDoc,
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

        _state.value = _state.value.copy(
            title = "", body = "", colorCard = 0
        )

    }

    fun deleteHomework() {

        val id = _state.value.id ?: return

        viewModelScope.launch {
            repo.deleteNote(id)
        }

        _state.value = _state.value.copy(
            title = "", body = "", colorCard = 0
        )
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

    fun sortedBy(orderBy: Int) {
        when (orderBy) {
            ORDER_BY_DEFAULT -> {
                orderByDefault()
            }
            
            ORDER_BY_COLOR -> {
                orderByColor()
            }

            ORDER_BY_LETTER -> {
                orderByLetter()
            }

            ORDER_BY_RECENTLY -> {
                orderByRecently()
            }

            else -> {
                orderByRecently()
            }
        }
    }

    private fun orderByDefault() {
        val id = _state.value.listHomeWork.sortedBy { it.id }
        _state.value = _state.value.copy(
            listHomeWork = id,
            orderBy = 0
        )
    }

    private fun orderByColor() {
        val color = _state.value.listHomeWork.sortedBy { it.color }
        _state.value = _state.value.copy(
            listHomeWork = color,
            orderBy = 1
        )
    }

    private fun orderByLetter() {
        val letter = _state.value.listHomeWork.sortedBy { it.title }

        _state.value = _state.value.copy(
            listHomeWork = letter,
            orderBy = 2
        )
    }

    private fun orderByRecently() {
        val id = _state.value.listHomeWork.sortedByDescending { it.id }
        _state.value = _state.value.copy(
            listHomeWork = id,
            orderBy = 3
        )
    }

    fun listSearchQuery(value: String): List<HomeworkState> {

        return if(value.isNotBlank()) {
            _state.value.listHomeWork.filter { note ->
                note.title.contains(value, ignoreCase = true) || note.body.contains(
                    value,
                    ignoreCase = true
                )
            }
        } else {
            _state.value.listHomeWork
        }

    }

    private fun loadCardsView() {

        val value = dataPref.getCardsView()

        _state.value = _state.value.copy(sorterView = value)

    }

}