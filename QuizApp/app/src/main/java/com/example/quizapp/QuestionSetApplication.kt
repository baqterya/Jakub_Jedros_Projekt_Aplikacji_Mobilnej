package com.example.quizapp

import android.app.Application
import com.example.quizapp.database.QuestionSetDatabase
import com.example.quizapp.repository.Repository
import kotlinx.coroutines.*

class QuestionSetApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { QuestionSetDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.questionSetDao()) }
}