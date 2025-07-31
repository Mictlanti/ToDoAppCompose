package com.horizon.todoappgit.repository

import com.horizon.todoappgit.dao.HomeworkDao
import com.horizon.todoappgit.entitys.HomeworkEntity
import javax.inject.Inject

class HomeworkRepo @Inject constructor(
    private val dao : HomeworkDao
) {

    fun getAllNotes() = dao.getAllHomework()

    suspend fun insertNote(homeworkEntity: HomeworkEntity) : Long {
        return dao.insertHomework(homeworkEntity)
    }

    fun getNoteById(idDoc: Int) = dao.getHomeWorkById(idDoc)

    suspend fun deleteNote(id: Int) = dao.deletedHomework(id)

}