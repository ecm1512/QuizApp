package com.educode.data.service

import com.educode.data.service.dto.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount")
        page: Int = 10,
        @Query("category")
        category: Int
    ): Response<BaseResponse>
}