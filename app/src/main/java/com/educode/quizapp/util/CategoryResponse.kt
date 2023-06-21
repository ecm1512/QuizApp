package com.educode.quizapp.util

sealed class CategoryResponse(){
    class Success(val categories: List<Category>): CategoryResponse()
    class Failure(val error: String): CategoryResponse()
    object Loading: CategoryResponse()
}
