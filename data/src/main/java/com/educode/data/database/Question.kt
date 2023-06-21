package com.educode.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val question: String,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val correctAnswer: String
)

fun Question.toDomainQuestion(): com.educode.domain.models.Question = com.educode.domain.models.Question(
    id,
    category,
    question,
    answer1,
    answer2,
    answer3,
    answer4,
    correctAnswer
)
