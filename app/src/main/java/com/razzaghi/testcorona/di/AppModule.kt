package com.razzaghi.testcorona.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.razzaghi.testcorona.db.AppDatabase
import com.razzaghi.testcorona.other.Constants.DATABASE_NAME
import com.razzaghi.testcorona.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.razzaghi.testcorona.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideCoronaQuizDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).createFromAsset("QuizCoronaDB.db").build()



    @Singleton
    @Provides
    fun provideOptionDAO(db: AppDatabase) = db.optionDAO

    @Singleton
    @Provides
    fun provideQuizDao(db: AppDatabase) = db.quizDao


    @Singleton
    @Provides
    fun provideSharedPrefernces(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)



}