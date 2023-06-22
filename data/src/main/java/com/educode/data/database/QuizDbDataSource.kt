package com.educode.data.database

import android.util.Log
import com.educode.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizDbDataSource @Inject constructor(db: QuizDatabase): LocalDataSource {

    private val questionDao = db.questionDao()

    override suspend fun isEmpty(category: String): Boolean {
        return withContext(Dispatchers.IO){
            questionDao.questionCount(category) <= 0
        }
    }

    override suspend fun insertQuestions(question: List<Question>){
        return withContext(Dispatchers.IO){
            questionDao.insertQuestions(question)
        }
    }

    override suspend fun getQuestions(category: String): List<Question> {
        return withContext(Dispatchers.IO){
            questionDao.getQuestions(category)
        }
    }
}