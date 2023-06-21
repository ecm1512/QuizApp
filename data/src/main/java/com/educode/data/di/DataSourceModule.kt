package com.educode.data.di

import com.educode.data.database.QuizDatabase
import com.educode.data.database.QuizDbDataSource
import com.educode.data.service.QuizService
import com.educode.data.service.QuizServiceDataSource
import com.educode.data.source.LocalDataSource
import com.educode.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun bindRoomDataSource(db: QuizDatabase): LocalDataSource {
        return QuizDbDataSource(db)
    }


    @Provides
    fun bindRemoteDataSource(service: QuizService): RemoteDataSource {
        return QuizServiceDataSource(service)
    }
}