package com.example.quizapp.viewmodel

import androidx.lifecycle.*
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.repository.Repository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class QuestionSetViewModel(private val repository: Repository) : ViewModel() {
    val allQuestionSets: LiveData<List<QuestionSet>> =  repository.allQuestionSets.asLiveData()

    fun insert(questionSet: QuestionSet) = viewModelScope.launch {
        repository.insertQuestionSet(questionSet)
    }
}

class QuestionSetViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionSetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionSetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}