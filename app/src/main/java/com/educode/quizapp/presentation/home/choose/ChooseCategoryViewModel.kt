package com.educode.quizapp.presentation.home.choose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educode.quizapp.util.CategoryResponse
import com.educode.quizapp.util.ListCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChooseCategoryViewModel @Inject() constructor() : ViewModel()  {
    private val _chooseCategoryUIState: MutableStateFlow<CategoryResponse> = MutableStateFlow(CategoryResponse.Loading)
    val chooseCategoryUIState: StateFlow<CategoryResponse> get() = _chooseCategoryUIState

    fun loadCategories(){
        viewModelScope.launch {
            try{
                val response = withContext(Dispatchers.IO){
                    ListCategories.categories
                }
                _chooseCategoryUIState.emit(CategoryResponse.Success(response))
            }catch (e: Exception){
                _chooseCategoryUIState.emit(CategoryResponse.Failure(e.message.toString()))
            }
        }
    }
}