package com.example.quizapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.quizapp.model.Category
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
public abstract class QuestionSetDatabase : RoomDatabase() {
    abstract fun questionSetDao() : QuestionSetDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionSetDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): QuestionSetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionSetDatabase::class.java,
                    "question_set_database"
                ).addCallback(QuestionSetDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class QuestionSetDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->
                scope.launch {
                    populateDatabase(database.questionSetDao())
                }
            }
        }

        suspend fun populateDatabase(questionSetDao: QuestionSetDao) {
            questionSetDao.deleteAllQuestionSets()

            var questionSet = QuestionSet(0, "Fruits and Vegetables")
            questionSetDao.insertQuestionSet(questionSet)
            questionSet = QuestionSet(1, "Family")
            questionSetDao.insertQuestionSet(questionSet)
        }
    }
}
