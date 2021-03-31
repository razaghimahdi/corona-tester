package com.razzaghi.testcorona.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "option",
    foreignKeys = [
        ForeignKey(
            entity = Quiz::class,
            parentColumns = arrayOf("quiz_id"),
            childColumns = arrayOf("quizId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class Option(

    @ColumnInfo(name = "option_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "option")
    var option: String = "",

    @ColumnInfo(name = "answer")
    var answer: String = "",

    @ColumnInfo(name = "score")
    var score: String = "",

    @ColumnInfo(name = "quizId")
    var quizId: Long ,
)
