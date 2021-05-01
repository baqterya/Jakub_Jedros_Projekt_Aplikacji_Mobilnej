package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.relations.QuestionAndAnswer

class ListQuestionAndAnswerAdapter : RecyclerView.Adapter<ListQuestionAndAnswerAdapter.QuestionAndAnswerViewHolder>() {
    private var questionAndAnswerList = emptyList<QuestionAndAnswer>()

    class QuestionAndAnswerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAndAnswerViewHolder {
        return QuestionAndAnswerViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_question_and_answer,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: QuestionAndAnswerViewHolder, position: Int) {
        val currentItem = questionAndAnswerList[position]
        holder.itemView.findViewById<TextView>(R.id.questionItemTextView).text = currentItem.question.questionText
        holder.itemView.findViewById<TextView>(R.id.answerItemTextView).text = currentItem.answer.answerText
    }

    override fun getItemCount(): Int {
        return questionAndAnswerList.size
    }

    fun setData(questionsAndAnswers: List<QuestionAndAnswer>) {
        this.questionAndAnswerList = questionsAndAnswers
    }
}