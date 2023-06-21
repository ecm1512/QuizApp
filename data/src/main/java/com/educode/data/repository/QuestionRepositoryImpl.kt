package com.educode.data.repository

import com.educode.data.database.toDomainQuestion
import com.educode.data.service.dto.toRoomQuestion
import com.educode.data.source.LocalDataSource
import com.educode.data.source.RemoteDataSource
import com.educode.domain.models.Category
import com.educode.domain.models.Question
import com.educode.domain.repositories.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): QuestionRepository {
    override suspend fun getQuestions(category: Category): List<Question> {
        if(localDataSource.isEmpty(category.name)){
            val questions = remoteDataSource.getQuestions(category.id)
            localDataSource.insertQuestions(questions.body()?.results?.map {
                it.toRoomQuestion()
            }!!)
        }
        return localDataSource.getQuestions(category.name).map {
            it.toDomainQuestion()
        }
    }
}