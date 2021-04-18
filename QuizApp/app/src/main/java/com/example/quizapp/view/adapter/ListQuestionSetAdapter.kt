package com.example.quizapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.model.QuestionSet
import com.example.quizapp.view.fragment.ListQuestionSetFragmentDirections

class ListQuestionSetAdapter : RecyclerView.Adapter<ListQuestionSetAdapter.QuestionSetViewHolder>() {

    private var questionSetList = emptyList<QuestionSet>()

    class QuestionSetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionSetViewHolder {
        return QuestionSetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_question_set, parent, false))
    }

    override fun onBindViewHolder(holder: QuestionSetViewHolder, position: Int) {
        val currentItem = questionSetList[position]
        holder.itemView.findViewById<TextView>(R.id.questionSetItemTextView).text = currentItem.questionSetName
        // TODO change to onLongPressListener later
        holder.itemView.findViewById<CardView>(R.id.questionSetItemCardView).setOnClickListener {
            val action = ListQuestionSetFragmentDirections.actionListQuestionSetFragmentToEditQuestionSetFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return questionSetList.size
    }

    fun setData(questionSets: List<QuestionSet>) {
        this.questionSetList = questionSets
    }
}