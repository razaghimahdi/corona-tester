package com.razzaghi.testcorona.db.option

import androidx.lifecycle.LiveData
import com.razzaghi.testcorona.model.Option
import javax.inject.Inject

class OptionRepository @Inject constructor(private val optionDAO: OptionDAO){


    suspend fun upsertOption(option: Option):Long{
        return optionDAO.upsertOption(option)
    }

    fun getAllOption(quizId:Long): LiveData<List<Option>> {
        return optionDAO.getAllOption(quizId)
    }


}