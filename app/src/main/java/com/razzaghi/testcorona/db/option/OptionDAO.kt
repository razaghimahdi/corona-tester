package com.razzaghi.testcorona.db.option

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.razzaghi.testcorona.model.Option

@Dao
interface  OptionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertOption(option:Option): Long


    @Query("SELECT * FROM `option` WHERE quizId=:quizId  ORDER BY score+0 ASC")
    fun getAllOption(quizId:Long): LiveData<List<Option>>

}