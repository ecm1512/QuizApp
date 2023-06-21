package com.educode.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Question::class], version = 1)
abstract class QuizDatabase : RoomDatabase(){
    companion object{
        fun build(context: Context) = Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            "QuizDb"
        ).build()
    }

    abstract fun questionDao(): QuestionDao
}