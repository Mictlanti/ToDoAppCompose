package com.horizon.todoappgit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.horizon.todoappgit.data.local.entitys.HomeworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeworkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomework(homework: HomeworkEntity): Long //This Long returns de id autogenerate from the doc

    @Query("SELECT * FROM homework_table")
    fun getAllHomework(): Flow<List<HomeworkEntity>>

    @Query("SELECT * FROM homework_table WHERE idDoc = :idDoc")
    fun getHomeWorkById(idDoc: Int) : Flow<HomeworkEntity?>

    @Query("DELETE FROM homework_table WHERE idDoc = :id")
    suspend fun deletedHomework(id: Int)

}