package com.razzaghi.testcorona.db.quiz

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz
import com.razzaghi.testcorona.other.Event
import kotlinx.coroutines.launch

class QuizViewModel @ViewModelInject constructor(private val quizRepository: QuizRepository) :
    ViewModel() {

    val TAG = "QuizViewModel"


    private val newQuizIdMutable = MutableLiveData<Event<Long>>()

    val newQuizId: LiveData<Event<Long>>
        get() = newQuizIdMutable


    fun upsertQuiz(quiz: Quiz) = viewModelScope.launch {
        val newId = quizRepository.upsertQuiz(quiz)
        newQuizIdMutable.value = Event(newId)
        Log.i(TAG, "upsertQuiz id: $newId")
    }

    fun getAllQuiz(): LiveData<List<Quiz>> {
        return quizRepository.getAllQuiz()
    }
}