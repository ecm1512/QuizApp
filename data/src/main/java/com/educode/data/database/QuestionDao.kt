package com.educode.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {
    @Query("SELECT COUNT(*) FROM Question WHERE category = :category")
    fun questionCount(category: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(question: List<Question>)

    @Query("SELECT * FROM Question WHERE category= :category")
    fun getQuestions(category: String): List<Question>
}