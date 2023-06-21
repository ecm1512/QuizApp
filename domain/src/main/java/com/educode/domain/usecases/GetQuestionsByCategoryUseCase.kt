package com.educode.domain.usecases

import com.educode.domain.models.Category
import com.educode.domain.models.Question
import com.educode.domain.repositories.QuestionRepository
import javax.inject.Inject

class GetQuestionsByCategoryUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {
    suspend operator fun invoke(category: Category): List<Question>{
        return questionRepository.getQuestions(category)
    }
}