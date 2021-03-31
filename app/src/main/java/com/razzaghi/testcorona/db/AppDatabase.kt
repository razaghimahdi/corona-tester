package com.razzaghi.testcorona.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.razzaghi.testcorona.db.option.OptionDAO
import com.razzaghi.testcorona.db.quiz.QuizDao
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz

@Database(entities = [ Option::class, Quiz::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val optionDAO: OptionDAO
    abstract val quizDao: QuizDao

}