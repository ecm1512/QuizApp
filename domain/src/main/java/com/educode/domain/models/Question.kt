package com.educode.domain.models

data class Question(
    val id: Int,
    val category: String,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String
)