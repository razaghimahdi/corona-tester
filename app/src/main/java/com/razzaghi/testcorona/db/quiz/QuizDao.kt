package com.razzaghi.testcorona.db.quiz

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz

@Dao
interface QuizDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuiz(quiz: Quiz): Long

    @Query("SELECT * FROM `quiz` ORDER BY factor+0 ASC")
    fun getAllQuiz(): LiveData<List<Quiz>>

}