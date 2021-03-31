package com.razzaghi.testcorona.db.option

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razzaghi.testcorona.model.Option
import kotlinx.coroutines.launch

class OptionViewModel @ViewModelInject constructor(private val optionRepository: OptionRepository) : ViewModel() {

    val TAG="OptionViewModel"



    fun upsertOption(option: Option) = viewModelScope.launch {
        val id=optionRepository.upsertOption(option)
        Log.i(TAG, "upsertImage id: $id")
    }

    fun getAllOption(quizId:Long): LiveData<List<Option>> {
        return optionRepository.getAllOption(quizId)
    }

}