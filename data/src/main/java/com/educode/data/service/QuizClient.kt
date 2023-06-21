package com.educode.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: QuizService = retrofit.create(QuizService::class.java)
}