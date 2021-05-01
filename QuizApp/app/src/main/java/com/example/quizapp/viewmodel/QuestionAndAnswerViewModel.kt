package com.example.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.database.QuestionSetDatabase
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.repository.QuestionAndAnswerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionAndAnswerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QuestionAndAnswerRepository

    init {
        val questionAndAnswerDao = QuestionSetDatabase.getDatabase(application).questionAndAnswerDao()
        repository = QuestionAndAnswerRepository(questionAndAnswerDao)
    }

    fun insertQuestionAndAnswer(question: Question, answer: Answer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQuestionAndAnswer(question, answer)
        }
    }

    fun editQuestionAndAnswer(question: Question, answer: Answer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editQuestionAndAnswer(question, answer)
        }
    }

    fun deleteQuestionAndAnswer(question: Question, answer: Answer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestionAndAnswer(question, answer)
        }
    }

    fun getAllQuestionsAndAnswersFromCategory(categoryId: Int): LiveData<List<QuestionAndAnswer>> {
        return repository.getAllQuestionsAndAnswersFromCategory(categoryId)
    }

    fun deleteQuestionsAndAnswersFromCategory(categoryId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestionsAndAnswersFromCategory(categoryId)
        }
    }

    fun deleteQuestionsAndAnswersFromQuestionSet(questionSetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteQuestionsAndAnswersFromQuestionSet(questionSetId)
        }
    }

    fun deleteAllQuestionsAndAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllQuestionsAndAnswers()
        }
    }
}