package com.razzaghi.testcorona.view

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.razzaghi.testcorona.R
import com.razzaghi.testcorona.db.option.OptionViewModel
import com.razzaghi.testcorona.db.quiz.QuizViewModel
import com.razzaghi.testcorona.model.Option
import com.razzaghi.testcorona.model.Quiz
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}