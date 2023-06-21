package com.educode.data.service.dto

import com.educode.data.database.Question
import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    val category: String,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)

fun QuestionDTO.toRoomQuestion(): Question = Question(
    category = category,
    question = question,
    answer1 = incorrectAnswers[0],
    answer2 = incorrectAnswers[1],
    answer3 = incorrectAnswers[2],
    answer4 = correctAnswer,
    correctAnswer = correctAnswer
)