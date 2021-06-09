package com.example.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.quizapp.database.QuestionSetDatabase
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.repository.QuestionSetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionSetViewModel(application: Application) : AndroidViewModel(application) {
    val allQuestionSets: LiveData<List<QuestionSet>>
    private val repository: QuestionSetRepository

    init {
        val questionSetDao = QuestionSetDatabase.getDatabase(application).questionSetDao()
        repository = QuestionSetRepository(questionSetDao)
        allQuestionSets = repository.allQuestionSets
    }

    fun insertQuestionSet(questionSet: QuestionSet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQuestionSet(questionSet)
        }
    }

    fun editQuestionSet(questionSet: QuestionSet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editQuestionSet(questionSet)
        }
    }

    fun deleteQuestionSet(questionSet: QuestionSet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestionSet(questionSet)
        }
    }

    fun deleteAllQuestionSets() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllQuestionSets()
        }
    }
}