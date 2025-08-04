package com.horizon.todoappgit.data.di

import android.content.Context
import android.content.SharedPreferences
import com.horizon.todoappgit.ui.constans.PREFS_USER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun providesSharePref(@ApplicationContext appContext: Context) : SharedPreferences {
        return appContext.getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesSharedPrefsEdit(sharedPreferences: SharedPreferences) : SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}