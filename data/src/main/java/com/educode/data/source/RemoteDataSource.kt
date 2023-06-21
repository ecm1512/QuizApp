package com.educode.data.source

import com.educode.data.service.dto.BaseResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getQuestions(category: Int): Response<BaseResponse>
}