package com.horizon.todoappgit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horizon.todoappgit.data.local.dao.HomeworkDao
import com.horizon.todoappgit.data.local.entitys.HomeworkEntity

@Database(entities = [HomeworkEntity::class], version = 4)
abstract class HomeworkDb : RoomDatabase() {

    abstract fun homeworkDao() : HomeworkDao

}