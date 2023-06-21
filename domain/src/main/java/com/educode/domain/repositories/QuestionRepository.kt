package com.educode.domain.repositories

import com.educode.domain.models.Category
import com.educode.domain.models.Question

interface QuestionRepository {
    suspend fun getQuestions(category: Category): List<Question>
}