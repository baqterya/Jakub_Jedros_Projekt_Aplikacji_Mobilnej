package com.example.quizapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quizapp.model.Category
import com.example.quizapp.model.CategoryDao
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.model.QuestionSetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        QuestionSet::class,
        Category::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuestionSetDatabase : RoomDatabase() {
    abstract fun questionSetDao(): QuestionSetDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionSetDatabase? = null

        fun getDatabase(context: Context): QuestionSetDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                         context.applicationContext,
                        QuestionSetDatabase::class.java,
                        "question_set_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
