package com.educode.data.source

import com.educode.data.database.Question
import com.educode.data.service.dto.BaseResponse
import retrofit2.Response

interface LocalDataSource {

    suspend fun isEmpty(category: String): Boolean
    suspend fun insertQuestions(question: List<Question>)
    suspend fun getQuestions(category: String): List<Question>
}