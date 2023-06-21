package com.educode.quizapp.util

import com.educode.domain.models.Question

sealed class QuestionResponse() {
    class Success(val questions: List<Question>): QuestionResponse()
    class Failure(val error: String): QuestionResponse()
    object Loading: QuestionResponse()
}