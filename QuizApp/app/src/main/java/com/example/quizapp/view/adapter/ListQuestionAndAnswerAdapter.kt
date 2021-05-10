package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.view.fragment.ListQuestionAndAnswerFragmentDirections

class ListQuestionAndAnswerAdapter(private var data: LiveData<List<QuestionAndAnswer>>): RecyclerView.Adapter<ListQuestionAndAnswerAdapter.Holder>() {

    class Holder(view: View, data: LiveData<List<QuestionAndAnswer>>): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_question_and_answer,
            parent,
            false
        ) as View

        return Holder(view, data)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = data.value?.get(position)
        if (currentItem != null) {
            holder.itemView.findViewById<TextView>(R.id.questionItemTextView).text = currentItem.question.questionText
        }
        if (currentItem != null) {
            holder.itemView.findViewById<TextView>(R.id.answerItemTextView).text = currentItem.answer.answerText
        }

        holder.itemView.findViewById<ImageButton>(R.id.editQuestionAndAnswerStartButton).setOnClickListener {
            val action = currentItem?.let { it1 -> ListQuestionAndAnswerFragmentDirections
                .actionListQuestionAndAnswerFragmentToEditQuestionAndAnswerFragment(it1) }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }

}