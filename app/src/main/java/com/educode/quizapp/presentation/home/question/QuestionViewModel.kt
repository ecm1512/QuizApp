package com.educode.quizapp.presentation.home.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educode.domain.models.Category
import com.educode.domain.usecases.GetQuestionsByCategoryUseCase
import com.educode.quizapp.util.QuestionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val getQuestionsByCategoryUseCase: GetQuestionsByCategoryUseCase
): ViewModel() {
    private val _questionUIState: MutableStateFlow<QuestionResponse> = MutableStateFlow(QuestionResponse.Loading)
    val questionUIState: StateFlow<QuestionResponse> get() = _questionUIState

    fun loadQuestions(category: Category){
        viewModelScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    getQuestionsByCategoryUseCase(category)
                }
                _questionUIState.emit(QuestionResponse.Success(response))
            }catch (e: Exception){
                _questionUIState.emit(QuestionResponse.Failure(e.message.toString()))
            }
        }
    }
}