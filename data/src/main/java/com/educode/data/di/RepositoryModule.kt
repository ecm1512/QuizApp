package com.educode.data.di

import com.educode.data.repository.QuestionRepositoryImpl
import com.educode.data.source.LocalDataSource
import com.educode.data.source.RemoteDataSource
import com.educode.domain.repositories.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun bindQuestionRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): QuestionRepository{
        return QuestionRepositoryImpl(remoteDataSource,localDataSource)
    }
}