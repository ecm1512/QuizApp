package com.educode.data.service

import com.educode.data.service.dto.BaseResponse
import com.educode.data.source.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class QuizServiceDataSource @Inject constructor(private val service: QuizService): RemoteDataSource {
    override suspend fun getQuestions(category: Int): Response<BaseResponse> {
        return service.getQuestions(
            category = category
        )
    }
}