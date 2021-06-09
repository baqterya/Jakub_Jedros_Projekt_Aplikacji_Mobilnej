package com.example.quizapp.view.adapter

import android.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel

class ListQuestionAndAnswerAdapter(
    private var data: LiveData<List<QuestionAndAnswer>>,
    private var questionAndAnswerViewModel: QuestionAndAnswerViewModel
    ): RecyclerView.Adapter<ListQuestionAndAnswerAdapter.Holder>() {

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
            holder.itemView.findViewById<TextView>(R.id.answerItemTextView).text = currentItem.answer.answerText
        }

        holder.itemView.findViewById<CardView>(R.id.questionAndAnswerItemCardView).setOnLongClickListener {
            if (currentItem != null) {
                editQuestionAndAnswerDialog(holder, currentItem)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }

    private fun editQuestionAndAnswerDialog(holder: Holder, questionAndAnswer: QuestionAndAnswer) {
        val context = holder.itemView.context

        val dialog = MaterialDialog(context)
            .noAutoDismiss()
            .customView(R.layout.dialog_edit_question_and_answer)
        dialog.findViewById<EditText>(R.id.dialogEditQuestionEditText).setText(questionAndAnswer.question.questionText)
        dialog.findViewById<EditText>(R.id.dialogEditAnswerEditText).setText(questionAndAnswer.answer.answerText)

        dialog.findViewById<Button>(R.id.dialogEditQuestionAndAnswerButton).setOnClickListener {
            val questionText = dialog.findViewById<EditText>(R.id.dialogEditQuestionEditText).text.toString()
            val answerText = dialog.findViewById<EditText>(R.id.dialogEditAnswerEditText).text.toString()
            if (inputCheck(questionText, answerText)) {
                val editedQuestion = Question(
                    questionAndAnswer.question.questionId,
                    questionText,
                    questionAndAnswer.question.parentCategoryId,
                    questionAndAnswer.question.parentQuestionSetId
                )
                val editedAnswer = Answer(
                    questionAndAnswer.answer.answerId,
                    answerText,
                    questionText,
                    questionAndAnswer.answer.parentCategoryId,
                    questionAndAnswer.answer.parentQuestionSetId
                )
                questionAndAnswerViewModel.editQuestionAndAnswer(QuestionAndAnswer(editedQuestion, editedAnswer))
                Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter the text", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<ImageButton>(R.id.dialogEditQuestionAndAnswerDeleteButton).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setPositiveButton("Yes") { _, _ ->
                questionAndAnswerViewModel.deleteQuestionAndAnswer(
                    questionAndAnswer.question,
                    questionAndAnswer.answer
                )
                Toast.makeText(context, "Successfully removed ${questionAndAnswer.question.questionText}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") {_, _ ->}
            builder.setTitle("Delete ${questionAndAnswer.question.questionText}?")
            builder.setMessage("Are you sure you want to delete?")
            builder.create().show()
        }

        dialog.show()
    }

    private fun inputCheck(name1: String, name2: String): Boolean {
        return !(TextUtils.isEmpty(name1) && TextUtils.isEmpty(name2))
    }

}