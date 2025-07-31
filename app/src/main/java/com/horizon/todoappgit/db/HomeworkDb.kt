package com.horizon.todoappgit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horizon.todoappgit.dao.HomeworkDao
import com.horizon.todoappgit.entitys.HomeworkEntity

@Database(entities = [HomeworkEntity::class], version = 4)
abstract class HomeworkDb : RoomDatabase() {

    abstract fun homeworkDao() : HomeworkDao

}