package com.example.quizapp.repository

import androidx.annotation.WorkerThread
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.model.QuestionSetDao
import kotlinx.coroutines.flow.Flow

class Repository(private val questionSetDao: QuestionSetDao) {

    val allQuestionSets: Flow<List<QuestionSet>> = questionSetDao.getAllQuestionSets()

    @WorkerThread
    suspend fun insertQuestionSet(questionSet: QuestionSet) {
        questionSetDao.insertQuestionSet(questionSet)
    }
}