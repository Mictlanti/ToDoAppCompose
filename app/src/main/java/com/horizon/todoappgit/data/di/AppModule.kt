package com.horizon.todoappgit.data.di

import android.content.Context
import androidx.room.Room
import com.horizon.todoappgit.data.local.dao.HomeworkDao
import com.horizon.todoappgit.data.HomeworkDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext appContext: Context
    ) : HomeworkDb {
        return Room.databaseBuilder(
            appContext,
            HomeworkDb::class.java,
            "homework_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(db: HomeworkDb) : HomeworkDao = db.homeworkDao()

}