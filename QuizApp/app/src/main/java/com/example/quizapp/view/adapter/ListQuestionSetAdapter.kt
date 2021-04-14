package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.QuestionSet

class ListQuestionSetAdapter : ListAdapter<QuestionSet, ListQuestionSetAdapter.QuestionSetViewHolder>(QuestionSetComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionSetViewHolder {
        return QuestionSetViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: QuestionSetViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.questionSetName)
    }

    class  QuestionSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionSetItemView: TextView = itemView.findViewById(R.id.question_set_item_textview)

        fun bind(text: String) {
            questionSetItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): QuestionSetViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_question_set, parent, false)
                return QuestionSetViewHolder(view)
            }
        }
    }

    class QuestionSetComparator : DiffUtil.ItemCallback<QuestionSet>() {
        override fun areItemsTheSame(oldItem: QuestionSet, newItem: QuestionSet): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: QuestionSet, newItem: QuestionSet): Boolean {
            return oldItem.questionSetId == newItem.questionSetId
                    && oldItem.questionSetName == newItem.questionSetName
        }
    }
}