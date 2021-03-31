package com.razzaghi.testcorona.db.quiz

import androidx.lifecycle.LiveData
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz
import javax.inject.Inject

class QuizRepository @Inject constructor(private val quizDao: QuizDao){

    suspend fun upsertQuiz(quiz: Quiz):Long{
        return quizDao.upsertQuiz(quiz)
    }

    fun getAllQuiz(): LiveData<List<Quiz>> {
        return quizDao.getAllQuiz()
    }
}