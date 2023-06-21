package com.educode.data.di

import android.content.Context
import com.educode.data.database.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(@ApplicationContext appContext: Context): QuizDatabase {
        return QuizDatabase.build(appContext)
    }
}