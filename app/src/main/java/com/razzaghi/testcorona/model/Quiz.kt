package com.razzaghi.testcorona.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "quiz"
)
data class Quiz(

    @ColumnInfo(name = "quiz_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "question")
    var question: String,

    @ColumnInfo(name = "factor")
    var factor: String,

)

