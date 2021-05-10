package com.example.quizapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentEditQuestionAndAnswerBinding
import com.example.quizapp.model.Answer
import com.example.quizapp.model.Question
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel


class EditQuestionAndAnswerFragment : Fragment() {
    private lateinit var binding: FragmentEditQuestionAndAnswerBinding
    private lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    private val args by navArgs<EditQuestionAndAnswerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditQuestionAndAnswerBinding.inflate(inflater, container, false)
        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        binding.editQuestionEditText.setText(args.currentQuestionAndAnswer.question.questionText)
        binding.editAnswerEditText.setText(args.currentQuestionAndAnswer.answer.answerText)

        binding.editQuestionAndAnswerButton.setOnClickListener {
            editQuestionAndAnswer()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete) {
            deleteQuestionAndAnswer()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editQuestionAndAnswer() {
        val questionText = binding.editQuestionEditText.text.toString()
        val answerText = binding.editAnswerEditText.text.toString()
        if (inputCheck(questionText, answerText)) {
            val editedQuestion = Question(
                args.currentQuestionAndAnswer.question.questionId,
                questionText,
                args.currentQuestionAndAnswer.question.parentCategoryId,
                args.currentQuestionAndAnswer.question.parentQuestionSetId
            )
            val editedAnswer = Answer(
                args.currentQuestionAndAnswer.answer.answerId,
                answerText,
                args.currentQuestionAndAnswer.answer.parentQuestionText,
                args.currentQuestionAndAnswer.answer.parentCategoryId,
                args.currentQuestionAndAnswer.answer.parentQuestionSetId
            )
            mQuestionAndAnswerViewModel.editQuestionAndAnswer(QuestionAndAnswer(editedQuestion, editedAnswer))
            Toast.makeText(requireContext(), "Update successful", Toast.LENGTH_SHORT).show()
            val action = EditQuestionAndAnswerFragmentDirections
                .actionEditQuestionAndAnswerFragmentToListQuestionAndAnswerFragment(
                    args.currentQuestionAndAnswer.question.parentCategoryId,
                    args.currentQuestionAndAnswer.question.parentQuestionSetId
                )
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please enter the text", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteQuestionAndAnswer() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mQuestionAndAnswerViewModel.deleteQuestionAndAnswer(
                args.currentQuestionAndAnswer.question,
                args.currentQuestionAndAnswer.answer
            )
            Toast.makeText(requireContext(), "Successfully removed ${args.currentQuestionAndAnswer.question.questionText}", Toast.LENGTH_SHORT).show()
            val action = EditQuestionAndAnswerFragmentDirections
                .actionEditQuestionAndAnswerFragmentToListQuestionAndAnswerFragment(
                    args.currentQuestionAndAnswer.question.parentCategoryId,
                    args.currentQuestionAndAnswer.question.parentQuestionSetId
                )
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No") {_, _ ->}
        builder.setTitle("Delete ${args.currentQuestionAndAnswer.question.questionText}?")
        builder.setMessage("Are you sure you want to delete?")
        builder.create().show()
    }

    private fun inputCheck(name1: String, name2: String): Boolean {
        return !(TextUtils.isEmpty(name1) && TextUtils.isEmpty(name2))
    }
}