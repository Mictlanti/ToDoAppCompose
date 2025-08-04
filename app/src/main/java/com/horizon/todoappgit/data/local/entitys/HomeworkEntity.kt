package com.horizon.todoappgit.data.local.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homework_table")
data class HomeworkEntity(
    @PrimaryKey(autoGenerate = true) val idDoc: Int = 0,
    val title: String,
    val body: String,
    val color : Int
)
