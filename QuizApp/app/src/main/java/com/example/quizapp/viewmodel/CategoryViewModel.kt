package com.example.quizapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.quizapp.database.QuestionSetDatabase
import com.example.quizapp.model.Category
import com.example.quizapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository

    init {
        val categoryDao = QuestionSetDatabase.getDatabase(application).categoryDao()
        repository = CategoryRepository(categoryDao)
    }

    fun insertCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCategory(category)
        }
    }

    fun editCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.editCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCategory(category)
        }
    }

    fun getAllCategoriesFromQuestionSet(questionSetId: Int): LiveData<List<Category>> {
        return repository.getAllCategoriesFromQuestionSet(questionSetId)
    }

    fun deleteAllCategoriesFromQuestionSet(questionSetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCategoriesFromQuestionSet(questionSetId)
        }
    }

    fun deleteAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCategories()
        }
    }
}