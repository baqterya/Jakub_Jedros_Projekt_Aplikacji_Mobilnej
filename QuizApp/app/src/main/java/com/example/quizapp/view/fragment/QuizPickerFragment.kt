package com.example.quizapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizPickerBinding
import com.example.quizapp.model.relations.QuestionAndAnswer
import com.example.quizapp.viewmodel.QuestionAndAnswerViewModel


class QuizPickerFragment : Fragment() {
    lateinit var binding: FragmentQuizPickerBinding
    lateinit var mQuestionAndAnswerViewModel: QuestionAndAnswerViewModel

    lateinit var listQuestionAndAnswer: Array<QuestionAndAnswer>
    private val args by navArgs<QuizPickerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizPickerBinding.inflate(inflater, container, false)

        mQuestionAndAnswerViewModel = ViewModelProvider(this).get(QuestionAndAnswerViewModel::class.java)

        if (args.categoryId == -1) {
            mQuestionAndAnswerViewModel.getAllQuestionsAndAnswersFromQuestionSet(args.questionSetId)

            mQuestionAndAnswerViewModel.questionAndAnswerFromQuestionSet.observe(viewLifecycleOwner, { list ->
                listQuestionAndAnswer = list.toTypedArray()
                checkAmount(listQuestionAndAnswer)
            })
        }
        else {
            mQuestionAndAnswerViewModel.setCategory(args.categoryId)

            mQuestionAndAnswerViewModel.questionAndAnswerFromCategory.observe(viewLifecycleOwner, { list ->
                listQuestionAndAnswer = list.toTypedArray()
                checkAmount(listQuestionAndAnswer)
            })
        }

        binding.quizPickerStartSimple.setOnClickListener {
            val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.dialog_quiz_question_amount)

            val numberPicker = dialog.findViewById<NumberPicker>(R.id.dialogSimpleQuizQuestionsNumberPicker)
            numberPicker.minValue = 6
            numberPicker.maxValue = 20
            var questionAmount = 6

            numberPicker.setOnValueChangedListener { _, _, newVal ->
                questionAmount = newVal
            }

            dialog.findViewById<Button>(R.id.dialogSimpleQuizQuestionsButton).setOnClickListener {
                val action = QuizPickerFragmentDirections.actionQuizPickerFragmentToQuizSimpleFragment(listQuestionAndAnswer, questionAmount)
                findNavController().navigate(action)
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.quizPickerStartTime.setOnClickListener {
            val action = QuizPickerFragmentDirections.actionQuizPickerFragmentToQuizTimeFragment(listQuestionAndAnswer)
            findNavController().navigate(action)
        }

        binding.quizPickerStartWriting.setOnClickListener {
            val dialog = MaterialDialog(requireContext())
                .noAutoDismiss()
                .customView(R.layout.dialog_quiz_question_amount)

            val numberPicker = dialog.findViewById<NumberPicker>(R.id.dialogSimpleQuizQuestionsNumberPicker)
            numberPicker.minValue = 6
            numberPicker.maxValue = 20
            var questionAmount = 6

            numberPicker.setOnValueChangedListener { _, _, newVal ->
                questionAmount = newVal
            }

            dialog.findViewById<Button>(R.id.dialogSimpleQuizQuestionsButton).setOnClickListener {
                val action = QuizPickerFragmentDirections.actionQuizPickerFragmentToQuizWritingFragment(listQuestionAndAnswer, questionAmount)
                findNavController().navigate(action)
                dialog.dismiss()
            }
            dialog.show()
        }

        return binding.root
    }

    private fun checkAmount(listQuestionsAndAnswer: Array<QuestionAndAnswer>) {
        if (listQuestionsAndAnswer.size < 5) {
            binding.quizSimpleToFewTextView.visibility = View.VISIBLE
            binding.quizPickerTextView1.visibility = View.INVISIBLE
            binding.quizPickerStartTime.visibility = View.INVISIBLE
            binding.quizPickerStartSimple.visibility = View.INVISIBLE
            binding.quizPickerStartWriting.visibility = View.INVISIBLE
        }
    }

}